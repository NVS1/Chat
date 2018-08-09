package Server.Servlets;

import Server.Rooms;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetMsgServlet extends HttpServlet {
    private final Rooms rooms = Rooms.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromStr = request.getParameter("from");
        String roomName = request.getParameter("room");

        int from = 0;
        try {
            from = Integer.parseInt(fromStr);
            if (from < 0) from = 0;
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!rooms.isPresent(roomName)){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        response.setContentType("application/json");
        String json = rooms.toJSON(from,roomName);

        if (json != null) {
            OutputStream os = response.getOutputStream();
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);
            os.write(buf);
        }
    }
}
