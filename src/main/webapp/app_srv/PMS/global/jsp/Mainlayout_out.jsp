
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>
<sec:csrfMetaTags />
<meta charset="utf-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">

<title>PMS</title>
<!-- JS -->
<script type="text/javascript"
	src="/PMS/resources/app_srv/PMS/global/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
	src="/PMS/resources/app_srv/PMS/global/js/bootstrap.js"></script>
<script type="text/javascript"
	src="/PMS/resources/app_srv/PMS/global/js/bootstrapValidator.js"></script>
<!-- <script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/upgraded/js/numscroller-1.0.js"></script> -->
<script type="text/javascript"
	src="/PMS/resources/app_srv/PMS/global/js/CSRF.js"></script>
<script type="text/javascript"
	src="/PMS/resources/app_srv/PMS/global/js/md5.js"></script>
<script type="text/javascript"
	src="/PMS/resources/app_srv/PMS/global/js/html5shiv.js"></script>
<script type="text/javascript"
	src="/PMS/resources/app_srv/PMS/global/js/respond.min.js"></script>
	
	<!-- //banner slider -->

<link href="/PMS/resources/app_srv/PMS/global/css/bootstrap.css"
	rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/bootstrapValidator.css" />
<link href="/PMS/resources/app_srv/PMS/global/css/font-awesome.min.css"
	rel="stylesheet" media="all">
<link href="/PMS/resources/app_srv/PMS/global/css/home.css"
	rel="stylesheet" type="text/css" media="all" />
<link href="/PMS/resources/app_srv/PMS/global/css/style.css"
	rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" type="text/css"
	href="/PMS/resources/app_srv/PMS/global/css/mobile.css">
<link rel="stylesheet" type="text/css"
	href="/PMS/resources/app_srv/PMS/global/css/core.css">
<link rel="stylesheet" type="text/css"
	href="/PMS/resources/app_srv/PMS/global/css/responsive.css">

<!-- code for favicon -->
<link rel="apple-touch-icon" sizes="57x57"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/favicon-16x16.png">
<link rel="manifest"
	href="/NewMedDev/resources/app_srv/NMD/global/img/favicon/manifest.json">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage"
	content="/NewMedDev/resources/app_srv/NMD/global/img/favicon/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">
<!-- favicon ends here -->
</head>
<script type="text/javascript">
	$(document).ready(function() {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});

	});
</script>
<style>
.main_div{
overflow-x:hidden;
}
</style>

<body class="home main_div">
	<div class="wrapper">
		<div class="container-fluid">
		<div class="row">
			<tiles:insertAttribute name="header" />
		</div>
</div>

		<div class="container-fluid">
		<div class="row content-wrapper ">
			<tiles:insertAttribute name="body" />
		</div>

		</div>


		
		<div class="container-fluid">	
		<div class="push"></div>	
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
	
</body>


</html>


