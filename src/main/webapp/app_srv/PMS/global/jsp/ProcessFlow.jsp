<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>

<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/ProcessFlow.css">	

 <style>
/*  .outer {
  display: flex;
  justify-content: center;
  align-items: center;
} */
/*   .steps_width{
	width: 14% !important;
}  */ 
/* .bs-wizard > .bs-wizard-step .bs-wizard-info {color: #333 !important; font-size: 16px!important;}
.bs-wizard > .bs-wizard-step > .progress > .progress-bar{
    background: #d5d5d5 !important;
}
.bs-wizard > .bs-wizard-step > .bs-wizard-dot{
  background: #d5d5d5 !important;
}
.bs-wizard > .bs-wizard-step > .bs-wizard-dot:after{
background:#72706b;
} */

</style>


<body>
<section id="main-content" class="main-content merge-left">

		<div class=" container">
			<div class="row">
		
        <div class="col-md-12 col-lg-12 col-xs-12 col-sm-12">
            <div id="parent" class="bs-wizard" style="border-bottom:0;" >
    
            </div>
            </div>
   
	</div>
	</div>
	</section>
	</body>

<!-- <script type="text/javascript">


$(document).ready( function() {
	
	var moduleTypeId=${param.moduleTypeId};
var applicationId=${param.applicationId};

	   $.ajax({
	        type: "GET",
	        url: "/PMS/getWorkflowSteps",
	        data: "moduleTypeId="+moduleTypeId+"&applicationId="+applicationId,
	        success: function(response) 
	        {
	        	//console.log("response.length"+response.length);
	        	if(response==null || response=='' || response=='[]')			
        		{
        		
        		}
	        var str='  <div class="bs-wizard" style="border-bottom:0;" >';
    		var count=1;
	        	$.each(response,function(index,item){
	        		if(response.length>2){
	        		str+='  <div class="col-xs-2 bs-wizard-step steps_width complete ">';
	        		}else{
		        		str+='  <div class="col-xs-6 bs-wizard-step steps_width complete ">';

	        		}
	        		str+='<div class="text-center bs-wizard-stepnum"> '+count+'</div>';
	        		str+='<div class="progress"><div class="progress-bar"></div></div>';
	        		str+='<div class="bs-wizard-dot "></div>';
	        		str+='<div class="bs-wizard-info text-center ">'+item.strProcessDescription+'</div>';
	        		str+='</div>';
	        		count++;
	        	});
	        
	        	$('#parent').append(str);
	        	
	        }
	    });

});
</script> -->

<script type="text/javascript">

//alert(moduleTypeId);
$(document).ready( function() {
	var moduleTypeId=${param.moduleTypeId};
	var applicationId=${param.applicationId};
	console.log("moduleTypeId"+moduleTypeId);
	console.log("applicationId"+applicationId);
	   $.ajax({
	        type: "GET",
	        url: "/PMS/getWorkflowSteps",
	        data: {"moduleTypeId":moduleTypeId,
	        	"applicationId":applicationId},	        
	        success: function(response) 
	        {
	        	//alert(response);
	        	/* if(response==null || response=='' || response=='[]')			
        		{
        		
        		} */
	        var str='  <div class="row bs-wizard" style="border-bottom:0;">';
    		var count=1;
	        	$.each(response,function(index,item){
	        		str+='  <div class="col-xs-2 bs-wizard-step complete steps_width">';
	        		str+='<div class="text-center bs-wizard-stepnum"> '+count+'</div>';
	        		str+='<div class="progress"><div class="progress-bar"></div></div>';
	        		str+='<a onclick=redirectPage("'+item.strProcessPath+'") class="bs-wizard-dot"></a>';
	        		str+='<div class="bs-wizard-info text-center">'+item.strProcessDescription+'</div>';
	        		str+='</div>';
	        		count++;
	        	});
	        
	        	$('#parent').append(str);
	        	
	        }
	    });
});
</script>
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/block.js"></script>        
</html>
