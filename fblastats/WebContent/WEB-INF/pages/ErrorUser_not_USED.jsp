<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Northview FBLA</title>
</head>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js" type="text/javascript"></script>
<script>
$(document).ready(function(){});

function signIn() {
	var email = $("#email_input").val();
	var password = $("#password_input").val();
	var mode = "sign_in";
	$.post("FblaServlet", 
			{username : email, userpass: password}, 
	        function(response, status) {
	        // notify user that his order is successfull
	        }
	);
}
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
</script>

<style>
	body{
  	font-family: 'Open Sans', sans-serif;
  	background:#8998db;
  	margin: 0 auto 0 auto;  
  	width:100%; 
  	text-align:center;
  	margin: 20px 0px 20px 0px;   
	}
	p{
  	font-size:12px;
  	text-decoration: none;
  	color:#ffffff;
	}
	h1{
  	font-size:1.5em;
  	color:#525252;
	}
	.box{
  	background:white;
  	width:300px;
  	border-radius:6px;
  	margin: 0 auto 0 auto;
  	padding:0px 0px 70px 0px;
  	border: #2980b9 4px solid; 
	}
	.email{
  	background:#ecf0f1;
  	border: #ccc 1px solid;
  	border-bottom: #ccc 2px solid;
  	padding: 8px;
  	width:250px;
  	color:#AAAAAA;
  	margin-top:10px;
  	font-size:1em;
  	border-radius:4px;
	}
	.password{
  	border-radius:4px;
  	background:#ecf0f1;
  	border: #ccc 1px solid;
  	padding: 8px;
  	width:250px;
  	font-size:1em;
	}
	.btn{
  	background:#2ecc71;
  	width:125px;
  	padding-top:5px;
  	padding-bottom:5px;
  	color:white;
  	border-radius:4px;
 	border: #27ae60 1px solid;  
  	margin-top:20px;
  	margin-bottom:20px;
  	float:left;
  	margin-left:16px;
  	font-weight:800;
  	font-size:0.8em;
	}
	.btn:hover{
  	background:#2CC06B; 
	}
	#btn2{
  	float:left;
  	background:#3498db;
  	width:125px;  padding-top:5px;
  	padding-bottom:5px;
  	color:white;
  	border-radius:4px;
  	border: #2980b9 1px solid;
  	margin-top:20px;
  	margin-bottom:20px;
  	margin-left:10px;
  	font-weight:800;
  	font-size:0.8em;
	}
	#btn2:hover{ 
	background:#3594D2; 
	}
</style>

<body>
  	<!-- <link href='https://fonts.googleapis.com/css?family=Open+Sans:700,600' rel='stylesheet' type='text/css'> -->
  	
  	<%
Object name = request.getAttribute("invalidUser");
out.println("Attribute Value : " +name);


%>

<%= request.getAttribute("invalidUser") %>.

	${invalidUser}
	<form method="post" action="FblaServlet">
		<div class="box">
			<h1>Northview FBLA</h1>
			
			<div>
		
			
			
			
			
			
			</div>
			<input id="email_input" type="text" name="email" value="email" 
			onFocus="field_focus(this, 'email');" onblur="field_blur(this, 'email');" class="email" />
			
			<input id="password_input" type="password" name="email" value="email" 
			onFocus="field_focus(this, 'email');" onblur="field_blur(this, 'email');" class="email" />
			
			<input id="grade_input" type="text" name="grade" value="grade" style="display:none;"
			onFocus="field_focus(this, 'email');" onblur="field_blur(this, 'email');" class="email" />
  			
  			<a href="#" onclick="signIn();"><div id="btn1" class="btn">Sign In</div></a>
			
			<a href="#" onclick="signUp();"><div id="btn2">Sign Up</div></a>
  		</div> <!-- End Box -->
  	</form>
  	
	<p>Forgot your password? <u style="color:#f1c40f;">Click Here!</u></p>
  
</body>
</html>