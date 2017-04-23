import java.util.Random;

public class Monitor {
	
	private ConditionVariable ok_to_enter = new ConditionVariable();
	private ConditionVariable seatOne = new ConditionVariable();
	private ConditionVariable seatTwo = new ConditionVariable();
	private ConditionVariable seatThree = new ConditionVariable();
	private ConditionVariable seatFour = new ConditionVariable();
	private ConditionVariable waitingForResult = new ConditionVariable();
	private boolean isGrading;
	private final int CAPACITY = 12;
	private int numberOfStudents;
	private ConditionVariable takingExam = new ConditionVariable();
	private int currentlyBlocked[]= new int[17];
	public static int gradeSheet[][] = new int[5][17];
	private static int participants[];

	public Monitor(){
		isGrading = false;
		numberOfStudents = 0;
		for(int i =0; i<5;i++){
			for(int j=0; j<17;j++){
				gradeSheet[i][j] = 0;
			}
		}
		participants = new int[17];
		for(int i =0; i<participants.length;i++){
			participants[i]=0;
		}
	}
	
	public void waitForProfessor(int id){
		try{
			Random rand = new Random();
			int generator = rand.nextInt(100)+1;
			Thread.sleep(generator);
			if(Professor.testGiven == 0)
			System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"I am student "+ id+" and I am close to school");
			else{
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"I am student "+ id+" and I am close to exam hall");
			}
			Integer d = id;
			//Thread.sleep(generator);
			synchronized(d){
			System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Student "+id+" has now arrived in school and waiting for professor");
			d.notify();
			ok_to_enter.block();
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void professorArrived(int id){
		try{
			Integer i = id;
			Thread.sleep(700);
			System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Professor has now arrived to class");
			if(ok_to_enter.getNumberOfBlockeditems()>0){
				ok_to_enter.freeAll();
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Professor waiting for students to take seat");
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void haveASeat(int id){
		try{
			//Integer num = numberOfStudents;
			Integer i = id;
			synchronized(i){
				if(numberOfStudents < CAPACITY){
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"This is student "+ id+ " now entering the class");
				numberOfStudents++;
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Current student body = "+ numberOfStudents);
				Thread.sleep(200);
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Current student in class : "+ numberOfStudents);
					if(seatOne.getNumberOfBlockeditems()<3){
						System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"This is student "+ id+" taking a seat in bench 1");
						i.notify();
						seatOne.block();
					}
					else if(seatTwo.getNumberOfBlockeditems()<3){
						System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"This is student "+ id+" taking a seat in bench 2");
						i.notify();
						seatTwo.block();
						Thread.sleep(40);
					}
					else if(seatThree.getNumberOfBlockeditems()<3){
						System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"This is student "+ id+" taking a seat in bench 3");
						i.notify();
						seatThree.block();
						Thread.sleep(40);
					}
					else if(seatFour.getNumberOfBlockeditems()<3){
						System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"This is student "+ id+" taking a seat in bench 4");
						i.notify();
						seatFour.block();
						Thread.sleep(40);
					}
				
				}//end of if (i)
				else{
					System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"this is student "+ id+ " class is now full I have to wait");
					ok_to_enter.block();
					currentlyBlocked[id]= 1;
					i.notify();
				}//end of else
			}//end of synchronize
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void beginExam(){
		System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Professor will now hand in the exam paper to seat 1");
		if(seatOne.getNumberOfBlockeditems() > 0){
			try{
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"handing in the papers to seat one....");
				Thread.sleep(100);
				seatOne.freeAll();
			}
			catch(Exception e){
				
			}
		}
		if(seatTwo.getNumberOfBlockeditems() > 0){
			try{
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"handing in the papers to seat Two....");
				Thread.sleep(100);
				seatTwo.freeAll();
			}
			catch(Exception e){
				
			}
		}
		if(seatThree.getNumberOfBlockeditems() > 0){
			try{
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"handing in the papers to seat Three....");
				Thread.sleep(100);
				seatThree.freeAll();
			}
			catch(Exception e){
				
			}
		}
		if(seatFour.getNumberOfBlockeditems() > 0){
			try{
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"handing in the papers to seat four....");
				Thread.sleep(100);
				seatFour.freeAll();
				//System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Please wait until exam is finished(this will take some time)");
				Thread.sleep(3000);
			}
			catch(Exception e){
				
			}
		}
	}//end of begin exam()
	
	public void takeExam(int id){
		Integer i = id;
		synchronized(i){
			if(currentlyBlocked[id]!= 1){
			System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Student "+ id+" is now taking his/her exam....");
			i.notify();
			try {
				takingExam.block();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		}
	}
	
	public void collectExam(){
		System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Professor will now collect the exam papers....");
		if(takingExam.getNumberOfBlockeditems() > 0){
			takingExam.freeAll();
		}
		Professor.testGiven++;
	}
	
	public void waitForGrades(int i,Student s){
		Object tag = i;
		synchronized(tag){
			if(currentlyBlocked[i]!=1){
			participants[i]=1;
			System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Student "+ i +" is now handing his/her paper to the professor....");
			tag.notify();
			try{
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Student "+ i+" is now waiting for his/her grade....");
				waitingForResult.block();
			}
			catch(Exception e){
				e.printStackTrace();
				}
			}
		}
	}//end of waitForGrades
	
	public void giveGrades(){
		if(waitingForResult.getNumberOfBlockeditems() >0){
			for(int i=0; i<participants.length;i++){
				if(participants[i]!=0){
					Random rand = new Random();
					int generator = rand.nextInt(60)+40;
					gradeSheet[Professor.testGiven][i] =generator;
				}
//				for(int j=0;j<5;j++){
//					for(int k=0; k<17;k++){
//						System.out.print(gradeSheet[j][k]);
//					}
//					System.out.println();
//				}
			}
			waitingForResult.freeAll();
			Student.checker++;
			numberOfStudents = 0;
		}
	}//end of giveGrades
	
	
}

