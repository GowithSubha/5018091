public class main {
    public static void main(String[] args) {
        TaskManagementSystem taskManagementSystem = new TaskManagementSystem();
        System.out.println("Assinging tasks...");
        taskManagementSystem.addTask(new Task(1, "Task1", "Completed"));
        taskManagementSystem.addTask(new Task(2, "Task2", "In Progress"));
        taskManagementSystem.addTask(new Task(3, "Task3", "Not Started"));
        taskManagementSystem.addTask(new Task(4, "Task4", "Not Started"));
        taskManagementSystem.addTask(new Task(5, "Task5", "Completed"));
        System.out.println("After adding tasks...");
        taskManagementSystem.display();
        System.out.println("Searching for tasks...");
        taskManagementSystem.seachTask(new Task(3, "Task3", "Not Started"));
        System.out.println("Removing tasks...");
        taskManagementSystem.removeTask(3);
        System.out.println("After removing tasks...");
        taskManagementSystem.display();
        System.out.println("Size of the list: " + taskManagementSystem.getSize());
    }

}
