package fbla;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/FblaServlet1")
public class FblaServlet extends HttpServlet {
	//http://localhost:9080/fblastats/FblaServlet#
	private static final long serialVersionUID = 1L;

	public FblaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//FblaDAO.addTables();
		FblaDAO.testDbConnectivity();
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("/WEB-INF/pages/fbla.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//FblaDAO.addTables();
		Gson gson = new Gson();
		// Values from the form
		String m = request.getParameter("mode");
		String n = request.getParameter("username");
		String p = request.getParameter("userpass");  
		String g = request.getParameter("usergrade");  
		
		// Values to be sent back to java script
		HashMap<String, String> resultMap = new HashMap<String, String>();

		if (m != null && m.equalsIgnoreCase("sign_in")) {
			if(FblaManager.loginUser(n, p)) {  
				RequestDispatcher rd=request.getRequestDispatcher("StudentServlet");  
				//rd.forward(request,response);
				resultMap.put("success", "Welcome " + n);
				resultMap.put("student_id", "100");
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

		if (m != null && m.equalsIgnoreCase("sign_up")) {
			if(false) {  
				RequestDispatcher rd=request.getRequestDispatcher("servlet2");  
				rd.forward(request,response); 
				resultMap.put("success", "Success");
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

//background:#8998db;

}

//https://www.baeldung.com/servlet-json-response
//https://bitbucket.org/xerial/sqlite-jdbc/downloads/
