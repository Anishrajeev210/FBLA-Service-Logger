package fbla;

import java.util.ArrayList;

public class Student {
	
	private int studentid;
	private String name;
	private int grade;
	private long phone;
	private String category;
	private int totalHours;
	private ArrayList<StudentActivity> studentActivites = new ArrayList<StudentActivity>();
	
	public int getStudentid() {
		return studentid;
	}
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(int totalHours) {
		this.totalHours = totalHours;
	}
	public ArrayList<StudentActivity> getStudentActivites() {
		return studentActivites;
	}
	public void setStudentActivites(ArrayList<StudentActivity> studentActivites) {
		this.studentActivites = studentActivites;
	}
	
	public String toString() {
		String s = 
		"id:" + studentid + 
		"name:" + name + 
		"grade:" + grade +
		"category:" + category +
		"totalHours:" + totalHours +
		"activites:" + studentActivites.toString();
		return s;
	}
}

