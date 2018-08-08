package Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;

public class Message {
    private final String from;
    private Date date = new Date();
    private final String text;
    private final String to;
    private final String room;

    public Message(String from, String text, String to, String room) {
        this.from = from;
        this.text = text;
        this.to = to;
        this.room = room;
    }

    public String getFrom() {
        return from;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static Message fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, Message.class);
    }

    public String getTo() {
        return to;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[").append(date)
                .append(", From: ").append(from).append(", To: ").append(to).append(", Room: :").append(room)
                .append("] ").append(text)
                .toString();
    }

}
