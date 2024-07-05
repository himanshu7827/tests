<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/OrgChart/jquery.orgchart.css"> -->
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/bootstrap-dropdownhover.css">


</head>
<style>
.btn.dropdown-toggle:after, .nav-link.dropdown-toggle:after{
     content: none !important; 
}
.btn-secondary{
padding: 12px 12px !important;
}
</style>




<body>

	<section id="main-content" class="main-content merge-left">
	<input type="hidden"  id="roleId" value="${roleId}"> <!-- Added by devesh on 13-02-24 to get roleId of login user -->
		<div class=" container wrapper1">
			<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
		
			<li class="active"><spring:message code="proposal.report.proposalDetail"/></li>
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
		
	<!--Start data table-->
	
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
	<fieldset class="fieldset-border">

		<!-- 	<legend class="bold legend_details">Details :</legend> -->
			<%-- <div class="row pad-top pad-bottom">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<button type='button' class="btn btn-orange "  onclick="window.open('/PMS/applicationBasicDetails','_blank')" ><spring:message code="form.create.proposal"/> <i class="fa fa-plus-circle "></i> </button>
					</div>
				</div>
			</div> --%>
		
			<table id="example" class="table table-striped table-bordered margin-top"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
					    <th><spring:message code="serial.no"/></th>
						<th><spring:message code="proposal.proposalTitle"/></th>
						<th><spring:message code="proposal.proposalRefNo"/></th>						
<%-- 						<th><spring:message code="application.businesstype.label"/></th>
 --%>						<th><spring:message code="application.projecttype.label"/></th>
						<th><spring:message code="application.project.category.label"/></th>
<%-- 						<th ><spring:message code="Client_Contact_Person_Master.organisationName"/></th>
 --%>						<th><spring:message code="group.groupName"/></th>
						<th ><spring:message code="application.totalProposal.cost"/></th>
						<th>Status</th>
						<th><spring:message code="forms.action"/></th>
					</tr>
				</thead>
				<tbody class="">
					<%-- <c:forEach items="${data}" var="appdetail" varStatus="theCount">
							<tr id="tr_${appdetail.numId}" >
						    <td>${theCount.count}</td>
							<td ><a class="bold" title='Click Here to View Proposal Details' onclick="viewProjectDetails('/PMS/mst/proposalDetails/${appdetail.encNumId}')">${appdetail.proposalTitle}</a></td>
							<td>${appdetail.proposalRefNo}</td>							
							<td>${appdetail.businessTypeName}</td>
							<td>${appdetail.projectTypeName}</td>
							<td>${appdetail.categoryName}</td>
							<td >${appdetail.organisationName }</td>
							<td >${appdetail.groupName}</td>
							<td class="currency-inr">${appdetail.totalProposalCost}</td>
							<td><c:if test="${appdetail.strStatus=='REV'}"><span class="orange_bright">Revision</span></c:if><c:if test="${appdetail.strStatus=='SUB'}"><span class="green" title="In case of edit new copy of proposal will be generated">Submitted</span></c:if><c:if test="${appdetail.strStatus=='SAD'}"><span class="lightblue">Save as Draft</span></c:if></td>	
							<td>
							 <div class="dropdown show">
 							 <a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onmouseover="viewApplicationStatus(${appdetail.numId})" aria-haspopup="true" aria-expanded="false">
  								<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i>
 						 </a>
  								<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="${appdetail.numId}">
  							</ul>
						</div>
							</td>		
						</tr>
					</c:forEach> --%>
				</tbody>
			</table>
			
			<!--End of data table-->
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
</div>

</section>



<script>
//Added by devesh on 24/08/23 to make proposal table with new controller
$(document).ready(function() {
	//alert("I am here 1");
    $('#example').DataTable().destroy();
	 var tableId = $('#example').attr('id')
			var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/getProposalDetailListforGC",
			success : function(response) {
				//console.log(response);
				for (var i = 0; i < response.length; i++) {
					var row = response[i];
					//console.log(row);
					var encNumId = row[9];
					var sno=i+1;
					tableData +="<tr ><td >"+sno+"</td>";
					tableData += "<td class='font_14' > <a class='bold' title='Click Here to View Proposal Details' onclick=viewProjectDetails('/PMS/mst/proposalDetails/"+row[9]+"')>" + row[1] + " </a></td>";
					tableData += "<td class='font_14'>"+ row[7] + "</td>"  ;
					tableData += "<td class='font_14'>"+ row[2] + "</td>"  ;
					tableData += "<td class='font_14' >"+ row[3] + "</td>";
					tableData += "<td class='font_14' > "+ row[5] + "</td>";
					tableData += "<td  class='currency-inr'>"+ row[6]+  "</td>";
					if(row[8]=='REV')tableData += "<td><span class='orange_bright'>Revision</span></td>";
					else if(row[8]=='SUB')tableData += "<td><span class='green' title='In case of edit new copy of proposal will be generated'>Submitted</span></td>";
					else if(row[8]=='SAD')tableData += "<td><span class='lightblue'>Save as Draft</span></td>";
					else tableData += "<td></td>";
					tableData += "<td> <div class='dropdown show'> <a class='btn btn-secondary dropdown-toggle' data-toggle='dropdown' onmouseover='viewApplicationStatus("+row[0]+")' aria-haspopup='true' aria-expanded='false'><i class='icon-th-large icon-1halfx blue pad-top' aria-hidden='true'></i>";
					tableData += "</a><ul class='dropdown-menu pull-right' aria-labelledby='dropdownMenuLink' id='"+row[0]+"'></ul></div></td> </tr>	";
			}
				$('#example > tbody').html('');
				$('#example').append(tableData);
				convertToLakhsForComponent(tableId)
				$('#example').DataTable({
					 "bLengthChange": false,

				 });
                       
				}
			
			});
});

//End
</script>
	
</body>
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/ApplicationList.js"></script>

</html>