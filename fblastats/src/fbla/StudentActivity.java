package fbla;

public class StudentActivity {
	
	private int activityid;
	private int studentid;
	private int hours;
	private String date;// mm/dd/yyyy
	private String eventName;
	private String studentIdActivityId;
	
	public int getActivityid() {
		return activityid;
	}
	public void setActivityid(int activityid) {
		this.activityid = activityid;
	}
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStudentIdActivityId() {
		return studentIdActivityId;
	}
	public void setStudentIdActivityId(String studentIdActivityId) {
		this.studentIdActivityId = studentIdActivityId;
	}
	public String toString() {
		String s = 
				" name:" + eventName +
				" hours:" + hours + 
				" date:" + date +
				" said:" + studentIdActivityId;
		return s;
	}
}

