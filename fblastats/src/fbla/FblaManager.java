package fbla;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FblaManager {

	public static boolean loginUser(String n, String p) {
		boolean result = true;
		return result;
	}

	public static ArrayList<Student> loadStudents() {
		ArrayList<Student> students = new ArrayList<Student>();
		students = FblaDAO.loadStudents();
		return students;
	}
	
	public static Student loadStudentInfo(String studentId) {
		
		Student student = new Student();
		student.setStudentid(100);
		student.setGrade(10);
		student.setName("ABC");
		ArrayList<StudentActivity> studentActivites = new ArrayList<StudentActivity>();

		try {
		StudentActivity activity = new StudentActivity();
		activity.setEventName("eventName1");
		activity.setHours(10);
		activity.setDate("02/12/2020");


		studentActivites.add(activity);

		activity = new StudentActivity();
		activity.setEventName("eventName2");
		activity.setHours(11);
		activity.setDate("02/12/2020");
		studentActivites.add(activity);

		student.setStudentActivites(studentActivites);
		}
		catch (Exception e) {
			System.err.println();
		}
		return student;
	}

	public static Student loadStudentReport(String studentid, String monthyear) {
		return FblaDAO.loadStudent(Integer.parseInt(studentid), monthyear);
	}
	
	public static boolean addStudentInfo(String name, String grade, String phone) {
		int _grade = 0;
		long _phone = 0;
		try {_grade = Integer.parseInt(grade);} catch (Exception e) {}
		try {_phone = Long.parseLong(phone);} catch (Exception e) {}
		return FblaDAO.addStudent(name, _grade, _phone);
	}
	
	public static boolean deleteStudent(String studentid) {
		return FblaDAO.deleteStudent(Integer.parseInt(studentid));
	}
	
	public static boolean addStudentActivity(int studentid, int eventhours, String eventname, String eventdate) {
		return FblaDAO.addStudentActivity(studentid, eventhours, eventname, eventdate);
	}
	
	public static boolean updateStudentActivity(int studentid, int activityid, int eventhours, String eventname, String eventdate) {
		return FblaDAO.updateStudentActivity(studentid, activityid, eventhours, eventname, eventdate);
	}
	
	private static String getParsedDate(int yyyymmdd) {

		try {
			String pattern = "yyyymmdd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = simpleDateFormat.parse("20181201");
		System.out.println(date);
		return date.toString();
		}
		catch (Exception e) {
			return "" + yyyymmdd;
		}
	}
}
