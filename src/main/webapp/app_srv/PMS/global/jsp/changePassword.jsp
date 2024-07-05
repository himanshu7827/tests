<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII" />
<style>
.password-verdict {
	color: #000;
}
.progress{
width:30% !important;
height:20px !important;
background-color: #fff !important;
}
.progress-bar{
background-color: #f4c797 !important;
}
.password-verdict{
font-size:14px !important;

}
/* .pad_19{
padding-left: 19%;
} */
</style>

<script src="/PMS/resources/app_srv/PMS/global/js/pwstrength.js"></script>
<title>Change password</title>
</head>
<body>
	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
		<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>			
			<li class="active">Change Password</li>
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

<form:form id="form1" method="post" modelAttribute="resetPasswordModel" action="/PMS/updatePassword" onsubmit="return savePass()">
	<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Change Password</h4></div>
					<div class="panel-body text-center">
						<div class="row pad-top">
							<div id="errormsg" class="alert alert-danger"
								style="display: none"></div>
								</div>
							
								
					<div class="row pad-top ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12">
								<input type="hidden" id="prefixNew" value="${sessionScope.prefixNew}"/>
								<input type="hidden" id="suffixNew" value="${sessionScope.suffixNew}"/>
								<input type="hidden" id="prefixOld" value="${sessionScope.prefixOld}"/>
								<input type="hidden" id="suffixOld" value="${sessionScope.suffixOld}"/>
											<label class="label-class">Old Password:<span
												style="color: red;">*</span></label>
										</div>

										 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-lock icon"></i> <form:password
													class="input-field" id="oldpass" path="oldPassword"
													required="required" />
											</div>
										</div>
									</div>
								</div>
								<div class="row pad-top ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12">


											<label class="label-class">New Password:<span
												style="color: red;">*</span></label>
										</div>
											 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-lock icon"></i> <form:password
													class="input-field" id="password" path="newPassword"
													required="required" />
											</div>
											
										</div>
									</div>
								</div>
								<div class="row pad-top ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12">

											<label class="label-class">Confirm Password:<span
												style="color: red;">*</span></label>
										</div>

										 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-lock icon"></i> 
												<input class="input-field" id="matchPassword"  type="password" required="required"/>
											</div>
											<div id="globalError" class="alert alert-danger col-sm-12"
												style="display: none">error</div>
										</div>
									</div>
								</div>

                        	<div class="row pad-top  form_btn">					
						        <button type="submit" class="btn btn-primary font_14" id="save">
									<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						  		</button>
					
					  		</div>

						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</section>

	<script src="/PMS/resources/app_srv/PMS/global/js/sha256.js"></script>

	<script>
		$(document)
				.ready(
						function() {
						$(":password")
									.keyup(
											function() {
												if ($("#password").val() != $(
														"#matchPassword").val()) {
													$("#globalError")
															.show()
															.html(
																	'Password does not match!');
												} else {
													$("#globalError").html("")
															.hide();
												}
											});

							options = {
								common : {
									minChar : 8
								},
								ui : {
									showVerdictsInsideProgressBar : true,
									showErrors : true,
									errorMessages : {
										wordLength : 'Your password is too short',
										wordNotEmail : 'Do not use your email as your password',
										wordSequences : 'Your password contains sequences',
										wordLowercase : 'Use lower case characters',
										wordUppercase : 'Use upper case characters',
										wordOneNumber : 'Use numbers',
										wordOneSpecialChar : 'Use special characters'
									}
								}
							};
							$('#password').pwstrength(options);
						});

		function savePass() {			
			var oldPassword = $('#oldpass').val();
			var password = $('#password').val();
			var confirmPassword = $('#matchPassword').val();
			
			var formValid = true;
			
			if(!oldPassword){
				formValid = false;
				swal('Attention','Old Password is required');
				return false;
			}
			
			if(!password){
				formValid = false;
				swal('Attention','New Password is required');
				return false;
			}
			
			if(!confirmPassword){
				formValid = false;
				swal('Attention','Confirm Password is required');
				return false;
			}
			
			if($('ul.error-list li').length >0){
				formValid = false;
				swal('Attention','Password Validation not satisfied');
				return false;
			}
			
			$(".alert").html("").hide();
			$(".error-list").html("");
			if (password != confirmPassword) {
				formValid = false;
				$("#globalError").show().html('Password does not match!');
				return false;
			}	
			
			if(formValid){
								
				var prefixNew = $('#prefixNew').val();
				var suffixNew = $('#suffixNew').val();
				
				var prefixOld = $('#prefixOld').val();
				var suffixOld = $('#suffixOld').val();
				
				var baseNew = window.btoa(prefixNew+' ### '+password +' ### '+suffixNew);			
				$('#password').val(baseNew);
				
				var baseOld = window.btoa(prefixOld+' ### '+oldPassword +' ### '+suffixOld);			
				$('#oldpass').val(baseOld);
				
				$('#matchPassword').val('');				
				return true;
			}
			
			return formValid;			
		}
	</script>
</body>

</html>