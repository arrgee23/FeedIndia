package rahul.feedindia;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String name = checkNull(req.getParameter("fullName"));
		String pwd = checkNull(req.getParameter("hashedPassword"));
	}
}
