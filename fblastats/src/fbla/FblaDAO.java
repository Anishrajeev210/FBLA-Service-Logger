package fbla;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FblaDAO {

	public static ArrayList<Student> loadStudents() {
		ArrayList<Student> students = new ArrayList<Student>();
		HashMap<Integer, Student> studentsMap = new HashMap<Integer, Student>();
		Connection connection = null;
		PreparedStatement ps = null; 
		try {
			Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader
			connection = DriverManager.getConnection("jdbc:sqlite:fbla");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);

			String sql = "SELECT s.id, s.name, s.grade, s.phone, a.aid, a.event_name, a.event_date, a.hours from fbla_students s " +
					" LEFT OUTER JOIN student_activity a on s.id = a.id";
			ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {// iterate & read the result set
				
				Student student = new Student();//student
				StudentActivity activity = new StudentActivity();
				student.setStudentid(resultSet.getInt("id"));
				student.setName(resultSet.getString("name"));
				student.setGrade(resultSet.getInt("grade"));
				student.setPhone(resultSet.getLong("phone"));

				activity.setActivityid(resultSet.getInt("aid"));//activity
				activity.setEventName(resultSet.getString("event_name"));
				activity.setDate(resultSet.getString("event_date"));
				activity.setHours(resultSet.getInt("hours"));
				
				if (studentsMap != null && studentsMap.get(new Integer(student.getStudentid())) != null) {
					Student tempStudent = (Student) studentsMap.get(new Integer(student.getStudentid()));
					tempStudent.getStudentActivites().add(activity);	
					studentsMap.put(new Integer(student.getStudentid()), tempStudent);
				}
				else {
					student.getStudentActivites().add(activity);
					studentsMap.put(new Integer(student.getStudentid()), student);
				}
				//System.out.print("id=" + student.getStudentid() + " Evtname=" + activity.getEventName() + " date=" + activity.getDate() + " hrs=" + activity.getHours());
			}
			//Move map values into an list
			students.addAll(studentsMap.values());

			for (int i = 0; students != null && i < students.size(); i ++) {
				Student student = students.get(i);
				ArrayList<StudentActivity> activites = student.getStudentActivites();
				int totalHours = 0;
				String category = "";
				for (int j = 0; activites != null && j < activites.size(); j ++) {
					StudentActivity activity = activites.get(j);
					totalHours = totalHours + activity.getHours();
				}
				if (totalHours <= 50) {
					category = "Community";
				}
				else if (totalHours > 50 && totalHours <=200) {
					category = "Service";
				}
				else if (totalHours > 200) {
					category = "Achievement";
				}
				student.setTotalHours(totalHours);
				student.setCategory(category);
				//System.out.print("id=" + student.getStudentid() + " hours=" + totalHours + " category:" + category); 
			}
			ps.close();
			System.out.println("Students:" + students.toString());
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}       

		finally {
			try {
				if (ps != null) ps.close();
				if(connection != null) connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}
		return students;
	}

	public static Student loadStudent(int studentid, String yyyymm) {
		Student student = new Student();
		Connection connection = null;
		//Statement statement = null;
		PreparedStatement ps = null;
		try {
			Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader
			connection = DriverManager.getConnection("jdbc:sqlite:fbla");
			//statement = connection.createStatement();
			//statement.setQueryTimeout(30);

			/*ResultSet resultSet1 = statement.executeQuery("SELECT s.id, s.name, s.grade, a.event_name, a.event_date, a.hours from fbla_students s " +
					"LEFT OUTER JOIN student_activity a on s.id=a.id where s.id="+studentid);
			while(resultSet1.next()) {
				// iterate & read the result set
				System.out.print("id=" + resultSet1.getInt("id") + " hours=" + resultSet1.getString("hours") + 
						" eventname=" + resultSet1.getString("event_name") + " eventdate=" + resultSet1.getString("event_date"));
			}*/

			ArrayList<StudentActivity> activites = new ArrayList<StudentActivity>(); 
			int totalHours = 0;
			String category = "";
			String sql = "SELECT s.id, s.name, s.grade, s.phone, a.aid, a.event_name, a.event_date, a.hours from fbla_students s " +
					"LEFT OUTER JOIN student_activity a on s.id=a.id where s.id="+studentid;
			ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				// iterate & read the result set
				StudentActivity activity = new StudentActivity();
				student.setStudentid(resultSet.getInt("id"));
				student.setName(resultSet.getString("name"));
				student.setGrade(resultSet.getInt("grade"));
				student.setPhone(resultSet.getInt("phone"));

				activity.setActivityid(resultSet.getInt("aid"));
				activity.setStudentid(resultSet.getInt("id"));
				activity.setStudentIdActivityId(activity.getStudentid()+"AND"+activity.getActivityid());
				activity.setEventName(resultSet.getString("event_name"));
				activity.setDate(resultSet.getString("event_date"));
				activity.setHours(resultSet.getInt("hours"));
				totalHours = totalHours + activity.getHours();
				student.setTotalHours(totalHours);
				
				if (activity.getEventName() != null && activity.getHours() > 0) {
					student.getStudentActivites().add(activity);// only add if event is valid
				}
				System.out.print("id=" + student.getStudentid() + " Evtname=" + activity.getEventName() + " date=" + activity.getDate() + " hrs=" + activity.getHours());
			}
			
			if (student.getTotalHours() <= 50) {
				category = "CAS Community";
			}
			else if (student.getTotalHours() > 50 && student.getTotalHours() <=200) {
				category = "CAS Service";
			}
			else if (student.getTotalHours() > 200) {
				category = "CSA Achievement";
			}
			student.setCategory(category);
			ps.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}       

		finally {
			try {
				if(ps != null) ps.close();
				if(connection != null) connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}
		return student;
	}

	public static boolean addStudent(String name, int grade, long phone) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader
			connection = DriverManager.getConnection("jdbc:sqlite:fbla");
			//Statement statement = connection.createStatement();
			//statement.setQueryTimeout(30);

			String sql = "INSERT INTO fbla_students(name, grade, phone) values (?, ?, ?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, grade);
			ps.setLong(3, phone);
			int count = ps.executeUpdate();
			if (count == 1) result = true; 
			/*ResultSet resultSet = statement.executeQuery("SELECT * from fbla_students");
			while(resultSet.next()) {
				// iterate & read the result set
				System.out.print("name = " + resultSet.getString("name"));
				System.out.println("id = " + resultSet.getInt("id"));
			}*/
			ps.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}       

		finally {
			try {
				if(ps != null) ps.close();
				if(connection != null) connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}
		return result;
	}

	public static boolean deleteStudent(int studentid) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader
			connection = DriverManager.getConnection("jdbc:sqlite:fbla");
			//Statement statement = connection.createStatement();
			//statement.setQueryTimeout(30);

			String sql = "DELETE from fbla_students where id=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, studentid);
			int count = ps.executeUpdate();
			if (count == 1) result = true;
			ps.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				if(ps != null) ps.close();
				if(connection != null) connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}
		return result;
	}

	public static boolean addStudentActivity(int studentid, int eventhours, String eventname, String eventdate) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader
			connection = DriverManager.getConnection("jdbc:sqlite:fbla");
			//Statement statement = connection.createStatement();
			//statement.setQueryTimeout(30);
			
			// first get max activity number
			int newActivityId = 1;
			String sql = "select max(aid) as activity_id from student_activity where id=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, studentid);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				newActivityId = resultSet.getInt("activity_id");
				newActivityId++;
			}
			try{
				resultSet.close();
				ps.close();
				connection.close();
			} catch(Exception e) {}
			
			connection = DriverManager.getConnection("jdbc:sqlite:fbla");
			sql = "INSERT INTO student_activity(aid, id, event_name, event_date, hours) values (?, ?, ?, ?, ?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, newActivityId);
			ps.setInt(2, studentid);
			ps.setString(3, eventname);
			ps.setString(4, eventdate);
			ps.setInt(5, eventhours);
			int count = ps.executeUpdate();
			if (count == 1) result = true; 
			
			/*For debug purposes
			 * ResultSet resultSet = statement.executeQuery("SELECT * from student_activity where id="+studentid);
			while(resultSet.next()) {
				// iterate & read the result set
				System.out.print("id=" + resultSet.getInt("id") + " hours=" + eventhours + 
						" eventname=" + resultSet.getString("event_name") + " eventdate=" + resultSet.getString("event_date"));
			}*/
			ps.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}       

		finally {
			try {
				if(ps != null) ps.close();
				if(connection != null) connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}
		return result;
	}

	public static boolean updateStudentActivity(int studentid, int activityid, int eventhours, String eventname, String eventdate) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader
			connection = DriverManager.getConnection("jdbc:sqlite:fbla");
			//Statement statement = connection.createStatement();
			//statement.setQueryTimeout(30);

			String sql = "UPDATE student_activity set event_name=?, event_date=?, hours=? where aid=?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, eventname);
			ps.setString(2, eventdate);
			ps.setInt(3, eventhours);
			ps.setInt(4, activityid);
			int count = ps.executeUpdate();
			if (count == 1) result = true; 
			/* For debug purposes
			ResultSet resultSet = statement.executeQuery("SELECT * from student_activity where id="+studentid);
			while(resultSet.next()) {
				// iterate & read the result set
				System.out.print("id=" + resultSet.getInt("id") + " hours=" + eventhours + 
						" eventname=" + resultSet.getString("event_name") + " eventdate=" + resultSet.getString("event_date"));
			}*/
			ps.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}       

		finally {
			try {
				if(ps != null) ps.close();
				if(connection != null) connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}
		return result;
	}
	public static void addTables() {
		Connection connection = null;
		Statement statement  = null;
		ResultSet resultSet = null;
		try {
			// create a database connection
			Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader
			connection = DriverManager.getConnection("jdbc:sqlite:fbla");

			statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			//fbla_students
			statement.executeUpdate("DROP TABLE IF EXISTS fbla_students");
			statement.executeUpdate("CREATE TABLE fbla_students (" + 
					"    id    INTEGER      PRIMARY KEY AUTOINCREMENT," + 
					"    name  VARCHAR (50)," + 
					"    email VARCHAR (30)," + 
					"    phone INT (10)," + 
					"    grade INT" + 
					")");
			resultSet = statement.executeQuery("SELECT * from fbla_students");
			while(resultSet.next()) {
				// iterate & read the result set
				System.out.println("name = " + resultSet.getString("name"));
				System.out.println("id = " + resultSet.getInt("id"));
			}

			//student_activity
			statement.executeUpdate("DROP TABLE IF EXISTS student_activity");
			statement.executeUpdate("CREATE TABLE student_activity (" + 
					"    id CONSTRAINT student_id REFERENCES fbla_students (id)," + 
					"    aid    INTEGER," + 
					"    event_name VARCHAR (100)," + 
					"    event_date VARCHAR (20)," + 
					"    hours      INT" + 
					")");
			resultSet = statement.executeQuery("SELECT * from student_activity");
			while(resultSet.next()) {
				// iterate & read the result set
				System.out.println("id = " + resultSet.getInt("id"));
			}
			statement.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}       

		finally {
			try {
				if(resultSet != null) resultSet.close();
				if(statement != null) statement.close();
				if(connection != null) connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}
	}

	public static void testDbConnectivity() {
		Connection connection = null;
		PreparedStatement ps = null;
		boolean dbExists = true;
		try {
			Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader
			connection = DriverManager.getConnection("jdbc:sqlite:fbla");
			String sql = "select * from fbla_students";
			ps = connection.prepareStatement(sql);
			ps.executeQuery();
			ps.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.out.println("Need to Create Database.");
			dbExists = false;
		}
		finally {
			try {
				if(ps != null) ps.close();
				if(connection != null) connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}
		if (!dbExists) addTables();
	}
	
	/*public static void testDBConnectivity() {
		Connection connection = null;
		try {
			//Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			statement.executeUpdate("DROP TABLE IF EXISTS member");
			statement.executeUpdate("CREATE TABLE person (id INTEGER, name STRING)");

			int ids [] = {1,2,3,4,5};
			String names [] = {"Peter","Pallar","William","Paul","James Bond"};

			for(int i=0;i<ids.length;i++) {
				statement.executeUpdate("INSERT INTO person values(' "+ids[i]+"', '"+names[i]+"')");   
			}

			ResultSet resultSet = statement.executeQuery("SELECT * from person");
			while(resultSet.next()) {
				// iterate & read the result set
				System.out.println("name = " + resultSet.getString("name"));
				System.out.println("id = " + resultSet.getInt("id"));
			}
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}       

		finally {
			try {
				if(connection != null)
					connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}
	}*/
	/*
	CREATE TABLE fbla_students (
    	id    INTEGER      PRIMARY KEY AUTOINCREMENT,
    	name  VARCHAR (50),
    	email VARCHAR (30),
    	phone INT (10),
    	grade INT
	);
	CREATE TABLE student_activity (
    	id CONSTRAINT student_id REFERENCES fbla_students (id),
    	event_name VARCHAR (100),
    	event_date INT,
    	hours      INT
	);
	 */
}
