<style>
.smallfont{
  	font-family: 'Open Sans', sans-serif;
  	font-size: 12px;
  	cellspacing:3px;
}
.entrybox {  
    border:0;
    border-bottom:1px solid;
    outline:none;
    padding: 20;
    margin: 0;
  }
</style>
<!-- 
<div id=""><%@include file="StudentHeader.jspf"%></div>
 -->
<table id="add_student_info" class="smallfont">
   
  <tr>
    <td> Event Name:</td>
    <td><input id="event_name_input" type="text" name="name" class="entrybox" /></td>
    <td> Event Date:</td>
    <td><input id="event_date_input" type="text" name="name" class="entrybox" /></td>
    
  </tr>
  
  <tr>
  <td> Hours:</td>
    <td><input id="hours_input" type="text" name="name" class="entrybox" /></td>
    <td>&nbsp;</td>
  <td>&nbsp;</td>
  </tr>
</table>
<div id="msg_div"></div>