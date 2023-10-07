import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Received username: " + username);
        System.out.println("Received password: " + password);

        UserService userService = new UserService();
        UserAuthorization user = userService.authenticateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            response.sendRedirect("main-servlet");
        } else {
            System.out.println("Authentication failed for username: " + username);
            response.sendRedirect("login.jsp?error=1");
        }
    }
}