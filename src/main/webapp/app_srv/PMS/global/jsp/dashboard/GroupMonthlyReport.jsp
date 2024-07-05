
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/MonthPicker.min.css"> 
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script> 
</head>


<body>
	<div tabindex='1' id="mainDiv"></div>
	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>

						<li class="active"><spring:message
								code="project.report.groupMonthReport" /></li>
					</ol>

				</div>
			</div>
<div class="row pad-bottom">


		</div>
			<div class="row pad-bottom ">
    <div class="col-md-12"> 
      <!-- Nav tabs -->
      <div class="card">
              

     
        <!-- Tab panes -->
        <div class=" ">

          <div role="" class="" >
          <input type="hidden" value="${group}" id="groupId">
          <input type="hidden" id="monthlyParentId" value=""/>
	         <c:forEach items="${categoryList}" var="categoryList">
				<input type="hidden" id="isProjectBasedCat_${categoryList.numCategoryId}" value="${categoryList.strProjectGroupFlag}"/>
				<input type="hidden" id="strCatgController_${categoryList.encCategoryId}" value="${categoryList.strCategoryController}"/>
			</c:forEach> 
          
          
          <div class="pad-top pad-bottom"></div>
          	
											<div class="row pad-top">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<p  class="label-class">Group Report For Month:<span style="color: red;">*</span><i><span class="magenta " id="displayMonth"></span> <span  class="magenta pad-right " id="displayYear"></span></i>
															</p>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">													
														
															<p class="font_14 bold">
														
															<input id="strMonthAndYear" class='Default ' type="text" placeholder=""/> 
														
															    </p>
														
													</div>
												</div>
										</div>
										
									<div class="row pad-top">
										
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group "id="">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
															<label class="label-class"><spring:message
																code="label.progress.category" />:<span
															style="color: red;">*</span></label>
													</div>
													
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container">
															<select  id="numCategoryId" class="select2Option">
																<option value="0">----Select Category----<option>
																<c:forEach items="${categoryList}" var="categoryList">
																	<option value="${categoryList.encCategoryId}">
																		${categoryList.strCategoryName}
																	</option>
																</c:forEach>
																
													</select>
													</div>
													</div>
	</div>
													
													
										
										</div>
										
									<!-- <div class="row pad-top pad-bottm double form_btn center">
<button type="button" class="btn btn-primary font_14" onclick="monthlyReport()">Save/Update Details</button>
<button type="button" class="btn btn-primary font_14" id="save" onclick="previewOfReport()">Preview</button>										
										
									</div>  -->	
									<div class="row pad-top form_btn center" id="nextAction"></div>
												
										</div>
						
					
          </div>
          
          
           
          
			
		</div>

      </div>
    </div>


			
			</div>
	
		
		
			<!-- Modal For client Details -->
	
	
<!-- 	Modal for history -->
			<!-- Modal For client Details -->
	
	
	<!-- Modal for Add Document -->
			
	
	
	
	</section>
<script>
// Get the modal
var modal = document.getElementById('history');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}
</script>
	 <script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/jsp/dashboard/js/GroupMonthlyReport.js"></script> 
		<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/progressReportWorkflow.js"></script>	
		 
</body>
</html>