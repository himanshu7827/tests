<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/SERB/resources/app_srv/serb/gl/theme/dataTables.responsive.css"  rel="stylesheet" type="text/css" />
<script src="/SERB/resources/app_srv/serb/gl/jssrc/dataTables.responsive.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>
 
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
<!-- <script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/progressPreview.js"></script> -->
<!-- <link href="/SERB/resources/app_srv/serb/gl/theme/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<link href="/SERB/resources/app_srv/serb/gl/theme/dataTables.responsive.css"  rel="stylesheet" type="text/css" />
<script src="/SERB/resources/app_srv/serb/gl/jssrc/jquery.dataTables.js" type="text/javascript"></script>
 --><script type="text/javascript">

			</script>
			<%
	System.out.println("request.getAttribute(encMonthlyProgressId)"   + request.getAttribute("encMonthlyProgressId"));
 	String encMonthlyProgressId = "";
 	Long numCategoryId = null;
 	int numId=0;
 	
 	
 	encMonthlyProgressId = request.getAttribute("encMonthlyProgressId").toString();
 	
 	String t=request.getAttribute("numCategoryId").toString();
 	numCategoryId=Long.valueOf(t);
 	
 	String n=request.getAttribute("numId").toString();
 	numId=Integer.parseInt(n);
 	%>

</head>
<body>

	<section id="main-content" class="main-content">

		<div class=" container wrapper1">
			<!-- <div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
					
					</ol>
				</div>
			</div> -->	
						<div class="row "></div>
				
				<!-- for flashing the success message -->
			
		       		

         <sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')"> 
         
       <%--   <div class="hidden" id="encProjectId" >${monthlyProgressModel.encProjectId}</div>
         <div class="hidden" id="encGroupId">${monthlyProgressModel.encGroupId}</div> 
         <div class="hidden" id="encCategoryId">${encCategoryId}</div>  --%>
         
         
        <form:form id="form1" name="form1" modelAttribute="monthlyProgressModel"  method="post" autocomplete="off">
			<input type="hidden" id="encMonthlyProgressId" value="${encMonthlyProgressId}"/>
			<input type="hidden" id="numId" value="${numId}"/>
			<input type="hidden" id="encCategoryId" value="${encCategoryId}"/>
			<input type="hidden" id="numCategoryId" value="${numCategoryId}"/>
			<div class="center">
			<c:forEach items="${docIds}" var="data">
			
			<div class="row pad-top" style="margin-left: 39%;">
			<div class="col-md-7 col-lg-2 col-sm-6 col-xs-6 text-justify">
			<img class="centred thumbPhoto" src="/PMS/viewImage?FileType=I&encMonthlyProgressId=<%=encMonthlyProgressId%>&numCategoryId=<%=numCategoryId%>&numId=<%=numId%>&docId=${data}" alt="UserImage" style="
    margin-right: 51%;
    width: 75%;
    height: 125%;
"></img>
			</div>
			<div class="col-md-5 col-lg-8 col-sm-6 col-xs-6">
			<input type="button" class="btn btn-orange font_14"" onclick="download('${data}')"  value="Download Document" style="
    background-clip: unset;
    margin-right: 26%;
    margin-top: 2%;
    border-block-start-width: unset;
    font-size: larger;
    margin-bottom: 4%;
"/>
			</div>
			</div>
			</c:forEach>
			</div>
</form:form></sec:authorize> 
									
			</div>
			

			</section>
			
	<script>
	function download(documentId){
		var encCategoryId=$("#encCategoryId").val();
		var encMonthlyProgressId=$("#encMonthlyProgressId").val();
		
		openWindowWithPost('POST','/PMS/downloadDeploymentImages',{"numDocumentId" : documentId,"encCategoryId":encCategoryId,"encMonthlyProgressId":encMonthlyProgressId},'_blank');
	}
	</script>	
			
			
</body>
</html>
