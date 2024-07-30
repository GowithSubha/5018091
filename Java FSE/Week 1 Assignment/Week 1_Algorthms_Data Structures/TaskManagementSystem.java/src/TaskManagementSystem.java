public class TaskManagementSystem {
    private Node head;
    private Node tail;
    private int size;

    public TaskManagementSystem() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    public void seachTask(Task task) {
        Node current = head;
        while (current != null) {
            if (current.getTask().getTaskid() == task.getTaskid()) {
                System.out.println(current.getTask());
                return;
            }
            current = current.getNext();
        }
        System.out.println("Task not found");
    }

    public void removeTask(int taskid) {
        Node current = head;
        Node previous = null;
        while (current != null) {
            if (current.getTask().getTaskid() == taskid) {
                if (previous == null) {
                    head = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                return;
            }
            previous = current;
            current = current.getNext();
        }
    }

    public void display() {
        Node current = head;
        while (current != null) {
            System.out.println(current.getTask());
            current = current.getNext();
        }
    }

    public int getSize() {
        return size;
    }

}
