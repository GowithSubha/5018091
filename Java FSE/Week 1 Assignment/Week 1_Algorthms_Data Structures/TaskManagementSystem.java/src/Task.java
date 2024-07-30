public class Task {
    private int taskid;
    private String taskname;
    private String status;

    public Task(int taskid, String taskname, String status) {
        this.taskid = taskid;
        this.taskname = taskname;
        this.status = status;
    }

    public int getTaskid() {
        return taskid;
    }

    public String getTaskName() {
        return taskname;
    }

    public String getTaskStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskid=" + taskid +
                ", taskname='" + taskname + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
