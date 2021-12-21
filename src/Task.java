public class Task {
    private Client client = null;
    private String taskType = null;

    public Task(Client client, String taskType){
        this.client = client;
        this.taskType = taskType;
    }

    public String getTaskType() {
        return taskType;
    }
}
