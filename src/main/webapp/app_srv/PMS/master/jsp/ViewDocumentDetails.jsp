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
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/OrgChart/jquery.orgchart.css">
	
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/easyTree/css/easyTree.css">
	
<style >

.amount_receive_deatil_modal .modal-dialog.modal-lg {
	padding-top: 10%;
}

#tooltip{
    display: inline;
    position: relative;
}

#tooltip:hover:after{
    background: #333;
    background: rgba(0,0,0,.8);
    border-radius: 5px;
  color: #fff;
    content: attr(title);
     padding: 5px 15px;
    position: absolute;
    z-index: 98;
    width: 220px;
}

 #tooltip:hover:before{
    border: solid;
    border-color: #333 transparent;
    border-width: 6px 6px 0 6px;
    bottom: 20px;
    content: "";
    left: 50%;
    position: absolute;
    z-index: 99;
} 

#team-modal{
	padding-top: 5%;
	
	}
</style>

</head>
<body>

<c:choose>
		<c:when test="${showWrapper != 0}">
		<section id="main-content" class="main-content merge-left">
			<div class=" container wrapper1">
				<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
		
			<li class="active">View Project Document Details</li>
		</ol>
	</div>
</div>
		</c:when>
		<c:otherwise>
		<section id="main-content" class="merge-left">
			<div class=" container-fluid wrapper pad-top">
			
		</c:otherwise>
	</c:choose>
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

			<legend class="bold legend_details">Details :</legend>
		<!-- 
			<table id="example" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th style="width:5%;"><spring:message code="serial.no"/></th>
 					    <th style="width:12%;"><spring:message code="document.documentName"/></th>
  					    <th style="width:12%;"><spring:message code="docupload.document.version"/></th>
						<th style="width:10%;"><spring:message code="docupload.document.Date"/></th>						
						<th style="width:40%;"><spring:message code="master.description"/></th>
						<th style="width:10%;"><spring:message code="task.download" /></th>
		
					    
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${projectDocument}" var="documentdetail" varStatus="theCount">
						<tr >
						    <td>${theCount.count}</td>
						    <td ><p class="bold">${documentdetail.documentTypeName}</p>
						    <c:choose>
						    <c:when test="${documentdetail.periodFrom != null}">
						   
						    <p><span class="green">${documentdetail.periodFrom}</span>&nbsp;&nbsp;
						    <span class="red">Valid to: ${documentdetail.periodTo}</span></p>
						    </c:when>
						    <c:otherwise>
						    <p><span class="green">${documentdetail.periodFrom}</span>&nbsp;&nbsp;
						    <span class="red">${documentdetail.periodTo}</span></p>
						    </c:otherwise>
						    </c:choose>
						    </td>
    						<td>${documentdetail.documentVersion}<br>
    						<div style='float: right;'>
							<a  onclick="showProjectDocumentRevision('${documentdetail.encNumId}')">
							<spring:message code="record.show.history" />
							</a>
							</div></td>
							<td>${documentdetail.documentDate}</td>
						
 							<td>${documentdetail.description}</td>
							<td align="center">
								<c:forEach items="${documentdetail.detailsModels}" var="details" varStatus="theCount">
								
								<c:choose>
									<c:when test="${documentdetail.uploadedFor == 'Proposal'}">
										<span> <a onclick=downloadProposalFile("${details.encNumId}")>${details.icon}</a> </span>
									</c:when>
									<c:otherwise>
									<span> <a onclick=downloadDocument("${details.encNumId}")>${details.icon}</a> </span>
									</c:otherwise>
								</c:choose>							
								
								</c:forEach>
							
 							
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			
			
			<br/>
		 -->
			<br/>		
			 <div class="easy-tree">
			 <ul><li>Project Documents
			 	<ul>
					<c:forEach items="${categorywiseDocuments}" var="category" varStatus="categoryLoop">					   
					    <li id="category_${categoryLoop.index}">${category.key}
					    	<ul>
						    	<c:forEach items="${category.value}" var="document" >
						    		<li>${document.key}
						    			<ul>
						    				<c:forEach items="${document.value}" var="doc">
						    					<li> ${doc.description} [${doc.documentDate}] 
						    					 <c:choose>
												    <c:when test="${doc.periodFrom != null}">												   
												    <p><span class="green">${doc.periodFrom}</span>&nbsp;&nbsp;
												    	<span class="red">Valid to: ${doc.periodTo}</span></p>
												    </c:when>
												    <c:otherwise>
												    	<p><span class="red">${doc.periodTo}</span></p>
												    </c:otherwise>
												  </c:choose>
												    
												    <c:forEach items="${doc.detailsModels}" var="details" varStatus="theCount">								
														<c:choose>
															<c:when test="${doc.uploadedFor == 'Proposal'}">
																<span> <a onclick=downloadProposalFile("${details.encNumId}")>${details.icon}</a> </span>
															</c:when>
															<c:otherwise>
															<span> <a onclick=downloadDocument("${details.encNumId}")>${details.icon}</a> </span>
															</c:otherwise>
														</c:choose>							
														
													</c:forEach>
												    
												    </li>						    				
						    				</c:forEach>
						    			</ul>
						    		</li>
						    	</c:forEach>
					    	</ul>
					    </li>
					</c:forEach>
				</ul>
			</li></ul>
			</div>
			
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>

</div>

</section>
<!-- <script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/projectDetails.js"></script> -->	
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/easyTree/js/easyTree.js"></script>
	
			
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/projectDetails.js"></script>
	
   <script>	
			$(document).ready(function() {
				 $('.easy-tree').EasyTree();
				 $("li[id^='category_']").find('span:first').trigger('click');
			}) 
			
		
			
		</script>	
			
			
			
</body>


</html>