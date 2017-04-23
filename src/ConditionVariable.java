
public class ConditionVariable {

	private int numberOfBlockeditems;
	
	public ConditionVariable(){
		numberOfBlockeditems = 0;
	}
	
	public synchronized boolean isEmpty(){
		if (numberOfBlockeditems == 0)
			return true;
		else 
			return false;
	}
	
	public synchronized void free(){
		if(numberOfBlockeditems > 0){
			numberOfBlockeditems--;
			notify();
		}
	}
	
	public synchronized void freeAll(){
		if(numberOfBlockeditems > 0){
			numberOfBlockeditems = 0;
			notifyAll();
		}
	}
	
	public synchronized void block() throws InterruptedException{
		numberOfBlockeditems++;
		wait(1000000000);
	}

	public int getNumberOfBlockeditems() {
		return numberOfBlockeditems;
	}

	public void setNumberOfBlockeditems(int numberOfBlockeditems) {
		this.numberOfBlockeditems = numberOfBlockeditems;
	}
}
