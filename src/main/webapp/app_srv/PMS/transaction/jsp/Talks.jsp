
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
			<li class="active">Talks</li>
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
        
             <div class="container pad-top pad-bottom">
        	<div class="panel panel-info  ">					
			<div class="panel-body">
					<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${monthlyProgressModel.strProjectName}</i></span></p>
					<p><span class="bold  font_16 ">For : </span><span class="bold orange font_16"><i>${monthlyProgressModel.strMonth}-${monthlyProgressModel.year}</i></span></p>
					</div>
				</div>
			</div>
        
        
		
			 <sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">
			 <div class="hidden" id="encProjectId" >${monthlyProgressModel.encProjectId}</div>
         <div class="hidden" id="encGroupId">${monthlyProgressModel.encGroupId}</div> 
        <%--  <div class="hidden" id="encCategoryId">${encCategoryId}</div>  --%> 
			 	
		<form:form id="form1" modelAttribute="talksModel" action="/PMS/saveUpdateTalks" method="post">
			<form:hidden path="numTalkID"/>
			
			<input type="hidden" name=encCategoryId id="encCategoryId" value="${encCategoryId}"/>
			 <form:hidden path="encMonthlyProgressId"  value="${encMonthlyProgressId}"/>
			
	<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Talks</h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="Talks.Eventname"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon "></i> 
									<form:input class="input-field" path="strEventName" placeholder="Event Name"/>
									<form:errors path="strEventName" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="Talks.Agency"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon "></i> 
									<form:input class="input-field" path="strAgencyName" placeholder="Agency Name"/>
									<form:errors path="strAgencyName" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="Talks.Title"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon "></i> 
									<form:input class="input-field" path="strTalkTitle" placeholder="Talk Title"/>
									<form:errors path="strTalkTitle" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
								<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="Talks.Date"/>: <span
									style="color: red;">*</span></label>
							</div>
							
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="dtDate" placeholder="Date"/>							
								<form:errors path="dtDate" cssClass="error" />
								</div>
							</div>
							
							
						</div>
					</div>
					
									<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="Talks.Speaker"/>: <span
									style="color: red;">*</span></label>
							</div>
							
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon "></i> 
									<form:input class="input-field" path="strNameSpeaker" placeholder="Name of Speaker"/>
									<form:errors path="strNameSpeaker" cssClass="error" />
								</div>
							</div>
							
							
						</div>
					</div>
						
						
							<div class="row pad-top ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="Talks.City"/>:<span
									style="color: red;">*</span> </label>
							</div> 
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon "></i>
									<form:input class="input-field"  path="strCityLocation" placeholder="City/Location"/>
									<form:errors path="strCityLocation" cssClass="error" />
								</div>
							</div>
						</div>
						</div>		
					
					<div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save as Draft
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
						<input type="button" class="btn btn-orange font_14" id="back" onclick="backToMainPage('${monthlyProgressModel.encProjectId}','${encCategoryId}','${monthlyProgressModel.encGroupId}')" value="Back To Main Page"/>
					</div>
					<div class="row pad-top" id="mainPrevNext">
						
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
		 <sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')"> 
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
					    	  $("#numTalkID").val(selected);
					    	 
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

 document.getElementById("form1").action = "/PMS/deleteTalks";
document.getElementById("form1").method = "POST";
document.getElementById("form1").submit(); 
} 
 


</script>
		<table id="example" class="table table-responsive table-bordered compact display hover stripe"
				style="width: 100%">
				
				<thead class="datatable_thead bold ">
				 
					<tr id="row">
						<th class="width10 check ">Select</th>
							<th width="3%"></th>
						<th class="width10 check control">S.No.</th>
					
						<th class="hidden" ></th>
						<th class="details-control" width="20%">Event Name</th>	
						<th class="control" width="20%">Agency Name</th>
						<th class="none">Talk Title</th>
						<th class="control" width="10%">Date</th>
						<th class="control" width="20%">Name of Speaker</th>
						<th class="control" width="20%">City/Location</th>
						
						<th>Edit</th>
					</tr>
				</thead>
				<tbody class="ui-sortable">
						<c:forEach items="${data}" var="copy" varStatus="loop">
					 	<tr id="row-${loop.index}">
							<td align="center"><input type="checkbox" class="CheckBox" id="Checkbox" value="${copy.numTalkID}" autocomplete="off"></td>
							<td></td>
							<td align="center">${loop.count}</td>
							<td class="hidden">${copy.numTalkID}</td>
							<td>${copy.strEventName}</td>
						    <td>${copy.strAgencyName}</td>
						    <td>${copy.strTalkTitle}</td>
						    <td>${copy.dtDate}</td>
						    <td>${copy.strNameSpeaker}</td>
						    <td>${copy.strCityLocation}</td>
						
							
							<td>
							 <sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">
								<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" id="edit" aria-hidden="true"></span>
								</sec:authorize> 
							</td>

						</tr> 
					</c:forEach>
					
				</tbody>
			
			</table>
		<!-- 		<div class="row pad-top  form_btn center">
			<button type="button" class="btn btn-primary font_14 " >
			<span class="pad-right" ><i class="fa fa-folder"></i></span>Save Hierarchy
			</button>
			</div> -->

			<!--End of data table-->
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
</div>
</div>
</section>

<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/Talks.js"></script>
<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/showPrevNextButton.js"></script>	 
		<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
</body>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>

</html>