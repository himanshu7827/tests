
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- header -->

<div class="row">
	<div class="col-md-12 col-lg-12 col-xs-12 col-sm-12 top-header div-pad d-top-head" style="background: linear-gradient(145deg, #1578c2 68%, #ca9b35  68%);">
		<div class=" pad-top-half sys_date font-italic">
			<i class="fa fa-calendar fa-1x hidden-xs white"></i> <span id="date2"
				class="" style="color: #fff;"></span>
		</div>
	<!-- 	<div class="col-md-8 col-lg-8 hidden-xs col-sm-7">
			<div id="triangle-bottomright"></div>
		</div> -->
		
		<div id="acess-icons" class="pad-right skip-pad">
          <a class="text-white lh font-italic skip" aria-label="toggle Contrast" href="#about" id="contrast">Skip to content</a>
        </div>

	</div>

	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12  div-pad">
    <div class="row justify-content-between">
    <div class="col-md-7 col-lg-7 col-sm-12 col-xs-12 ">
		<div class="header-logo pad-top pad-bottom-half d-flex">
			<a href="https://cdac.in/" target="new"><img
				src="/PMS/resources/app_srv/PMS/global/images/cdac-logo-new2.png"
				class="img-responsive"></a>
				
			<div class="header-nav ">
			<div class="header-nav-content">
				<h1>
					<span class="font-40" style="color: /* #173faa */ #ca9532;;">P</span><span
						class="" style="color: /* #3e71ca */ #1578c2;">roject</span><span
						class="font-40" style="color: /* #173faa */ #ca9532;;"> M</span><span
						class="" style="color: /* #3e71ca */ #1578c2;">anagement</span> <span
						class="font-40" style="color: /* #173faa */ #ca9532;;">S</span><span
						class="" style="color: /* #3e71ca */ #1578c2;">ystem</span>
				</h1>
				<h4>Centre For Development of Advanced Computing  <span>(Noida)</span></h4>
			</div>
		</div>	
		</div>
		</div>
		<div class=" col-md-5 col-lg-5 col-sm-12 col-xs-12 text-right d-logo-none">
		<div class="header-logo pad-top pad-bottom-half d-none">
			<a href="https://cdac.in/" target="new"><img
				src="/PMS/resources/app_srv/PMS/global/images//pngegg (4).png" width="190px"></a>
		</div>
	</div>	
</div>
</div>
</div>


<!-- <div class="row">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
		<nav class="navbar navigation-bar">
			<div class="container-fluid">
				<ul class="nav navbar-nav navbar-right">
					<li class="sign-btn"><a href="" data-toggle="modal"
						data-target="#exampleModal" class="log"> <i
							class="fa fa-user "></i><span class="pad-left">Login</span>
					</a></li>

				</ul>
			</div>
		</nav>
	</div>
</div> -->

<div class="row bg-light-gray">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 bdr-bottom-nav">
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="bdr-l"><a href="#">Home <span class="sr-only">(current)</span></a></li>
       <li class="bdr-l "><a href="#about">About Us</a></li>
       <li class="bdr-l " ><a href="#thrust_area">Thrust Areas</a></li>
       <li class="bdr-l" ><a href="#blog">Services</a></li> 
       <li class="bdr-l bdr-r" ><a href="#contact">Contact Us</a></li> 
       
      </ul>
    
 <!--     <ul class="nav navbar-nav navbar-right">
					<li class="sign-btn"><a href="" data-toggle="modal"
						data-target="#exampleModal" class="log">
							<span class="pad-left my-3">Login</span>
					</a></li>

				</ul>
				 -->
	<a class="float-login mt-log" href="" data-toggle="modal"
						data-target="#exampleModal">
							<button type="button" class="btn btn-primary">Login</button> </a>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

</div>
</div> 



<script type="text/javascript">
	var tday = [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
			"Friday", "Saturday" ];
	var tmonth = [ "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" ];

	function GetClock() {
		var d = new Date();
		var nday = d.getDay(), nmonth = d.getMonth(), ndate = d.getDate(), nyear = d
				.getFullYear();
		var nhour = d.getHours(), nmin = d.getMinutes(), nsec = d.getSeconds(), ap;

		if (nhour == 0) {
			ap = " AM";
			nhour = 12;
		} else if (nhour < 12) {
			ap = " AM";
		} else if (nhour == 12) {
			ap = " PM";
		} else if (nhour > 12) {
			ap = " PM";
			nhour -= 12;
		}

		if (nmin <= 9)
			nmin = "0" + nmin;
		if (nsec <= 9)
			nsec = "0" + nsec;

		var clocktext = "" + tday[nday] + ", " + tmonth[nmonth] + " " + ndate
				+ ", " + nyear + " " + nhour + ":" + nmin + ":" + nsec + ap
				+ "";
		document.getElementById('date2').innerHTML = clocktext;
	}

	GetClock();
	setInterval(GetClock, 1000);
</script>