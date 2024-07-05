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
	
	 <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <!-- DataTables Buttons CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/2.2.1/css/buttons.dataTables.min.css">
</head>
<style>

.style-select select {
  padding: 0;
}

.style-select select option {
  padding: 4px 10px 4px 10px;
}

.style-select select option:hover {
  background: #EEEEEE;
}

.add-btns {
  padding: 0;
}

.add-btns input {
  margin-top: 25px;
  width: 100%;
}

.selected-left {
  float: left;
  width: 88%;
}

.selected-right {
  float: left;
}

.selected-right button {
  display: block;
  margin-left: 4px;
  margin-bottom: 2px;
}

@media (max-width: 517px) {
  .selected-right button {
    display: inline;
    margin-bottom: 5px;
  }
}

.subject-info-box-1,
.subject-info-box-2 {
  float: left;
  width: 45%;
}

.subject-info-box-1 select,
.subject-info-box-2 select {
  height: 200px;
  padding: 0;
}

.subject-info-box-1 select option,
.subject-info-box-2 select option {
  padding: 4px 10px 4px 10px;
}

.subject-info-box-1 select option:hover,
.subject-info-box-2 select option:hover {
  background: #EEEEEE;
}

.subject-info-arrows {
  float: left;
  width: 10%;
}

.subject-info-arrows input {
  width: 70%;
  margin-bottom: 5px;
}

</style>
<body>

<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/global/js/MonthPicker.min.js"></script>

       

 <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12" style="padding: 65px; margin: 53px; margin-left: 0px;">
	<div class="panel panel-info panel-info1">
			<div class="panel-heading">
				<h4 class="text-center "> Project Report </h4>
		</div>
		<div class="panel-body text-center" style="margin-top: 26px;">					
				<div class="row pad-top " id="groupDiv">
					
						<div class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">
							<label class="label-class" style="font-size: 19px;">Group:<span style="color: red;">*</span></label>

						</div>
						<div class="col-md-5 col-lg-5 col-sm-5 col-xs-12" style="right: 8%; ">
    <div class="input-container">
        <select id="selectedProjectGroup" onchange="getAllProjectsDetails()" class="select2Option" multiple>
              
            <!-- <option value="-1">All Groups</option> --> 
            <c:forEach items="${groupList}" var="group">
                <option value="${group.numId}">${group.groupName}</option>
            </c:forEach>
        </select>
        <span style="margin-left: 2%;font-style: oblique;color: cadetblue;font-size: revert;">Select All</span>
        <span style="margin-left:1%;"><input class="group_check" id="AllGroup" type='checkbox' value='Select All' onchange="checkGroups()"></span>
        
      
            
    </div>
  

						</div>
						<div class="col-md-5 col-lg-5 col-sm-5 col-xs-12" style="right: 8%; ">
						<label class="label-class" style="font-size: 16px;">Select Range:</label>
						  <label class="font_16" for="from"><span style="color: #1578c2;" > &nbsp; &nbsp; </span>From</label> <input
											type="text" id="fromvalidated" name="from" readonly /> <label
											class="font_16" for="to">To</label> <input type="text"
											id="tovalidated" name="to" readonly /> &nbsp; &nbsp;
										<button type="button" class="btn btn-success go-btnvalidate pad-left">Go</button>
						
						</div>

						
					    <%-- <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12" style="right: 8%; ">
							<div class="input-container">
								<select id="selectedProjectGroup" onchange="getAllProjectsDetails()" class="select2Option">
								<option value="0">Select Group</option>
								<c:forEach items="${groupList}" var="group">
									<option value="${group.numId}">${group.groupName}</option>
								</c:forEach>
								</select>
							</div>
						</div> --%>
											                                        
				</div>														          
			</div>
			<div class="row style-select">
				<div class="col-md-12">
					<div class="subject-info-box-1" >
						<label>Available Project Field</label>
						<select multiple class="form-control"  id="lstBox1"  onchange="hiddentable()">
						 <option value="Serial">Serial No</option>
						 <option value="ProjectName">Project Name</option>
						 <option value="ReferenceNo">Project Reference No</option>
		                <option value="ClientName">Funding Organization</option>
		                <option value="ProjectManager">Project Manager</option>
		                <option value="StartDate">Start Date</option>
		                 <option value="EndDate">End Date</option>
						  <option value="WorkOrder">Work-Order Date</option>
						    <option value="MouDate">MoU Date</option>
						    <option value="ProjectType">Project Type</option>
						    <option value="ProjectCategory">Project Category</option>
						    <option value="ThrustArea">Thrust Area</option>
						    <option value="ProjectCost">CDAC Outlay</option>
						     <option value="ProjectDuration"> Project Duration</option>
						     <option value="TotalOutlay">Total Outlay</option>
						     <option value="ProjectDescription"> Project Description</option>
						      <option value="ProjectObjective"> Project Objective</option>
						       <option value="ProjectAim"> Project Aim</option>
						      
						       <option value="ValidationRemarks"> Validation Remarks</option>
						        <option value="ValidationStatus"> Validation Status</option>    
						      <option value="ProjectStatus"> Project Status</option>
						          <option value="ProjectClosureStatus"> Project closure Status</option>
							<option value="EmailCollaborative">Collaborative Organization Email</option> 
								<option value="ContactNumber">Collaborative Organization ContactNo</option> 
								<option value="OrganisationName">Collaborative Organization Name</option> 
								<option value="OrganisationAddress">Collaborative Organization Address</option> 
								<option value="Website">Collaborative Organization Website</option>     
								   
							</select>
						</div>
		                  
		                  <input class="hidden-input hidden" id="hiddenInputField">
		                  <input class="hidden-input hidden" id="hiddenField">
		                 <!--  <input class="value hidden" id="flagvalue"> -->
						<div class="subject-info-arrows text-center">		
						<br/>
						<br/>			
							 <input type='button' id='btnAllRight' value='>>' class="btn btn-default"/> 
							<input type='button' id='btnRight' value='>' class="btn btn-default"/>
							   <input type='button' id='btnLeft' value='<' class="btn btn-default"/>
							 <input type='button' id='btnAllLeft' value='<<' class="btn btn-default"/>   
						</div>
		
						<div class="subject-info-box-2">
							<label>Selected Project Field</label>
							<select multiple class="form-control" name="lstBox2" id="lstBox2" onchange="hiddenremovetable()"> 
								
							</select>
						</div>
		
						<div class="clearfix"></div> 
					</div>
				</div>	
			
			</div>
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top hidden" id ="allviewprojectdiv" style="overflow-x: auto;">
							
								<table id="AllviewProject_datatable"
									class="table table-striped table-bordered">
									<thead class="datatable_thead bold ">
										<tr>
											<th class="hidetable Serial hidden" width="2%">S.No</th>
											<th class="hidetable ProjectName hidden" width="5%">Project Name</th>
											<th class="hidetable ReferenceNo hidden" width="5%">Project Reference No</th>
											<th class="hidetable ClientName hidden" width="5%">Funding Organization</th>
											<th class="hidetable ProjectManager hidden" width="5%">Project Manager</th>
											<th class="hidetable StartDate hidden" width="4%">Start Date</th>
											<th class="hidetable EndDate hidden" width="4%">End Date</th>
											<th class="hidetable WorkOrder hidden" width="4%">Work-Order Date</th>
											<th class="hidetable MouDate hidden" width="4%">MoU Date</th>
											<th class="hidetable ProjectType hidden" width="4%">Project Type</th>
											<th class="hidetable ProjectCategory hidden" width="4%">Project Category</th>
											<th class="hidetable ThrustArea hidden" width="4%">Thrust Area</th>
											<th class="hidetable ProjectCost hidden" width="4%">CDAC Outlay</th>
											<th class="hidetable ProjectDuration hidden" width="4%">Project Duration</th>
										    <th class="hidetable TotalOutlay hidden" width="4%">Total Outlay </th>
										    <th class="hidetable ProjectDescription hidden" width="4%">Project Description </th>
										      <th class="hidetable ProjectObjective hidden" width="4%">Project Objective </th>
										       <th class="hidetable ProjectAim hidden" width="4%">Project Aim </th>
										      
										      <th class="hidetable ValidationRemarks hidden" width="4%">Validation Remarks </th>
										      <th class="hidetable ValidationStatus hidden" width="4%">Validation Status </th>
											<th class="hidetable ProjectStatus hidden" width="4%">Project Status </th>
											<th class="hidetable ProjectClosureStatus hidden" width="4%">Project Closure Status </th>
											<th class="hidetable EmailCollaborative hidden" width="4%">Collaborative Organization Email</th>
											<th class="hidetable ContactNumber hidden" width="4%"> Collaborative Organization ContactNo</th>
											<th class="hidetable OrganisationName hidden" width="4%">Collaborative Organization Name </th>
											 <th class="hidetable OrganisationAddress hidden" width="4%">Collaborative Organization Address </th>
											 <th class="hidetable Website hidden" width="4%">Collaborative Organization Website </th>
											
										
										</tr>
									</thead>
									
									<tbody class="">
									</tbody>
							
								</table>
								
							</div>
	</div>
	



			
									<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/projectDetailsReport.js"></script>	
										<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/new/jquery.selectlistactions.js"></script>
										
										<script type="text/javascript" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
										<script type="text/javascript" src="https://cdn.datatables.net/buttons/2.2.1/js/dataTables.buttons.min.js"></script>
										<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
							<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
							<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
							<script type="text/javascript" src="https://cdn.datatables.net/buttons/2.2.1/js/buttons.html5.min.js"></script>
										</body>
										</html>
										