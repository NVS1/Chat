package Client;

import java.util.LinkedList;
import java.util.List;

public class Room {
    private final String name;
    private final List<Message> msgList = new LinkedList<>();


    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addMsg (Message message){
        msgList.add(message);
    }

    public List<Message> getMsgList() {
        return msgList;
    }
}
