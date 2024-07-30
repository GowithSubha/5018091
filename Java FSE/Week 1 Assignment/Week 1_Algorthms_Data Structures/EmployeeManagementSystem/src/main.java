public class main {
    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem(5);

        Employee employee1 = new Employee(1, "Subha", "Sr. Software Engineer", 100000);
        Employee employee2 = new Employee(2, "Aniket", "Marketing Manager", 120000);
        Employee employee3 = new Employee(3, "Khushi", "Sales Representative", 80000);

        ems.addEmployee(employee1);
        ems.addEmployee(employee2);
        ems.addEmployee(employee3);

        System.out.println("Employees:");
        ems.traverseEmployees();

        Employee foundEmployee = ems.searchEmployee(2);
        if (foundEmployee != null) {
            System.out.println("Found employee: " + foundEmployee.toString());
        } else {
            System.out.println("Employee not found.");
        }

        ems.deleteEmployee(2);

        System.out.println("Employees after deletion:");
        ems.traverseEmployees();
    }
}