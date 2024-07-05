<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/MonthPicker.min.css"> 
</head>
<body>

<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script>

	 
	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Generate Report</li>
					</ol>
				</div>
			</div>	
			<input type="hidden" id="month" value=""/>
			<input type="hidden" id="year" value=""/>
						<div class="row "></div>
				
				<!-- for flashing the success message -->
			
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
       
        			             <div class="container">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="panel panel-info panel-info1">
									<div class="panel-heading panel-heading-fd">
										<h4 class="text-center ">Generate Report</h4>
									</div>
									<div class="panel-body text-center">
						
						
										
										
			
						
							
<!-- Drop down for Group -->
			<div class="row pad-top " id="groupDiv">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"> Group:<span
														style="color: red;">*</span></label>
							</div>
			                 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
													<select id="selectedGroup"   class="select2Option" multiple="true">
													<c:forEach items="${groupList}" var="group">
														<option value="${group.numId}">${group.groupName}</option>
													</c:forEach>
												</select>
										<p style="margin-left: 3%;font-style: oblique;color: cadetblue;font-size: revert;">Select All</p>		<input class="group_check" id="AllGroup" type='checkbox' style="margin-left: 3%;margin-right: 3%;" value='Select All' onchange="checkGroups()">
													</div>
													

						   	</div>
						   
						                           </div>
						                                        
						          </div>
						          <div class="row pad-top " id="">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
						          	<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"> Employee Contract:<span
														style="color: red;">*</span></label>
							</div>
						   	    <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
													<select id="selectYear"  class="select2Option" >
													
														<option value=">-1">less than 1 Year</option>
														<option value=">-2">less than 2 Year</option>
														<option value=">-3">less than 3 Year</option>
														<option value="<-3">beyond 3 Year</option>
													
												</select>
										
													</div>
													
</div>
</div>
						   	</div>

						          				<!-- Drop down for Severity -->
						          				<input type="button" class="btn btn-primary font_14" id="Generate" onclick="generateReport()" value="Get Report">
													<input type="button" class="btn btn-primary font_14" onclick="resetData()" value="Reset"/>
						          				</div>
						          				
						          				</div>
						          				
						          				</div>
						          				</div>
						          			

										</div>
										</section>
									<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/generateEmployeeDetails.js"></script>	
									
										</body>
										</html>
										