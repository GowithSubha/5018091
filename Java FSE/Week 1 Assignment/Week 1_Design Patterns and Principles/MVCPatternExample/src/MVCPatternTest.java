public class MVCPatternTest {
    public static void main(String[] args) {
        Student model = new Student("Aniket", 02, 85.0);
        StudentView view = new StudentView();
        StudentController controller = new StudentController(model, view);

        controller.updateView();

        controller.setStudentName("Aniket Roy");
        controller.setStudentGrade(72.5);
        controller.updateView();
    }
}