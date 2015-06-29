package rahul.feedindia;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 2908006218826393293L;
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session = req.getSession(true);
		session.invalidate();
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session = req.getSession(true);
		session.invalidate();
	}
	
}
