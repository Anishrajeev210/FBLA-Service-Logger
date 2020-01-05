package fbla;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/UpdateStudentActivityServlet")
public class UpdateStudentActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateStudentActivityServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();

		// Values to be sent back to java script
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// Values from the form
		//{mode:mode, studentid:studentid, eventname:eventname, eventdate:eventdate, eventhours:eventhours}
		String mode = request.getParameter("mode");
		String studentid = request.getParameter("studentid");
		String eventhours = request.getParameter("eventhours");
		String eventname = request.getParameter("eventname");
		String eventdate = request.getParameter("eventdate");
		boolean studentUpdated = false;
		if (mode.equals("add_activity")) {
			studentUpdated = FblaManager.addStudentActivity(Integer.parseInt(studentid), Integer.parseInt(eventhours), eventname, eventdate);
		}
		else if (mode.equals("edit_activity")) {
			studentUpdated = FblaManager.updateStudentActivity(Integer.parseInt(studentid), Integer.parseInt(eventhours), eventname, eventdate);
		}
		resultMap.put("success", studentUpdated);
		
		String resultJsonString = gson.toJson(resultMap);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(resultJsonString);
		out.flush();
	}
}

