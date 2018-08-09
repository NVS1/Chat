package Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    public int send(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)
                obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        try {
            String json = toJSON();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            return conn.getResponseCode();
        } finally {
            os.close();
        }
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
                .append(", From: ").append(from).append(", To: ").append(to).append(", Room: ").append(room)
                .append("] ").append(text)
                .toString();
    }

}
