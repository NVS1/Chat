package Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetThread implements Runnable {
    private final Gson gson;
    private int n;
    private String room;

    public GetThread(String room) {
        gson = new GsonBuilder().create();
        this.room = room;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                URL url = new URL(Utils.getURL() + "/get?from=" + n + "&room=" + room);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                if (http.getResponseCode() == 404) {
                    System.out.println("recipient not found");
                    room = "global";
                }
                InputStream is = http.getInputStream();
                try {
                    byte[] buf = ComonOperation.requestBodyToArray(is);
                    String strBuf = new String(buf, StandardCharsets.UTF_8);

                    JsonMessages list = gson.fromJson(strBuf, JsonMessages.class);
                    if (list != null) {
                        for (Message m : list.getList()) {
                            System.out.println(m);
                            n++;
                        }
                    }
                } finally {
                    is.close();
                }
                Thread.sleep(500);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setRoom(String room) {
        this.room = room;
        n = 0;
    }
}
