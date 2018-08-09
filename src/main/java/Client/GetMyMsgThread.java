package Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetMyMsgThread implements Runnable {
    private final Gson gson;
    private int n;
    private String login;

    public GetMyMsgThread(String login) {
        this.login = login;
        gson = new GsonBuilder().create();
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                URL url = new URL(Utils.getURL() + "/my_msg?from=" + n + "&login=" + login);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                if (http.getResponseCode() == 404) {
                    System.out.println("recipient not found");
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

}
