
/*$(document).ready(function(){
	initToolbarBootstrapBindings();  
	$('#completedActivity').wysiwyg();
	
	//$('#editor').wysiwyg({ fileUploadError: showErrorAlert} )
});*/



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
	$('#editor').wysiwyg({ fileUploadError: showErrorAlert} );
    window.prettyPrint && prettyPrint();
  });
  
  
  $(document).ready(function() {
 	 
 	 $("#editor").css("height",$(window).height()-500);
 	 $("#editor").css("max-height",$(window).height()-500);
 	 
 	 
 	});
  
  
  $(document).ready(function(){
	  var descr=$('#strProjectOthersHtml').val();
	 // alert(descr);
	  $('#buttonupdate').hide();
	  
	  //alert(descr);
	 if(descr!=''){
		 var t=descr.replace(/[(?*]+/g,"\"");
		 $("#editor").html(t);
		 $('#buttonupdate').show();
		 $('#buttonsave').hide();
		 
	 }
	 else{

	 }
	  
	  $('#save').click(function(){
		 // alert("here");
		 // var reportid=$('#numReportId').val();
		  console.log($("#editor").html());
		  console.log($("#editor").text());
		  var html=$("#editor").html();
		  var text=$("#editor").text();
		 // alert(html);
		//  alert(text);
		  var cateoryId=$('#encCategoryId').val();
		  var monthlyprogressId=$('#encMonthlyProgressId').val();
		  if(text!=''){
		  $.ajax({
		        type: "POST",
		        url: "/PMS/SaveProjectOthers",
		        data:{"strProjectOthersHtml": html,
			    	"strProjectOthers":text,
			    	"encMonthlyProgressId":monthlyprogressId,
			    	"encCategoryId":cateoryId},
		        success: function(response){
		        	if(response!=''||response!=null){
		        		swal("Saved SuccessFully");
		        		$("#editor").html(response);
		        		$('#buttonupdate').show();
		       		 	$('#buttonsave').hide();
		        	}
		        	else{
		        		swal("Problem in  saving.");
		        	}
		        	
		        
		        }
		        });
		  }else{
			  swal("Please Enter Others Description.")
		  }
	  });
	  
	  $('#update').click(function(){
			 // alert("here");
			 // var reportid=$('#numReportId').val();
			  console.log($("#editor").html());
			  console.log($("#editor").text());
			  var html=$("#editor").html();
			  var text=$("#editor").text();
			 // alert(html);
			//  alert(text);
			  var cateoryId=$('#encCategoryId').val();
			  var monthlyprogressId=$('#encMonthlyProgressId').val();
			  if(text!=''){
			  $.ajax({
			        type: "POST",
			        url: "/PMS/SaveProjectOthers",
			        data:{"strProjectOthersHtml": html,
				    	"strProjectOthers":text,
				    	"encMonthlyProgressId":monthlyprogressId,
				    	"encCategoryId":cateoryId},
			        success: function(response){
			        	if(response!=0){
			        		swal("Saved SuccessFully");
			        		
			        	}
			        	else{
			        		swal("Problem in  saving.");
			        	}
			        	
			        
			        }
			        });
			  }
			  else{
				  swal("Please Enter Others Description.")
			  }
		  });
  });