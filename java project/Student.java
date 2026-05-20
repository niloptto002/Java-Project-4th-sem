import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int studentId;
    private String name;
    private Date dob;

    public Student(int studentId, String name, Date dob) {
        this.studentId = studentId;
        this.name = name;
        this.dob = dob;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    @Override
    public String toString() {
        return studentId + " - " + name;
    }
}