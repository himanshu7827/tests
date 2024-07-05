<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
table.dt-rowReorder-float{position:absolute !important;opacity:0.8;table-layout:fixed;outline:2px solid #888;outline-offset:-2px;z-index:2001}
tr.dt-rowReorder-moving{outline:2px solid #555;outline-offset:-2px}
body.dt-rowReorder-noOverflow{overflow-x:hidden}
table.dataTable td.reorder{text-align:center;cursor:move}
</style>
	

<style>
.wrapper1 {
    display: inline-block;
    margin-top: 80px;
    width: 100% !important;
}

.oddRow {
	background-color: #c2c3ea;
}

#editor {
	height: 200px;
	/* border: 1px solid #000; */
	border-radius: 5px;
	OVERFLOW: scroll;
	border-bottom: 2px solid #000;
	border-left: 1px solid #000;
	border-right: 2px solid #000;
	border-top: 1px solid #000;
}
.btn.dropdown-toggle:after{
content:none !important;
}

.btn-group>.btn, .btn-group>.dropdown-menu, .btn-group>.popover {
    font-size: 16px !important;
}
.pad-14{
padding-top: 14px !important;
    padding-bottom: 14px !important;
}
 .header{
height:90px;
} 
</style>
<link href="/PMS/resources/app_srv/PMS/global/editor/prettify.css"
	rel="stylesheet" />
<link
	href="/PMS/resources/app_srv/PMS/global/editor/bootstrap-combined.no-icons.min.css"
	rel="stylesheet" />
<link href="/PMS/resources/app_srv/PMS/global/editor/font-awesome.css"
	rel="stylesheet" />
<link href="/PMS/resources/app_srv/PMS/global/editor/editor.css"
	rel="stylesheet" />


</head>

<body>

	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<li class="active">Innovations</li>
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
        <div class="container pad-top pad-bottom">
        	<div class="panel panel-info  ">					
			<div class="panel-body">
					<p><span class="bold  font_16 ">Project: </span> <span class="bold blue font_16"><i>${monthlyProgressModel.strProjectName}</i></span></p>
					<p><span class="bold  font_16 ">For : </span><span class="bold orange font_16"><i>${monthlyProgressModel.strMonth}-${monthlyProgressModel.year}</i></span></p>
					</div>
				</div>
			</div>
			<sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">	
			
			 <div class="hidden" id="encProjectId" >${monthlyProgressModel.encProjectId}</div>
         <div class="hidden" id="encGroupId">${monthlyProgressModel.encGroupId}</div> 
<%--          <div class="hidden" id="encCategoryId">${encCategoryId}</div> 
 --%>         
			<form:form id="form1" modelAttribute="projectInnovationsModel" action="" method="post">
			 
			 <input type="hidden" name=numId id="numId" value="0"/>
					<input type="hidden" name=strInnovationDescriptionHtml id="strInnovationDescriptionHtml"
					value=""/>
					<input type="hidden" name=encCategoryId id="encCategoryId" value="${encCategoryId}"/>
			 <form:hidden path="encMonthlyProgressId" id="encMonthlyProgressId"  value="${encMonthlyProgressId}"/>
				
<div class="container">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">Innovations</h4>
							</div>
							<div class="panel-body text-center">


								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-4 col-lg-4 col-sm-4 col-xs-12 text-justify">

											<!-- <label class="label-class">Project Innovations
												Description :<span style="color: red;">*</span>
											</label>
 -->
										</div>
									</div>
								</div>
								<div class="row">
									<!-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">						
											
   											 <textarea id="edit3" ></textarea>
										</div> -->

									<!-- ADDED FOR TEXT EDITOR -->
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
										<div class="hero-unit">
											<div id="alerts"></div>
											<div class="btn-toolbar" data-role="editor-toolbar"
												data-target="#editor">
												<div class="btn-group">
													<a class="btn dropdown-toggle" data-toggle="dropdown"
														title="Font"><i class="icon-font"></i>&nbsp;<b class="caret"></b></a>
													<ul class="dropdown-menu" style="height:350px; overflow-y:scroll;">
													</ul>
												</div>
												<div class="btn-group">
													<a class="btn dropdown-toggle" data-toggle="dropdown"
														title="Font Size"><i class="icon-text-height"></i>&nbsp;<b
														class="caret"></b></a>
													<ul class="dropdown-menu">
														<li><a data-edit="fontSize 5"><font size="5">Huge</font></a></li>
														<li><a data-edit="fontSize 3"><font size="3">Normal</font></a></li>
														<li><a data-edit="fontSize 1"><font size="1">Small</font></a></li>
													</ul>
												</div>
												<div class="btn-group">
													<a class="btn pad-14" data-edit="bold" title="Bold (Ctrl/Cmd+B)"><i
														class="icon-bold"></i></a> <a class="btn pad-14" data-edit="italic"
														title="Italic (Ctrl/Cmd+I)"><i class="icon-italic"></i></a>
													<a class="btn pad-14" data-edit="strikethrough"
														title="Strikethrough"><i class="icon-strikethrough"></i></a>
													<a class="btn pad-14" data-edit="underline"
														title="Underline (Ctrl/Cmd+U)"><i
														class="icon-underline"></i></a>
													<!-- 	<div id="froala-editor"></div> -->
												</div>
												<div class="btn-group">
													<a class="btn pad-14" data-edit="insertunorderedlist"
														title="Bullet list"><i class="icon-list-ul"></i></a> <a
														class="btn pad-14" data-edit="insertorderedlist"
														title="Number list"><i class="icon-list-ol"></i></a> <a
														class="btn pad-14" data-edit="outdent"
														title="Reduce indent (Shift+Tab)"><i
														class="icon-indent-left"></i></a> <a class="btn pad-14"
														data-edit="indent" title="Indent (Tab)"><i
														class="icon-indent-right"></i></a>
												</div>
												<div class="btn-group">
													<a class="btn pad-14" data-edit="justifyleft"
														title="Align Left (Ctrl/Cmd+L)"><i
														class="icon-align-left"></i></a> <a class="btn pad-14"
														data-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i
														class="icon-align-center"></i></a> <a class="btn pad-14"
														data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i
														class="icon-align-right"></i></a> <a class="btn pad-14"
														data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i
														class="icon-align-justify"></i></a>
												</div>
												<div class="btn-group">
													<a class="btn dropdown-toggle pad-14" data-toggle="dropdown"
														title="Hyperlink"><i class="icon-link"></i></a>
													<div class="dropdown-menu input-append">
														<input class="span2" placeholder="URL" type="text"
															data-edit="createLink" />
														<button class="btn" type="button">Add</button>
													</div>
													<a class="btn pad-14" data-edit="unlink" title="Remove Hyperlink"><i
														class="icon-cut"></i></a>

												</div>

												<div class="btn-group">
													<a class="btn pad-14" title="Insert picture (or just drag & drop)"
														id="pictureBtn"><i class="icon-picture"></i></a> <input
														type="file" data-role="magic-overlay"
														data-target="#pictureBtn" data-edit="insertImage" />
												</div>
												<div class="btn-group">
													<a class="btn pad-14" data-edit="undo" title="Undo (Ctrl/Cmd+Z)"><i
														class="icon-undo"></i></a> <a class="btn pad-14" data-edit="redo"
														title="Redo (Ctrl/Cmd+Y)"><i class="icon-repeat"></i></a>
												</div>

											</div>

											<div id="editor" contentEditable="true"></div>
										</div>
									</div>
								</div>

								<!-- END FOR TEXT EDITOR -->
								<sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')">
								
								<div class="row pad-top  form_btn">
							
								<button type="button" class="btn btn-primary font_14" id="save">
									<span class="pad-right"><i class="fa fa-folder"></i></span>Save as Draft
								</button>
								
									<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
						<button type="button" class="btn btn-success font_14" id="previewDetailsBtn">
							<span class="pad-right"><i class="fa fa-eye" aria-hidden="true"></i></span>Preview
						</button>
							<input type="button" class="btn btn-orange font_14" id="back" onclick="backToMainPage('${monthlyProgressModel.encProjectId}','${encCategoryId}','${monthlyProgressModel.encGroupId}')" value="Back To Main Page"/>
						

						</div>
						<div class="row pad-top" id="mainPrevNext">
						
						</div>
						</sec:authorize>
							</div>
						</div>

						

					</div>
				</div>


			</form:form>
			</sec:authorize>
		</div>
					<div class="container">
										<fieldset>
				    <legend class="info">
				   List of Innovations
				    </legend>
				    <div class="whole">
					   
					  
					  
						<div class="row">
							<div class="pull-right">
								<div class="col-md-4">
									<input type="button" value="Delete" id="delete"
										class="btn btn-primary a-btn-slide-text">
								</div> 
							</div>
						</div>
				
		<table class="table table-responsive table-bordered compact display hover stripe" id="inboxTable"  data-page-size="5"  id="tab1">
					<thead class="datatable_thead bold">
				        <tr>
				         <th width="3%" class="check">Select</th>
				       <th width="5%">S.No.</th>
						<th width="100%">Innovation</th>	
						<th width="8%">Edit</th>
				        </tr>
					</thead>
					
					<tbody id="addRowData">
					 <%int i = 1 ;%>
					<c:forEach  items="${modelList}" var="list">
					<tr>
					<td><input type="checkbox" path="checkbox"
														class="CheckBox" id="CheckBox"
															value="${list.numId}" autocomplete="off"></td>
					<td class="hidden">${list.numId}</td>
					<td><%=i%></td>
					<td>${list.strInnovationDescriptionHtml}</td>
				
					<td>
							 <sec:authorize access="hasAuthority('WRITE_FOR_PROGRESS_REPORT')"> 
			<div id="Edit"><input type="hidden"  value="${list.numId}" >	<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" aria-hidden="true"></span>
							</div> </sec:authorize> 
							</td>
				</tr>
					<%i++; %>
					</c:forEach>
					</tbody>
					
				  <!-- <tfoot class="footable-pagination">
				        <tr>
				          <td colspan="6"><ul id="pagination" class="footable-nav"></ul></td>
				        </tr>
				      </tfoot> -->
				</table>
				</div> <!--end of div before table-->
				</fieldset>
					</div>				
	</section>
 

	<!-- ADDED FOR Editor  -->
	<script>
	$(function() {
		function initToolbarBootstrapBindings() {
			var fonts = [ 'Serif', 'Sans', 'Arial', 'Arial Black', 'Courier',
					'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact',
					'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
					'Times New Roman', 'Verdana' ], fontTarget = $(
					'[title=Font]').siblings('.dropdown-menu');
			$
					.each(
							fonts,
							function(idx, fontName) {
								fontTarget
										.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'
												+ fontName + '</a></li>'));
							});
			$('a[title]').tooltip({
				container : 'body'
			});
			$('.dropdown-menu input').click(function() {
				return false;
			}).change(
					function() {
						$(this).parent('.dropdown-menu').siblings(
								'.dropdown-toggle').dropdown('toggle');
					}).keydown('esc', function() {
				this.value = '';
				$(this).change();
			});

			$('[data-role=magic-overlay]').each(
					function() {
						var overlay = $(this), target = $(overlay
								.data('target'));
						overlay.css('opacity', 0).css('position', 'absolute')
								.offset(target.offset()).width(
										target.outerWidth()).height(
										target.outerHeight());
					});
			if ("onwebkitspeechchange" in document.createElement("input")) {
				var editorOffset = $('#editor').offset();
				$('#voiceBtn').css('position', 'absolute').offset({
					top : editorOffset.top,
					left : editorOffset.left + $('#editor').innerWidth() - 35
				});
			} else {
				$('#voiceBtn').hide();
			}
		}
		;
		function showErrorAlert(reason, detail) {
			var msg = '';
			if (reason === 'unsupported-file-type') {
				msg = "Unsupported format " + detail;
			} else {
				console.log("error uploading file", reason, detail);
			}
			$(
					'<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'
							+ '<strong>File upload error</strong> '
							+ msg
							+ ' </div>').prependTo('#alerts');
		}
		;
		initToolbarBootstrapBindings();
		$('#editor').wysiwyg({
			fileUploadError : showErrorAlert
		});
		window.prettyPrint && prettyPrint();
	});
</script>

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/ProjectInnovations.js"></script>
	<script src="/PMS/resources/app_srv/PMS/global/editor/prettify.js"></script>
	<script
		src="/PMS/resources/app_srv/PMS/global/editor/jquery.hotkeys.js"></script>
	<script
		src="/PMS/resources/app_srv/PMS/global/editor/bootstrap-wysiwyg.js"></script>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/showPrevNextButton.js"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.js"></script>
			 <script type="text/javascript">
	   $(document).ready(function(){
		   
	  
	    	//("#editor").html('${descrip}');
	    
	    });
	</script>
</body>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/r-2.2.5/datatables.min.css"/>

</html>