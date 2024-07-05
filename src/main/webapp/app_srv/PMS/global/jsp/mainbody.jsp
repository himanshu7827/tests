<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" import="java.util.*,java.text.*"%>

<!doctype html>
<html>
<head>
<!-- Meta Tags -->
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Page Title -->
<title>PMS</title>

<link href="/PMS/resources/app_srv/PMS/global/css/aos.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/owl-carousel/1.3.3/owl.carousel.css"
	rel="stylesheet" />
<!-- <link
	href="/PMS/resources/app_srv/PMS/global/css/lib/font-awesome/css/font-awesome.min.css"
	rel="stylesheet"> -->
<link
	href="/PMS/resources/app_srv/PMS/global/css/lib/animate/animate.min.css"
	rel="stylesheet">

<script>
	let
	i = 2;

	$(document).ready(

			function() {
				var radius = 200;
				var fields = $('.itemDot');
				var container = $('.dotCircle');
				var width = container.width();
				radius = width / 2.5;

				var height = container.height();
				var angle = 0, step = (2 * Math.PI) / fields.length;
				fields.each(function() {
					var x = Math.round(width / 2 + radius * Math.cos(angle)
							- $(this).width() / 2);
					var y = Math.round(height / 2 + radius * Math.sin(angle)
							- $(this).height() / 2);
					if (window.console) {
						console.log($(this).text(), x, y);
					}

					$(this).css({
						left : x + 'px',
						top : y + 'px'
					});
					angle += step;
				});

				$('.itemDot').click(
						function() {

							var dataTab = $(this).data("tab");
							$('.itemDot').removeClass('active');
							$(this).addClass('active');
							$('.CirItem').removeClass('active');
							$('.CirItem' + dataTab).addClass('active');
							i = dataTab;

							$('.dotCircle')
									.css(
											{
												"transform" : "rotate("
														+ (360 - (i - 1) * 36)
														+ "deg)",
												"transition" : "2s"
											});
							$('.itemDot').css(
									{
										"transform" : "rotate("
												+ ((i - 1) * 36) + "deg)",
										"transition" : "1s"
									});

						});
			
				setInterval(function() {
					var dataTab = $('.itemDot.active').data("tab");
					if (dataTab > 6 || i > 6) {
						dataTab = 1;
						i = 1;
					}
					$('.itemDot').removeClass('active');
					$('[data-tab="' + i + '"]').addClass('active');
					$('.CirItem').removeClass('active');
					$('.CirItem' + i).addClass('active');
					i++;

					$('.dotCircle').css(
							{
								"transform" : "rotate(" + (360 - (i - 2) * 36)
										+ "deg)",
								"transition" : "2s"
							});
					$('.itemDot').css({
						"transform" : "rotate(" + ((i - 2) * 36) + "deg)",
						"transition" : "1s"
					});

				}, 5000);

				if ('${loginModal}') {
					$("#exampleModal").modal();
				}

			});
	/* $('#exampleModal').on('shown.bs.modal', function () {
	    $('#j_username').focus();
	}); */
</script>
<script>
	$(document).ready(function() {
		$("#owl-demo").owlCarousel({

			autoPlay : 5000, //Set AutoPlay to 3 seconds
			stopOnHover : true,
			nav : true,
			dots : true,
			items : 5,
			itemsDesktop : [ 1199, 3 ],
			itemsDesktopSmall : [ 979, 3 ]

		});
		var owl = $("#owl-demo");
		 
		 
		 
		  // Custom Navigation Events
		  $(".next").click(function(){
		    owl.trigger('owl.next');
		  })
		  $(".prev").click(function(){
		    owl.trigger('owl.prev');
		  })
		  
		  
		$('#carousel').carousel({
			interval : 6000

		});

	});
</script>

<style type="text/css">

.text-blue{
color: #022260;
}

#loginSubmit {
    width: 100%;
    }
    
  
#loginSubmit:hover {
    background-color: #002f8b !important;
    }  
    
.push{
    height: 0px !important;
}

.about-ul li {
    font-size: 18px !important;
    }
    
.px-3{
padding: 2rem;

}

.m-0{
margin: 0px;

}

</style>

</head>
<body>
	<!-- Start Slider Area -->
	<div class="row slider-row mt-9">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 slider-section">

			<div id="carousel" class="carousel slide-new slide carousel-fade"
				data-ride="carousel">
		 <ol class="carousel-indicators">
        <li data-target="#carousel" data-slide-to="0" class="active"></li>
        <li data-target="#carousel" data-slide-to="1"></li>
       <li data-target="#carousel" data-slide-to="2"></li>
    </ol> 
				<!--  Carousel items -->
				<div class="carousel-inner carousel-zoom">
					<div class="active item">
						<img class="img-responsive"
							src="/PMS/resources/app_srv/PMS/global/images/slider/sdlc5.png"
							width="100%;">
					
					</div>
					<div class=" item">
						<img class="img-responsive"
							src="/PMS/resources/app_srv/PMS/global/images/slider/pms7.png"
							width="100%;">
						<!-- <div
							class="carousel-caption carousel-caption-home wow slideInLeft"
							data-wow-duration="2s" data-wow-delay="1s">
							<h2>Academic Block</h2>

						</div> -->
					</div>
					
					<div class=" item">
						<img class="img-responsive"
							src="/PMS/resources/app_srv/PMS/global/images/slider/finance-monitoring2.png
					"
							width="100%;">
						<!-- <div
							class="carousel-caption carousel-caption-home wow slideInLeft"
							data-wow-duration="2s" data-wow-delay="1s">
							<h2>Academic Block</h2>

						</div> -->
					</div>
					
					
				<!-- 	<div class="item ">
						<img class="img-responsive"
							src="/PMS/resources/app_srv/PMS/global/images/slider/ban3.3.png"
							width="100%;">
						<div
							class="carousel-caption carousel-caption-home wow slideInLeft"
							data-wow-duration="2s" data-wow-delay="1s">
							<h2>Anusandhan Bhawan</h2>

						</div>
					</div> -->

				</div>
				<a class="left carousel-control" href="#carousel" role="button" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#carousel" role="button" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
				
				

			</div>

		</div>
	</div>

	<!-- End Slider Area -->
		
	<!--==========================
      under carousel section
    ============================-->
  <section id="under-caro">
  <div class="container-fluid">
  <div class="row padding-10">
  <div class="col-md-4 ">
  <div class="tile-gra">
  <span class="tile-text"> Team Management</span>
   <span class="pre-icon "><img src="/PMS/resources/app_srv/PMS/global/images/slider/tm-mgt.png" width="40px"> </span> <!-- bg-tile-1 -->
  </div>
   </div>
   <!-- Changed col-md-3 to col-md-4  -->
 <div class="col-md-4 ">
  <div class="tile-gra "> <!--  icon-blue -->
 <span class="tile-text"> Project Management</span>
   <span class="pre-icon "><img src="/PMS/resources/app_srv/PMS/global/images/slider/project-mgt.png" width="40px"> </span> <!-- icon-blue -->
  </div>
   </div>
  
   <!-- Changed col-md-3 to col-md-4  -->
 <div class="col-md-4 ">
  <div class="tile-gra "> <!-- bg-tile -->
  <span class="tile-text"> Finance Management</span>
   <span class="pre-icon "><img src="/PMS/resources/app_srv/PMS/global/images/slider/finance-mgt.png" width="40px"> </span>
  </div><!-- bg-tile -->
   </div> 

  <%-- hide the Inventory Management 
    <div class="col-md-3">
  <div class="tile-gra "> <!-- icon-red -->
  <span class="tile-text"> Inventory Management</span>
   <span class="pre-icon "><img src="/PMS/resources/app_srv/PMS/global/images/slider/inventory-mgt.png" width="40px"> </span>
  </div> <!-- icon-red -->
   </div> 
    --%>
   
  </div>
  </div>
  </section>
	
	
	
	
	<!--==========================
      About Us Section
    ============================-->
	<div class="row">
		<section id="about">

			<div class="row">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div
						class="col-md-4 col-lg-4 col-sm-4 col-xs-12 about-us-new-box-1 zero wow slideInLeft"
						data-wow-duration="1s" data-wow-delay=".5s">
						<div class="serviceBoxAbout">
							<div class="service-content">
								<h3 class="title">OBJECTIVES</h3>
								<p class="description">This system focuses on managing
									projects by planning, organizing, and managing there different
									required aspects. It includes</p>

								<!-- <p class="description">   </p>  -->
								<ul type="circle" class="about-ul px-3">
								<!-- Hide theEstimation activities  -->
										
									<!-- <li class="about-li"><i
										class="fa fa-calculator  icon-orange"></i> Estimation
										activities</li>  -->
									<li class="about-li"><i
										class="fa fa-calendar-plus-o icon-blue"></i>
										Scheduling</li>
									<li class="about-li"><i
										class="fa fa-inr  icon-green"></i> Income &amp; Expenditure</li>
									<li class="about-li"><i
										class="fa fa-users  icon-red"></i> Resource allocation</li>
										<!-- Hide Change control  -->
									<!--<li class="about-li"><i
										class="fa fa-refresh  icon-pink"></i> Change control</li>  -->
								</ul>  
                           
							</div>
						</div>
					</div>
					<div
						class="col-md-8 col-lg-8 col-sm-8 col-xs-12 about-us-new-box-2 wow slideInRight"
						data-wow-duration="1s" data-wow-delay=".5s">

						<div
							class="col-md-12 col-lg-12 col-sm-12 col-xs-12 about-box margin-top30">
							<div class="box pad-right">
								<div class="icon">
									<div class="image mission-image">
										<img class="img-responsive"
											src="/PMS/resources/app_srv/PMS/global/images/mission-new1.png">

									</div>
									<div class="info "
										style="background: #f2dffc; border-bottom: 3px solid #7e29a8;">
										<h3 class="title about-content-heading"
											style="color: #7e29a8; text-align: left;">Mission</h3>
										<p>Evolve technology solutions - architectures, systems
											and standards for nationally important problems. Also,
											utilize the Intellectual Property generated by converting it
											to business opportunity.</p>

									</div>
								</div>


							</div>

						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 about-box">
							<div class="box pad-right">
								<div class="icon">
									<div class="image plan-image">
										<img class="img-responsive"
											src="/PMS/resources/app_srv/PMS/global/images/plan-new1.png">
									</div>
									<div class="info"
										style="background: #ddecfa; border-bottom: 3px solid #2a67a0;">
										<h3 class="title about-content-heading"
											style="color: #2a67a0; text-align: left;">Plan</h3>
										<p>Innovation and pursuit of excellence in 'Applications',
											'Research' and 'Technology'. Integrity, transparency and
											openness in all our actions. Also, strive to continuously
											improve our processes and quality.</p>

									</div>
								</div>

							</div>

						</div>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 about-box">
							<div class="box pad-right">
								<div class="icon">
									<div class="image vision-image">
										<img class="img-responsive"
											src="/PMS/resources/app_srv/PMS/global/images/vision-new1.png">
									</div>
									<div class="info"
										style="background: #dffecf; border-bottom: 3px solid #348f09;">
										<h3 class="title about-content-heading"
											style="color: #348f09; text-align: left;">Vision</h3>
										<p>To emerge as the premier R&amp;D institution for the
											design, development and deployment of world class electronic
											and IT solutions for economic and human advancement.</p>

									</div>
								</div>

							</div>

						</div>
					</div>

				</div>
			</div>
		</section>
	</div>
	<!-- #about -->

	<!--Section for thrust area  -->
	<div class="row">
		<section class="thrust_area" id="thrust_area">

			<!-- <div class="container"> -->


			<header class="section-header section-header-dark wow fadeInDown"
				data-wow-duration="1s" data-wow-delay=".5s">
				<h3>Thrust Areas</h3>
			</header>

			<div class="row pad-top-double">

				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

					<div id="owl-demo" class="">

						<div class="item">
							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img13.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">
											Consultancy or <br>Evaluations
										</h5>

									</div>
								</div>
							</div>



						</div>

						<div class="item">
							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img9.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">
											Design and<br> Implementation
										</h5>

									</div>
								</div>
							</div>



						</div>

						<div class="item">
							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img2.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">
											Design &amp;<br> Development
										</h5>

									</div>
								</div>
							</div>


						</div>

						<!-- <div class="item">
							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img1.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">
											Design / <br> Implementation
										</h5>

									</div>
								</div>
							</div>

						</div> -->
						<div class="item">

							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img6.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">
											Healthcare Data Analytics &amp;<br> AI/ML
										</h5>

									</div>
								</div>
							</div>


						</div>
						<div class="item">
							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img3.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">
											Health<br> Informatics
										</h5>


									</div>
								</div>
							</div>


						</div>

						<div class="item">

							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img4.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">Multilingual Computing
											and Heritage</h5>

									</div>
								</div>
							</div>

						</div>

						<div class="item">
							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img5.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">Professional
											Electronics,including VLSI &amp; Embedded System</h5>

									</div>
								</div>

							</div>




						</div>

						<div class="item">

							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img10.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">Software Technologies
											including FOSS</h5>

									</div>
								</div>
							</div>

						</div>



						<div class="item">

							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img7.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">
											Technology Platform/<br> Initiatives
										</h5>

									</div>
								</div>
							</div>


						</div>
						<div class="item">

							<div class="cuadro_intro_hover ">
								<p style="text-align: center;">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/thrustAreas/img11.png"
										class="img-responsive" width="100%;">
								</p>
								<div class="caption">
									<div class="blur"></div>
									<div class="caption-text">
										<h5 class="padded caption-text-h5">
											Trustworthy Digital<br> Repositories
										</h5>

									</div>
								</div>
							</div>

						</div>
					</div>
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top">
					<div class="customNavigation">
						<a class="btn prev">Previous</a> <a class="btn next">Next</a>
						</div>
					</div>
				</div>
			</div>
			<!-- </div> -->
		</section>
	</div>
	<!--Thrust Area Section Ends here  -->

	<!-- Services Section  -->
	<div class="row">
		<section class="blog " id="blog">
			<div class="container ">
				<header class="section-header section-header-dark wow fadeInDown">
					<h3>Services</h3>
				</header>
				<div class="row pad-top-double">
					<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 "
						data-aos="flip-left" data-aos-easing="ease-out-cubic"
						>
						<div class="serviceBox">
							<div class="service-content">
								<div class="service-icon">
									<span><i class="fa fa-list-alt"></i></span>
								</div>
								<h3 class="title">Project Monitoring</h3>
								<div class="service-img pad-top-semi">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/service-img1.png">
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 "
						data-aos="flip-left" data-aos-easing="ease-out-cubic"
						>
						<div class="serviceBox orange">
							<div class="service-content">
								<div class="service-icon">
									<span><i class="fa fa-users"></i></span>
								</div>
								<h3 class="title">Team Monitoring</h3>
								<div class="service-img pad-top-semi">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/service-img2.png">
								</div>
							</div>
						</div>
					</div>

					<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 "
						data-aos="flip-left" data-aos-easing="ease-out-cubic"
						>
						<div class="serviceBox purple">
							<div class="service-content">
								<div class="service-icon">
									<span><i class="fa fa-inr"></i></span>
								</div>
								<h3 class="title">Project Finance Monitoring</h3>
								<div class="service-img">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/service-img3.png">
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12"
						data-aos="flip-left" data-aos-easing="ease-out-cubic"
						>
						<div class="serviceBox green">
							<div class="service-content">
								<div class="service-icon">
									<span><i class="fa fa-cogs"></i></span>
								</div>
								<h3 class="title">Statistics</h3>
								<div class="service-img pad-top-semi">
									<img
										src="/PMS/resources/app_srv/PMS/global/images/service-img4.png">
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
	
	<!-- Contact-Us -->
		<section class="contact " id="contact">
		<div class="container-fluid ">
		
		<header class="section-header section-header-dark wow fadeInDown">
					<h3>Contact Us</h3>
				</header>
		
		<div class="row pad-top-double">
		<div class="col-xs-12 col-sm-6 col-lg-6 col-md-6 tex-align">
		<div class="cont-card">
		<div class="anushandhan-img">
		 <img src="/PMS/resources/app_srv/PMS/global/images/slider/ban3.3.png" width="100%">
		<h3 class="title bg-blue-new text-center">Anusandhan Bhawan</h3>
		</div>
		 <div class="contact-detail mt-10">
			<div class="row flex-justi ">
		
	
	<span class="contact-fa-icon margin-right"> <i
													class="fa fa-map-marker"></i></span>
			
	<span>
												<p>C - 56/1, Sector - 62,</p>
												<p class="  ">Noida - 201307</p>
												<p class="  ">Uttar Pradesh (India)</p>
											</span>	
											
			</div>															
		</div>
			 <div class="contact-detail mt-10">
			<div class="row flex-justi ">
		
	
	<span class="contact-fa-icon margin-right"> <i
													class="fa fa-phone"></i></span>
	<%-- changed No.
	 <span class="mt-5">+91-120- 3063311-14 </span> --%>		
	<span class="mt-5">+91-120- 2210800 </span>	
											
			</div>															
		</div>
		</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-lg-6 col-md-6 tex-align">
		<div class="cont-card">
		<div class="anushandhan-img">
		 <img src="/PMS/resources/app_srv/PMS/global/images/slider/ban3.2.png" width="100%">
		<h3 class="title bg-blue-new text-center">Academic Block</h3>
		</div>
		 <div class="contact-detail mt-10">
			<div class="row flex-justi ">
		
	
	<span class="contact-fa-icon margin-right"> <i
													class="fa fa-map-marker"></i></span>
			
	<span>
												<p>B - 30, Sector-62, Institution </p>
												<p class="  "> Area, Noida -201307</p>
												<p class="  ">Uttar Pradesh (India)</p>
											</span>	
											
			</div>															
		</div>
			 <div class="contact-detail mt-10">
			<div class="row flex-justi ">
		
	
	<span class="contact-fa-icon margin-right"> <i
													class="fa fa-phone"></i></span>
 <%-- changed No.
	 <span class="mt-5">+91-120- 3063371-73</span> --%>			
	<span class="mt-5">+91-120- 2210800</span>	
											
			</div>															
		</div>
		</div>
		</div>
		
		</div>
		</div>
		</section>
	
	
	<!-- <div class="row">
		<section class="contact " id="contact">
			<div class="container contact-container ">
				<header class="section-header section-header-dark wow fadeInDown">
					<h3>Contact Us</h3>
				</header>
				<div class="row pad-top-double">
					
						<div class="col-xs-12 col-sm-6 col-lg-6 col-md-6 contact-box">
							<div class="box">
								<div class="icon">
								
									
									
									<div class="info contact-box-info-height">
									<img src="/PMS/resources/app_srv/PMS/global/images/slider/ban3.3.png" width="100%">
										<h3 class="title blue2 text-center">Anusandhan Bhawan</h3>
										<div
											class=" col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double margin-left-100">
											<div class="col-md-1 col-lg-1 col-sm-2 col-xs-2">
												<span class="  contact-fa-icon"> <i
													class="fa fa-map-marker"></i></span>
											</div>
											<div
												class="col-md-11 col-lg-11 col-sm-10 col-xs-10 contact-detail">
												<p>C - 56/1, Sector - 62,</p>
												<p class="  ">Noida - 201307</p>
												<p class="  ">Uttar Pradesh (India)</p>
											</div>
										</div>
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double margin-left-100">
											<div class="col-md-1 col-lg-1 col-sm-2 col-xs-2">
												<p class="  contact-fa-icon">
													<i class="fa fa-phone"></i>
											</div>
											<div
												class="col-md-11 col-lg-11 col-sm-10 col-xs-10 contact-detail">
												<p class="pad-top-half">+91-120- 3063311-14</p>


											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-6 col-md-6 contact-box">
							<div class="box">
								<div class="icon">
										<div class="image image-color-2">
									<img class="img-responsive"
											src="/PMS/resources/app_srv/PMS/global/images/contact/phone-icon.png">
									<i class="fa fa-phone"></i>
									</div>
									<div class="info contact-box-info-height">
										<h3 class="title blue2 text-center">Academic Block</h3>

										<div
											class=" col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double margin-left-100">
											<div class="col-md-1 col-lg-1 col-sm-2 col-xs-2">
												<span class="  contact-fa-icon"> <i
													class="fa fa-map-marker"></i></span>
											</div>
											<div
												class="col-md-11 col-lg-11 col-sm-10 col-xs-10 contact-detail">
												<p>B - 30, Sector - 62, Institution Area,</p>

												<p class="  ">Noida - 201307</p>
												<p class="  ">Uttar Pradesh (India)</p>
											</div>
										</div>
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-top-double margin-left-100">
											<div class="col-md-1 col-lg-1 col-sm-2 col-xs-2">
												<p class="  contact-fa-icon">
													<i class="fa fa-phone"></i>
											</div>
											<div
												class="col-md-11 col-lg-11 col-sm-10 col-xs-10 contact-detail">
												<p class="pad-top-half">+91-120- 3063371-73</p>

											</div>
										</div>
									</div>
								</div>

							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-lg-4 col-md-4 contact-box">
							<div class="box">
								<div class="icon">
									<div class="image image-color-3">
										<img class="img-responsive"
											src="/PMS/resources/app_srv/PMS/global/images/contact/email-icon.png">
											<i class="fa fa-envelope-open"></i>
									</div>
									<div class="info contact-box-info-height">
										<h3 class="title blue2 text-center">Email</h3>
										<p class=" text-center ">N.A.</p>


									</div>
								</div>

							</div>
						</div>
					
				</div>
			</div>
		</section>
	</div> -->
	<!--//contact -->
	<!--model-forms-->
	<!--/Login-->
	<!-- Login modal added by harshita on 30Aug 2018 -->
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel1" aria-hidden="true"
		data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<form name="f" action="login" method="POST"
					onsubmit="return validate();">
					<div class="modal-header">
						<h5 class="modal-title "
							id="exampleModalLabel1">Login</h5>
						<button type="button" class="close login_close"
							data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>

					</div>
					<div class="modal-body">
					
				<div class="row">
					<div class="col-12 text-center">
					<div class="avtar"> <i class="  fa fa-users fa-5x text-center text-blue"></i></div>
					</div>
					</div> 
					
					
						<div class="row">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
								<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
									<p class="red-bright font_12">
										Your login attempt was not successful due to
										<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
										.
									</p>
								</c:if>
							</div>
						</div>
						<input type="hidden" id="prefixRandom" value="${sessionScope.prefixRandom}"/>
						<input type="hidden" id="suffixRandom" value="${sessionScope.suffixRandom}"/>
								
						<div class="form-group">
							<label for="recipient-name" class="col-form-label login_names">User
								Name</label> <input type="email" class="form-control" name="username"
								placeholder="User Name" onfocus="this.placeholder = ''"
								id="j_username" autofocus></input>
						</div>
						<div class="form-group">
							<label for="recipient-name1" class="col-form-label login_names">Password</label>
							<input type="password" class="form-control"
								placeholder="Password" onfocus="this.placeholder = ''"
								id="password" name="password"></input>
						</div>
						<c:set var="rand1"><%=java.lang.Math.round(java.lang.Math.random() * 10)%>
						</c:set>
						<c:set var="rand2"><%=java.lang.Math.round(java.lang.Math.random() * 10)%>
						</c:set>

						<c:set var="rand1" value="${rand1}" scope="session" />
						<c:set var="rand2" value="${rand2}" scope="session" />
						<label>What is ${rand1}+${rand2} ?</label>

						<div class="pure-control-group">
							<input name="captcha" type="text" placeholder="Captcha value"
								pattern="[0-9]+" title="Please enter number only"
								required="true" />
						</div>

						<div class="row pad-top">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-center">

								<button type="submit" id="loginSubmit" class="btn btn-primary"
									value="Login">Submit</button>

							</div>
						</div>
						<div class="row pad-top">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-center">
								<label for="Forget Password" class="col-form-label ">
								 <a class="font_12 text-secondary" href=""
									onclick="return popitup('/PMS/ForgotPassword')">Forgot
										Password</a>

								</label>
							</div>

						</div>
<h5 align="center"> OR</h5>
<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-center">
					<label for="Generate OTP" class="col-form-label ">
								 <a class="font_14 blue-bright" href=""
									onclick="return openInNewTab('/PMS/GenerateOTP')">Login with OTP</a>
	</label>
</div>
					</div>
				</form>
			</div>

		</div>

	</div>

	<!-- //Login modal -->
	<!--//Login-->

	<!--//model-form-->

	<!-- dropdown nav -->
	<script>
		$(document).ready(function() {
			$(".dropdown").hover(function() {
				$('.dropdown-menu', this).stop(true, true).slideDown("fast");
				$(this).toggleClass('open');
			}, function() {
				$('.dropdown-menu', this).stop(true, true).slideUp("fast");
				$(this).toggleClass('open');
			});

		});
	</script>


	<script>
	
	function openInNewTab(url) {
		//alert(url);
		  window.open(url, '_blank').focus();
		}
	
	</script>
	<script>
		function popitup(url) {
			newwindow = window.open(url, 'window', 'width=' + window.outerWidth
					/ 2 + ',height=200,top=200px,left=350px');

		}
	</script>

	<script
		src="/PMS/resources/app_srv/PMS/global/js/new/SmoothScroll.min.js"></script>
	<script src="/PMS/resources/app_srv/PMS/global/js/new/move-top.js"></script>
	<script src="/PMS/resources/app_srv/PMS/global/js/new/easing.js"></script>
	<script src="/PMS/resources/app_srv/PMS/global/js/lib/aos.js"></script>

	<script src="/PMS/resources/app_srv/PMS/global/js/sha256.js"></script>

	<script src="/PMS/resources/app_srv/PMS/global/js/lib/wow/wow.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/owl-carousel/1.3.3/owl.carousel.js"></script>



	<script>
		new WOW().init();
		AOS.init();
	</script>
	<script>
	function validate() {
		
		var password = $('#password').val();
		var prefixRandom = $('#prefixRandom').val();
		var suffixRandom = $('#suffixRandom').val();
		
		//var hash = CryptoJS.SHA256(randomValue1+' ### '+password+' ### '+randomValue2);		
		var base = window.btoa(prefixRandom+' ### '+password +' ### '+suffixRandom);
		
		$('#password').val(base);
		return true;
	}
	</script>
	
</body>
</html>