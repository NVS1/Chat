package Server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetAllUsersServlet extends HttpServlet {
    private AccountList accountList = AccountList.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = accountList.toJSON();
        if (json != null) {
            OutputStream os = response.getOutputStream();
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);
            os.write(buf);
        }
    }
}
