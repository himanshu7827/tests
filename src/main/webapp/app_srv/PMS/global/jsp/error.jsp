<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/new/bootstrap.min-3.3.7.css">
	
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/master/css/home_form2.css">
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/master/css/style.bundle.css">	
	
 <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/new/style.css">	
 <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/new/font-awesome.css">	


 <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/error.css">	
</head>

<body>

<section id="main-content" class="main-content merge-left">
	<div class=" container wrapper1">
		
			<%-- <div class="row padded"></div>				
			<h4> Something went wrong </h4>
			
 --%>		
 
<div class="loading">
 
  <h1 class="padded-double" >Something went wrong! </h1>
 <h4> <a class="padded-double" href="${pageContext.request.contextPath}/Homepage"> Go Back to Homepage </a></h4>
  <div class="gears">
    <div class="gear one">
      <div class="bar"></div>
      <div class="bar"></div>
      <div class="bar"></div>
    </div>
    <div class="gear two">
      <div class="bar"></div>
      <div class="bar"></div>
      <div class="bar"></div>
    </div>
    <div class="gear three">
      <div class="bar"></div>
      <div class="bar"></div>
      <div class="bar"></div>
    </div>
  </div>
 </div>
 </div>
</section>	
</body>
  <script src="/PMS/resources/app_srv/PMS/global/js/main.js"></script>
  <script
	src="/PMS/resources/app_srv/PMS/global/js/new/jquery-3.3.1.js"></script>
<script
	src="/PMS/resources/app_srv/PMS/global/js/new/bootstrap.min-3.4.0.js"></script>

</html>