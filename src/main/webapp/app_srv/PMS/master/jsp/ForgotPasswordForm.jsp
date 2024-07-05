<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import=" java.io.File,java.util.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<style>
.wrapper1 {
    height: 100% !important;
}

.msg{
text-align: center;
font-weight: bold;
position: fixed;
 top:6px;
 z-index:100;
left:35%;
right:35%;
}

.msgbg {
border-color: #f0c36d;
background-color: #f9edbe;
padding: 6px 10px;
border: 1px solid transparent;
border-radius: 2px;
-webkit-border-radius: 2px;
-webkit-box-shadow: 0 2px 4px rgba(0,0,0,0.2);
box-shadow: 0 2px 4px rgba(0,0,0,0.2);
background: #f9edbe solid;
color: #222;
}

</style>

<body onload="resetval()">
	<form:form name="formPass" id="formPass" method="post"
		modelAttribute="employeeMasterModel">

		<c:set var="message" scope="session" value="${Message}" />
		<c:if test="${message!=null && message!= ''}">
			<div id="userinfo-message" class="msg msgbg centered">${Message}</div>
		</c:if>

		<div id="window-message">

			<c:set var="message" scope="session" value="${Message1}" />
			<c:choose>
				<c:when test="${Message1 == 'WindowClose'}">
					<script>
						setTimeout(function() {
							window.close();
						}, 3000);
						//window.delay(5000).close();
					</script>
				</c:when>
			</c:choose>
		</div>


		<section id="main-content" class="main-content merge-left">

			<div class=" container wrapper1">
			<!-- <div class="container pad-top-double "> -->
				<div class="container forget-password">
          		  <div class="row">
               		 <div class="col-md-12">
                   		 <div class="panel panel-default">
                       		 <div class="panel-body">
                       	
                          	   <div class="text-center">
                          	   <i class="fa fa-lock fa-4x"></i>
                          	     <!-- <img src="https://i.ibb.co/rshckyB/car-key.png" alt="car-key" border="0"> -->
                                <h2 class="text-center">Forgot Password?</h2>
                                <p class="blue">You can reset your password here.</p>
                                	 <div class="row text-jutify red font_14">
								<span class="errmsg"><c:out value="${errormsg}"></c:out></span>
							</div>
                              <div class="row pad-top ">
								<div
									class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
									<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

										<label class="label-class "> <form:label
												path="officeEmail">Email Id</form:label>:<span
											style="color: red;">*</span></label>
									</div>

									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<i class="fa fa-envelope-o icon"></i>
											<form:input type="text" class="input-field" id="officeEmail" path="officeEmail"
												maxlength="50" required="true" />
										</div>
									</div>
								</div>
							</div>

							<div class="row pad-top">
								<div
									class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">



									<c:set var="rand1"><%=java.lang.Math.round(java.lang.Math.random() * 10)%>
									</c:set>
									<c:set var="rand2"><%=java.lang.Math.round(java.lang.Math.random() * 10)%>
									</c:set>

									<c:set var="rand1" value="${rand1}" scope="session" />
									<c:set var="rand2" value="${rand2}" scope="session" />
									<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
			<%---------------------------- SET RANDOM VALUES [02/08/2023] ----------------------------------------------------%>
										<input type="text" value="${rand1 }" id="rand1" class="hidden"/>
										<input type="text" value="${rand2 }" id="rand2" class="hidden"/>
			<%-----------------------------------------------------------------------------------------------------------------%>
										<form:label path="captcha">What is ${rand1}+${rand2} ?<span
											style="color: red;">*</span></form:label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<div class="input-container">
												<form:input type="text" class="input-field" id="captcha" path="captcha"
												maxlength="2" required="true" />
											</div>
										</div>
									</div>

								</div>
							</div>


							<div class="row pad-top form_btn center">
								<input type="button" class="btn btn-primary" id="Reset" value="Reset Password"
									class="blue gap-right" name="Reset Password" />


							</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

		</div>
		</section>


	</form:form>
</body>
<script>
	function resetval() {
		document.getElementById("officeEmail").value = "";
	}
</script>
<script>
	$('#Reset').click(function() {

		var email = $('#officeEmail').val();
		var val=$('#captcha').val();
		if ($('#officeEmail').val() == '') {
			swal({
				title : "Email id is required",
				type : "error",
				confirmButtonText : "OK"
			})

		}
		else if ($('#captcha').val() == '') {
			//alert("Please enter above text in the given field.");
			swal({
				title : "Please enter above text in the given field",
				type : "error",
				confirmButtonText : "OK"
			})

		}

		else {
			var randomValue1 = $('#rand1').val();
			var randomValue2 = $('#rand2').val();
			var randomBothValue=randomValue1+","+randomValue2;
			$.ajax({
				type : "POST",
				url : "/PMS/GetEmailId",
				data : "officeEmail=" + email+"&captcha="+val+"&dataLoadedBasedOn="+randomBothValue,
				success : function(response) {
					console.log(response);
					var res = response.split(";");
					var r=res[0];
					if (r == "false") {
						//alert("Email Id is not registered");
						swal({
							title : res[1],
							type : "error",
							confirmButtonText : "OK"
						})
					} else if ($('#formPass').form('validate')) {
						//alert("pass");
						document.formPass.action = "/PMS/EmailSend";
						document.getElementById("formPass").submit();

					}

				}
			});

		}
	});
</script>
</html>