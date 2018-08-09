package Server.Servlets;

import Server.AccountList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetPrivateMsgServlet extends HttpServlet {
    private final AccountList accountList = AccountList.getInstance();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromStr = request.getParameter("from");
        String login = request.getParameter("login");
        int from = 0;
        try {
            from = Integer.parseInt(fromStr);
            if (from < 0) from = 0;
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType("application/json");
        String json = accountList.get(login).toJSON(from);
        if (json != null) {
            OutputStream os = response.getOutputStream();
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);
            os.write(buf);
        }
    }
}
