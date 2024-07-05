<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<style>
.wrapper1 {
    display: inline-block;
    margin-top: 80px;
    width: 100% !important;
}

.oddRow {
	background-color: #c2c3ea;
}

#editor {
	height: 200px;
	/* border: 1px solid #000; */
	border-radius: 5px;
	OVERFLOW: scroll;
	border-bottom: 2px solid #000;
	border-left: 1px solid #000;
	border-right: 2px solid #000;
	border-top: 1px solid #000;
}


.btn.dropdown-toggle:after{
content:none !important;
}

.btn-group>.btn, .btn-group>.dropdown-menu, .btn-group>.popover {
    font-size: 16px !important;
}
.pad-14{
padding-top: 14px !important;
    padding-bottom: 14px !important;
}
 .header{
height:90px;
} 
</style>
<link href="/PMS/resources/app_srv/PMS/global/editor/prettify.css"
	rel="stylesheet" />
<link
	href="/PMS/resources/app_srv/PMS/global/editor/bootstrap-combined.no-icons.min.css"
	rel="stylesheet" />
<link href="/PMS/resources/app_srv/PMS/global/editor/font-awesome.css"
	rel="stylesheet" />
<link href="/PMS/resources/app_srv/PMS/global/editor/editor.css"
	rel="stylesheet" />

<link href="/PMS/resources/app_srv/PMS/global/css/switchery.css" rel="stylesheet" />
<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/editor/jquery-labelauty.css"/>
<script src="/PMS/resources/app_srv/PMS/global/editor/jquery-labelauty.js"></script>
<script src="/PMS/resources/app_srv/PMS/global/js/sweetalert2.min.js"></script>
<link  rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css//sweetalert2.css" />
<script src="/PMS/resources/app_srv/PMS/global/js/jquery.fileupload.js"></script>


</head>
<script>
var filterArray=[];
</script>
<body>

	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<li class="active">Create Newsletter</li>
					</ol>
				</div>
			</div>
			<div class="row "></div>
			<c:if test="${message != null && message != ''}">
				<c:choose>
					<c:when test="${status=='success'}">
						<div id="userinfo-message">
							<p class="success_msg">${message}</p>
						</div>
					</c:when>
					<c:otherwise>
						<div id="userinfo-message">
							<p class="error_msg">${message}</p>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>
     
			<form action="" method="post" modelAttribute="" id="newsLetter">

			<input type="hidden" id="nletterid"></input> <input type="hidden"
			value="" id="documentIDs" />
			<img id="displayBox"  src="/PMS/resources/app_srv/PMS/global/images/gears.svg" style="display:none" alt="Loading" >
<!-- /PMS/src/main/webapp/app_srv/PMS/global/images/gears.svg -->
				<div class="container">
				
				
				<div class="row pad-top pad-left pad-bottom double">
					<div class="col-md-4">
						<input type="radio" name="stream" value="User" id="user"> Send News Letter With User Details
					</div>
					
					<div class="col-md-4">
						<input type="radio" name="stream" value="PropF" id="allproposal"> Send News Letter With Proposal Details
					</div>
				</div>
<div class="whole  pad-top pad-bottom" id="PropDetail">
<div class="col-md-3 col-lg-3 col-sm-2 col-xs-12 form-group ">	</div>
<div class="col-md-6 col-lg-6 col-sm-2 col-xs-12 form-group pad-left" >	
<div class="col-md-5 col-lg-5 col-sm-2 col-xs-12 form-group ">
				<label><b>Project Details to add :</b></label>
			</div>
			<div class="col-md-5 col-lg-5 col-sm-12 col-xs-12 form-group ">
				<select id="proID" class="select2Option">
					<option value="select">Select Any Option </option>
					<option value="$PROJECT_NAME$">Project Name </option>
					<option value="$PROJECT_COST$">Project Cost</option>
					<option value="$PROJECT_START_DATE$">Project Start Date </option>
					<!-- <option value="$PROJECT_DURATION$">Project Duration </option> -->
					<option value="$PROJECT_END_DATE$">Project End Date</option>
					<option value="$CURRENT_MONTH$">Current Month</option>
					<option value="$CURRENT_YEAR$">Current Year</option>
				</select>
			
					</div>
				<div class="col-md-1 col-lg-1 col-sm-12 col-xs-12 form-group ">
			<input type="button" id="Use" value="Use" class="blue gap-right gap-top" name="Use"/></div>	
			</div>	
			<div class="col-md-4 col-lg-4 col-sm-2 col-xs-12 form-group ">
</div> 
</div>
</div>
				<br>
				

			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group pad-left">
								<div class="col-md-1 col-lg-1 col-sm-6 col-xs-12">
									 <label class="label-class">To :</label> 
								</div>
								<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12">
								
								<%-- <form:hidden path="roleName" id="roleName" value=""/> --%>
									<div class="input-container">
								<%-- 	<form:hidden path="to"/>
									<form:hidden path="cc"/>
									<form:hidden path="bcc"/> --%>
										<select id="roleTo" class="select2Option" multiple="multiple">
											<option value="0">Select Role</option>
											<c:forEach items="${roleList}" var="role">
												<option value="${role.numId}">
													<c:out value="${role.roleName}"></c:out>
												</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="col-md-1 col-lg-1 col-sm-6 col-xs-12">
									 <label class="label-class">Cc :</label> 
								</div>
								<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12">
								
								<%-- <form:hidden path="roleName" id="roleName" value=""/> --%>
									<div class="input-container">
										<select id="roleCc" class="select2Option" multiple="multiple">
											<option value="0">Select Role</option>
											<c:forEach items="${roleList}" var="role">
												<option value="${role.numId}">
													<c:out value="${role.roleName}"></c:out>
												</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
											
								
								<div class="col-md-1 col-lg-1 col-sm-6 col-xs-12">
									 <label class="label-class">Bcc :</label> 
								</div>
								<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12">
								
								<%-- <form:hidden path="roleName" id="roleName" value=""/> --%>
									<div class="input-container">
										<select id="roleBcc" class="select2Option" multiple="multiple">
											<option value="0">Select Role</option>
											<c:forEach items="${roleList}" var="role">
												<option value="${role.numId}">
													<c:out value="${role.roleName}"></c:out>
												</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
				<div class="row pad-left pad-bottom">
					<div class="col-md-1 label-class">Subject:</div>
					<div class="col-md-5">
						<input id="subject" type="text">
					</div>
				</div>
				
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							
							<div class="panel-body text-center">


								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-4 col-lg-4 col-sm-4 col-xs-12 text-justify">

											

										</div>
									</div>
								</div>
								<div class="row">
									<!-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">						
											
   											 <textarea id="edit3" ></textarea>
										</div> -->

									<!-- ADDED FOR TEXT EDITOR -->
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
										<div class="hero-unit">
										
											<div id="alerts"></div>
											<div class="btn-toolbar" data-role="editor-toolbar"
												data-target="#editor">
												<div class="btn-group">
													<a class="btn dropdown-toggle" data-toggle="dropdown"
														title="Font"><i class="icon-font"></i>&nbsp;<b class="caret"></b></a>
													<ul class="dropdown-menu" style="height:350px; overflow-y:scroll;">
													</ul>
												</div>
												<div class="btn-group">
													<a class="btn dropdown-toggle" data-toggle="dropdown"
														title="Font Size"><i class="icon-text-height"></i>&nbsp;<b
														class="caret"></b></a>
													<ul class="dropdown-menu">
														<li><a data-edit="fontSize 5"><font size="5">Huge</font></a></li>
														<li><a data-edit="fontSize 3"><font size="3">Normal</font></a></li>
														<li><a data-edit="fontSize 1"><font size="1">Small</font></a></li>
													</ul>
												</div>
												<div class="btn-group">
													<a class="btn pad-14" data-edit="bold" title="Bold (Ctrl/Cmd+B)"><i
														class="icon-bold"></i></a> <a class="btn pad-14" data-edit="italic"
														title="Italic (Ctrl/Cmd+I)"><i class="icon-italic"></i></a>
													<a class="btn pad-14" data-edit="strikethrough"
														title="Strikethrough"><i class="icon-strikethrough"></i></a>
													<a class="btn pad-14" data-edit="underline"
														title="Underline (Ctrl/Cmd+U)"><i
														class="icon-underline"></i></a>
													<!-- 	<div id="froala-editor"></div> -->
												</div>
												<div class="btn-group">
													<a class="btn pad-14" data-edit="insertunorderedlist"
														title="Bullet list"><i class="icon-list-ul"></i></a> <a
														class="btn pad-14" data-edit="insertorderedlist"
														title="Number list"><i class="icon-list-ol"></i></a> <a
														class="btn pad-14" data-edit="outdent"
														title="Reduce indent (Shift+Tab)"><i
														class="icon-indent-left"></i></a> <a class="btn pad-14"
														data-edit="indent" title="Indent (Tab)"><i
														class="icon-indent-right"></i></a>
												</div>
												<div class="btn-group">
													<a class="btn pad-14" data-edit="justifyleft"
														title="Align Left (Ctrl/Cmd+L)"><i
														class="icon-align-left"></i></a> <a class="btn pad-14"
														data-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i
														class="icon-align-center"></i></a> <a class="btn pad-14"
														data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i
														class="icon-align-right"></i></a> <a class="btn pad-14"
														data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i
														class="icon-align-justify"></i></a>
												</div>																								
												<div class="btn-group">
													<a class="btn pad-14" data-edit="undo" title="Undo (Ctrl/Cmd+Z)"><i
														class="icon-undo"></i></a> <a class="btn pad-14" data-edit="redo"
														title="Redo (Ctrl/Cmd+Y)"><i class="icon-repeat"></i></a>
												</div>

											</div>

											<div id="editor" contentEditable="true" style="min-height:250px !important;"></div>
										</div>
									</div>
								</div>

								<!-- END FOR TEXT EDITOR -->
								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-4 col-lg-4 col-sm-4 col-xs-12 text-justify">
										</div>
									</div>
								</div>
																	
								</div>
					<div class="row  pad-left double pad-bottom double">
						<div class="col-md-3">Send SMS Notification ?</div>
						<div class="col-md-1 align-left">
							<input type="checkbox" class="demo" value="Y" id="smsalert"/>
						</div>
						<div class="col-md-6 aling-left">
							<div id="smsDiv" class="gap-bottom double ">
								<textarea id="smsText" placeholder="SMS Content goes here..." style="margin-top: 0px; margin-bottom: 0px; height: 150px;width:500px"></textarea>
								<input type="hidden" value="N" id="smsInput">
							</div>
						</div>
					</div>	
					
			<div class="row pad-top pad-left double pad-bottom double">
			<div class="col-md-3 pad-top"> <span class="periodic_NL " title="">Send NewsLetter Periodically?</span></div>
			<div class="col-md-1 align-left pad-top ">
				<input type="checkbox" class="demo " value="Y" id="isPeriodic" />
			</div>
			<div id="datePicker" class="col-md-6 col-lg-6 col-sm-12 col-xs-12 text-center NewsletterDate ">
								
				<div id="scheduleTime" class="col-md-5 blue_heading ">
					<label> Date of Newsletter to be send periodically:<span class="red bold">*</span>
					</label>
				</div>
				
				<div >
				<div class="col-md-2">
<select name="DOBDay" id="day" >
	<option value="00"> - Day - </option>
	<option value="01">1</option>
	<option value="02">2</option>
	<option value="03">3</option>
	<option value="04">4</option>
	<option value="05">5</option>
	<option value="06">6</option>
	<option value="07">7</option>
	<option value="08">8</option>
	<option value="09">9</option>
	<option value="10">10</option>
	<option value="11">11</option>
	<option value="12">12</option>
	<option value="13">13</option>
	<option value="14">14</option>
	<option value="15">15</option>
	<option value="16">16</option>
	<option value="17">17</option>
	<option value="18">18</option>
	<option value="19">19</option>
	<option value="20">20</option>
	<option value="21">21</option>
	<option value="22">22</option>
	<option value="23">23</option>
	<option value="24">24</option>
	<option value="25">25</option>
	<option value="26">26</option>
	<option value="27">27</option>
	<option value="28">28</option>
	<option value="29">29</option>
	<option value="30">30</option>
	<option value="31">31</option>
</select>
</div>
				<div class="col-md-2">
					
				
				
		<select name="DOBMonth" id="month" >
	<option value="000"> - Month - </option>
	<option value="JAN">January</option>
	<option value="Feb">Febuary</option>
	<option value="Mar">March</option>
	<option value="Apr">April</option>
	<option value="May">May</option>
	<option value="Jun">June</option>
	<option value="Jul">July</option>
	<option value="Aug">August</option>
	<option value="Sep">September</option>
	<option value="Oct">October</option>
	<option value="Nov">November</option>
	<option value="Dec">December</option>
</select>
</div>

<div class="col-md-2">

<select name="DOBYear" id="year" >
	<option value="0000"> - Year - </option>
	<option value="2017">2017</option>
	<option value="2018">2018</option>
	<option value="2019">2019</option>
	<option value="2020">2020</option>
	<option value="2021">2021</option>
	<option value="2022">2022</option>
	
</select>
  </div>
  <div class="col-md-2"></div>
    
    
					
				</div>
			</div>
</div>

<div id="one-time" class="row pad-top pad-left double pad-bottom double ">



			<div class="col-md-3  align-left"><span class="oneTime_NL" title="">Send News Letter Later (One-Time) ?</span></div>
			<div class="col-md-1 align-left">
				<input type="checkbox" class="demo" value="Y" id="scheduledAt"
					/>
			</div>
			 <div class="col-md-6 col-lg-6 col-sm-12 col-xs-12 NewsletterDate text-center">
				<div class="col-md-4 blue_heading">
					<label> Date of Newsletter:<span class="red bold">*</span>
					</label>
				</div>
				<div class="col-md-4">
					<input size="4" type="text" id="nsaDateID"
						placeholder="dd month,yyyy" autocomplete="off" style="width: 96%"></input>
				</div>
			</div> 
			<!-- end of third row-->
		</div>
		
		<div class="row pad-top pad-left double pad-bottom double" id= "foruser">
			<div class="col-md-3  align-left">Send News Letter to All
				Subscribed users ?</div>
			<div class="col-md-1 align-left">
				<input type="checkbox"  value="Y" class='demo' id="alluser"  />
			</div>
	
		</div>
		
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group pad-top pad-left double pad-bottom">
			<div id="PropDiv" class="  pad-bottom">
				<table class="table table-striped table-bordered" data-filter="#filter" id="filterTabP">
					<thead class="datatable_thead bold ">
						<tr>
							<th style="width:25%">Select Filter(s)</th>
							<th>Description</th>
							<!-- <th>TYPE</th> -->
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${FilterList}" var="list1">
							<c:if test="${list1.strFilterType =='P'}">
							<!-- <tr><td>p</td></tr> -->
								 <tr class="P">							
								<td class="nobreak"><input type="checkbox"
									id="${list1.numFilterId}" value="${list1.numFilterId}"
									class="stylish pCheck" data-labelauty="${list1.strFilterName}" />
									
									</td>
								<td class="nobreak">${list1.strFilterDesc}</td>
								
							</tr>
							</c:if>						
						</c:forEach>
					</tbody>
		   			
				</table>
			</div>
		</div>

		<div class="whole padded double">
			<div id="alluserDiv" class="whole gap-bottom double ">
				<table class="table table-striped table-bordered" data-filter="#filter" id="filterTabU" >
					<thead class="datatable_thead bold ">
						<tr>
							<th style="width:50%">Select Filter(s)</th>
							<th>Description</th>
						</tr>
					</thead>
					<tbody>
					 <script>
					filterArray=[];
					</script>
						<c:forEach items="${FilterList}" var="list1">
						<c:if test="${list1.strFilterType =='U'}">
						
							<tr class="U">	
								<td class="nobreak"><input type="checkbox"
									id="${list1.numFilterId}" value="${list1.numFilterId}"
									class="stylish" data-labelauty="${list1.strFilterName}" /></td>
								<td class="nobreak">${list1.strFilterDesc}</td>
								 <script>
								filterArray.push('${list1.numFilterId}')</script> 
							</tr>
							</c:if>		
						</c:forEach>
					</tbody>
		   		
				</table>
			</div>
		</div>
<div class="row pad-top pad-left double pad-bottom double">
			<div class="col-md-3 align-left">Attach files ?</div>
			<div class="col-md-1 align-left">
				<input type="checkbox" class="demo" value="N" id="attach" />
			</div>
			<div class="col-md-6 aling-left"></div>
		</div>
		<div id="attachDiv" class="row pad-bottom ">

			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
				<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 form-group"></div>
				<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 form-group">
					<label id="YearLabel">Upload File:</label>
				</div>
				<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 form-group">
					<input id="fileupload" type="file" name="files[]"
						data-url="/PMS/uploadNewsletterDocument"
						accept="application/pdf" />
				</div>
				<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 form-group"></div>
			</div>

			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
				<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 form-group"></div>
				<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 form-group">
					<label id="UploadStatus">Upload Status:</label>
				</div>
				<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 form-group">
					<div id="progress" class="progress">
						<div class="bar" style="width: 0%;"></div>
					</div>
				</div>
				<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12 form-group"></div>
			</div>

			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
				<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12 form-group">
					<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12 form-group">Search:</div>
					<div class="col-md-2 col-lg-2 col-sm-12 col-xs-12 form-group">
						<input id="filter" type="text" />
					</div>
				</div>
				<table id="dataTable" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
							<tr>
							<th>ID</th>

							<th>Document Name</th>

							<th>Delete</th>
							</tr>
								</thead>
								<tbody class="">
									
								</tbody>
							</table>
				<!-- <table class="footable blue" data-filter="#filter"
					data-page-size="5" id="dataTable">
					<thead>
						<tr>
							<th data-class="expand" data-sort-initial="true">ID</th>

							<th data-hide="phone">Document Name</th>

							<th data-hide="phone">Delete</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
					<tfoot class="footable-pagination">
						<tr>
							<td colspan="7"><ul id="pagination" class="footable-nav" /></td>
						</tr>
					</tfoot>
				</table> -->
			</div>
			<!--end of div before table-->
		</div>
								
								
								<div class="row pad-top center  form_btn">
							<div id="buttonsave">
								<button type="button" class="btn btn-primary font_14" id="Send" value="Save Newsletter" class="blue gap-right gap-top" name="Send">
									<span class="pad-right"><i class="fa fa-folder"></i></span>Save Newsletter
								</button>
							<input type="button" class="btn btn-orange font_14" value="Close" name="Ok" class="blue gap-right gap-top" onclick="window.close()"/>
							
							<button type="button" class="btn btn-success font_16" id="Update" value="Update" class="blue gap-right gap-top" name="Update" style="display: none">
									<span class="pad-right"><i  aria-hidden="true"></i></span>Update
							</button>
							</div>
							<%-- <div id="buttonupdate">
								<button type="button" class="btn btn-primary font_16"
									id="update">
									<span class="pad-right"><i class="fa fa-folder"></i></span>Update
								</button>
							<input type="button" class="btn btn-orange font_14" id="back" onclick="backToMainPage('${monthlyProgressModel.encProjectId}','${encCategoryId}','${monthlyProgressModel.encGroupId}')" value="Back To Main Page"/>
							
							<button type="button" class="btn btn-success font_16 previewDetailsBtn">
									<span class="pad-right"><i class="fa fa-eye" aria-hidden="true"></i></span>Preview
							</button>
							
							</div> --%>
							

						</div>
						
					
					<div class="container pad- top" >
				<div class="col-md-11 col-lg-11 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">

							<legend class="bold legend_details">Newsletter Details :</legend>
								<div class="row">
							<div class="pull-right">
								<div class="col-md-4">
									<input type="button" value="Delete"  id="dln" onclick="deleteNewsLetterDetails()"
										class="btn btn-primary a-btn-slide-text">
								</div>
							</div>
						</div> 
							<table id="tab1" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th>
								<%
													int i = 0;
												%> <input type="checkbox">
							<th >Subject</th>


							<th >Status</th>


							<th>Scheduled Date</th>
							<th>Periodic Scheduled Date</th>
							<th></th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${NewsLettersList}" var="l">
													<tr>
								
								<td id="checkbox${l.numNewsLetterId}"><input
									type="checkbox" id="checkbox" class="checkId" name="checkBoxId"
									value="${l.numNewsLetterId}" /></td>
								<td>${l.strSubject}</td>
								<td>${l.strStatus}</td>
								<td>${l.scheduledDate}</td>
								<td>${l.strPeriodicScheduleDate}</td>
								<td>

									<div id="Edit">
										<a href="#" class="no-line"> <i class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text "
											title="Edit"></i> 
										</a>
									</div> 
								</td>
								<td style="display: none"><input
									id="smsContentId${l.numNewsLetterId}" type="hidden"
									value="${l.strContentSMS}"></input>
								</td>

								<td style="display: none"><input type='hidden'
									id='emailContent${l.numNewsLetterId}'
									value="${l.strContentMail}"></input>
								</td>

								<td style="display: none"><input type="hidden" id="nlId"
									value="${l.numNewsLetterId}"></input>
								</td>

							</tr>
							<%
												++i;
											%>
									</c:forEach>
								</tbody>
							</table>

							<!--End of data table-->
						</fieldset>
					</div>
					<!--End of datatable_row-->
				</div>
			</div>
							</div>
						</div>

						

					</div>
				



	
			<!--end of div before table-->
		

			</form>
			
		</div>
	</section>
 

	

	<script src="/PMS/resources/app_srv/PMS/global/editor/switchery.js"></script>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/Newsletter.js"></script>
	<script src="/PMS/resources/app_srv/PMS/global/editor/prettify.js"></script>
	<script
		src="/PMS/resources/app_srv/PMS/global/editor/jquery.hotkeys.js"></script>
	<script
		src="/PMS/resources/app_srv/PMS/global/editor/bootstrap-wysiwyg.js"></script>
			 	 
</body>
</html>