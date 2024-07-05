<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import=" java.io.File,java.util.*"%>
<%@ page isELIgnored="false" %>
    <link rel="stylesheet"  href="/SERB/resources/app_srv/serb/gl/theme/chosen.css">
      <link rel="stylesheet" href="/SERB/resources/app_srv/serb/gl/theme/JqueryUi.css" />

     
 <link href="/SERB/resources/app_srv/serb/gl/theme/bootstrap_foo.css" rel="stylesheet" type="text/css"/> 
  <link href="/SERB/resources/app_srv/serb/gl/theme/footable.core.css?v=2-0-1" rel="stylesheet" type="text/css"/>
  <link href="/SERB/resources/app_srv/serb/gl/theme/footable-0.1.css" rel="stylesheet" type="text/css"/>  
     
    <script src="/SERB/resources/app_srv/serb/gl/jssrc/libs/footable_v2.js?v=2-0-1" type="text/javascript"></script>
      
  <script src="/SERB/resources/app_srv/serb/gl/jssrc/libs/footable.sort.js?v=2-0-1" type="text/javascript"></script>
  <script src="/SERB/resources/app_srv/serb/gl/jssrc/libs/footable.filter_v2.js?v=2-0-1" type="text/javascript"></script>
  <script src="/SERB/resources/app_srv/serb/gl/jssrc/libs/footable.paginate_v2.js?v=2-0-1" type="text/javascript"></script>

 	<script src="/SERB/resources/app_srv/serb/gl/jssrc/sweetalert2.min.js"></script>
<link rel="stylesheet" type="text/css" href="/SERB/resources/app_srv/serb/gl/theme/sweetalert2.css" />

  <script src="/SERB/resources/app_srv/serb/gl/jssrc/chosen.jquery.js" 	type="text/javascript"></script>
  <script type="text/javascript" src="/SERB/resources/app_srv/serb/gl/jssrc/charCount.js"></script>
  <script type="text/javascript">
		
		 function resetval()
			{
				document.getElementById("strFilterName").value="";
				document.getElementById("strFilterDesc").value="";
				document.getElementById("strFilterQuery").value=""; 
				 $('#filterType').val("select");
				 $('#filterType').trigger("liszt:updated"); 
				
				$('#Update').hide();
		    	$('#Save').show();
			} 
		 </script>
		 <script>

$(document).ready( function() {
	
	document.getElementById("strFilterName").value="";
	document.getElementById("strFilterDesc").value="";
	document.getElementById("strFilterQuery").value=""; 
	 $('#filterType').val("select");
	 $('#filterType').trigger("liszt:updated"); 
	
	$('#Update').hide();
	$('#Save').show();
	
	 $('#userinfo-message').delay(1000).fadeOut();
	//$("#userinfo-message").show().delay(3000).fadeOut();
	
	$("#strFilterDesc").charCount({
		allowed: 6000,		
		warning: 10,
		counterText: 'Characters left:  '	
	});
	
	 $('#strFilterName').validatebox({
         required:true
       });
	 
	 $('#strFilterDesc').validatebox({
         required:true,
         validType:['maxLength[6000]']
       });
	 
	 $('#strFilterQuery').validatebox({
         required:true
       });
	 
	var result='${status}';
	if(result == 'error'){
	 $('#strFilterQuery').addClass("red-border");
 	$("#subareaSelect").show().delay(5000).fadeOut();
		$('html, body').animate({scrollTop: '0px'}, 0);
	}
	else{
		resetval();
	}
	
	$('#strFilterQuery').click(function(){
		$('#strFilterQuery').removeClass("red-border");
	});


	
	
});
</script>
		 
		 
		 
   <body>
   
   <section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active"> News Letter Filter</li>
					</ol>
				</div>
			</div>
			<div class="row "></div>
			<c:if test="${Message != null && Message != ''}">
				<c:choose>
					<c:when test="${status=='success'}">
						<div id="userinfo-message">
							<p class="success_msg">${Message}</p>
						</div>
					</c:when>
					<c:otherwise>
						<div id="userinfo-message">
							<p class="error_msg">${Message}</p>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>


			<form:form name="form" id="form" action="" method="post" modelAttribute="NewsLetterFilterForm">
				<%-- <form:hidden path="numId" /> --%>
				<div class="container ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">News Letter Filter</h4>
							</div>
							<div class="panel-body text-center">

								
								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
										<form:input type="hidden" path="numFilterId"></form:input>
										<form:hidden path="arrCheckbox" id="Cid" value=""></form:hidden>
										<label class="label-class">Filter Name:<span style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-user icon"></i>
												
												<form:input path="strFilterName" id="strFilterName" style="width:75%"></form:input>
												<form:errors path="strFilterName" cssClass="error" />
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class">Filter Description: </label>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">

												<i class="fa fa-th-large icon"></i>
												<form:textarea path="strFilterDesc" name="strFilterDesc" id="strFilterDesc" style="width:75%"></form:textarea>
												<form:errors path="strFilterDesc" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class">Filter Query: </label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-file-text-o icon "></i>
												<form:textarea path="strFilterQuery" name="strFilterQuery" id="strFilterQuery" style="width:75%"></form:textarea>
												<form:errors path="strFilterQuery" cssClass="error" />
											</div>
										</div>


									</div>
								</div>
								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class">Filter Type: </label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-file-text-o icon "></i>
												<form:select path="strFilterType" id="filterType" class="chzn-select whole">
												<form:option value="select">SELECT FILTER TYPE</form:option>
												<form:option value="U">User Details</form:option>
												<form:option value="P">Project Details</form:option>
					

				</form:select>
												<form:errors path="strFilterType" cssClass="error" />
											</div>
										</div>


									</div>
								</div>
											
									
									
									
									
					
					
			
				<div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="Update" style="display:none" name="Update">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Update
						</button>
						<button type="button" class="btn btn-primary font_14" id="Save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="reset" onclick="resetval()">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
					</div>
					</div>
					</div>
					</div>
					</div>
			</form:form>
			<!-- </div> -->
			<!--End of panel-->
			<!--Start data table-->

			<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">

							<legend class="bold legend_details">Filter Details :</legend>
								<div class="row">
							<div class="pull-right">
								<div class="col-md-4">
									<input type="submit" value="Delete" onclick="" id="delete"
										class="btn btn-primary a-btn-slide-text">
								</div>
							</div>
						</div> 
							<table id="example" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th>Select</th>
										<th>Filter Name</th>
										<th>Filter Description</th>
										<th>Filter Query</th>
										<th>Filter Type</th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${FilterList}" var="pro">
										<tr>
											<td><input type="checkbox" name = "Newside1" class="CheckBox" value="${pro.numFilterId}" id ="${pro.numFilterId}"
												autocomplete="off"></td>
											<td>${pro.strFilterName}</td>
											<td>${pro.strFilterDesc}</td>
											<td>${pro.strFilterQuery}</td>
											<c:choose><c:when test="${pro.strFilterType eq 'U'}"><td>User Details</td></c:when><c:otherwise><td>Project Details</td></c:otherwise></c:choose>

											

											<td><span
												class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
												id="edit" aria-hidden="true"></span></td>

										</tr>
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
	</section>
       
		
		
		<!--  <script src="/SERB/resources/app_srv/serb/gl/jssrc/jquery.easyui.js"></script>
 <script src="/SERB/resources/app_srv/serb/gl/jssrc/ValidationFunctions.js" language="JavaScript" type="text/javascript"></script> -->
	
		<script type="text/javascript">
						
		
		$('#Save').click(function() {
		
					/*  if($('#form').form('validate')){ */
							if($('#filterType').val()=="select")
							   {
							   alert("Please Select a Filter Type Also");
							   return false;
							   }
							else{
								submit_form();
						    	$("#userinfo-message").show().delay(1000).fadeOut();
						    	
							}
					    
					    	
					 /*  } */
					
				
			});
	
		      
	
		
		function submit_form() {
			/* callMe(); */
			
			document.form.action = "/PMS/saveNewsLetterFilter";
			document.getElementById("form").submit();
			resetval();
			/* $('#tableData').show(); */
		}
		
		
		$(function () {

			$('table').footable();
			

	      $('.clear-filter').click(function (e) {
	          e.preventDefault();
	          $('.demo').trigger('footable_clear_filter');
				$('.filter-status').val('');
	      });

	      $('.filter-status').change(function (e) {
	          e.preventDefault();
				var filter = $(this).val();
	          $('#filter').val('');
	          $('.demo').trigger('footable_filter', {filter: filter});
	      });

	     
	      });
		
			
	     $(document).on('click','#edit',function(e){
	      	
	      	$('#Update').show();
	      	$('#Save').hide();
	     	 
	  		var resultArray1 = $(this).closest('tr').find('td').map( function()
	  		{
	  		return $(this).text();
	  		}).get();
	  		//console.log(resultArray1);
	  		
	  		var checkID = $(this).closest('tr').find('input[type=checkbox]').map( function()
	  		{
	  		return $(this).val();
	  		}).get();
	  		
	  			  	
	  		$('#numFilterId').val(checkID);
	  		$('#strFilterName').val(resultArray1[1]);
	  		$('#strFilterDesc').val(resultArray1[2]);
	  		$('#strFilterQuery').val(resultArray1[3]);
			var FilterType=resultArray1[4].trim();
	  		
	  		if(FilterType=='User Details')
	  			{
	  		
				$('#filterType').val("U");
	  			}
			else if (FilterType=='Proposal Details')
			$('#filterType').val("P");
			else 
				$('#filterType').val("select");
			
			 $('#filterType').trigger('liszt:updated');
	  		
	  		});

	      
	      $('#Update').click(function() {
	    	  
	    	  if($('#form').form('validate')){
	 				 if(confirm("Are you sure you want to update the record"))
	 				  {
	 				submit_form_update();
	 				 $("#userinfo-message").show().delay(1000).fadeOut();
	 				resetval();
	 				}
	    	  }
	 		});
	         
		
	     $('#delete').click(function(){
	 		
	 		    var chkArray = [];
	 		     
	 		    $(".CheckBox:checked").each(function() {
	 		        chkArray.push($(this).val());
	 		    });
	 		     
	 		    var selected;
	 		  //  alert (chkArray.length);
	 		    selected = chkArray.join(',') + ","; 
	 		    if(selected.length > 1){
	 			  	  if(confirm("Are you sure you want to delete the record"))
	 				  {
	 			  		$('#Cid').val(chkArray);
	 			  		
	 			        submit_form_delete();
	 			        $("#userinfo-message").show().delay(1000).fadeOut();
	 			       resetval();
	 			    }
	 			    }
	 		    else{
	 		        alert("Please select at least one of the checkbox");  
	 		}
	 		 
	 	});
	      
	      function submit_form_update() {
	    		document.form.action = "/PMS/saveNewsLetterFilter";
	    		document.getElementById("form").submit();
	    	}


	    	function submit_form_delete()
	    	{
	    	document.form.action = "/PMS/DeleteNewsLetterFilter";
	    	document.getElementById("form").submit();
	    	}

		
		</script>
		<script type="text/javascript"> 
var config = {
'.chzn-select'           : {},
'.chzn-select-deselect'  : {allow_single_deselect:true},
'.chzn-select-no-single' : {disable_search_threshold:10},
'.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
'.chzn-select-width'     : {width:"95%"}
}
for (var selector in config) {
$(selector).chosen(config[selector]);
}
</script>
		<!--script for select box-->
		</body>