<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import=" java.io.File,java.util.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html 
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


.loader  {
  animation: rotate 1s infinite;  
  height: 50px;
  width: 50px; 
  margin-left:750px;
  margin-top:20px;
}
.loader:before,
.loader:after {   
  border-radius: 50%;
  content: '';
  display: block;
  height: 20px;  
  width: 20px;
}
.loader:before {
  animation: ball1 1s infinite;  
  background-color: #cb2025;
  box-shadow: 30px 0 0 #f8b334;
  margin-bottom: 10px;
}
.loader:after {
  animation: ball2 1s infinite; 
  background-color: #00a096;
  box-shadow: 30px 0 0 #97bf0d;
}
@keyframes rotate {
  0% { 
    -webkit-transform: rotate(0deg) scale(0.8); 
    -moz-transform: rotate(0deg) scale(0.8);}
  50% { 
    -webkit-transform: rotate(360deg) scale(1.2); 
    -moz-transform: rotate(360deg) scale(1.2);}
  100% { 
    -webkit-transform: rotate(720deg) scale(0.8); 
    -moz-transform: rotate(720deg) scale(0.8);}
}
@keyframes ball1 {
  0% {
    box-shadow: 30px 0 0 #f8b334;}
  50% {
    box-shadow: 0 0 0 #f8b334;
    margin-bottom: 0;
    -webkit-transform: translate(15px,15px);
    -moz-transform: translate(15px, 15px);} 
  100% {
    box-shadow: 30px 0 0 #f8b334;
    margin-bottom: 10px;}
}
@keyframes ball2 {
  0% {
    box-shadow: 30px 0 0 #97bf0d;}  
  50% {
    box-shadow: 0 0 0 #97bf0d;
    margin-top: -20px;
    -webkit-transform: translate(15px,15px);
    -moz-transform: translate(15px, 15px);} 
  100% {
    box-shadow: 30px 0 0 #97bf0d;
    margin-top: 0;
  } 
}


#loader-content {
  font-family: "Avant Garde", Avantgarde, "Century Gothic", CenturyGothic, "AppleGothic", sans-serif;
  font-size: 20px;
  padding: 20px 20px;
  text-align: center;
  text-transform: uppercase;
  text-rendering: optimizeLegibility;
  
  color: #2c2c2c; 
  letter-spacing: .05em;
  /* text-shadow: 
  4px 4px 0px #d5d5d5,7px 7px 0px rgba(0, 0, 0, 0.2); */
  
}


</style>

 <body onload="loadingFunction();">
<form:form name="formGenOTP" id="formGenOTP" method="post" modelAttribute="otpInfoModel">


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
		
		

						<input type="hidden" id="prefixRandom" value="${sessionScope.prefixRandom}"/>
						<input type="hidden" id="suffixRandom" value="${sessionScope.suffixRandom}"/>
						
						

		<section id="main-content" class="main-content merge-left">

			<div class=" container wrapper1">
			<!-- <div class="container pad-top-double "> -->
			<div id="loader" class="loader"></div>
			<div id="loader-content"><b>Generating OTP....</b></div>
						
			
				<div id="OTPcontent" class="container forget-password">
          		  <div class="row">
               		 <div class="col-md-12">
                   		 <div class="panel panel-default">
                       		 <div class="panel-body">
                       	
                          	   <div class="text-center">
                          	   <!-- <i class="fa fa-lock fa-4x"></i> -->
                          	     <!-- <img src="https://i.ibb.co/rshckyB/car-key.png" alt="car-key" border="0"> -->
                                <h2 class="text-center">Login With OTP</h2>
                                
                                	 <div class="row text-jutify red font_14">
								<span class="errmsg"><c:out value="${errormsg}"></c:out></span>
							</div>
                              <div class="row pad-top ">
								<div
									class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
									<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

										<label class="label-class "> <form:label
												path="stremail">Email Id</form:label>:<span
											style="color: red;">*</span></label>
									</div>

									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<i class="fa fa-envelope-o icon"></i>
											<form:input type="text" class="input-field" id="stremail" path="stremail"
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

									<c:set var="rand1" value="${rand1}" scope="session"/>
									<c:set var="rand2" value="${rand2}" scope="session"/>
									<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
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
								<input type="button" class="btn btn-primary" id="GenOTP" value="Generate OTP"
									class="blue gap-right" name="Generate OTP" />


							</div>
							
							
							
							
							
							
							
							 <div class="row pad-top">
								<div
									class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
										<form:label path="otpenter">Enter OTP<span
											style="color: red;">*</span></form:label>
									</div>
							
							
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<div class="input-container">
												<form:input type="password" class="input-field" id="otpenter" path="otpenter"
												maxlength="6" required="true" />
											</div>
										</div>
									</div>
							
							
							
							
							
							
							
                            </div>
                            
                            
                            <div class="row pad-top form_btn center">
								<input type="button" class="btn btn-primary" id="VerifyOTP" value="Verify OTP"
									class="blue gap-right" name="Verify OTP" onclick="return validate();"/>


							</div>
							
							<!-- <div id="load" align="center" >
							<p style="text-align: center;">
							
							<img src="/PMS/resources/app_srv/PMS/global/images/Limage.png"
									
										class="img-responsive" width="100%;">
								</p>
								</div> -->
						
												
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
function loadingFunction() {
	document.querySelector("#loader").style.display = "none";

	document.querySelector("#loader-content").style.display= "none";
	
	//document.querySelector("#OTPcontent").style.opacity=0.2;
	};
	</script>
	<script>
	$('#GenOTP').click(function() {
		 
		var email = $('#stremail').val();
		var val=$('#captcha').val();

		if ($('#stremail').val() == '') {
			swal({
				title : "Email id is required",
				type : "error",
				confirmButtonText : "OK"
			})

		}
		else if ($('#captcha').val() == '' || $('#captcha').val() == 0 ) {
			
			swal({
				title : "Please enter the calculated value in the given field",
				type : "error",
				confirmButtonText : "OK"
			})
			
		}
		else {
			 
			 $.ajax({
					type : "POST",
					url : "/PMS/GetEmailIdOTP",
					data : {"stremail": email,
						    "captcha":val},
					async: false, 
					success : function(response) {
						
						console.log("GetEmailIdOTP"+response);
						var res = response.split(";");
						var r=res[0];
						if (r == "false") {
							//alert("Email Id is not registered");
							swal({
								title : res[1],
								type : "error",
								confirmButtonText : "OK"
							})
							window.location.reload();
						} 

						 else if ($('#formGenOTP').form('validate'))
							{
	
								//$("#OTPcontent").hide();
								document.querySelector("#OTPcontent").style.opacity=0.4;
								$("#loader").show();
								$("#loader-content").show();
							$.ajax({
								type:"POST",
								url:"/PMS/EmailSendOTP",
								data : {"stremail": email,
									   "captcha":val},
								//async:false,
								 
								success:function(response)
								{
									
									alert("OTP has been sent to your registered Email Id. The OTP will be valid for only 10 mins");
									$("#loader").hide();
									$("#loader-content").hide();
									document.querySelector("#OTPcontent").style.opacity=1;
									//$("#OTPcontent").show();

								}
							});
							}  

					}

					});
		 }

		}); 
		
	</script>
	
	<script>	 
	
		$('#VerifyOTP').click(function(){
			
			if ($('#otpenter').val() == '') {
				 swal({
					title : "OTP is required",
					type : "error",
					confirmButtonText : "OK"
					
				}) 
			

			}
			else
				{
					verify_OTP();
				}
			
			 
		});

	function verify_OTP()
	{

	 /* document.getElementById("formGenOTP").action = "/PMS/VerifyOTP"; */
	 document.getElementById("formGenOTP").action = "login";
	 document.getElementById("formGenOTP").method = "POST";
	 document.getElementById("formGenOTP").submit(); 
	}
	 
	
 function validate() {
		
		var otpenter = $('#otpenter').val();
		
		var prefixRandom = $('#prefixRandom').val();
		var suffixRandom = $('#suffixRandom').val();
		if (otpenter!=''){
				
		var base = window.btoa(prefixRandom+' ### '+otpenter +' ### '+suffixRandom);
		
		$('#otpenter').val(base);
		}
		
		else{
			$('#otpenter').val(otpenter);
			
		}
		return true;
	} 
</script> 


</html>