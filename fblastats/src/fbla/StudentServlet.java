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

@WebServlet("/StudentServlet1")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StudentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();

		// Values to be sent back to java script
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// Values from the from
		String m = request.getParameter("mode");
		String studentid = request.getParameter("studentid");
		if (m != null && m.equalsIgnoreCase("load_details")) {
			Student header = FblaManager.loadStudentInfo(studentid);
			resultMap.put("success", "success");
			resultMap.put("student_id", header.getStudentid());
			resultMap.put("student_grade", header.getGrade());
			resultMap.put("student_activites", header.getStudentActivites());
		}  
		else {  
			resultMap.put("error", "Sorry Could not load student details");
		}
		String resultJsonString = gson.toJson(resultMap);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(resultJsonString);
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Gson gson = new Gson();
	}
}

