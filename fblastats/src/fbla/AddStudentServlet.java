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

@WebServlet("/AddStudentServlet1")
public class AddStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddStudentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();

		// Values to be sent back to java script
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// Values from the form
		String studentname = request.getParameter("studentname");
		String studentgrade = request.getParameter("studentgrade");
		String studentphone = request.getParameter("studentphone");
		boolean studentAdded = FblaManager.addStudentInfo(studentname, studentgrade, studentphone);
		if (studentAdded) resultMap.put("success", "Student was added successfully");
		else resultMap.put("error", "Student was not added");
		
		String resultJsonString = gson.toJson(resultMap);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(resultJsonString);
		out.flush();
	}
}

