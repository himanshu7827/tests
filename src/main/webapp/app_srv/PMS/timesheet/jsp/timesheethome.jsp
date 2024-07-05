<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.time.temporal.WeekFields"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.time.DayOfWeek"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--   <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"> -->
  <link rel="stylesheet" href="<c:url value="/resources/app_srv/PMS/timesheet/css/style.css"></c:url>">
  <title>Timesheet</title>
</head>
<body>
	<section id="main-content" class="main-content merge-left">
	
		<div class=" container wrapper1">
		
			
			<%-- <c:choose>
			    <c:when test="${empty yearToday}">
			    	<c:set var="reportYear" value="${calenderSelectedYear}"></c:set>
			    </c:when>
			    <c:otherwise>
			    	<c:set var="reportYear" value="${yearToday}"></c:set>
			    </c:otherwise>
			</c:choose> --%>
			<div style="display: flex; justify-content:right">
		  		<%-- <a href="individualReports/${reportYear }"> --%>
		  		<a href="individualReports">
		  			<h4 class="timesheetTitle" style="padding-bottom: 20px;">
						<span style="color: #173faa;">R</span><span style="color: #1578c2">eports</span>
					</h4>
				</a>
		  	</div>
		  <div class="container mt-5">
			<div>
				<header style="display: flex; justify-content:center">
					<h1 class="timesheetTitle" style="text-shadow: 2px 0px 2px #1e2787; padding-bottom: 20px;">
					<span style="color: #173faa;">T</span><span style="color: #1578c2">imesheet</span>
					</h1>
				</header>
		  	</div> 
		<div class="container" style=" display: flex; justify-content: center;">
			 <form id="myForm" style="width: 400px;" class="calendarForm" action="calenderSelectedMonthYear" method="post">
			  	 <input type="hidden" id="selectedDateNumber" name="selectedDateNumber" value="15"/>
			  	 <input type="hidden" name="userId" value="${empId }"/>
			  	 <div class="row" style="display: flex; justify-content: center; padding-bottom: 10px;">
			  	 	<div class="col-md-4">
			            <label for="calenderSelectedMonth">Select Month:</label>
						 <select class="monthList form-control" name="calenderSelectedMonth">
							 <c:choose>
									    <c:when test="${empty monthToday}">
									        <option>${calenderSelectedMonth}</option>
									    </c:when>
									    <c:otherwise>
									        <option>${monthToday}</option>
									    </c:otherwise>
							 </c:choose>
						 	<c:forEach var="monthName" items="${monthNameList }">
								<option value="${monthName }">${monthName }</option>
							</c:forEach>
						 </select>
					 </div>
					 <div class="col-md-4">
			             <label for="calenderSelectedYear">Select Year:</label>
						 <select class="yearList form-control" name="calenderSelectedYear">
						 	<c:choose>
									    <c:when test="${empty yearToday}">
									        <option>${calenderSelectedYear}</option>
									    </c:when>
									    <c:otherwise>
									        <option>${yearToday}</option>
									    </c:otherwise>
							 </c:choose>
						 	<c:forEach var="year" items="${yearList }">
								<option value="${year }">${year }</option>
							</c:forEach>
						 </select>
					 </div>
				 </div>
				 <c:set var = "check" value = '${fn:length(calenderDayList)}'></c:set>
		        <div class="calendar" style="width: 407px">
		            <table border: "1" class="table table-bordered">
		            	<thead class="thead-dark">
		            	<tr>
		            		<th></th>
		            		<th>Mon</th>
		            		<th>Tue</th>
		            		<th>Wed</th>
		            		<th>Thu</th>
		            		<th>Fri</th>
		            		<th style="background-color:#666666;border-color: #666666;">Sat</th>
		            		<th style="background-color:#666666;border-color: #666666;">Sun</th>
		            	</tr>
		            	</thead>
		            	<tbody>
		            		<c:set var="filledTilldate" value="1" />
		            		
		            		<tr>
		            			<td style="background-color: rgb(33, 37, 41); color: white;"><strong><span>Week ${calenderWeekNumbers[0]}</span></strong></td>
							    <c:forEach var="index" begin="1" end="${calenderStartDay - 1}">
							    		<c:choose>
							    			<c:when test="${index eq 6 or index eq 7}">
							    				<td style="background-color: #e6deed"><div class="block"></div></td>
							    			</c:when>
							    			<c:otherwise>
							    				<td style="background-color: ${calendarColor[0]};"><div class="block"></div></td>
							    			</c:otherwise>
							    		</c:choose>
								</c:forEach>
								<c:forEach var="index" begin="${calenderStartDay}" end="7">
									<c:set var="isHolidayCompulsary" value="false" />
									<c:set var="isHolidayCompulsaryNameIndex" value="-1" />
									<c:forEach var="item" items="${holidayListDaysCompulsary}" varStatus="loopStatus">
										<c:if test="${item eq filledTilldate}">
											<c:set var="isHolidayCompulsary" value="true" />
											<c:set var="isHolidayCompulsaryNameIndex" value="${loopStatus.index}" />
										</c:if>
									</c:forEach>
									<c:set var="isHolidayRestricted" value="false" />
									<c:set var="isHolidayRestrictedNameIndex" value="-1" />
									<c:forEach var="item" items="${holidayListDaysRestricted}" varStatus="loopStatus">
										<c:if test="${item eq filledTilldate}">
											<c:set var="isHolidayRestricted" value="true" />
											<c:set var="isHolidayRestrictedNameIndex" value="${loopStatus.index}" />
										</c:if>
									</c:forEach>
									<c:choose>
										<c:when test="${isHolidayCompulsary}">
											<c:choose>
												<c:when test="${(filledTilldate eq currentDayOfMonth) and currentDayFlag}">
													<td title="${holidayNamesDaysCompulsary[isHolidayCompulsaryNameIndex] } (Today)" style="background-color: yellow; transform: translate(-4px, -4px); border: 2px solid #90EE90; filter: brightness(80%);"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td>
												</c:when>
												<c:otherwise>
													<td title="${holidayNamesDaysCompulsary[isHolidayCompulsaryNameIndex] }" style="background-color: yellow; filter: brightness(80%);"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td>
												</c:otherwise>
											</c:choose>
											<%-- <td style="background-color: yellow;"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td> --%>
										</c:when>
										<%-- <c:when test="${isHolidayRestricted}">
											<td style="background-color: pink;"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td>
										</c:when> --%>
										<c:otherwise>
											
											
											<c:choose>
								    			<c:when test="${index eq 6 or index eq 7}">
								    				<c:choose>
														<c:when test="${(filledTilldate eq currentDayOfMonth) and currentDayFlag}">
															<td title="Today" style="background-color: #e6deed; transform: translate(-4px, -4px); border: 2px solid #90EE90; filter: brightness(80%);"><div class="block" onclick="selectDateNumber(${filledTilldate})">${filledTilldate}</div></td>
														</c:when>
														<c:otherwise>
															<td style="background-color: #e6deed"><div class="block" onclick="selectDateNumber(${filledTilldate})">${filledTilldate}</div></td>
														</c:otherwise>
													</c:choose>
								    				<%-- <td style="background-color: #e6deed"><div class="block" onclick="selectDateNumber(${filledTilldate})">${filledTilldate}</div></td> --%>
								    			</c:when>
								    			<c:otherwise>
								    				<c:choose>
														<c:when test="${(filledTilldate eq currentDayOfMonth) and currentDayFlag}">
															<td title="Today" style="background-color: ${calendarColor[0]}; transform: translate(-4px, -4px); border: 2px solid #90EE90; filter: brightness(80%);"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td>
														</c:when>
														<c:otherwise>
															<td style="background-color: ${calendarColor[0]};"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td>
														</c:otherwise>
													</c:choose>
								    				<%-- <td style="background-color: ${calendarColor[0]};"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td> --%>
								    			</c:otherwise>
							    			</c:choose> 
											
										</c:otherwise>
									</c:choose>
								    <c:set var="filledTilldate" value="${filledTilldate + 1}" />
									    
								</c:forEach>
							</tr>
		            		<c:forEach var="indexWeek" begin="1" end="${fn:length(calenderWeekNumbers)-1}">
							    <tr>
							    	<td style="background-color: rgb(33, 37, 41); color: white;"><strong><span>Week ${calenderWeekNumbers[indexWeek]}</span></strong></td>
							    	<c:forEach var="index" begin="1" end="7">
							    		<c:set var="isHolidayCompulsary" value="false" />
							    		<c:set var="isHolidayCompulsaryNameIndex" value="-1" />
										<c:forEach var="item" items="${holidayListDaysCompulsary}" varStatus="loopStatus">
											<c:if test="${item eq filledTilldate}">
												<c:set var="isHolidayCompulsary" value="true" />
												<c:set var="isHolidayCompulsaryNameIndex" value="${loopStatus.index}" />
											</c:if>
										</c:forEach>
										<c:set var="isHolidayRestricted" value="false" />
										<c:set var="isHolidayRestrictedNameIndex" value="-1" />
										<c:forEach var="item" items="${holidayListDaysRestricted}" varStatus="loopStatus">
											<c:if test="${item eq filledTilldate}">
												<c:set var="isHolidayRestricted" value="true" />
												<c:set var="isHolidayRestrictedNameIndex" value="${loopStatus.index}" />
											</c:if>
										</c:forEach>
							    		<c:choose>
										    <c:when test="${filledTilldate gt check}">
										    	<c:choose>
										    		<c:when test="${index eq 6 or index eq 7}">
										    			<td style="background-color:#e6deed "><div class="block"></div></td>
										    		</c:when>
										    		<c:otherwise>
										    			<td style="background-color: ${calendarColor[indexWeek]}"><div class="block"></div></td>
										    		</c:otherwise>
										    	</c:choose>
										        
										    </c:when>
										    <c:otherwise>
										    	<c:choose>
													<c:when test="${isHolidayCompulsary}">
														<c:choose>
															<c:when test="${(filledTilldate eq currentDayOfMonth) and currentDayFlag}">
																<td title="${holidayNamesDaysCompulsary[isHolidayCompulsaryNameIndex] } (Today)" style="background-color: yellow; transform: translate(-4px, -4px); border: 2px solid #90EE90; filter: brightness(80%);"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td>
															</c:when>
															<c:otherwise>
																<td title="${holidayNamesDaysCompulsary[isHolidayCompulsaryNameIndex] }" style="background-color: yellow; filter: brightness(80%);"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td>
															</c:otherwise>
														</c:choose>
														<%-- <td style="background-color: yellow;"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td> --%>
													</c:when>
													<%-- <c:when test="${isHolidayRestricted}">
														<td style="background-color: pink;"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td>
													</c:when> --%>
													<c:otherwise>
													
														<c:choose>
											    			<c:when test="${index eq 6 or index eq 7}">
											    				<c:choose>
																	<c:when test="${(filledTilldate eq currentDayOfMonth) and currentDayFlag}">
																		<td title="Today" style="background-color: #e6deed; transform: translate(-4px, -4px); border: 2px solid #90EE90; filter: brightness(80%);"><div class="block" onclick="selectDateNumber(${filledTilldate})">${filledTilldate}</div></td>
																	</c:when>
																	<c:otherwise>
																		<td style="background-color: #e6deed"><div class="block" onclick="selectDateNumber(${filledTilldate})">${filledTilldate}</div></td>
																	</c:otherwise>
																</c:choose>
											    				<%-- <td style="background-color: #e6deed"><div class="block" onclick="selectDateNumber(${filledTilldate})">${filledTilldate}</div></td> --%>
											    			</c:when>
											    			<c:otherwise>
											    				<c:choose>
																	<c:when test="${(filledTilldate eq currentDayOfMonth) and currentDayFlag}">
																		<td title="Today" style="background-color: ${calendarColor[indexWeek]}; transform: translate(-4px, -4px); border: 2px solid #90EE90; filter: brightness(80%);"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td>
																	</c:when>
																	<c:otherwise>
																		<td style="background-color: ${calendarColor[indexWeek]}"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td>
																	</c:otherwise>
																</c:choose>
											    				<%-- <td style="background-color: ${calendarColor[indexWeek]}"><div class="block" onclick="selectDateNumber(${filledTilldate})"><strong>${filledTilldate}</strong></div></td> --%>
											    			</c:otherwise>
										    			</c:choose>
													</c:otherwise>
												</c:choose>
										    </c:otherwise>
										 </c:choose>
									    <c:set var="filledTilldate" value="${filledTilldate + 1}" />
									</c:forEach>
							    </tr>
							</c:forEach>
		            	</tbody>
		            	
		            </table>
		        </div>
			</form>
		</div>
		<div style="display: flex; justify-content: center;">
			<div title="${holidayNamesDaysCompulsary }" style="background-color: yellow; filter: brightness(80%);" class="box"></div><p class="box-text">Holiday</p> <!-- Mandatory Holiday -->
			<!-- <div style="background-color: pink" class="box"></div><p class="box-text">Restricted</p> -->
		</div> 
		<!-- Help Link -->
		<div class = <c:if test = "${empty projectIds}">"hidden"</c:if>>
			<div class = "hidden" style="display: flex; justify-content: right; font-size: 18px;"> <!-- help button is hidden because it's not required now -->
				<u><strong><a href="#" id="helpLink">Help</a></strong></u>
			</div>
			<div style="font-size: 17px; display: flex; justify-content: left;">
				<p><strong><em>Click on Project Name to fill efforts</em></strong></p>
			</div>
		</div>
		<c:if test="${errorMessage != null && errorMessage != ''}">
           
                 
             <div id="userinfo-message" style="margin-top: 10px">
                   <p class="error_msg">${errorMessage}</p>
             </div>
                  
         </c:if>
	     
	     <div style="display: flex; justify-content: right; font-size: 18px;">
	      		<div style="display: flex; justify-content: center; align-items: center;">Full Month View:&nbsp;&nbsp;</div>
		     <label class="toggle-switch">
		     	 
				  <input type="checkbox" id="myCheckbox">
				  <span class="toggle-slider"></span>
			 </label>
		 </div>
	     
	     
         <input type="hidden" id="haveProjects" name="haveProjects" value="${empty projectIds}"></input>
		 <form style="padding-top: 20px;" id="timesheetForm" action="weeksubmit" method="post">
			 <c:choose>
			    <c:when test="${empty projectIds}">
			      <p style="font-size: 20px"><em>No project under this employee</em></p>
			      <table class="table table-bordered mt-5 lg-8" id="timesheetTable">
					      <!-- Table headers -->
					      <thead>
					        <tr>
					          <th class="top-back" style="width: 61px;">S. No.</th>
					          <th class="top-back">Activity Name</th>
					          	<c:set var="colorIndex" value="0"></c:set>
					            <c:forEach var="item" items="${weekNumbers }">
									<th style="background-color: ${calendarColor[colorIndex]}">Week <c:out value="${item }"></c:out></th>
									<c:set var="colorIndex" value="${colorIndex + 1}"></c:set>
								</c:forEach>
					          <th class="top-back" style="width: 155px;">Total Project Effort</th>
					        </tr>
					      </thead>
					    
					      <!-- Table body -->
					      <tbody>
					        	<!-- Rows for timesheet data - Projects -->
					        	<input type="hidden" class="form-control" name="projectIds" value="${projectIds}"/>
								<!-- Rows for timesheet data - Activities -->
							    <input type="hidden" class="form-control" name="miscActivityIds" value="${miscActivityIds}"/>
							    	<c:set var="previousWeek" value="${activeWeekNumber - 1}" />
							    	<c:set var="serialNo" value="1" />
							        <c:forEach var="index" begin="0" end="${fn:length(miscActivityIds)-1}">
								        <tr class="misc-activity-rows" style="background-color: #CDD4ED;">
								        	<td><c:out value="${serialNo}"></c:out></td>
								        	
											<td><c:out value="${miscActivityNames[index ]}"></c:out></td>
											<c:set var="holidayCountIndex" value="0" />
											<c:forEach var="item" items="${weekNumbers }" varStatus="loopStatus">
												<c:forEach var="holidayCountWeekKey" items="${holidayCountWeekKeys }" varStatus="loopStatusHolidayCount">
													<c:if test="${holidayCountWeekKey eq  item}">
														<c:set var="holidayCountIndex" value="${holidayCount[loopStatusHolidayCount.index] }" />
													</c:if>
												</c:forEach>
												<c:set var="isMoreWorkIndex" value="false" />
												<c:forEach var="weekKey" items="${weekKeys }" varStatus="loopStatus">
													<c:if test="${weekKey eq item and values1[loopStatus.index] gt (2400 - 480*(holidayCountIndex))}">
														<c:set var="isMoreWorkIndex" value="true" />
													</c:if>
												</c:forEach>	
																						
												<c:choose>
													<c:when test="${previousWeek gt item}">
														<c:choose>
															<c:when test="${empty eachAndEveryWeek }">
																<td style="width:90px;">
																	<span>0</span>
																	<!-- <input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" disabled value="0"/> -->
																	<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" />
																</td>
															</c:when>
															<c:otherwise>
																<td style="width:90px;<c:if test="${isMoreWorkIndex }">background-color: #fce9e9;</c:if>">
																	<c:set var="hadValue" value="false"></c:set>
																	
																	<c:forEach var="checkHadValue" begin="0" end="${fn:length(eachAndEveryWeek)-1 }">
																		<c:if test="${(item eq eachAndEveryWeek[checkHadValue]) and (miscActivityIds[index] eq eachAndEveryProject[checkHadValue]) and (eachAndEveryStatus[checkHadValue] eq 'm')}">
																			<c:set var="hadValue" value="true"></c:set>
																			<c:set var="checkHadValueIndex" value="${checkHadValue }"></c:set>
																		</c:if>
																	</c:forEach>
																	<c:choose>
																		<c:when test="${hadValue }">
																			<span><strong>${eachAndEveryOnlyEffort[checkHadValueIndex] }</strong></span>
																			<%-- <input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" disabled value="${eachAndEveryOnlyEffort[checkHadValueIndex] }"/> --%>
																			<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex] }"/>
																		</c:when>
																		<c:otherwise>
																			<span>0</span>
																			<!-- <input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" disabled value="0"/> -->
																			<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)"/>
																		</c:otherwise>
																	</c:choose>
																</td>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${item gt activeWeekNumber }">
																<c:choose>
																	<c:when test="${empty eachAndEveryWeek }">
																		<td style="width:90px;">
																			<span>--</span>
																			<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" />
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td style="width:90px;<c:if test="${isMoreWorkIndex }">background-color: #fce9e9;</c:if>">
																			<c:set var="hadValue" value="false"></c:set>
																			<c:forEach var="checkHadValue" begin="0" end="${fn:length(eachAndEveryWeek)-1 }">
																				<c:if test="${(item eq eachAndEveryWeek[checkHadValue]) and (miscActivityIds[index] eq eachAndEveryProject[checkHadValue]) and (eachAndEveryStatus[checkHadValue] eq 'm')}">
																					<c:set var="hadValue" value="true"></c:set>
																					<c:set var="checkHadValueIndex" value="${checkHadValue }"></c:set>
																				</c:if>
																			</c:forEach>
																			<c:choose>
																				<c:when test="${hadValue }">
																					<span><strong>${eachAndEveryOnlyEffort[checkHadValueIndex] }</strong></span>
																					<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex] }"/>
																				</c:when>
																				<c:otherwise>
																					<span>--</span>
																					<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" />
																				</c:otherwise>
																			</c:choose>
																		
																		</td>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${empty eachAndEveryWeek }">
																		<td style="width:90px;">
																			<c:choose>
																				<c:when test="${activeWeekNumberFlag }">
																					<input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
																				</c:when>
																				<c:otherwise>
																					<span>0</span>
																					<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
																				</c:otherwise>
																			</c:choose>
																			<!-- <input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/> -->
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td style="width:90px;<c:if test="${isMoreWorkIndex }">background-color: #fce9e9;</c:if>">
																		<c:set var="hadValue" value="false"></c:set>
																		<c:forEach var="checkHadValue" begin="0" end="${fn:length(eachAndEveryWeek)-1 }">
																			<c:if test="${(item eq eachAndEveryWeek[checkHadValue]) and (miscActivityIds[index] eq eachAndEveryProject[checkHadValue]) and (eachAndEveryStatus[checkHadValue] eq 'm')}">
																				<c:set var="hadValue" value="true"></c:set>
																				<c:set var="checkHadValueIndex" value="${checkHadValue }"></c:set>
																			</c:if>
																		</c:forEach>
																		<c:choose>
																			<c:when test="${hadValue }">
																				<c:choose>
																					<c:when test="${activeWeekNumberFlag }">
																						<strong><input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex] }" placeholder="HH:MM"/></strong>
																					</c:when>
																					<c:otherwise>
																						<span><strong>${eachAndEveryOnlyEffort[checkHadValueIndex] }</strong></span>
																						<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex] }" placeholder="HH:MM"/>
																					</c:otherwise>
																				</c:choose>
																				<%-- <strong><input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex] }" placeholder="HH:MM"/></strong> --%>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when test="${activeWeekNumberFlag }">
																						<input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
																					</c:when>
																					<c:otherwise>
																						<span>0</span>
																						<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
																					</c:otherwise>
																				</c:choose>
																				<!-- <input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/> -->
																			</c:otherwise>
																		</c:choose>
																		</td>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:otherwise> 
												</c:choose>
											</c:forEach>
											<c:choose>
												<c:when test="${empty activityIdKeys}">
													<td class="dynamicTotalProjectEffort">0</td>
												</c:when>
												<c:otherwise>
														<c:set var="isActivityIdMatch" value="false" />
														<c:set var="j" value="0" />
														<c:forEach var="i" begin="0" end="${fn:length(activityIdKeys)-1}">
															<c:if test="${activityIdKeys[i] eq  miscActivityIds[index]}">
																<c:set var="isActivityIdMatch" value="true" />
																<c:set var="j" value="${i }" />
															</c:if>
														</c:forEach>
														<c:choose>
															<c:when test="${isActivityIdMatch }">
																<td class="dynamicTotalProjectEffort"><strong>${activityEffortValues[j] }</strong></td>
															</c:when>
															<c:otherwise>
																<td class="dynamicTotalProjectEffort">0</td>
															</c:otherwise>
														</c:choose>
												</c:otherwise>
											</c:choose>   
										</tr>
										<c:set var="serialNo" value="${serialNo + 1 }" />
									</c:forEach>
					        
							<input id="yearSelectDB" type="hidden" class="form-control" name="yearSelectDB" value="${yearSelectDB}"/>
							<input type="hidden" class="form-control" name="selectedDate" value="${selectedDate}"/>
							<input type="hidden" class="form-control" name="todaysDate" value="${todaysDate }"/>
							<input id="employeeIdDB" type="hidden" class="form-control" name="employeeIdDB" value="${empId }"/>
							<input type="hidden" class="form-control" name="employeeNameDB" value="${userName }"/>
							<input type="hidden" class="form-control" name="groupIdDB" value="${groupId }"/>
							<input type="hidden" class="form-control" name="weekNumbers" value="${weekNumbers }"/>
							<input type="hidden" id = "activeWeekNumber" name="activeWeekNumber" value="${activeWeekNumber }"/>
							<input type="hidden" id="projectTaskDetails" class="form-control" name="projectTaskDetails" value=""/>
							<input type="hidden" id="taskProjectDetails" class="form-control" name="taskProjectDetails" value=""/>
							<input type="hidden" id="taskProjectTimesheetTypeDetails" class="form-control" name="taskProjectTimesheetTypeDetails" value=""/>
							
					      </tbody>
				      
				      <!-- Table footer -->
					      <tfoot>
					        <tr>
					          <td class="bottom-back"></td>
					          <td class="bottom-back">Total Week Effort</td>
					          <c:forEach var="item" items="${weekNumbers}">
						            <c:choose>
										<c:when test="${empty weekKeys}">
											<td class="dynamicTotalWeekEffort">0</td>
										</c:when>
										<c:otherwise>
												<c:set var="isWeekMatch" value="false" />
												<c:set var="j" value="0" />
												<c:forEach var="i" begin="0" end="${fn:length(weekKeys)-1}">
													<c:if test="${weekKeys[i] eq  item}">
														<c:set var="isWeekMatch" value="true" />
														<c:set var="j" value="${i }" />
													</c:if>
												</c:forEach>
												<c:choose>
													<c:when test="${isWeekMatch }">
														<td class="dynamicTotalWeekEffort"><strong>${weekEffortValues[j] }</strong></td>
													</c:when>
													<c:otherwise>
														<td class="dynamicTotalWeekEffort">0</td>
													</c:otherwise>
												</c:choose>
										</c:otherwise>
									</c:choose>
						       </c:forEach> 
						       <td class="bottom-back"></td>
					        </tr>
					        
					      </tfoot>
					    </table>   
				    	<div style="justify-content: center; display: flex;">
				    		<button class="btn btn-primary btn-success btn-lg validationWeekNumberSubmitted" name="weekNumberSubmitted" style="font-size: larger;">Submit</button>
				    	</div>
					    <div style="display: flex; justify-content: left; margin-top: 5px;" >
				  			<div style="background-color: #CDD4ED" class="box"></div><p class="box-text">Activity</p> <!-- Miscellaneous Activity -->
				  		</div>
				</c:when>
				<c:otherwise>
					    <table class="table table-bordered mt-5 lg-8" id="timesheetTable">
					      <!-- Table headers -->
					      <thead>
					        <tr>
					          <th class="top-back" style="width: 61px;">S. No.</th>
					          <th class="top-back">Project Reference Number & Name</th>
					          	<c:set var="colorIndex" value="0"></c:set>
					            <c:forEach var="item" items="${weekNumbers }">
									<th style="background-color: ${calendarColor[colorIndex]}">Week <c:out value="${item }"></c:out></th>
									<c:set var="colorIndex" value="${colorIndex + 1}"></c:set>
								</c:forEach>
					          <th class="top-back" style="width: 155px;">Total Project Effort</th>
					        </tr>
					      </thead>
					    
					      <!-- Table body -->
					      <tbody>
					        <!-- Rows for timesheet data - Projects -->
					        		<input type="hidden" class="form-control" name="projectIds" value="${projectIds}"/>
					        		<c:set var="previousWeek" value="${activeWeekNumber - 1}" />
					        		<c:set var="serialNo" value="1" />
							        <c:forEach var="index" begin="0" end="${fn:length(projectIds)-1}">
							        	
								        <tr class="project-rows projectRowColor">
								        	<td><c:out value="${serialNo}"></c:out></td>
											<td projectTimesheetType = "${projectTypesByUserId[index ]}" projectId = "${projectIds[index ]}" class="projectNamesUI"><a><span><c:out value="${projectNames[index ]}"></c:out></span></a></td>
											<c:set var="holidayCountIndex" value="0" />
											<c:forEach var="item" items="${weekNumbers }" varStatus="loopStatus">
												<c:forEach var="holidayCountWeekKey" items="${holidayCountWeekKeys }" varStatus="loopStatusHolidayCount">
													<c:if test="${holidayCountWeekKey eq  item}">
														<c:set var="holidayCountIndex" value="${holidayCount[loopStatusHolidayCount.index] }" />
													</c:if>
												</c:forEach>
												<c:set var="isMoreWorkIndex" value="false" />
												<c:forEach var="weekKey" items="${weekKeys }" varStatus="loopStatus">
													<c:if test="${weekKey eq item and values1[loopStatus.index] gt (2400 - 480*(holidayCountIndex))}">
														<c:set var="isMoreWorkIndex" value="true" />
													</c:if>
												</c:forEach>
												
												<!-- <td><input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" /></td> -->
												<c:choose>
													<c:when test="${previousWeek gt item}">
														<c:choose>
															<c:when test="${empty eachAndEveryWeek}">
																<td style="width:90px;">
																	<span>0</span>
																	<!-- <input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" disabled value="0"/> -->
																	<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)"/>								
																</td>
															</c:when>
															<c:otherwise>
																<td style="width:90px;<c:if test="${isMoreWorkIndex }">background-color: #fce9e9;</c:if>">
																	<c:set var="hadValue" value="false"></c:set>
																	<c:forEach var="checkHadValue" begin="0" end="${fn:length(eachAndEveryWeek)-1 }">
																		<c:if test="${(item eq eachAndEveryWeek[checkHadValue]) and (projectIds[index] eq eachAndEveryProject[checkHadValue]) and (eachAndEveryStatus[checkHadValue] eq 'p')}">
																			<c:set var="hadValue" value="true"></c:set>
																			<c:set var="checkHadValueIndex" value="${checkHadValue }"></c:set>
																		</c:if>
																	</c:forEach>
																		<c:choose>
																			<c:when test="${hadValue }">
																				<span><strong>${eachAndEveryOnlyEffort[checkHadValueIndex]}</strong></span>
																				<%-- <input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" disabled value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/> --%>
																				<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/>
																			</c:when>
																			<c:otherwise>
																				<span>0</span>
																				<!-- <input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" disabled value="0"/> -->
																				<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)"/>
																			</c:otherwise>
																		</c:choose>
																</td>
															</c:otherwise>
														</c:choose>
														
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${item gt activeWeekNumber }">
																<c:choose>
																	<c:when test="${empty eachAndEveryWeek}">
																		<td style="width:90px;">
																			<span>--</span>
																			<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" />
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td style="width:90px;<c:if test="${isMoreWorkIndex }">background-color: #fce9e9;</c:if>">
																			<c:set var="hadValue" value="false"></c:set>
																			<c:forEach var="checkHadValue" begin="0" end="${fn:length(eachAndEveryWeek)-1 }">
																				<c:if test="${(item eq eachAndEveryWeek[checkHadValue]) and (projectIds[index] eq eachAndEveryProject[checkHadValue]) and (eachAndEveryStatus[checkHadValue] eq 'p')}">
																					<c:set var="hadValue" value="true"></c:set>
																					<c:set var="checkHadValueIndex" value="${checkHadValue }"></c:set>
																				</c:if>
																			</c:forEach>
																			<c:choose>
																				<c:when test="${hadValue }">
																					<span><strong>${eachAndEveryOnlyEffort[checkHadValueIndex]}</strong></span>
																					<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/>
																				</c:when>
																				<c:otherwise>
																					<span>--</span>
																					<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" />
																				</c:otherwise>
																			</c:choose>
																		</td>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${empty eachAndEveryWeek}">
																		<td style="width:90px;">
																			<c:choose>
																				<c:when test="${activeWeekNumberFlag }">
																					<input style="background: #f7faff;" class="form-control validateWeekEffort dynamicWeekEffort ${projectIds[index]}${item}" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM" readonly/>
																				</c:when>
																				<c:otherwise>
																					<span>0</span>
																					<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
																				</c:otherwise>
																			</c:choose>
																			<!-- <input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/> -->
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td style="width:90px;<c:if test="${isMoreWorkIndex }">background-color: #fce9e9;</c:if>">
																			<c:set var="hadValue" value="false"></c:set>
																			<c:forEach var="checkHadValue" begin="0" end="${fn:length(eachAndEveryWeek)-1 }">
																				<c:if test="${(item eq eachAndEveryWeek[checkHadValue]) and (projectIds[index] eq eachAndEveryProject[checkHadValue]) and (eachAndEveryStatus[checkHadValue] eq 'p')}">
																					<c:set var="hadValue" value="true"></c:set>
																					<c:set var="checkHadValueIndex" value="${checkHadValue }"></c:set>
																				</c:if>
																			</c:forEach>
																			<c:choose>
																				<c:when test="${hadValue }">
																					<c:choose>
																						<c:when test="${activeWeekNumberFlag }">
																							<strong><input style="background: #f7faff;" class="form-control validateWeekEffort dynamicWeekEffort ${projectIds[index]}${item}" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex]}" readonly/></strong>
																						</c:when>
																						<c:otherwise>
																							<span><strong>${eachAndEveryOnlyEffort[checkHadValueIndex]}</strong></span>
																							<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/>
																						</c:otherwise>
																					</c:choose>
																					<%-- <strong><input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/></strong> --%>
																				</c:when>
																				<c:otherwise>
																					<c:choose>
																						<c:when test="${activeWeekNumberFlag }">
																							<input style="background: #f7faff;" class="form-control validateWeekEffort dynamicWeekEffort ${projectIds[index]}${item}" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM" readonly/>
																						</c:when>
																						<c:otherwise>
																							<span>0</span>
																							<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
																						</c:otherwise>
																					</c:choose>
																					<!-- <input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/> -->
																				</c:otherwise>
																			</c:choose>
																		</td>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											
											<c:choose>
												<c:when test="${empty projectIdKeys}">
													<td class="dynamicTotalProjectEffort">0</td>
												</c:when>
												<c:otherwise>
													
														<c:set var="isProjectIdMatch" value="false" />
														<c:set var="j" value="0" />
														<c:forEach var="i" begin="0" end="${fn:length(projectIdKeys)-1}">
															<c:if test="${projectIdKeys[i] eq  projectIds[index]}">
																<c:set var="isProjectIdMatch" value="true" />
																<c:set var="j" value="${i }" />
															</c:if>
														</c:forEach>
														<c:choose>
															<c:when test="${isProjectIdMatch }">
																<td class="dynamicTotalProjectEffort"><strong>${projectEffortValues[j] }</strong></td>
															</c:when>
															<c:otherwise>
																<td class="dynamicTotalProjectEffort">0</td>
															</c:otherwise>
														</c:choose>
												</c:otherwise>
											</c:choose>   
										</tr>
										<c:set var="serialNo" value="${serialNo + 1 }" />
									</c:forEach>
									
								<!-- Rows for timesheet data - Activities -->
							    <input type="hidden" class="form-control" name="miscActivityIds" value="${miscActivityIds}"/>
							    <c:set var="serialNo" value="1" />
							    	<thead>
								        <tr>
								          <th class="top-back"></th>
								          <th class="top-back">Activity Name</th>
								          	<c:set var="colorIndex" value="0"></c:set>
								            <c:forEach var="item" items="${weekNumbers }">
												<th style="background-color: ${calendarColor[colorIndex]}">Week <c:out value="${item }"></c:out></th>
												<c:set var="colorIndex" value="${colorIndex + 1}"></c:set>
											</c:forEach>
								          <th class="top-back" style="width: 155px;"></th>
								        </tr>
								      </thead>
							        <c:forEach var="index" begin="0" end="${fn:length(miscActivityIds)-1}">
								        <tr class="misc-activity-rows" style="background-color: #CDD4ED;">
								        	<td><c:out value="${serialNo}"></c:out></td>
											<td><c:out value="${miscActivityNames[index ]}"></c:out></td>
											
											<c:set var="holidayCountIndex" value="0" />
											<c:forEach var="item" items="${weekNumbers }">
												<c:forEach var="holidayCountWeekKey" items="${holidayCountWeekKeys }" varStatus="loopStatusHolidayCount">
													<c:if test="${holidayCountWeekKey eq  item}">
														<c:set var="holidayCountIndex" value="${holidayCount[loopStatusHolidayCount.index] }" />
													</c:if>
												</c:forEach>
												<c:set var="isMoreWorkIndex" value="false" />
												<c:forEach var="weekKey" items="${weekKeys }" varStatus="loopStatus">
													<c:if test="${weekKey eq item and values1[loopStatus.index] gt (2400 - 480*(holidayCountIndex))}">
														<c:set var="isMoreWorkIndex" value="true" />
													</c:if>
												</c:forEach>
												<!-- <td><input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" /></td> -->
												<c:choose>
													<c:when test="${previousWeek gt item}">
														<c:choose>
															<c:when test="${empty eachAndEveryWeek}">
																<td style="width:90px;">
																	<span>0</span>
																	<!-- <input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" disabled value="0"/> -->
																	<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)"/>
																</td>
															</c:when>
															<c:otherwise>
																<td style="width:90px;<c:if test="${isMoreWorkIndex }">background-color: #fce9e9;</c:if>">
																	<c:set var="hadValue" value="false"></c:set>
																	<c:forEach var="checkHadValue" begin="0" end="${fn:length(eachAndEveryWeek)-1 }">
																		<c:if test="${(item eq eachAndEveryWeek[checkHadValue]) and (miscActivityIds[index] eq eachAndEveryProject[checkHadValue]) and (eachAndEveryStatus[checkHadValue] eq 'm')}">
																			<c:set var="hadValue" value="true"></c:set>
																			<c:set var="checkHadValueIndex" value="${checkHadValue }"></c:set>
																		</c:if>
																	</c:forEach>
																	<c:choose>
																		<c:when test="${hadValue }">
																			<span><strong>${eachAndEveryOnlyEffort[checkHadValueIndex]}</strong></span>
																			<%-- <input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" disabled value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/> --%>
																			<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/>
																		</c:when>
																		<c:otherwise>
																			<span>0</span>
																			<!-- <input type="text" class="form-control" name="weekEffort" onchange="checkWeekEffort(this)" disabled value="0"/> -->
																			<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)"/>
																		</c:otherwise>
																	</c:choose>	
																</td>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${item gt activeWeekNumber }">
																<c:choose>
																	<c:when test="${empty eachAndEveryWeek}">
																		<td style="width:90px;">
																			<span>--</span>
																			<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" />
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td style="width:90px;<c:if test="${isMoreWorkIndex }">background-color: #fce9e9;</c:if>">
																			<c:set var="hadValue" value="false"></c:set>
																			<c:forEach var="checkHadValue" begin="0" end="${fn:length(eachAndEveryWeek)-1 }">
																				<c:if test="${(item eq eachAndEveryWeek[checkHadValue]) and (miscActivityIds[index] eq eachAndEveryProject[checkHadValue]) and (eachAndEveryStatus[checkHadValue] eq 'm')}">
																					<c:set var="hadValue" value="true"></c:set>
																					<c:set var="checkHadValueIndex" value="${checkHadValue }"></c:set>
																				</c:if>
																			</c:forEach>
																			<c:choose>
																				<c:when test="${hadValue }">
																					<span><strong>${eachAndEveryOnlyEffort[checkHadValueIndex]}</strong></span>
																					<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/>
																				</c:when>
																				<c:otherwise>
																					<span>--</span>
																					<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" />
																				</c:otherwise>
																			</c:choose>
																		</td>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${empty eachAndEveryWeek}">
																		<td style="width:90px;">
																			<c:choose>
																				<c:when test="${activeWeekNumberFlag }">
																					<input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
																				</c:when>
																				<c:otherwise>
																					<span>0</span>
																					<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
																				</c:otherwise>
																			</c:choose>
																			<!-- <input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/> -->
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td style="width:90px;<c:if test="${isMoreWorkIndex }">background-color: #fce9e9;</c:if>">
																			<c:set var="hadValue" value="false"></c:set>
																			<c:forEach var="checkHadValue" begin="0" end="${fn:length(eachAndEveryWeek)-1 }">
																				<c:if test="${(item eq eachAndEveryWeek[checkHadValue]) and (miscActivityIds[index] eq eachAndEveryProject[checkHadValue]) and (eachAndEveryStatus[checkHadValue] eq 'm')}">
																					<c:set var="hadValue" value="true"></c:set>
																					<c:set var="checkHadValueIndex" value="${checkHadValue }"></c:set>
																				</c:if>
																			</c:forEach>
																			<c:choose>
																				<c:when test="${hadValue }">
																					<c:choose>
																						<c:when test="${activeWeekNumberFlag }">
																							<strong><input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/></strong>
																						</c:when>
																						<c:otherwise>
																							<span><strong>${eachAndEveryOnlyEffort[checkHadValueIndex]}</strong></span>
																							<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/>
																						</c:otherwise>
																					</c:choose>
																					<%-- <strong><input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" value="${eachAndEveryOnlyEffort[checkHadValueIndex]}"/></strong> --%>
																				</c:when>
																				<c:otherwise>
																					<c:choose>
																						<c:when test="${activeWeekNumberFlag }">
																							<input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
																						</c:when>
																						<c:otherwise>
																							<span>0</span>
																							<input type="hidden" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
																						</c:otherwise>
																					</c:choose>
																					<!-- <input type="text" class="form-control validateWeekEffort dynamicWeekEffort" name="weekEffort" onchange="checkWeekEffort(this)" placeholder="HH:MM"/> -->
																				</c:otherwise>
																			</c:choose>
																		</td>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											
											<c:choose>
												<c:when test="${empty activityIdKeys}">
													<td class="dynamicTotalProjectEffort">0</td>
												</c:when>
												<c:otherwise>
													
														<c:set var="isActivityIdMatch" value="false" />
														<c:set var="j" value="0" />
														<c:forEach var="i" begin="0" end="${fn:length(activityIdKeys)-1}">
															<c:if test="${activityIdKeys[i] eq  miscActivityIds[index]}">
																<c:set var="isActivityIdMatch" value="true" />
																<c:set var="j" value="${i }" />
															</c:if>
														</c:forEach>
														<c:choose>
															<c:when test="${isActivityIdMatch }">
																<td class="dynamicTotalProjectEffort"><strong>${activityEffortValues[j] }</strong></td>
															</c:when>
															<c:otherwise>
																<td class="dynamicTotalProjectEffort">0</td>
															</c:otherwise>
														</c:choose>
												</c:otherwise>
											</c:choose>									        
										</tr>
										<c:set var="serialNo" value="${serialNo + 1 }" />
									</c:forEach>
					        
							<input id="yearSelectDB" type="hidden" class="form-control" name="yearSelectDB" value="${yearSelectDB}"/>
							<input type="hidden" class="form-control" name="selectedDate" value="${selectedDate}"/>
							<input type="hidden" class="form-control" name="todaysDate" value="${todaysDate }"/>
							<input id="employeeIdDB" type="hidden" class="form-control" name="employeeIdDB" value="${empId }"/>
							<input type="hidden" class="form-control" name="employeeNameDB" value="${userName }"/>
							<input type="hidden" class="form-control" name="groupIdDB" value="${groupId }"/>
							<input type="hidden" class="form-control" name="weekNumbers" value="${weekNumbers }"/>
							<input type="hidden" id = "activeWeekNumber" name="activeWeekNumber" value="${activeWeekNumber }"/>
							<input type="hidden" id="projectTaskDetails" class="form-control" name="projectTaskDetails" value=""/>
							<input type="hidden" id="taskProjectDetails" class="form-control" name="taskProjectDetails" value=""/>
							<input type="hidden" id="taskProjectTimesheetTypeDetails" class="form-control" name="taskProjectTimesheetTypeDetails" value=""/>
					      </tbody>
				      
				      <!-- Table footer -->
					      <tfoot>
					        <tr>
					          <td class="bottom-back"></td>
					          <td class="bottom-back"><strong>Total Week Effort</strong></td>
					          <c:forEach var="item" items="${weekNumbers}">
						            <c:choose>
										<c:when test="${empty weekKeys}">
											<td class="dynamicTotalWeekEffort">0</td>
										</c:when>
										<c:otherwise>
											
												<c:set var="isWeekMatch" value="false" />
												<c:set var="j" value="0" />
												<c:forEach var="i" begin="0" end="${fn:length(weekKeys)-1}">
													<c:if test="${weekKeys[i] eq  item}">
														<c:set var="isWeekMatch" value="true" />
														<c:set var="j" value="${i }" />
													</c:if>
												</c:forEach>
												<c:choose>
													<c:when test="${isWeekMatch }">
														<td class="dynamicTotalWeekEffort"><strong>${weekEffortValues[j] }</strong></td>
													</c:when>
													<c:otherwise>
														<td class="dynamicTotalWeekEffort">0</td>
													</c:otherwise>
												</c:choose>
										</c:otherwise>
									</c:choose>
						       </c:forEach> 
						       <td class="bottom-back"></td>
					          <!-- <td style="justify-content: center; display: flex;"><button class="btn btn-primary validationWeekNumberSubmitted" name="weekNumberSubmitted" >Submit</button></td> -->
					        </tr>
					      </tfoot>
					    </table>
					    <div style="justify-content: center; display: flex;">
					    	<button class="btn btn-success btn-lg validationWeekNumberSubmitted" name="weekNumberSubmitted" style="font-size: larger;">Submit</button>
					    </div>
					    <div style="display: flex; justify-content: left; margin-top: 5px">
						    <div style="background-color: #D8D7DA" class="box"></div><p class="box-text">Project</p>
					  		<div style="background-color: #CDD4ED" class="box"></div><p class="box-text">Activity</p> <!-- Miscellaneous Activity -->
				  		</div>
			   	</c:otherwise>
			  </c:choose>
				<input type="hidden" id = "weekNumbers" class="form-control" name="weekNumbersHolidayCount" value="${weekNumbers }"/>
				<input type="hidden" id = "holidayCountWeekKeys" class="form-control" name="holidayCountWeekKeys" value="${holidayCountWeekKeys }"/>
				<input type="hidden" id = "holidayCount" class="form-control" name="holidayCount" value="${holidayCount }"/>
		  </form>
		</div>
	</div>
		  <!-- Validation of hours > 168 in a week -->
		  <input type="hidden" id = "validateWeekNumber" name="weekNumbers" value="${weekNumbers }"/>
</section>
<input type="hidden" id = "activeWeekNumberPopUp" name="activeWeekNumberPopUp" value="${activeWeekNumber }"/>
<input type="hidden" id = "previousWeekNumberPopUp" name="previousWeekNumberPopUp" value="${activeWeekNumber - 1}"/>
<input type="hidden" id = "projectTaskIdsPopUp" name="projectTaskIdsPopUp" value="${projectTaskIds}"/>
<input type="hidden" id = "projectSubTaskDescIdsPlainPopUp" name="projectSubTaskDescIdsPlainPopUp" value="${projectSubTaskDescIdsPlain}"/>

<!-- Basic Timesheet POPUP -->
<div id="popupBasic" class="popup" style="margin-top: 3%; width: 600px;">
    <h4 class="popupHeading" style = "text-align: center;"></h4>
    <c:choose>
    	<c:when test="${!isPreviousWeekPresent && !isCurrentWeekPresent && monthSelectedPopUpMinus1 eq monthSelectedPopUpPlus1}">
    		<p><em>Please fill efforts of WEEK ${previousWeek} and ${activeWeekNumber} in ${monthSelectedPopUpMinus1 } month timesheet.</em></p>
    	</c:when>
    	<c:otherwise>
    		<c:if test="${!isPreviousWeekPresent}">
		    	<p><em>Please fill efforts of WEEK ${previousWeek} in ${monthSelectedPopUpMinus1 } month timesheet.</em></p>
		    </c:if>
		    <c:if test="${!isCurrentWeekPresent}">
		    	<p><em>Please fill efforts of WEEK ${activeWeekNumber} in ${monthSelectedPopUpPlus1 } month timesheet.</em></p>
		    </c:if>
    	</c:otherwise>
    </c:choose>
    
    <div class="scrollable-popup">
    <table id="popupTableBasic" class="table table-bordered">
        <thead class="thead-light">
            <tr>
                <th style="background: #fff2e6;">Tasks</th>
                <th style="width: 90px; background: #b3ecff;">Week ${previousWeek}</th>
                <th style="width: 90px; background: #66d9ff;">Week ${activeWeekNumber}</th>
            </tr>                                                                                   
        </thead>
        <tbody>
            <c:forEach var="index" begin="0" end="${fn:length(projectTaskIds)-1}">
            	<tr style="background: #D8D7DA;">
            		<td>${projectTaskNames[index]}</td>
            		<%-- <c:choose>
            			<c:when test="${isPreviousWeekPresent}">
            				<td><input id="${projectTaskIds[index] }-0" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
            			</c:when>
            			<c:otherwise>
            				<td><input disabled id="${projectTaskIds[index] }-0" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
            				<input id="${projectTaskIds[index] }-0" type="hidden" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
            			</c:otherwise>
            		</c:choose>
            		<c:choose>
            			<c:when test="${isCurrentWeekPresent}">
            				<td><input id="${projectTaskIds[index] }-1" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
            			</c:when>
            			<c:otherwise>
            				<td><input disabled id="${projectTaskIds[index] }-1" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
            				<input id="${projectTaskIds[index] }-1" type="hidden" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
            			</c:otherwise>
            		</c:choose> --%>
            		<td><input <c:if test="${!isPreviousWeekPresent}">disabled</c:if> id="${projectTaskIds[index] }-0" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
            		<td><input <c:if test="${!isCurrentWeekPresent}">disabled</c:if> id="${projectTaskIds[index] }-1" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
            	</tr>
            </c:forEach>
        </tbody>
    </table>
    </div>
    <div style="padding-top: 6px; display: flex; justify-content: center;">
    	<button timesheetType="b" style="font-size: larger; margin-right: 10px;" type="button" class="btn btn-outline-danger btn-sm cancelPopUpButton">Cancel</button>
    	<button timesheetType="b" style="font-size: larger;" type="button" class="btn btn-success btn-sm savePopUpButton">OK</button>
    </div>
</div>
<!-- Detailed Timesheet POPUP -->
<div id="popupDetailed" class="popup" style="margin-top: 3%; width: 600px;">
    <h4 class="popupHeading" style = "text-align: center;"></h4>
    <c:choose>
    	<c:when test="${!isPreviousWeekPresent && !isCurrentWeekPresent && monthSelectedPopUpMinus1 eq monthSelectedPopUpPlus1}">
    		<p><em>Please fill efforts of WEEK ${previousWeek} and ${activeWeekNumber} in ${monthSelectedPopUpMinus1 } month timesheet.</em></p>
    	</c:when>
    	<c:otherwise>
    		<c:if test="${!isPreviousWeekPresent}">
		    	<p><em>Please fill efforts of WEEK ${previousWeek} in ${monthSelectedPopUpMinus1 } month timesheet.</em></p>
		    </c:if>
		    <c:if test="${!isCurrentWeekPresent}">
		    	<p><em>Please fill efforts of WEEK ${activeWeekNumber} in ${monthSelectedPopUpPlus1 } month timesheet.</em></p>
		    </c:if>
    	</c:otherwise>
    </c:choose>
    <div class="scrollable-popup">
	    <table id="popupTableDetailed" class="table table-bordered">
	        <thead class="thead-light">
	            <tr>
	                <th style="background: #fff2e6;">Tasks</th>
	                <th style="width: 90px; background: #b3ecff;">Week ${previousWeek}</th>
	                <th style="width: 90px; background: #66d9ff;">Week ${activeWeekNumber}</th>
	            </tr>
	        </thead>
		        <c:forEach var="index" begin="0" end="${fn:length(projectTaskIds)-1}">
		            <tr class="category">
		                <td>
		                    <strong>${projectTaskNames[index]}</strong>&nbsp;&nbsp;
		                    <i class="toggle-icon fa fa-plus" style="color: #28a745;" onclick="toggleSubCategories(this)"></i>
		                </td>
		                <td style="text-align: center; font-weight: bold; font-size: large;"></td>
		                <td style="text-align: center; font-weight: bold; font-size: large;"></td>
		            </tr>
		            <c:forEach var="index1" begin="0" end="${fn:length(projectSubTaskIds[index])-1}">
				            <tr class="sub-category" style="display: none"> <!-- style="display: none" -->
				                <td><em>&nbsp;&nbsp;${projectSubTaskNames[index][index1]}</em> &nbsp;&nbsp; <i class="toggle-icon fa fa-plus" style="color: #28a745;" onclick="toggleSubSubCategories(this)"></i></td>
					    		<td style="text-align: center; font-size: medium;"></td>
					    		<td style="text-align: center; font-size: medium;"></td>
				            </tr>
				            
		    		
			            <c:forEach var="index2" begin="0" end="${fn:length(projectSubTaskDescIds[index][index1])-1}">
				            <tr class="sub-sub-category" style="background: #D8D7DA; display: none;"> <!-- display: none; --> 
				            
				                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${projectSubTaskDescNames[index][index1][index2]}</td>
				            	<%-- <c:choose>
			            			<c:when test="${isPreviousWeekPresent}">
			            				<td><input id="${projectSubTaskDescIds[index][index1][index2] }-0" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
			            			</c:when>
			            			<c:otherwise>
			            				<td><input disabled id="${projectSubTaskDescIds[index][index1][index2] }-0" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
			            				<input id="${projectSubTaskDescIds[index][index1][index2] }-0" type="hidden" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
			            			</c:otherwise>
			            		</c:choose>
			            		<c:choose>
			            			<c:when test="${isCurrentWeekPresent}">
			            				<td><input id="${projectSubTaskDescIds[index][index1][index2] }-1" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
			            			</c:when>
			            			<c:otherwise>
			            				<td><input disabled id="${projectSubTaskDescIds[index][index1][index2] }-1" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/>
			            				<input id="${projectSubTaskDescIds[index][index1][index2] }-1" type="hidden" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
			            			</c:otherwise>
			            		</c:choose> --%>
					    		<td><input <c:if test="${!isPreviousWeekPresent}">disabled</c:if> id="${projectSubTaskDescIds[index][index1][index2] }-0" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
								<td><input <c:if test="${!isCurrentWeekPresent}">disabled</c:if> id="${projectSubTaskDescIds[index][index1][index2] }-1" type="text" class="form-control validateWeekEffortPopUp" name="weekEffortPopUp" onchange="checkWeekEffort(this)" placeholder="HH:MM"/></td>
				            </tr>
		            	</c:forEach>
		            </c:forEach>
		        </c:forEach>
	    </table>
   </div>
    <div style="padding-top: 6px; display: flex; justify-content: center;">
        <button timesheetType="d" style="font-size: larger; margin-right: 10px;" type="button" class="btn btn-outline-danger btn-sm cancelPopUpButton">Cancel</button>
        <button timesheetType="d" style="font-size: larger;" type="button" class="btn btn-success btn-sm savePopUpButton">OK</button>
    </div>
</div>

<!-- pop up for help -->
<div id="popupHelp" style="display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: #f8f9fa; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.3); max-width: 400px;">
    <div style="background-color: #ffffff; padding: 20px; border-radius: 10px;">
        <h2 style="color: #007bff;">Task Descriptions</h2>
        <hr style="border-color: #007bff;">
        <div class="list-group" id="taskList">
            <!-- Iterate over projectTaskDescMap -->
            <c:forEach var="entry" items="${projectTaskDescMap}">
                <div class="list-group-item">
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 style="color: #6c757d;">${entry.key}</h4>
                        <i class="toggle-icon fa fa-plus" style="color: #28a745;" onclick="toggleList(this)"></i>
                    </div>
                    <ul class="description-list" style="display: none;">
                        <!-- Iterate over entry.value -->
                        <c:forEach var="desc" items="${entry.value}">
                            <li>${desc}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
        </div>
        <hr style="border-color: #007bff;">
        <button type="button" class="btn btn-outline-danger btn-block" onclick="closePopupHelp()">Close</button>
    </div>
</div>



<!-- already exixts misc other activity on text area for user to fill already exist misc other activity -->
<input type="hidden" id = "allMiscActivityDescTemp" name="allMiscActivityDescTemp" value="${allMiscActivityDescTemp}"/>
<%-- <!-- To get all project task names, and desc from backend help data to show on UI help -->
<input type="hidden" id = "projectTaskNameHelp" name="projectTaskNameHelp" value="${projectTaskNameHelp}"/>
<input type="hidden" id = "projectTaskDescHelp" name="projectTaskDescHelp" value="${projectTaskDescHelp}"/> --%>
  <!-- jQuery and Bootstrap JS -->
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
 <script src="<c:url value="/resources/app_srv/PMS/timesheet/js/script.js"></c:url>"></script>
 <!-- <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script> -->

</body>
</html>