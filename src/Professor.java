
public class Professor extends Thread {
	
	private int identification;
	private Monitor _class;
	public static int testGiven = 0;
	
	public int getIdentification() {
		return identification;
	}

	public void setIdentification(int identification) {
		this.identification = identification;
	}

	public Monitor get_class() {
		return _class;
	}

	public void set_class(Monitor _class) {
		this._class = _class;
	}

	public static int getTestGiven() {
		return testGiven;
	}

	public static void setTestGiven(int testGiven) {
		testGiven = testGiven;
	}

	public Professor(int i, Monitor c){
		identification = i;
		_class = c;
	}
	
	public void run(){
		System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Professor is now on his way to school");
		while(true){
			_class.professorArrived(identification);
			try {
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Professor still waiting for student to get comfortable....");
				Thread.sleep(600);
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Ok ! exam will now begin");
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"All the tests have been handed out. Students will now take the test");
				_class.beginExam();
				Thread.sleep(60);
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Test is still going on...");
				Thread.sleep(300);
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Ok test Finished...");
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Time to hand in the paper....");
				_class.collectExam();
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Professor will now grade the papers and assign grade ");
				Thread.sleep(60);
				_class.giveGrades();
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"exam "+testGiven+" is now over will take a break.....");
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"please Wait..");
				Thread.sleep(500);
				System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Wait until all the grades are posted....");
				Thread.sleep(400);
				for(int j=0; j<5;j++){
					if(j == Professor.testGiven){
					System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Grades for exam : "+j );
					for(int k=0; k<17;k++){
						if(Monitor.gradeSheet[j][k]!=0){
							System.out.println("["+(System.currentTimeMillis()-Student.time)+"]"+"Student "+ k+" : "+Monitor.gradeSheet[j][k] );
						}
					}
					System.out.println("["+(System.currentTimeMillis()-Student.time)+"]");
					}
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(testGiven == 3)break;
		}
	}
}
