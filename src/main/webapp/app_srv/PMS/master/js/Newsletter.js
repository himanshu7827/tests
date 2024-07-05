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
  $(document).ready(function() { 
	  var elems = Array.prototype.slice.call(document.querySelectorAll('.demo'));
	
		elems.forEach(function(html) {
		  var switchery = new Switchery(html,{ color: '#0090F1'});
		});
	
  });
  
  $(document).ready(function(){
	  
	   $('.select2Option').select2({
	    	 width: '100%'
	    });
		
		$('#PropDetail').hide();
		$('#foruser').hide();
		$('#attachDiv').hide();
		$('#smsDiv').hide();
		$('.NewsletterDate').hide();
		$('#PropDiv').hide();
		$('#alluserDiv').hide();
		
		$(".stylish").labelauty({same_width:true,minimum_width: "250px"}); 
		 
		$('#user').change(function(){
			
			$('#foruser').show();
			$('#PropDetail').hide();
			$('#PropDiv').hide();
			if($('#alluser').is(":checked")){
				 $('#alluserDiv').hide();
			}
			else
				{
				 $('#alluserDiv').show();
				}
			
	
		
		});  

			
	$('#allproposal').click(function() {


		if($(this).is(":checked")){
			$('#PropDetail').show();
			$('#foruser').hide();
			$('#PropDiv').show();
			 $('#alluserDiv').hide();
			globalVar='P';
	
		
	}
	else{
	
		$('#PropDetail').hide();
		$('#PropDiv').hide();
		globalVar='U';
	}
		
		});	
	
  	$('#attach').change(function() {
  	
        if($(this).is(":checked")) {
        	
            $('#attachDiv').show();
        }
        else{
        	
            $('#attachDiv').hide();
        }
	});
  	
	$('#smsalert').change(function() {
        if($(this).is(":checked")) {
            $('#smsDiv').show();
            $('#smsInput').val("Y");
        }
        else{
            $('#smsDiv').hide();
            $('#smsInput').val("N");
        }
	});
	
	$('#scheduledAt').change(function() {
        if($(this).is(":checked")) {
            $('.NewsletterDate').show();
            $('#scheduleTime').hide();
            $('#datePicker').hide();
            if($('#isPeriodic').is(":checked")) {
	            $('#isPeriodic').trigger('click');
	            }
        }
        else{
            $('.NewsletterDate').hide();
            if($('#isPeriodic').is(":checked")) {
            $('#scheduleTime').show();
            $('#datePicker').show();
            }

        }
	});
	
		$('#isPeriodic').change(function() {
	        if($(this).is(":checked")) {
	        	
	            $('#scheduleTime').show();
	            $('#datePicker').show();
	            $('#one-time').hide();
	            if($('#scheduledAt').is(":checked")) {
	            $('#scheduledAt').trigger('click');
	            }
	        }
	        else{
	            $('#scheduleTime').hide();
	            $('#datePicker').hide();
	            $('#one-time').show();
	            
	          //  $('#scheduledAt').trigger('click');
	        }
    	});
	});
  
	$(document).ready(function() {
		 var currentDate = new Date();
			$('#nsaDateID').datepicker({
				   dateFormat: 'dd/mm/yy', 
				   changeMonth: true,
				   changeYear:true,
			 	  	minDate:currentDate,
				   onSelect: function(date){
				    	$('#nsaDateID').trigger('input');
				    	}
				  
			});
		});
	
	  $(document).on('click','#Use',function(e)
			  {
		  
		  var value = $('#proID').val();
		
          $("#editor").append(value);
			  });
	  
	  
	  $(document).on('click','#Send',function(e){
			var subject = $('#subject').val();
			var html = $("#editor").html();
			var idString = "";
			var textString = "";
			var chkArray = [];
			var roleTo= $("#roleTo").val().toString();
			var roleCc= $("#roleCc").val().toString();
			var roleBCc= $("#roleBcc").val().toString();
			if($("#roleTo").val()==0){
				sweetSuccess('Attention','To is mandatory');
				return false;
			}
			$(".stylish:checked").each(function() {
				var parentTRClasses = $(this).closest('tr').prop('className');
				var radioVal = $('#allproposal').is(':checked');
					if(parentTRClasses == 'P' && radioVal){
					chkArray.push($(this).val());
					
				}else if(parentTRClasses == 'U' && radioVal != true){
					chkArray.push($(this).val());
					
				}
			    
			});
			console.log(chkArray);
			var filtert="";
			var Ids;
			var periodicdate="";
			var c =$('#alluser').is(":checked");
		
			if(globalVar == 'P'){
			
				Ids = chkArray.join(',');
				filtert = "P";
			}else{
			
			
	        if($('#alluser').is(":checked")) {
	        	Ids = "1";
	        }
	        else{
	        	Ids = chkArray.join(',');
	        	filtert = "U";
	        }
			}
			/*alert(Ids);*/
	       if(Ids.length === 0){
	    	   sweetSuccess('Attention','Atleast one Filter is mandatory');
				return false;
	       }
			var documentIds = $('#documentIDs').val();
			var smsFlag = $('#smsInput').val();
			var SMSContent = $('#smsText').val();
	        var date=$('#nsaDateID').val();
	        if($('#isPeriodic').is(":checked")){
	        var periodicNLDay=$('#day').val();
	        var periodicNLMonth=$('#month').val();
	        var periodicNLYear=$('#year').val();
	        periodicdate=periodicNLDay+"-"+periodicNLMonth+"-" + periodicNLYear;
	      
	        }
	        if(subject=="")
	        	{
	        	alert("Please enter the subject");
	        	}
	        else if(html==""&&SMSContent=="")
	        	{
	        	alert("Please enter the content");
	        	
	        	}
	        	
	        else if(date=="")
	        	{
	        	
	        	if(periodicdate==""){
	        		
	        		swal({
	        			  title: "Are you sure you want to save this newsletter?",
	        			  text: "As neither of periodic or one-time option is selected ,hence  newsletter will be sent just now!",
	        			  type: "info",
	        			  showCancelButton: true,
	        			  confirmButtonClass: "btn-danger",
	        			  confirmButtonText: "OK!",
	        			  allowOutsideClick: false,
	        			  closeOnConfirm: true,
	        			  closeOnCancel: true
	        			},
	        	    
	        		function(isConfirm){
	           		if(isConfirm)	{
	        			 $.ajax(
	 			        {
	 				        type: "POST",
	 				        url: "/PMS/SendNewsLetter",
	 				      
	 				        beforeSend: function(){
	 				        	callMe();
	 	               	    },
	 	               	    complete: function(){
	 	               	    	$.unblockUI();
	 	               	    },
	 					    data:{strContent:html,strIDs:Ids,strSubject:subject,strSMSContent:SMSContent,strSMSFlag:smsFlag,documentIds:documentIds,periodicNewsletterDate:periodicdate,strFilterType:filtert,to:roleTo,cc:roleCc,bcc:roleBCc},
	 				        success: function(response){
	 				        	alert("Email sent successfully");
	 				        	location.reload();
	 				         },
	 				        				        			        
	 		        	});	
	     	
	     	
	     	} 
	        		});
	        	}
	        	else{
	        		
	        		
	        		var answer = confirm('Are you sure you want to save this newsletter?');
	        		if(answer){
	        	 
	            		 $.ajax(
	        			        {
	        				        type: "POST",
	        				        url: "/PMS/SendNewsLetter",
	        				        beforeSend: function(){
	        				        	callMe();
	        	               	    },
	        	               	    complete: function(){
	        	               	    	$.unblockUI();
	        	               	    },
	        					    data:{strContent:html,strIDs:Ids,strSubject:subject,strSMSContent:SMSContent,strSMSFlag:smsFlag,documentIds:documentIds,periodicNewsletterDate:periodicdate,to:roleTo,cc:roleCc,bcc:roleBCc},
	        				        success: function(response){
	        				        	alert("Email sent successfully");
	        				        	location.reload();
	        				         },
	        				        				        			        
	        		        	});	
	        		}
	        	}}
	            else
	        	{      
			var answer = confirm('Are you sure you want to save this newsletter?');
			if(answer){
		 
	    		 $.ajax(
				        {
					        type: "POST",
					        url: "/PMS/SendNewsLetter",
					        beforeSend: function(){
					        	callMe();
		               	    },
		               	    complete: function(){
		               	    	/*$.unblockUI();*/
		               	    },
						    data:{strContent:html,strIDs:Ids,strSubject:subject,strSMSContent:SMSContent,strSMSFlag:smsFlag,documentIds:documentIds,scheduledDate:date,periodicNewsletterDate:periodicdate,to:roleTo,cc:roleCc,bcc:roleBCc},
					        success: function(response){
					        	alert("Email sent successfully");
					        	location.reload();
					         },
					        				        			        
			        	});
			}   	
	       	}
	        
	       
		});
	  
	  function deleteNewsLetterDetails(){
 
  				 var chkArray = [];

  				$(".checkId:checked").each(function() {
  					chkArray.push($(this).val());
  				});

  				var selected;
  				selected = chkArray.join(',') + ",";
  				
  				if (selected.length > 1) {
  					
                deleteNewsLetter(selected);	
                
                				} else {
  					alert("Please select at least one news-letter to delete");
  				};
  			
  			
  			}
  	
  	  function deleteNewsLetter(nlId){
  	       	
  		 
  	    	$.ajax(
  			        {
  				        type: "POST",
  				        url: "/PMS/DeleteNewsLetter",
  				        data: "nlId="+nlId,
  				        success: function(response){

  				        	$(".checkId:checked").each(function() {
  				        		
  					        	var id="#"+"checkbox"+$(this).val();
  					        	
  					        	$(id).parent().fadeOut("slow", function() {
  					        		$(id).parent().remove();
  					        	    });
  						}); 	
  				        	 		
  				        	 				        	 	
  				        },
  				        
  				        error: function(e){
  	    	 				// document.frm.numProposalId.value="";

  				        	alert("Error in posting path here!");
  				    	}
  			        
  		        	});

  	    }
  	  
  	$('#alluser').change(function() {
		var chk = $('#alluser').is(":checked");
	
        if($(this).is(":checked")) {
            $('#alluserDiv').hide();
        }
        else{
            $('#alluserDiv').show();
        }
	});
  	
  	$(document).on(
			'click',
			'#Edit',
			function(e) {
       	 	 $('#attachDiv').hide();
				$("#Send").hide();
				$("#Update").show();				
			
         
				$('#dataTable tbody').empty();
				
				var nlId = $(this).closest('tr')
				.find('input[type=checkbox]').map(function() {
					return $(this).val();
				}).get();
				smsId="#smsContentId"+nlId;
				emailId="#emailContent"+nlId;
			
				var chk=$('#smsalert').is(":checked");

			  if($(smsId).val()!="" && chk==false)
				{
	            $('#smsDiv').show();
	            $('#smsInput').val("Y");
				$('#smsText').html($(smsId).val());
				$('#smsalert').trigger('click');

				}
             else if($(smsId).val()==""&& $('#smsalert').is(":checked")==true)
				{
	            $('#smsalert').trigger('click');
		        $('#smsText').html("");
		        }
             else if($(smsId).val()!=""&& $('#smsalert').is(":checked")==true)
				{
	            $('#smsDiv').show();
                $('#smsInput').val("Y");
		        $('#smsText').html($(smsId).val());				
	            }
			
	        	$('#editor').html($(emailId).val());	
				$('#nletterid').val(nlId);
				getFilterMappingForNewsLetter(nlId);
				getPeriodicScheduleDateForNewsLetter(nlId);
				getScheduledDateFromNewsletter(nlId);
				getDocumentsForNewsLetter(nlId);
				getRolesIds(nlId);
				var resultArray1 = $(this).closest('tr').find('td').map(
						function() {
							return $(this).text();
						}).get(); 
                   	$('#subject').val($.trim(resultArray1[1]));	
                   	
				/*$('#numDocumentId').val(checkID);*/
						
			});
  	
  	$('#attachDiv').show();
    function getFilterMappingForNewsLetter(nlId){
    	
    	for(var i=0;i<filterArray.length;i++)
 		{
 		$("#"+filterArray[i]).prop('checked', false);
 		}
    	
    	$.ajax(
		        {
			        type: "POST",
			        url: "/PMS/GetNewsLetterFilter",
			        data: "nlId="+nlId,
			        success: function(response){
							
                          var chk=$('#alluser').is(":checked");
                          var chkproposal = $('#allproposal').is(":checked");
                         
                          if(chk){
			      	          for(var i=0;i<response.length;i++)
			      	 		{
			       		     if(response[i]==1&&chk==true)
			      	 			{

			      	 			$('#alluserDiv').hide();
			    			}
			      	 		else if(response[i]!=1&& chk==false)
			      	 			{
			 			        	           			        	 					                        
			          	$("#"+response[i]).prop('checked', true);
			      	    $('#alluserDiv').show();
			      	 			}
			      	 		else if(response[i]!=1&& chk==true)
			               {  
			      	 			$('#alluserDiv').show();
			      	 			$("#"+response[i]).prop('checked', true);
			      	 		 $('#alluser').trigger('click');
			      	 	   }
			      	 		else
			      	 			{
			      	 		 $('#alluser').trigger('click');
		      	 			 $('#alluserDiv').hide();

			      	 			}

			      	 		}
                          }
                          else{
                	          for(var i=0;i<response.length;i++)
  			      	 		{
  			       		     if(response[i]==1&&chkproposal==true)
  			      	 			{

  			      	 			$('#PropDiv').show();
  			      	 		$('#PropDetail').show();
  			    			}
  			      	 		else if(response[i]!=1&& chkproposal==false)
  			      	 			{
  			 			        		           			        	 					                        
  			          	$("#"+response[i]).prop('checked', true);
  			      	    $('#PropDiv').hide();
  			      		$('#PropDetail').hide();
  			      	 			}
  			      	 		else if(response[i]!=1&& chkproposal==true)
  			               {
  			      	 			$('#PropDiv').hide();
  			      	 		$('#PropDetail').hide();
  			      	 			$("#"+response[i]).prop('checked', true);
  			      	 		 $('#allproposal').trigger('click');
  			      	 	   }
  			      	 		else
  			      	 			{
  			      	 		 $('#allproposal').trigger('click');
  			      	 		//$('#alluser').prop('checked', true);
  		      	 			 $('#PropDiv').show();
  		      	 		$('#PropDetail').show();

  			      	 			}

  			      	 		} 
                        	  
                        	  
                          }
			        },
			        
			        error: function(e){
    	 				// document.frm.numProposalId.value="";

			        	alert("Error in posting path here!");
			    	}
		        
	        	});

    }
    
    function getPeriodicScheduleDateForNewsLetter(nlId){
    	
    	

    	$.ajax(
    	        {
    		        type: "POST",
    		        url: "/PMS/GetPeriodicScheduledDate",
    		        data: "nlId="+nlId,
    		        success: function(response){
    		      			  if(response.length>0){
    		      				
    		      			  $('#one-time').hide();
    		      				  if($('#isPeriodic').is(":checked")==true){
    		        	 $('#day').val(response.substring(0, 2));
    		             $('#month').val(response.substring(3, 6));
    		              $('#year').val(response.substring(7));
    		      				  }else{
    		      					  
    		      				      $('#datePicker').show();
    		      		    
    		      					$('#isPeriodic').trigger('click');  
    		      					 $('#day').val(response.substring(0, 2));
    		    		             $('#month').val(response.substring(3, 6));
    		    		              $('#year').val(response.substring(7));
    		      				  }
    		      			   }
    		      			  else{
    		      				if($('#scheduledAt').is(":checked")==false){      
    				        		 $('#scheduledAt').trigger('click'); 
    		      				}
    		      				  $('#datePicker').hide();
    		      				  if($('#isPeriodic').is(":checked")==true){
    		      					$('#isPeriodic').trigger('click'); 
    		      				   $('#one-time').show();
    		      				  }
    		      			  }
    		        },
    		        
    		        error: function(e){
    	 				// document.frm.numProposalId.value="";

    		        	alert("Error in posting path here!");
    		    	}
    	        
            	});
    }
    

    function getScheduledDateFromNewsletter(nlId){
    	$.ajax(
    	        {
    		        type: "POST",
    		        url: "/PMS/GetScheduledDisplayDate",
    		        data: "nlId="+nlId,
    		        success: function(response){
    		        	 
    		        	$('#nsaDateID').val(response);
    		        	 
    		        },
    		        
    		        error: function(e){
    	 				// document.frm.numProposalId.value="";

    		        	alert("Error in posting path here!");
    		    	}
    	        
            	});

    }
    
 function getDocumentsForNewsLetter(nlId){
       	
    	$.ajax(
		        {
			        type: "POST",
			        url: "/PMS/GetDocumentDetails",
			        data: "nlId="+nlId,
			        success: function(response){
			        	var chk=$('#attach').is(":checked");

			      			        		if(Object.keys(response).length==0 && chk==false)
			      			        			{
			      				        	 	 $('#attachDiv').hide();

			      			        			}
			      			        			
			      			        		else if(Object.keys(response).length!=0 && chk==false)
			      			        			{
			        	 	 $('#attachDiv').show();   				     
			        	 	 $('#attach').trigger('click');
	        		            
     				        	 	 $.each(response, function (key, value) {
     				     	           			var rowCount = $('#dataTable tr').length;
     				     				    	$("#dataTable").append("<tr><td>" + (rowCount-1) + "</td><td><a href='controller/getNewsLetterDocument/"+key+"'>" + value + "</a></td><td><a href='#!' class='removeClass' onclick='Remove(\""+key+"\")'><i title='Delete' class='pad-left icon-remove-sign asphalt '></i></a></td></tr>");
     				  
     				                 });
			      			        			}
		      			       else if(Object.keys(response).length==0 && chk==true)

     				     {
					        	 	 $('#attach').trigger('click');
			
		      			        			
     				     }
     			        		else if(Object.keys(response).length!=0 && chk==true)

		      			    	   
		      			    	   {
		      			    	   
     			        			 $('#attachDiv').show();   				     
     		        		            
     	     				        	 	 $.each(response, function (key, value) {
     	     				     	           			var rowCount = $('#dataTable tr').length;
     	     				     				    	$("#dataTable").append("<tr><td>" + (rowCount-1) + "</td><td><a href='controller/getNewsLetterDocument/"+key+"'>" + value + "</a></td><td><a href='#!' class='removeClass' onclick='Remove(\""+key+"\")'><i title='Delete' class='pad-left icon-remove-sign asphalt '></i></a></td></tr>");
     	     			
     	     				                 });
		      			    	   
		      			    	   }
		      			    	  
			        	 				        	 	
			        },
			        
			        error: function(e){
    	 				// document.frm.numProposalId.value="";

			        	alert("Error in posting path here!");
			    	}
		        
	        	});
	


    	
    }
 
 function getRolesIds(nlId){
 	$.ajax(
 	        {
 		        type: "POST",
 		        url: "/PMS/getRolesIds",
 		        data: "nlId="+nlId,
 		        success: function(response){
 		       	var  roleTo=response.to.trim().split(',');
 		       	var  roleCc=response.cc.trim().split(',');
 		       	var  roleBcc=response.bcc.trim().split(',');
 				$("#roleTo").val(roleTo).change();
 		        	/*$('#roleTo').val(response.to).change();*/
 		        	$('#roleCc').val(roleCc).change();
 		        	$('#roleBcc').val(roleBcc).change();
 		        	 
 		        },
 		        
 		        error: function(e){
 	 				// document.frm.numProposalId.value="";

 		        	alert("Error in posting path here!");
 		    	}
 	        
         	});

 }
 
 $(function () {
	    $('#fileupload').fileupload({
	        dataType: 'json',
	        beforeSend: function(){
		            callMe();
		       },
		       complete: function(){
		           /*$.unblockUI();*/
		       },
	        done: function (e, data) {
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        var flag = 0;
	            $.each(data.result, function (index, file) {
	           
		           			var rowCount = $('#dataTable tr').length;
					    	$("#dataTable").append("<tr><td>" + (rowCount-1) + "</td><td><a href='/getNewsLetterDocument/"+file.documentId+"'>" + file.fileName + "</a></td><td><a href='#!' class='removeClass' onclick='Remove(\""+file.documentId+"\")'><i title='Delete' class='pad-left icon-remove-sign asphalt '></i></a></td></tr>");
		                	var value_arr = $('#documentIDs').val();
		                	if($.trim(value_arr)==''){
		                		var array = [];
		                		array.push(file.documentId);
		                		$('#documentIDs').val(JSON.stringify(array));
		                	}
		                	else{
								value_arr = JSON.parse(value_arr);
								value_arr.push(file.documentId);
								$('#documentIDs').val(JSON.stringify(value_arr));
		                	}
		                	
		                	$('#progress .bar').css(
			    	        'width',
			    	        progress + '%'
			    	    	);
	            });
	        } 
	        });
	    });
 
 function Remove(filename){
	 var answer = confirm('Are you sure you want to delete this file?');
		if(answer){
		  	$.ajax(
		  	{
		  			type: "POST",
			        url: "/PMS/removeNewsLetterDoc/"+filename,
			        success: function(response){
						$('#progress .bar').css(
					        'width',
					        0 + '%'
					     );
						var value_arr = $('#documentIDs').val();
						value_arr = JSON.parse(value_arr);
						var removeItem = filename;
						value_arr = jQuery.grep(value_arr, function(value) {
						  return value != removeItem;
						});

						$('#documentIDs').val(JSON.stringify(value_arr));
			        },
			        
			        error: function(e){
		        	}
		  	});
		  	$(document).on('click', '.removeClass', function(){
						    $(this).closest ('tr').remove ();
						});
		}
  }
 
 $(document).on('click','#Update',function(e){
		var subject = $('#subject').val();
		var html = $("#editor").html();
		var idString = "";
		var textString = "";
		var chkArray = [];
		var roleTo= $("#roleTo").val().toString();
		var roleCc= $("#roleCc").val().toString();
		var roleBCc= $("#roleBcc").val().toString();
	
		var newsletterid= $('#nletterid').val();
		
		if($("#roleTo").val()==0){
			sweetSuccess('Attention','To is mandatory');
			return false;
		}
		
		$(".stylish:checked").each(function() {
		chkArray.push($(this).val());
		});
		var filtert="";
		var Ids;
		if($('#allproposal').is(":checked"))
		{
		Ids = chkArray.join(',');
		filtert = "P";
		}
	else{
		
  if($('#alluser').is(":checked")) {
     	Ids = "1";
     }
     else{
     	Ids = chkArray.join(',');	
     	filtert ="U";
     }
	}
		var documentIds = $('#documentIDs').val();
		var smsFlag = $('#smsInput').val();
		var SMSContent = $('#smsText').val();
     var date=$('#nsaDateID').val();
     var date=$('#nsaDateID').val();
   
var periodicdate="";
	 if($('#isPeriodic').is(":checked")){
	 date="";
  var periodicNLDay=$('#day').val();
  var periodicNLMonth=$('#month').val();
  var periodicNLYear=$('#year').val();
  periodicdate=periodicNLDay+"-"+periodicNLMonth+"-" + periodicNLYear;
	 }else{
		 periodicdate="";	 
	 }

if(subject=="")
	{
	alert("Please enter the subject");
	}
else if(html==""&&SMSContent=="")
	{
	alert("Please enter the content");
	
	}	
else if(date=="" && periodicdate=="")
{
	
alert("Please select a scheduled date for transfering newsletters");
}
else
{       
		var answer = confirm('Are you sure you want to save this newsletter?');
		if(answer){
		
			if(date!=""){
		
				 $.ajax(
					        {
						        type: "POST",
						        url: "/PMS/UpdateNewsLetter/",
						        beforeSend: function(){
					        		/*callMe();*/
			               	    },
			               	    complete: function(){
			               	    	$.unblockUI();
			               	    },
							    data:{strContent:html,strIDs:Ids,strSubject:subject,strSMSContent:SMSContent,strSMSFlag:smsFlag,documentIds:documentIds,scheduledDate:date,numletterId:newsletterid,strFilterType:filtert,to:roleTo,cc:roleCc,bcc:roleBCc},
						        success: function(response){
						        
						        	alert("Email sent successfully");
						        	location.reload();
						         },
						        				        			        
				        	});
			}
			else {
			
 		 $.ajax(
			        {
				        type: "POST",
				        url: "/PMS/UpdateNewsLetter/",
				        beforeSend: function(){
			        		/*callMe();*/
	               	    },
	               	    complete: function(){
	               	    	/*$.unblockUI();*/
	               	    },
					    data:{strContent:html,strIDs:Ids,strSubject:subject,strSMSContent:SMSContent,strSMSFlag:smsFlag,documentIds:documentIds,numletterId:newsletterid,periodicNewsletterDate:periodicdate,strFilterType:filtert,to:roleTo,cc:roleCc,bcc:roleBCc},
				        success: function(response){
				        
				        	alert("Email sent successfully");
				        	location.reload();
				         },
				        				        			        
		        	});
			}
		        	
    	}}
	});
 
 function callMe(){
	 $(document).ready(function() { 
			$.blockUI({ 
	    	message: $('#displayBox'),
	    	css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#000', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .8, 
	            color: '#fff' 
	        }
	     	});		      
			
			$('#displayBox').addClass("HidefromCanvas");
			
	        $(window).load(function(){
			      $('.blockUI').remove()
			})
	  
	  }); 
	 }