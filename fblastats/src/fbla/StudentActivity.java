package fbla;

public class StudentActivity {
	
	private int studentid;
	private int hours;
	private int date;//yyyymmyy
	private String eventName;
	private String formattedDate;	

	public int getStudentid() {
		return studentid;
	}
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}
	public String getEventName() {
		if (eventName != null) return eventName.trim();
		else return "";
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public String getFormattedDate() {
		return formattedDate;
	}
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

}

