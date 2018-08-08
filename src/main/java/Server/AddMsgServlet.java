package Server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AddMsgServlet extends HttpServlet {
    private final Rooms rooms = Rooms.getInstance();
    private AccountList accountList = AccountList.getInstance();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);
        Message msg = Message.fromJSON(bufStr);
        if (msg.getRoom().equals("global") && msg.getTo()==null){
            rooms.getGlobalRoom().addMsg(msg);
        } else if (rooms.isPresent(msg.getRoom()) && msg.getTo()==null){
            rooms.getRoom(msg.getRoom()).addMsg(msg);
        } else if (accountList.isRegistered(msg.getTo())){
            accountList.get(msg.getTo()).addMsg(msg);
            accountList.get(msg.getFrom()).addMsg(msg);
        } else{
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
}
