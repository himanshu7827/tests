
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
table.dt-rowReorder-float{position:absolute !important;opacity:0.8;table-layout:fixed;outline:2px solid #888;outline-offset:-2px;z-index:2001}
tr.dt-rowReorder-moving{outline:2px solid #555;outline-offset:-2px}
body.dt-rowReorder-noOverflow{overflow-x:hidden}
table.dataTable td.reorder{text-align:center;cursor:move}
</style>
	
	</head>
 
 
<body>

<section id="main-content" class="main-content merge-left">

	<div class=" container wrapper1">
	<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
			<!-- <li>Consumer Forms For Medical Devices</li> -->
			<li class="active">Workflow</li>
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
       
			<sec:authorize access="hasAuthority('ROLE_ADMIN')">	
		     
		<form:form id="form1" modelAttribute="workflowModel" action="/PMS/saveUpdateWorkflow" method="post">
			<form:hidden path="numWorkflowId"/>
			
	<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="Workflow.master"/></h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="Workflow.name"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon "></i> 
									<form:input class="input-field" path="strType" placeholder="Workflow Name"/>
									<form:errors path="strType" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="forms.status" /> :</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
													<form:radiobutton path="valid" value="true" id="toggle-on" />
													<form:label path="valid" for="toggle-on"
														class="btn inline  zero round no-pad">
														<span>Active</span>
													</form:label>
												</div>
												<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
													<form:radiobutton path="valid" value="false"
														id="toggle-off" />

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
			<sec:authorize access="hasAuthority('ROLE_ADMIN')">	
			<div class="row">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<input type= "button" value="Delete" id="delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div>
			</div>
		 </sec:authorize>
			
	
			<script>
			
	   $(document).ready(function() {
			$('#userinfo-message').delay(5000).fadeOut();
			$('#delete').click(function(){
				DegDelete();
			});
		});
	  
	  		 
	  function DegDelete(){
    var chkArray = [];
     
		     
		    $(".CheckBox:checked").each(function() {
		        chkArray.push($(this).val());
		    });
		    
		    var selected;
		    selected = chkArray.join(','); 
		    if(chkArray.length==1)
		    	{
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
					    	  $("#numWorkflowId").val(selected);
					    	 
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
		    else{
		    	swal("Please select only one checkbox to delete");
		    	
		    }
		    
	  } 
function submit_form_delete()
{

 document.getElementById("form1").action = "/PMS/deleteWorkflow";
document.getElementById("form1").method = "POST";
document.getElementById("form1").submit(); 
} 
 


</script>
		<table id="example" class="table table-striped table-bordered display select dataTable"
				style="width: 100%">
				
				<thead class="datatable_thead bold ">
				 
					<tr id="row">
						<th class="width10 check ">Select</th>
						<th class="width10 check ">S.No.</th>
						<th class="hidden" ></th>
						<th><spring:message code="master.name"/></th>	
						<th>Status</th>
						
						<th>Edit</th>
					</tr>
				</thead>
				<tbody class="ui-sortable">
						<c:forEach items="${data}" var="copy" varStatus="loop">
					 	<tr id="row-${loop.index}">
							<td align="center"><input type="checkbox" class="CheckBox" id="Checkbox" value="${copy.numWorkflowId}" autocomplete="off"></td>
							<td align="center">${loop.count}</td>
							<td class="hidden">${copy.numWorkflowId}</td>
							<td>${copy.strType}</td>
							<c:choose>
												<c:when test="${copy.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose>
						  <td>
							<sec:authorize access="hasAuthority('ROLE_ADMIN')">	
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

<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/WorkflowMaster.js"></script>
	 
</body>
</html>