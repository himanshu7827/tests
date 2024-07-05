
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
			<li class="active">Proceedings</li>
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
<p><span class="bold  font_16 ">Group: </span> <span class="bold blue font_16"><i>${monthlyProgressModel.strGroupName}</i></span></p>
<c:if test="${Type == 'project'}">
<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${monthlyProgressModel.strProjectName}</i></span></p>
</c:if>
<p><span class="bold  font_16 ">For : </span><span class="bold orange font_16"><i>${monthlyProgressModel.strMonth}-${monthlyProgressModel.year}</i></span></p>
</div>
</div>
</div> 
		<%-- 	<sec:authorize access="hasAuthority('WRITE_COPYRIGHT')"> --%>	
		
		<form:form id="form1" modelAttribute="ProceedingModel" method="post">
				
	<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Proceedings</h4></div>
					</div>
					</div>
					</div>
		</form:form>
<%--  </sec:authorize>  --%>
	<script>
	
	$(document).ready(function() {
	    var table = $('#example').DataTable( {
	    	"paging":   false,
	        "ordering": false,
	        "searching": false,
	        rowReorder: true
	    } );
	} );
	
	</script>
<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">
<table id="example" class="table table-striped table-bordered display select dataTable"
				style="width: 100%">
				
				<thead class="datatable_thead bold ">
				 
					<tr id="row">
						<th class="width10">Role</th>
						<th class="width10">Action</th>
						<th>Date and Time</th>	
						<th>Remarks</th>
					</tr>
				</thead>
				<tbody class="ui-sortable">
						<c:forEach items="${data}" var="copy" varStatus="loop">
					 	<tr id="row-${loop.index}">
							<td><b>${copy.roleName}</b> <br>[${copy.strName}]</td>
							<td>${copy.strAction}</td>
							<td>${copy.strDateTime}</td>
						    
						    <c:choose>
												<c:when test="${empty copy.strRemarks}">
													<td class="center">-</td>
												</c:when>
												<c:otherwise>
													<td>${copy.strRemarks}</td>
												</c:otherwise>
											</c:choose>
						 
						    
						</tr> 
					</c:forEach>
					
				</tbody>
			
			</table>
	
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
</div>
</div>
	<div class="row pad-top center form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="close" onclick="javascript:window.close()">
							<span class="center"></span>Close
						</button>
					</div>
</section>

</body>
</html>