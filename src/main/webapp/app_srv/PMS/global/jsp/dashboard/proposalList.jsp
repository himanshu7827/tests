<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


</head>



<body>
	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Proposal List</li>
					</ol>
				</div>
			</div>
			<div class="container">
			<div class="row">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-bottom">
					<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen"><p><spring:message code="dashboard.proposalList.label" /></p>
						<p><span class="font_12">(<spring:message code="dashboard.proposal.convertedToProject.label" />) </span></p>
						</div>
							<div class="panel-body">
							<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12 groupdiv"
							id="groupdiv">							
								<c:forEach items="${groupnames}" var="grp">
									<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2">
										<button class="btn btn1 "
											data-toggle="tooltip" data-placement="top"
											title="Click here to see details"
											style="font-size:17px; font-weight:600;background-color:${grp.bgColor}" onclick="loadGroupWiseProposals('${grp.encGroupId}')"><c:out value="${grp.groupName}"/></button>
									</div>
									</c:forEach>

						</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
								<div class="table-responsive">
								<table id="proposalList_dataTable" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th scope="col"><spring:message code="dashboard.serial.shortcode.label" /></th>
										<th><spring:message code="proposal.proposalTitle" /></th>
										<th><spring:message code="dashboard.group.label" /></th>
										
									</tr>
								</thead>
								<thead class="filters ">
								<tr> 
								<th ><spring:message code="dashboard.serial.shortcode.label" /></th>   
								<th class="comboBox" id="groupSelect"><spring:message code="dashboard.group.label" /></th>                                                  
									<th class="textBox"><spring:message code="proposal.proposalTitle" /></th>				
									
			
								</tr>
								</thead>
								<tbody>
									<c:forEach items="${activeGroupData}" var="groupData" varStatus="loop">
									<tr>
										<td style="width:2%" ><c:out value="${loop.index+1}"/> </td>
										<td class="font_14" style="width:10%"><c:out value=" ${groupData.groupName}"/> </td>
										<td class="font_14 "><c:out value=" ${groupData.proposalTitle}"/> </td>
										
										</tr>
									</c:forEach>
								</tbody>
								</table>
							</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	</section>


	 
	 


	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/proposalList.js"></script>


</body>
</html>