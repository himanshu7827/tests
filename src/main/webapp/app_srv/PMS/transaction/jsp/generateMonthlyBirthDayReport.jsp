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
						<li class="active">Generate Birthday Report</li>
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
										<h4 class="text-center ">Generate Birthday Report</h4>
									</div>
									<div class="panel-body text-center">
						
							<div class="row pad-top" id="monthandyear">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
														
															<label class="label-class"> Generate Birthday Report For Month:<span
														style="color: red;">*</span></label>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">												
														
															<div class="input-container">
														
															<input id="strMonthAndYear" class='Default ' type="text" placeholder=""/> 
														
															</div>
														
													</div>
												</div>
										</div>
														          
						
							
<!-- Drop down for Group -->
			

						          				<!-- Drop down for Severity -->	
						          				<div class="row pad-top double">									
						          				<input type="button" class="btn btn-primary font_14" id="Generate" onclick="generateReport()" value="Get Report">
													<input type="button" class="btn btn-primary font_14" onclick="resetData()" value="Reset"/>
						          				</div>
						          				
						          				</div>
						          				
						          				</div>
						          				</div>
						          			

										</div>
										</section>
									<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/generateMonthlyBirthDayReport.js"></script>	
									
										</body>
										</html>
										