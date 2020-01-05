<style>
.smallfont{
  	font-family: 'Open Sans', sans-serif;
  	font-size: 12px;
}
.entrybox {
  
    border:0;
    border-bottom:1px solid;
    outline:none;
    padding: 0;
    margin: 0;
  }
</style>

<table id="add_student_info" class="smallfont">
  <tr>
    <td> Name:</td>
    <td><input id="new_name_input" type="text" name="name" class="entrybox" /></td>
   </tr>
   <tr>  
    <td> Grade:</td>
    <td>
      <select id="student_grade_input" class="entrybox">
      <option value="9">Freshman</option>
      <option value="10">Sophomore</option>
      <option value="11">Junior</option>
      <option value="12">Senior</option>
      </select>
    </td>
  </tr>
 
</table>
<div id="msg_div"></div>