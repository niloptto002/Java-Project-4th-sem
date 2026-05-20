import java.io.*;
import java.util.*;

public class DataManager {
    private static final String STUDENT_FILE = "students.dat";
    private static final String COURSE_FILE = "courses.dat";
    private static final String ENROLLMENT_FILE = "enrollments.dat";

    private static void saveList(List<?> list, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> loadList(String filename) throws IOException, ClassNotFoundException {
        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj == null) return new ArrayList<>();
            if (!(obj instanceof List)) {
                throw new ClassCastException("Deserialized object is not a List. File: " + filename);
            }
            return (List<T>) obj;
        }
    }

    public static void saveStudents(List<Student> students) throws IOException {
        saveList(students, STUDENT_FILE);
    }

    public static List<Student> loadStudents() throws IOException, ClassNotFoundException {
        return loadList(STUDENT_FILE);
    }

    public static void saveCourses(List<Course> courses) throws IOException {
        saveList(courses, COURSE_FILE);
    }

    public static List<Course> loadCourses() throws IOException, ClassNotFoundException {
        return loadList(COURSE_FILE);
    }

    public static void saveEnrollments(List<Enrollment> enrollments) throws IOException {
        saveList(enrollments, ENROLLMENT_FILE);
    }

    public static List<Enrollment> loadEnrollments() throws IOException, ClassNotFoundException {
        return loadList(ENROLLMENT_FILE);
    }
}