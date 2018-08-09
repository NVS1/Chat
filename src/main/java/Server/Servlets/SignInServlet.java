package Server.Servlets;

import Server.Account;
import Server.AccountList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private AccountList accountList = AccountList.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        if (accountList.isRegistered(login)){
            Account account = accountList.get(login);
            if (account.getPass().equals(pass)){
                accountList.get(login).setOnline(true);
               response.getWriter().print("Hello "+ login);
            } else {
                response.setStatus(400);
            }
        } else {
           response.setStatus(404);
        }
    }
}
