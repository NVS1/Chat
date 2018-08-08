package Server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SignUpServlet extends HttpServlet {

    private AccountList accountList = AccountList.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        if (accountList.add(login,new Account(login, pass))){
            response.getWriter().print("Account created");
        } else {
            response.setStatus(400);
        }
    }

}
