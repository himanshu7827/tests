projectDetails new

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/OrgChart/jquery.orgchart.css"> -->
</head>
<style>
</style>

<body>
	<div tabindex='1' id="mainDiv"></div>
	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>

						<li class="active"><spring:message
								code="proposal.report.documentDetail" /></li>
					</ol>

				</div>
			</div>

			<div class="row pad-bottom">

<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
							<div class="panel panel-info  ">
							<div class="panel-heading bold font_16">Document History</div>
							<div class="panel-body">
						<table class="table table-bordered bg-white">
							<thead class="datatable_thead">
								<tr >
									<th class="bold " style="width:10%">Document Date</th>
									<th class=" bold " style="width:10%">Valid From</th>
									<th class=" bold " style="width:10%">Valid Upto</th>
									<th class=" bold " >Description</th>
									<th class=" bold ">Download</th>
								 </tr>
							
							</thead>
							<tbody>
							<c:forEach items="${proposalDocument}" var="document">
								<tr>
									
									<td class="font_16 ">${document.documentDate}</td>									
									<td>${document.periodFrom}</td>									
									<td>${document.periodTo}</td>									
									<td>${document.description}</td>									
									<td><c:forEach items="${document.detailsModels}"
											var="details">
											<span class="blue"><a onclick=downloadProposalFile("${details.encNumId}")>
												<c:choose>
													<c:when test="${empty  details.icon}">
														<i class="fa fa-download" aria-hidden="true"></i>
													</c:when>
													<c:otherwise>${details.icon} </c:otherwise>
												</c:choose>
													
													</a> </span>
										</c:forEach>
									</td>
								</tr>
							</c:forEach>	
							</tbody>
							
						</table>							
						</div>
						</div>
						</div>
					</div>
				</div>

	</section>

	
</body>
</html>