
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
  
  function resetForm(){
		$('#numId').val('0');
		$('#editor').html('');
		$('#save').text('Save as Draft');
	//	$('#save').text('Update');
		
		$('#form1').data('bootstrapValidator').resetForm(true);
		
	}
  
  
  
  
  $(document).ready(function() {
 	 
 	 $("#editor").css("height",$(window).height()-500);
 	 $("#editor").css("max-height",$(window).height()-500);
 	 
 	 
 	});
  
  $(document).ready(function() {
	    var table = $('#inboxTable').DataTable( {
	    	"paging":   false,
	        "ordering": false,
	        "searching": false,
	        rowReorder: true,
	        responsive: {
		        details: {
		            type: 'column',
		            target: 'tr'
		        }
		    },
		    columnDefs: [ {
		        className: 'control',
		        orderable: false,
		        targets:   1
		    } ],
		    order: [ 1, 'asc' ]
	    } );
	} );
  
  $(document).ready(function(){
	  
		$('#reset').click(function(){
			resetForm();
			$('#form1').data('bootstrapValidator').resetForm(true);
		});
		
	  
	  
	  var descr=$('#strInnovationDescriptionHtml').val();
	 //alert(descr);
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
		 // console.log($("#editor").html());
		  //console.log($("#editor").text());
		  var html=$("#editor").html();
		  var text=$("#editor").text();
		  var numId =$('#numId').val();
		  var cateoryId=$('#encCategoryId').val();
		  var monthlyprogressId=$('#encMonthlyProgressId').val();
		  if(text!=''){
		  $.ajax({
		        type: "POST",
		        url: "/PMS/SaveInnovations",
		       data:{"strInnovationDescriptionHtml": html,
			    	"strInnovationDescription":text,
			    	"encMonthlyProgressId":monthlyprogressId,
			    	"encCategoryId":cateoryId,
			    	"numId":numId},
		        success: function(response){
		        	console.log(response);
		        	if(response!=0){
		        		swal({
		        		    title: "",
		        		    text: "Saved Successfully!",
		        		    type: "success"
		        		}).then(function() {
		        			location.reload();
		        		});
		        		
		        		
		        		
		        	}
		        	else{
		        		swal("Problem in  saving.");
		        	}
		        	
		        
		        }
		        });
	  } else{
			  swal("Please enter Project Innovations")
		  }
	  });
	  
	  
	  $(document).on('click','#Edit',function(e){
		
			 var validDt=new Date();	
			var resultArray1 = $(this).closest('tr').find('td').map( function()
							{
							return $(this).html();
						}).get();
			var hiddenID = $(this).closest('tr').find('input[type=hidden]').map( function()
							{
							return $(this).val();
						}).get();

					
					console.log(resultArray1);
					$('#numId').val($.trim(hiddenID));
					//alert($('#projectPatentId').val());
					$('#editor').html(resultArray1[3]);
					$('#save').text('Update');
					document.getElementById("editor").focus();
				
					});
	  
	  /*$('#update').click(function(){
		 var html=$("#editor").html();
			  var text=$("#editor").text(); 
			  var numId =$('#numId').val();
			 var cateoryId=$('#encCategoryId').val();
			  var monthlyprogressId=$('#encMonthlyProgressId').val();
			  $.ajax({
			        type: "POST",
			        url: "/PMS/SaveInnovations",
			        data:{"strInnovationDescriptionHtml": html,
				    	"strInnovationDescription":text,
				    	"encMonthlyProgressId":monthlyprogressId,
				    	"encCategoryId":cateoryId,
				    	"numId":numId},
			        success: function(response){
			        	if(response!=''||response!=null){
			        	swal({
				        		    title: "",
				        		    text: "Updated Successfully!",
				        		    type: "success"
				        		}).then(function() {
				        			location.reload();
				        		});
			        	}
			        	else{
			        		swal("Problem in  saving.");
			        	}
			        	
			        
			        }
			        });
		  });*/
  });
  
  $('#delete').click(function(){
		deleteInnovationDetails();
		});
	function deleteInnovationDetails(){

	    var chkArray = [];
	     
	    $(".CheckBox:checked").each(function() {
	        chkArray.push($(this).val());
	        
	    });
	    
	    var selected;
	    selected = chkArray.join(','); 
		alert("amit="+selected);
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
			    	  
				    	 var numIds =selected;
				    	  var encCategoryId=$("#encCategoryId").val();
				    		var encMonthlyProgressId=$("#encMonthlyProgressId").val();
				    		  alert("great="+selected);
				    	  $.ajax({
					    	        type: "POST",
					    	        url: "/PMS/deleteInnovationDetails",	        
					    	        data:{"numIds":numIds,"encCategoryId":encCategoryId,"encMonthlyProgressId":encMonthlyProgressId},
					    	        
					    	        success: function(res) {
					    	        	if(res)
					    	        		{
					    	        		swal({
							        		    title: "",
							        		    text: "Innovation Details deleted Successfully!",
							        		    type: "success"
							        		}).then(function() {
							        			location.reload();
							        		});
					    	        		
					    	        		}
					    	        	else
					    	        		{
					    	        		  sweetSuccess('Attention','Error in Innovation delete!');
						    	        	  return true;
					    	        		
					    	        		}
					    	        	 
					    	       },
					    	       error: function() {
					    		        	
					    		            alert('Error');
					    		        }
					    		});

					    	  
					      
				      }
		  	 
				      else{
				    	  
				      return false;
		    }
	    });
	    }
	    else{
	        swal("Please select at least one Detail to delete");  
	}
	 
	}