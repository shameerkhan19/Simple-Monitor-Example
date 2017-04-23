import java.util.Vector;

public class Main {
	
	public static void main(String[] args) {
	Monitor classRoom = new Monitor();

	Vector<Student> students = new Vector<Student>();
	
	for(int i=0; i<12;i++){
		students.addElement(new Student(i+1,classRoom));
	}
	for(int i=0;i<students.size();i++){
		students.get(i).start();
	}
	Professor p = new Professor(1,classRoom);
	p.start();
	}
}
