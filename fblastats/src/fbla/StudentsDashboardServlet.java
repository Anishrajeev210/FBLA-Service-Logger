package fbla;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/StudentsServlet")
public class StudentsDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StudentsDashboardServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();

		// Values from the from
		String m = request.getParameter("mode");
		// Values to be sent back to java script
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		if (m != null && m.equalsIgnoreCase("load_students")) {
			ArrayList<Student> students = FblaManager.loadStudents();
				resultMap.put("success", "success");
				resultMap.put("students", students);
			}  
			else {  
				resultMap.put("error", "Sorry username or password error");
			}
			String resultJsonString = gson.toJson(resultMap);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(resultJsonString);
			out.flush();
	}

}

