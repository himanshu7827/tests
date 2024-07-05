<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ page import=" java.io.File,java.util.*"%>
 <%@ page isELIgnored="false" %>
 <%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<style>
.roles
{
margin:0px;
padding-top:0px;
padding-bottom:8px;
text-align:left;
border-bottom:1px dashed #A8A8A8;
text-color:#000000; 
list-style-type: none;
}
.all_roles a,.roles a
{
 text-decoration:none;
}
.roles:hover
{	
background-color:  	#E0E0E0 ;
border:1px #D8D8D8;
}
.all_roles
{
text-align:center;
margin-bottom:20px;
}
.all_roles:hover
{
background-color:  	#E0E0E0   ;
border:1px solid #C8C8C8;
}
.chzdefaultrole
{
text-align:center;	
}
.tooltipster-default 
{
border-radius: 2px;
background:#F8F8F8 ;
text-color: #000 !important;
color: #000 !important;
padding:0px;
-webkit-box-shadow: 0px 0px 3px 0px rgba(50, 50, 50, 0.75);
-moz-box-shadow:    0px 0px 3px 0px rgba(50, 50, 50, 0.75);
box-shadow:         0px 0px 3px 0px rgba(50, 50, 50, 0.75);
border:1px solid  #909090  ;
}
.tooltipster-default  .tooltipster-content {
	font-family: 'font-family: "Courier New",Courier,"Lucida Sans Typewriter","Lucida Typewriter",monospace;
	font-size: 14px;
	line-height: 16px;
	padding: 8px 0px;
	padding-bottom:0px;
}

</style>
<style>
.popupfooter
{
border-radius:2px;
padding:2%;
position: fixed; 
bottom: 0px; 
text-align:center;
display:inline-block; 
background: #eaeaea;
width: 100%;
color: #000;
}
.setrole
{
font-size: 14px !important;
display:inline-block;

}
.setrole span{
font-size: 14px !important;
}
.main_div1{
margin-top:0px !important;
}
.default-roles-li {
 list-style-type: none !important;

}
.font-normal{
font-weight: 400 !important;
}
</style>



<script>


$(document).ready(function() {
    $('#close').click(function() {
        $.magnificPopup.close();
    });
});

var id;
function setId(val){
	id=val;
}

function setRoles()
{
	
	var flag=1;
	if(id!=null || id!==undefined)
		{
		if ($("#def_rolechk").is(":checked")) {
		    flag=2;
		}
		//alert("flag::"+flag);
		
		
		 $.ajax(
		            {
		                 type: "POST",
		                 url: "/PMS/updateRoles",
		                 data:"ids="+id+"&flag="+flag,
		                 success: function(response) {
		               	 // alert("response::"+response);
	               
		               	window.parent.location="/PMS/dashboard";
	       			     
					       },
			                error: function(e){
					        	alert('Error in updating Roles: ' + e);
					        	
			                },
						       complete: function(){
						  	   }
		  			                
		  		                });	
		
		}
	else{
		alert("Kindly select a Role");
		return false;
	}
	
	
		
}



</script>


</head>
			  <div id="roles" style='padding-bottom:5em;' >
			  								<sec:authentication property="principal.employeeRoleList" var="authorities" />
			 
			  			<c:forEach items="${Roles}" var="authority" varStatus="vs">
										<li class="default-roles-li pad-top">
										<div class=''><input type='radio' name="testRadio" class="roleList"  onclick="setId(this.id)" id="${authority.numEmpId}##${authority.numRoleId}##${authority.numProjectId}##${authority.numGroupId}##${authority.numOrganisationId}" ><span class="bold font_16 orange"  >${authority.strRoleName} </span>
											<c:choose>
											<c:when test="${authority.numProjectId >0}">
											<h6 class="font_14 font-normal">(${authority.strProjectName})</h6>
											</c:when>
											<c:otherwise>
											<c:choose>
											<c:when test="${authority.numGroupId >0}">
											<h5 class="font_14 blue">(${authority.strGroupName})</h5>
											</c:when>
											<c:otherwise>
											<h5 class="font_14 maroon">(${authority.strOrganisationName})</h5>
											</c:otherwise>
											</c:choose>
											</c:otherwise>
											
											</c:choose>
											</div>

										</li>	
									</c:forEach>
									
									<div id='popFooter' class='popupfooter'><div class='setrole'><span>
		<input id='def_rolechk' type='checkbox'  value=''> &nbsp;Set as default Role
	
		</span></div>
		<!-- Bhavesh (10-10-23) added text style for continue text -->
		<div style='display:inline-block;'><button id='btn_dftrole' class='btn btn-primary' type='button' style='color:black' onclick='setRoles()'>Continue</i></button></div> 
		
			  </div>
</div>
  
</body>
</html>