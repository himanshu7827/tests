$(function(){
    function initToolbarBootstrapBindings() {
      var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier', 
            'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
            'Times New Roman', 'Verdana'],
            fontTarget = $('[title=Font]').siblings('.dropdown-menu');
      $.each(fonts, function (idx, fontName) {
          fontTarget.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'+fontName + '</a></li>'));
      });
      $('a[title]').tooltip({container:'body'});
    	$('.dropdown-menu input').click(function() {return false;})
		    .change(function () {$(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');})
        .keydown('esc', function () {this.value='';$(this).change();});

      $('[data-role=magic-overlay]').each(function () { 
        var overlay = $(this), target = $(overlay.data('target')); 
        overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
      });
      if ("onwebkitspeechchange"  in document.createElement("input")) {
        var editorOffset = $('#editor').offset();
        $('#voiceBtn').css('position','absolute').offset({top: editorOffset.top, left: editorOffset.left+$('#editor').innerWidth()-35});
      } else {
        $('#voiceBtn').hide();
      }
	};
	function showErrorAlert (reason, detail) {
		var msg='';
		if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
		else {
			console.log("error uploading file", reason, detail);
		}
		$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ 
		 '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
	};
    initToolbarBootstrapBindings();  
	$('#editor').wysiwyg({ fileUploadError: showErrorAlert, toolbarSelector: '[data-role=editor-toolbar]'} );
	$("#editorOngoing").wysiwyg({ fileUploadError: showErrorAlert , toolbarSelector: '[data-role=editor2-toolbar]'} );
    window.prettyPrint && prettyPrint();
  });
 
  $(document).ready(function() {
 	 
 	 $("#editor").css("height",$(window).height()-600);
 	 $("#editor").css("max-height",$(window).height()-600);
 	/*$("#editorc").css("height",$(window).height()-600);
	 $("#editorc").css("max-height",$(window).height()-600);*/
 	 
 	 
 	});
  
  
  $(document).ready(function(){
	  var descrCompleted=$('#strCompActivityHtml').val();
	  var descrOnoing=$('#strOngoingActivityHtml').val();
		 // alert(descr);
		  $('#buttonupdate').hide();
		  
		  //alert(descr);
		 if(descrCompleted!=''){
			 var t=descrCompleted.replace(/[(?*]+/g,"\"");
			 $("#editor").html(t);
			 $('#buttonupdate').show();
			 $('#buttonsave').hide();
			 
		 }
		 if(descrOnoing!=''){
			 var on=descrOnoing.replace(/[(?*]+/g,"\"");
			 $("#editorOngoing").html(on);
			 $('#buttonupdate').show();
			 $('#buttonsave').hide();
			 
		 }
	  
	  $('#save').click(function(){
		 // alert("here");
		//  var reportid=$('#numReportId').val();
		  console.log($("#editor").html());
		  console.log($("#editor").text());
		  console.log($("#editorOngoing").html());
		  console.log($("#editorOngoing").text());
		  var htmlCompleted=$("#editor").html();
		  var textCompleted=$("#editor").text();
		  var htmlOngoing=$("#editorOngoing").html();
		  var textOngoing=$("#editorOngoing").text();
		 // alert(html);
		//  alert(text);
		  var cateoryId=$('#encCategoryId').val();
		  var monthlyprogressId=$('#encMonthlyProgressId').val();
		  if(textCompleted!=''&&textOngoing!=''){
		  $.ajax({
		        type: "POST",
		        url: "/PMS/SaveHighlights",
		        data:{"strCompActivityHtml": htmlCompleted,
			    	"strCompActivity":textCompleted,
			    	"strOngoingActivityHtml":htmlOngoing,
			    	"strOrngoingActivity":textOngoing,
			    	"encMonthlyProgressId":monthlyprogressId,
			    	"encCategoryId":cateoryId},
		        success: function(response){
		        	if(response!=''||response!=null){
		        		swal("Saved SuccessFully");
		        		$("#editor").html(response.strCompActivityHtml);
		        		$("#editorOngoing").html(response.strOngoingActivityHtml);
		        		$('#buttonupdate').show();
		       		 	$('#buttonsave').hide();
		        	}
		        	else{
		        		swal("Problem in  saving.");
		        	}
		        	
		        
		        }
		        });
		  }else{
			  swal("Please Enter Details.")
		  }
	  });
	  
	  $('#update').click(function(){
		 // var reportid=$('#numReportId').val();
		 /* console.log($("#editor").html());
		  console.log($("#editor").text());
		  console.log($("#editorOngoing").html());
		  console.log($("#editorOngoing").text());*/
		  var htmlCompleted=$("#editor").html();
		  var textCompleted=$("#editor").text();
		  var htmlOngoing=$("#editorOngoing").html();
		  var textOngoing=$("#editorOngoing").text();
		 // alert(html);
		//  alert(text);
		  var cateoryId=$('#encCategoryId').val();
		  var monthlyprogressId=$('#encMonthlyProgressId').val();
		  if(textCompleted!=''&&textOngoing!=''){
		  $.ajax({
		        type: "POST",
		        url: "/PMS/SaveHighlights",
		        data:{"strCompActivityHtml": htmlCompleted,
			    	"strCompActivity":textCompleted,
			    	"strOngoingActivityHtml":htmlOngoing,
			    	"strOrngoingActivity":textOngoing,
			    	"encMonthlyProgressId":monthlyprogressId,
			    	"encCategoryId":cateoryId},
		        success: function(response){
		        	if(response!=''||response!=null){
		        		swal("Saved SuccessFully");
		        		$("#editor").html(response.strCompActivityHtml);
		        		$("#editorOngoing").html(response.strOngoingActivityHtml);
		        		$('#buttonupdate').show();
		       		 	$('#buttonsave').hide();
		        	}
		        	else{
		        		swal("Problem in  saving.");
		        	}
		        	
		        
		        }
		        });
		  }else{
			  swal("Please Enter Details.")
		  }
		  });
  });