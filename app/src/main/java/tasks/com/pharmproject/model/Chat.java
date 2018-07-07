package tasks.com.pharmproject.model;


public class Chat {
    public String message,id1,id2;
    public long time;

    public Chat() {
    }

    public Chat(String message, String id1, String id2) {
        this.message = message;
        this.id1 = id1;
        this.id2 = id2;
        time = 0;
    }

}
