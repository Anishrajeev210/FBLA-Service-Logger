<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Northview FBLA</title>
</head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script> 
<script src=" https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.print.min.js"></script>

<link rel="stylesheet" type="text/css" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.dataTables.min.css" />

<script>
$(document).ready(function(){});

function signUp() {
	$("#grade_input").show();
	var email = $("#email_input").val();
	var password = $("#password_input").val();
	var grade = $("#grade_input").val();
	var mode = "sign_up";
	$.post("FblaServlet", 
			{username : email, userpass: password, usergrade: grade}, 
	        function(response, status) {
	        // notify user that his order is successfull
	        }
	);
}

function signIn() {
	var email = $("#email_input").val();
	var password = $("#password_input").val();
	var mode = "sign_in";
	$.post("FblaServlet", {username:email, userpass:password, mode:'sign_in'},
			function(response, status) {
				if (response.success != undefined && response.success != '') {
		        	$("#login_Div").hide();
		        	$("#students_dashboard_Div").show();
		        	getStudentDetails();
				}
				else if (response.error != undefined && response.error != '') {
		        	$("#msg_div").html(response.error);
				}
	        }
	);
}

function getStudentDetails() {
	var mode = "load_details";
	$.post("StudentsDashboardServlet", {mode:'load_students'},
		function(response, status) {
			if (response.success != undefined && response.success != '') {
		    	var data =response.students;
		    	var table;
		    	if ( $.fn.dataTable.isDataTable('#studentstable') ) {
		    	    table = $('#studentstable').DataTable();
		    	    table.destroy();
		    	}
		    	table = $('#studentstable').DataTable({
			    	"aaData": data,
			    	"dom": 'Bfrtip',
			        "buttons": ['print'],
			        "aoColumns": [
			        { "mData": "studentid"}, 
			        { "mData": "name"},
			        { "mData": "phone"},
			        { "mData": "grade"},
			        { "mData": "category"},
			        { "mData": "totalHours"},
			        { "mData": "studentid",
			        	"render": function(data, type, row, meta) {
			                	if(type === 'display') {
		                    	//data = '<a href="#" onclick="getStudentInfo('+ data + ')">Edit</a>' +
		                        data = '<a href="#" onclick="addStudentActivity('+ data + ')">Add Hours</a>' +
		                        '&nbsp; <a href="#" onclick="deleteStudent(' + data + ')">Delete</a>' +
		                        '&nbsp; <a href="#" onclick="getStudentReport(' + data + ')">Report</a>';
		                    	}
			                	return data;
			             }
			         }],
			         "paging":true,
			         "pageLength":20,
			         "ordering":true
			   });
		    	
			}
			else if (response.error != undefined && response.error != '') {
		    	alert('Error occured fetching student details');
			}
	    }
	);
}
function addNewStudent() {
	createAddStudentDialog();
}
function createAddStudentDialog() {
	$( "#addstudent_dialog" ).dialog({
		modal:true,
		draggable: true,
		autoOpen: false,
		width: "645",
		resizable: true,
		position: {my: "center center", at: "center center", of: window, collision: "none"},
        buttons: {
           Add: function() {
        	   performNewStudentAdd();
        	}
        },
        title: "Add Student"
     });
	$("#addstudent_dialog").dialog("open");
	$("#addstudent_dialog").show();
	$("#add_student_msg_div").html(validations);
	$("#new_name_input").val('');
	$("#new_phone_id_input").val('');
}
function performNewStudentAdd() {
	var studentname = $("#new_name_input").val();
	var studentgrade = $("#student_grade_input").val();
	var studentphone = $("#new_phone_id_input").val();
	
	var validations = "";
	if (studentname == '') validations = validations + " student name ";
	if (studentphone == '') validations = validations + " student phone ";
	if (validations != '') {
		validations = "Please enter " + validations;
		$("#add_student_msg_div").html(validations);
		return false;
	}
	$.post("AddStudentServlet", {studentname:studentname, studentgrade:studentgrade, studentphone:studentphone}, 
			function(response, status) {
		//alert('Message data:' + response.success + " " + response.error);
		if (response.success != undefined && response.success != '') {
			$( "#addstudent_dialog" ).dialog( "close" );
			getStudentDetails();
		}
		else if (response.error != undefined && response.error != '') {
			$("#add_student_msg_div").html(response.error);
		}
	});
}

function deleteStudent(studentid) {
	performDeleteStudent(studentid);
}
function performDeleteStudent(studentid) {
	$.post("DeleteStudentServlet", {studentid:studentid}, 
			function(response, status) {
		//alert('Message data:' + response.success + " " + response.error);
		if (response.success != undefined && response.success != '') {
			$( "#deletestudent_dialog" ).dialog("close");
			getStudentDetails();
		}
		else if (response.error != undefined && response.error != '') {
			$("#msg_div").html(response.error);
		}
	});
}

function addStudentActivity(studentid) {
	createAddActivityDialog(studentid);
}
function createAddActivityDialog(studentid) {
	$( "#editstudent_activity_dialog" ).dialog({
		modal:true,
		draggable: true,
		autoOpen: false,
		width: "645",
		resizable: true,
		position: {my: "center center", at: "center center", of: window, collision: "none"},
        buttons: {
        	Add: function() {
        		performAddActivity(studentid, "add_activity");
         	}
        },
        title: "Add Student Activity"
     });
	$("#editstudent_activity_dialog").dialog("open");
	$("#editstudent_activity_dialog").show();
	$("#event_name_input").val('');
	$("#event_date_input").val('');
	$("#hours_input").val('');
	$("#activity_msg_div").html('');
}
function editStudentActivity(id) {
	//alert("studentIdActivityId:" + id);
	createEditActivityDialog(id);
}
function createEditActivityDialog(id) {
	$( "#editstudent_activity_dialog" ).dialog({
		modal:true,
		draggable: true,
		autoOpen: false,
		width: "645",
		resizable: true,
		position: {my: "center center", at: "center center", of: window, collision: "none"},
        buttons: {
        	Edit: function() {
        		performEditActivity(id, "edit_activity");
         	}
        },
        title: "Edit Student Activity"
     });
	$("#editstudent_activity_dialog").dialog("open");
	$("#editstudent_activity_dialog").show();
	$("#event_name_input").val('');
	$("#event_date_input").val('');
	$("#hours_input").val('');
	$("#activity_msg_div").html('');
}
function performAddActivity(id, mode) {	
	var eventname = $("#event_name_input").val();
	var eventdate = $("#event_date_input").val();
	var eventhours = $("#hours_input").val();
	//alert('Message Adddata:' + eventname + " " + eventdate + " hrs:" + eventhours);
	
	var validations = "";
	if (eventname == '') validations = validations + " event name ";
	if (eventdate == '') validations = validations + " event date ";
	if (eventhours == '') validations = validations + " event hours ";
	if (validations != '') {
		validations = "Please enter " + validations;
		$("#activity_msg_div").html(validations);
		return false;
	}
	
	$.post("UpdateStudentActivityServlet", {mode:mode, studentid:id, eventname:eventname, eventdate:eventdate, eventhours:eventhours}, 
			function(response, status) {
				//alert('Message data:' + response.success + " " + response.error);
				if (response.success != undefined && response.success != '') {
					$("#event_name_input").html();
					$("#event_date_input").html();
					$("#hours_input").html();
					$( "#editstudent_activity_dialog" ).dialog( "close" );
					//$( "#reportstudent_dialog" ).dialog( "close" );// NOt sure if we want to close this?
					getStudentDetails();
				}
				else if (response.error != undefined && response.error != '') {
					$("#activity_msg_div").html(response.error);
				}
	});
}
function performEditActivity(id, mode) {	
	var eventname = $("#event_name_input").val();
	var eventdate = $("#event_date_input").val();
	var eventhours = $("#hours_input").val();
	//alert('Message EDIT data:' + eventname + " " + eventdate + " hrs:" + eventhours);
	
	var validations = "";
	if (eventname == '') validations = validations + " event name ";
	if (eventdate == '') validations = validations + " event date ";
	if (eventhours == '') validations = validations + " event hours ";
	if (validations != '') {
		validations = "Please enter " + validations;
		$("#activity_msg_div").html(validations);
		return false;
	}
	$.post("UpdateStudentActivityServlet", {mode:mode, studentidactivityid:id, eventname:eventname, eventdate:eventdate, eventhours:eventhours}, 
			function(response, status) {
				//alert('Message data:' + response.success + " " + response.error);
				if (response.success != undefined && response.success != '') {
					$("#event_name_input").html();
					$("#event_date_input").html();
					$("#hours_input").html();
					$( "#editstudent_activity_dialog" ).dialog( "close" );
					$( "#reportstudent_dialog" ).dialog( "close" );// NOt sure if we want to close this?
					getStudentDetails();
				}
				else if (response.error != undefined && response.error != '') {
					$("#activity_msg_div").html(response.error);
				}
	});
}
/*function performActivityUpdate(studentid, mode) {
	
	var eventname = $("#event_name_input").val();
	var eventdate = $("#event_date_input").val();
	var eventhours = $("#hours_input").val();
	alert('Message data:' + eventname + " " + eventdate + " hrs:" + eventhours);
	$.post("UpdateStudentActivityServlet", {mode:mode, studentid:studentid, eventname:eventname, eventdate:eventdate, eventhours:eventhours}, 
			function(response, status) {
				//alert('Message data:' + response.success + " " + response.error);
				if (response.success != undefined && response.success != '') {
					$("#event_name_input").html();
					$("#event_date_input").html();
					$("#hours_input").html();
					$( "#editstudent_activity_dialog" ).dialog( "close" );
					//$( "#reportstudent_dialog" ).dialog( "close" );// NOt sure if we want to close this?
					getStudentDetails();
				}
				else if (response.error != undefined && response.error != '') {
					$("#msg_div").html(response.error);
				}
	});
}*/
/*function getStudentInfo(studentid) {
	createStudentDialog(studentid);
	loadStudentDialog(studentid);
}
function createStudentDialog(studentid) {
	$( "#editstudent_dialog" ).dialog({
		modal:true,
		draggable: true,
		autoOpen: false,
		width: "745",
		resizable: true,
		position: {my: "center center", at: "center center", of: window, collision: "none"},
        buttons: {
           OK: function() {$(this).dialog("close");}
        },
        title: "Student Info"
     });
	$( "#editstudent_dialog" ).dialog( "open" );
}
function loadStudentDialog(studentid) {
	$.get("StudentServlet", {mode:'load_details', studentid:studentid}, 
	        function(response, status) {
			var data =response.student_activites;
			//alert('Data:' + JSON.stringify(data, null, 2));
            var table = $('#studenttable').DataTable( {
                  "aaData": data,
                  "aoColumns": [
                	{ "mData": "date"}, 
                    { "mData": "eventName"},
                    { "mData": "hours"}
                  ],
                  "paging":true,
                  "pageLength":20,
                  "ordering":true
                });
		});
	$( "#editstudent_dialog" ).show();
}*/

function getStudentReport(studentid) {
	createStudentReportDialog(studentid);
	loadStudentReportDialog(studentid);
}
function createStudentReportDialog(studentid) {
	$( "#reportstudent_dialog" ).dialog({
		modal:true,
		draggable: true,
		autoOpen: false,
		width: "745",
		resizable: true,
		position: {my: "center center", at: "center center", of: window, collision: "none"},
        buttons: {
           OK: function() {$(this).dialog("close");}
        },
        title: "Student Report"
     });
	$( "#reportstudent_dialog" ).dialog( "open" );
}
function loadStudentReportDialog(studentid) {
	$("#student_report_main #name_input").val("");
	$("#student_report_main #grade_input").val("");
	$("#student_report_main #total_hrs").val("");
	$("#student_report_main #total_hrs").val("");
	$("#student_report_main #cservice").prop('checked', false);
	$("#student_report_main #ccommunity").prop('checked', false);
	$("#student_report_main #cachievement").prop('checked', false);
	
	$.get("ReportServlet", {mode:'load_report', studentid:studentid}, 
	        function(response, status) {
			
			var studentname = response.student_name;
			var studentgrade = response.student_grade;
			var studenttotalhours = response.student_total_hours;
			var studencategory = response.student_category;
			var data =response.student_activites;
			$("#student_report_main #name_input").val(studentname);
			$("#student_report_main #grade_input").val(studentgrade);
			$("#student_report_main #total_hrs").val(studenttotalhours);
			$("#student_report_main #total_hrs").val(studenttotalhours);
			if (studencategory=='Service') {
				$("#student_report_main #cservice").prop('checked', true);
			}
			if (studencategory=='Community') {
				$("#student_report_main #ccommunity").prop('checked', true);
			}
			if (studencategory=='Achievement') {
				$("#student_report_main #cachievement").prop('checked', true);
			}

			//alert('Data:' + JSON.stringify(data, null, 2));
			
			var table;
	    	if ( $.fn.dataTable.isDataTable('#student_report_table') ) {
	    	    table = $('#student_report_table').DataTable();
	    	    table.destroy();
	    	}
	    	
            table = $('#student_report_table').DataTable( {
                  "aaData": data,
                  "dom": 'Bfrtip',
			        "buttons": [
			            'print'
			        ],
                  "aoColumns": [
                	{ "mData": "date"}, 
                    { "mData": "eventName"},
                    { "mData": "hours"},
                    { "mData": "studentIdActivityId",
			        	"render": function(data, type, row, meta) {
			                	if(type === 'display') {
		                    		data = '<a href="#" onclick=editStudentActivity("'+ data + '")>Edit</a>';
		                    	}
			                	return data;
			             }
			         }],
                  "paging":true,
                  "pageLength":20,
                  "ordering":true
                });
		});
	$( "#reportstudent_dialog" ).show();
}
</script>

<body>
  	<!-- <link href='https://fonts.googleapis.com/css?family=Open+Sans:700,600' rel='stylesheet' type='text/css'> -->
  	
	<div id="msg_div"></div>
	
	<div id="login_Div"><%@include file="LoginUser.jspf"%></div>
  
	<div id="students_dashboard_Div" style="display:none;"><%@include file="StudentsDashboard.jspf"%></div>  
	
	<div id="reportstudent_dialog" style="display:none;"><%@include file="StudentReport.jsp"%></div>
	
	<div id="addstudent_dialog"  style="display:none;"><%@include file="AddStudent.jsp"%></div>
	
	<div id="editstudent_activity_dialog" style="display:none;"><%@include file="UpdateStudentActivity.jsp"%></div>
    
    <!-- <div id="editstudent_dialog" style="display:none;"><%@include file="StudentDetail.jsp"%></div> -->
</body>
</html>