<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

	
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/master/css/home_form2.css">
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/master/css/style.bundle.css">	
	
 <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/new/style.css">	
 <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/new/font-awesome.css">	
	
	 <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/select2.css">	
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/jquery-ui.css">
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/DataTable/dataTables.bootstrap.min.css">
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/master/css/bootstrapValidator.css">	

</head>


<body>

<section id="main-content" class="main-content merge-left">

	<div class=" container wrapper1">
	<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
			<!-- <li>Consumer Forms For Medical Devices</li> -->
			<li class="active">SubActivity Master</li>
		</ol>
	</div>
</div>
<div class="row ">
		
		 </div>
		 <c:if test="${message != null && message != ''}">
		 	<c:choose>
		 		<c:when test="${status=='success'}">
		 			<div id="userinfo-message"><p class="success_msg">${message}</p></div> 
		 		</c:when>
		 		<c:otherwise>
		 			<div id="userinfo-message"><p class="error_msg">${message}</p></div> 
		 		</c:otherwise>		 	
		 	</c:choose>       		
        </c:if>	
		
		  <sec:authorize access="hasAuthority('WRITE_SUBACTIVITY_MST')">  
		<form:form id="form1" modelAttribute="subActivityMasterModel" action="/PMS/mst/saveUpdateSubActivityMaster" method="post">
			<form:hidden path="numId"/>
			<form:hidden path="idCheck"/>
	<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">SubActivity Master</h4></div>
					<div class="panel-body text-center">
					
					
					
					
					
					<%-- 
					<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="subActivity_Master.activity_Name"/>:<span
									style="color: red;">*</span></label>
							</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<form:select path="numActivityId" id="numActivityId" class="select2Option">
												<form:option value="0">-- Select Activity Name --</form:option> 
												
												  
											 	<c:forEach items="${activityList}"
													var="activityList">
													<form:option value="${activityList.numId}">
														<c:out value="${activityList.strActivityName}"></c:out>
													</form:option> 
												</c:forEach> 
											</form:select>
										</div>
									</div>
									</div>
						
					</div>
					 --%>
					
					
					
					
					
			
			
				<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="subActivity_Master.subActivity_Name"/>:<span
									style="color: red;">*</span></label>
							</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
											<i class="fa fa-th-large icon pad-top-double"></i>
												<form:input class="input-field"
													path="strSubActivityName" />
												<form:errors path="strSubActivityName" cssClass="error" />
											</div>
										</div>
									</div>
						
					</div>

					<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message
													code="forms.status" /> :</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid"  value="true" id="toggle-on"	 />
									<form:label path="valid" for="toggle-on"
										class="btn inline  zero round no-pad">
										<span>Active</span>
									</form:label>
									</div>
									<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid" value="false" id="toggle-off" />

									<form:label path="valid" for="toggle-off"
										 class="btn round inline zero no-pad">
										<span class="">Inactive</span>
									</form:label>
</div>
							</div>
						</div>						
					</div> 	
					
					
			
				<div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
					</div>
					</div>
					</div>
					</div>
					</div>
		</form:form>
		  </sec:authorize>  
		
	
	<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
		 <sec:authorize access="hasAuthority('WRITE_SUBACTIVITY_MST')">   
			 <div class="row">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<input type= "button" value="Delete"   id="delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div>
			</div> 
			</sec:authorize>   
			
			<script>

	   $(document).ready(function() {
			
			
			 $('#userinfo-message').delay(5000).fadeOut();
			$('#delete').click(function(){
				SubActivityMasterDelete();
			});
		});
	  
	  		 
	  function SubActivityMasterDelete(){
    var chkArray = [];
     
		     
		    $(".CheckBox:checked").each(function() {
		        chkArray.push($(this).val());
		    });
		    
		    var selected;
		    selected = chkArray.join(','); 
		    if(selected.length >= 1){
		    	
		    	
		    	 swal({
				      title: "Are you sure you want to delete the record?",
				      icon: "warning",
				      buttons: [
				                'No',
				                'Yes'
				              ],
				      dangerMode: true,
				    }).then(function(isConfirm) {
				      if (isConfirm) {
					    	  $("#idCheck").val(selected);
					    	 
					  		  submit_form_delete();
					      }
			  	
					      else{
					    	
					      return false;
			    }
		    });
		    }
		    else{
		        swal("Please select at least one checkbox to delete");  
		    }
		    
	  } 
function submit_form_delete()
{

 document.getElementById("form1").action = "/PMS/mst/deletesubActivity";
document.getElementById("form1").method = "POST";
document.getElementById("form1").submit(); 
} 
 


</script>

			<table id="example" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th class="width20 check">Select</th>
						<th>SubActivity Name</th>	
						<th>Status</th>
						<th>Edit</th>
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data}" var="subActivityMasterModel">
						<tr>
						
						
							<td><input type="checkbox"  path="checkbox" class="CheckBox" id="Checkbox" value="${subActivityMasterModel.numId}" autocomplete="off">${subActivityMasterModel.numId}</td>
							<td>${subActivityMasterModel.strSubActivityName}</td>
						
							
							
							
							<c:choose>
								<c:when test="${subActivityMasterModel.valid}">
									<td>Active</td>
								</c:when>
								<c:otherwise>
									<td>Inactive</td>
								</c:otherwise>
							</c:choose>
							
							<td>
							 <sec:authorize access="hasAuthority('WRITE_SUBACTIVITY_MST')">  
								<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" id="edit" aria-hidden="true"></span>
							 </sec:authorize>  
							</td>

						</tr>
					</c:forEach>
				</tbody>
			</table>

			<!--End of data table-->
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
	</div>
</div>
</section>


<!--  <script
	src="/PMS/resources/app_srv/PMS/global/js/new/jquery3.3.1.min.js"></script> 
	<script
	src="/PMS/resources/app_srv/PMS/global/js/new/bootstrap.min-3.4.0.js"></script>
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/jquery.easyui.js"></script>
	<script src="/PMS/resources/app_srv/PMS/global/js/jquery-ui.js"></script>	
	<script src="/PMS/resources/app_srv/PMS/global/js/DataTable/jquery.dataTables.min.js"></script>
	 <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/bootstrapValidator.js"></script>
		<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/select2.min.js"></script> 
	  -->


	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/SubActivityMaster.js"></script>
	
</body>
</html>