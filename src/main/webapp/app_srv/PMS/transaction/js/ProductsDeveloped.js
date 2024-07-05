
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
	  $('#buttonupdate').hide();
	  //var descr=$('#strProductsDevelopedHtml').val();
	  var descr="";
	  //alert(descr);
	 
	 var t=descr.replace(/[(?*]+/g,"\"");
	// alert(t);
	 if(descr!=''){
		$("#editor").html(t);
		 $('#buttonupdate').show();
		 $('#buttonsave').hide();
		 
	 }
	 else{

	 }
	  
	 // alert(descr);
	  
	  
	  
	  $('#save').click(function(){
		 var html=$("#editor").html();
		 var text=$("#editor").text();
		 $('#strProductsDevelopedHtml').val(html);
		 $('#strProductsDeveloped').val(text);
		  var cateoryId=$('#encCategoryId').val();
		  var monthlyprogressId=$('#encMonthlyProgressId').val();
		  var numId=$('#numId').val();
		  $( "#form1")[0].submit();
		/* $.ajax({
		        type: "POST",
		        url: "/PMS/SaveProductsDeveloped",
		        data:{"strProductsDevelopedHtml": html,
			    	"strProductsDeveloped":text,
			    	"encMonthlyProgressId":monthlyprogressId,
			    	"encCategoryId":cateoryId,
			    	"numId":numId},
		        success: function(response){
		        	if(response!=''||response!=null){
		        		swal("Saved SuccessFully");
		        		$("#editor").html(response);
		        		$('#buttonupdate').show();
		       		 	$('#buttonsave').hide();
		       		 	var str='';
		       		 	str+='<tr><td>'
		       		 	
		        	}
		        	else{
		        		swal("Problem in  saving.");
		        	}
		        	
		        
		        }
		        });*/
	  });
	  $('#update').click(function(){
		 // var reportid=$('#numReportId').val();
		  console.log($("#editor").html());
		  console.log($("#editor").text());
		  var html=$("#editor").html();
		  $('#strProductsDevelopedHtml').val(html);
		  var text=$("#editor").text();
		  $('#strProductsDeveloped').val(text);
		 // alert($("#editor").html());
		
		  var cateoryId=$('#encCategoryId').val();
		  var monthlyprogressId=$('#encMonthlyProgressId').val();
		  var numId=$('#numId').val();
		 $( "#form1")[0].submit();
		/*  $.ajax({
		        type: "POST",
		        url: "/PMS/SaveProductsDeveloped",
		        data:{"strProductsDevelopedHtml": html,
			    	"strProductsDeveloped":text,
			    	"encMonthlyProgressId":monthlyprogressId,
			    	"encCategoryId":cateoryId,
			    	"numId":numId},
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
		        });*/
	  });
  });
  $(document).on('click','#Edit',function(e){
		 var validDt=new Date();	
		var resultArray1 = $(this).closest('tr').find('td').map( function()
						{
						return $(this).html();
					}).get();
				
		/*		var checkID = $(this).closest('tr').find('input[type=checkbox]').map( function()
						{
						return $(this).val();
					}).get();
				*/
				var hiddenID = $(this).closest('tr').find('input[type=hidden]').map( function()
						{
						return $(this).val();
					}).get();

				
				//alert(hiddenID);
				$('#numId').val($.trim(hiddenID));
			//	alert("gfdg"+resultArray1[1]);
				 var t=resultArray1[2].replace(/[(?*]+/g,"\"");
				//	alert("gfdg"+t);

				$("#editor").html(t);
				//alert($('#numId').val());
				
				});
  
  $(document).ready(function() {
		
		
		 $('#userinfo-message').delay(5000).fadeOut();
		$('#delete').click(function(){
			DegDelete();
		});
	});
  function DegDelete(){
	    var chkArray = [];
	     
			     
			    $(".CheckBox:checked").each(function() {
			        chkArray.push($(this).val());
			    });
			    
			    var selected;
			    selected = chkArray.join(','); 
			    if(selected.length >= 1){
			    	
			    	
			    	 swal({
					      title: "Are you sure you want to delete the record?",
					      icon: "warning",
					      buttons: [
					                'No',
					                'Yes'
					              ],
					      dangerMode: true,
					    }).then(function(isConfirm) {
					      if (isConfirm) {
						    	  $("#numIds").val(selected);
						    	 
						  		  submit_form_delete();
						      }
				  	
						      else{
						    	
						      return false;
				    }
			    });
			    }
			    else{
			        swal("Please select at least one checkbox to delete");  
			    }
			    
		  } 
	function submit_form_delete()
	{

	 document.getElementById("form1").action = "/PMS/deleteProductsDeveloped";
	document.getElementById("form1").method = "POST";
	document.getElementById("form1").submit(); 
	} 
	 


