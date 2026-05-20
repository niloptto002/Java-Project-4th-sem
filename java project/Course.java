import java.io.Serializable;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    private String courseId;
    private String title;

    public Course(String courseId, String title) {
        this.courseId = courseId;
        this.title = title;
    }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() {
        return courseId + " - " + title;
    }
}
