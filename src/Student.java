
public class Student extends Thread{
	
	private int identification;
	public int grades[];
	Monitor classRoomforStudents;
	public int testTaken;
	public static int checker = 0;
	//private int iterator = 0;
	public static long time = System.currentTimeMillis();
	
	
	public Student(){};
	
	public Student(int i, Monitor class_room){
		identification = i;
		grades = new int[3];
		for(int j =0;j<grades.length;j++){
			grades[j] =0;
		}
		classRoomforStudents = class_room;
		testTaken = 0;
	}
	
	public void run(){
		while(true){
			if(checker == 0)
			System.out.println("["+(System.currentTimeMillis()-time)+"]"+"Student "+identification +" is now leaving his/her house");
			else{
				System.out.println("["+(System.currentTimeMillis()-time)+"]"+"Student "+identification+" is now returning to take exam no. "+ Professor.testGiven);
			}
			classRoomforStudents.waitForProfessor(identification);
					try {
						Thread.sleep(identification*10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
			}
			classRoomforStudents.haveASeat(identification);
			classRoomforStudents.takeExam(identification);
			classRoomforStudents.waitForGrades(identification, this);
			try {
				Thread.sleep(400);
				grades[Professor.testGiven-1]=Monitor.gradeSheet[Professor.testGiven][identification];
				System.out.println("["+(System.currentTimeMillis()-time)+"]"+"Student "+ identification+" received "+grades[Professor.testGiven-1]+ " for test no. "+ Professor.testGiven);
				System.out.println("["+(System.currentTimeMillis()-time)+"]"+"Student "+ identification+ " will now wait until the next exam begins ");
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			testTaken++;
			if(testTaken == 3)break;
		}
//		for(int j=0; j<grades.length; j++){
//			if(j == 0){
//			System.out.println(" Grades for student "+identification+" \t "+grades[j]+"\t");
//			iterator++;
//			}
//			else{
//				System.out.print(grades[j]+"\t");
//			}
//			System.out.println();
//		}
	}

	public int getIdentifcation() {
		return identification;
	}

	public void setId(int id) {
		this.identification = id;
	}

	
}