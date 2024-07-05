
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="application/x-javascript">
	 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>

</head>
<style>
.ui-dialog-titlebar-close {
  background: url("http://code.jquery.com/ui/1.10.3/themes/smoothness/images/ui-icons_888888_256x240.png") repeat scroll -93px -128px rgba(0, 0, 0, 0) !important;
 
}
.ui-dialog-titlebar-close:hover {
  background: url("http://code.jquery.com/ui/1.10.3/themes/smoothness/images/ui-icons_222222_256x240.png") repeat scroll -93px -128px rgba(0, 0, 0, 0);
}


</style>

<body>

	<!--header start-->
	<header class="header fixed-top clearfix"> <!--logo start-->
	<div class="brand">
		<a href="${pageContext.request.contextPath}/Homepage" class="logo">
			<img src="/PMS/resources/app_srv/PMS/global/images/cdac_logo.png" />
		</a>
	
	</div>
	<!--logo end-->

	<div class="nav notify-row" id="top_menu">

<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
		<p class="title_pms">Project Management System</p></div>
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
		<h5 class="orange">Center For Development of Advanced Computing</h5>
		</div>
	
	
	</div>
	

	</header>
	<!--header end-->
	
	
	
</body>


</html>
