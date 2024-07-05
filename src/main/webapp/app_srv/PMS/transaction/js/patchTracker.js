$('.select2Option').select2({
	 width: '100%'
});

//min length for  auto complete
//$( ".selector" ).autocomplete({ minLength: 3 });



//date picker
$('#depDate').datepicker({
	   dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,  
	    maxDate: '0',
	    onSelect: function(date){
	    	$('#depDate').trigger('input');
	    	}
});

//Declaring the regex variables
var nameRegex='';
var nameErrorMessage='';
var urname='';
var urErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	urname = messageResource.get('nonRestrictname.regex', 'regexValidationforjs');
	urErrorMessage = messageResource.get('nonRestrictname.regex.message', 'regexValidationforjs');
	});




$(document).ready(function() {
	
	$('.select2Option').select2({
		 width: '100%'
	})
	
	
});
$(document).ready(function(){
	
	$('#reset').click(function(){
		 resetForm();
		 $('#form1').data('bootstrapValidator').resetForm(true);
		
	});
	
	//defining the fields under bootstrap validator
	
	$('#form1').bootstrapValidator({
//	      live: 'disabled',
		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	  severity:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose severity',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('severity').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          type:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose patch type',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('type').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          strdescription: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Description is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 2000,
	                        message: ' must be less than 2000 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	                 
	              }
	          },
	         strRequestedBy: {
	              group: '.col-md-12',
	              validators: {
	            	  stringLength: {
	                        max: 1000,
	                        message: ' must be less than 1000 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	                 
	              }
	          },
	          strNameOfFiles: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Name of files is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 2000,
	                        message: ' must be less than 2000 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	                 
	              }
	          },
	          strTeamMembers: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Name of team members is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 2000,
	                        message: ' must be less than 2000 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },    
	          depDate: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Date of Deployment is required and cannot be empty'
	                  },
	                  date:{
	    	        	  format: 'DD/MM/YYYY'
	    	        	  }
	                 
	              }
	          },
	          strBugId: {
	              group: '.col-md-12',
	              validators: {
	            	  stringLength: {
	                        max: 100,
	                        message: ' must be less than 100 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          strSvnNo: {
	              group: '.col-md-12',
	              validators: {
	            	  stringLength: {
	                        max: 20,
	                        message: ' must be less than 20 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	                 
	              }
	          },
	          stage:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose stage',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('stage').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          strModules: {
	              group: '.col-md-12',
	              validators: {
	                 
	                 stringLength: {
	                        max: 1000,
	                        message: ' must be less than 1000 characters'
	                    },
	                    regexp: {
	                        regexp: urname,
	                       message: urErrorMessage
	                    }
	                  
	                 
	              }
	          },
	          
	          
	 
	          
	      
	         
	      }
	  });

	
	
	
	
	$('#save').click(function(){
	
		
		var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   	}
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#valid').val(istoggle);
		
			var bootstrapValidator = $("#form1").data('bootstrapValidator');
			bootstrapValidator.validate();
		    if(bootstrapValidator.isValid()){				
					document.getElementById("save").disabled = true; 
					/*document.form1.submit();*/
					 $( "#form1")[0].submit();
					return true;
				
			}else{
				console.log('Not Validated');
				 //return false;
			}
	   	 
	});
		
});



//defining the functionality of reset


function resetForm(){
	$('#severity').val('0').trigger('change');
	$('#type').val('0').trigger('change');
	$('#strdescription').val("");
	$('#strRequestedBy').val("");
	$('#strNameOfFiles').val('');
	$('#strTeamMembers').val('');
	$('#strBugId').val('');
	$('#strSvnNo').val('');
	$('#valid').val('');
	$('#valid2').val('');
	$('#stage').val('');
	$('#strModules').val('');
	$('#save').text('Save');
}

function autocomplete(inp, arr) {
	
	var tempVal = inp.value;
	var temp = tempVal.split(',');	
	temp.pop();	
	var fieldValue = '';
	temp.forEach(function(item,index){		
				if(fieldValue == ''){
					fieldValue = item.trim();
				}else{
					fieldValue = fieldValue +', '+item.trim();
				}		 
		});

	  /*the autocomplete function takes two arguments,
	  the text field element and an array of possible autocompleted values:*/
	  var currentFocus;
	  /*execute a function when someone writes in the text field:*/
	  inp.addEventListener("input", function(e) {
	      var a, b, i;
	      var searchString = this.value;
	      var tempData = searchString.split(',');			
			var search = tempData[tempData.length-1];		
	      var val = search.trim();
	      /*close any already open
	       *  lists of auto-completed values*/
	      closeAllLists();
	      if (!val) { return false;}
	      currentFocus = -1;
	      /*create a DIV element that will contain the items (values):*/
	      if($('#'+this.id + "autocomplete-list").length == 0) {
	    	   a = document.createElement("DIV");
	 	      a.setAttribute("id", this.id + "autocomplete-list");
	 	      a.setAttribute("class", "autocomplete-items col-md-12");
	 	      /*append the DIV element as a child of the auto-complete container:*/
	 	      this.parentNode.appendChild(a);
	    	}
	 
	      /*for each item in the array...*/
	      for (i = 0; i < arr.length; i++) {
	    	  var tempValue = arr[i].toUpperCase();
	    	  var temp = val.toUpperCase();
	    	  tempValue =  tempValue.replace(new RegExp(temp, 'g'),'<strong>'+temp+'</strong>');
	    		    	 
	        /*check if the item starts with the same letters as the text field value:*/
	       /* if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {*/
	          /*create a DIV element for each matching element:*/
	        	
	           b = document.createElement("DIV");
	           /*make the matching letters bold:*/
	           b.innerHTML = tempValue;
	          /* b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
	           b.innerHTML += arr[i].substr(val.length);*/
	           /*insert a input field that will hold the current array item's value:*/
	           b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
	           /*execute a function when someone clicks on the item value (DIV element):*/
	              b.addEventListener("click", function(e) {
	              /*insert the value for the auto-complete text field:*/
	            if(fieldValue){	            	
	            	 inp.value = fieldValue+', '+ this.getElementsByTagName("input")[0].value+', ';
	            }else{	            	
	            	 inp.value = this.getElementsByTagName("input")[0].value+', ';
	            }
	             
	              /*close the list of auto-completed values,
	              (or any other open lists of auto-completed values:*/
	              closeAllLists();
	          });
	          a.appendChild(b);
	       /* }*/
	      }
	  });
	  /*execute a function presses a key on the keyboard:*/
	  inp.addEventListener("keydown", function(e) {
	      var x = document.getElementById(this.id + "autocomplete-list");
	      if (x) x = x.getElementsByTagName("div");
	      if (e.keyCode == 40) {
	        /*If the arrow DOWN key is pressed,
	        increase the currentFocus variable:*/
	        currentFocus++;
	        /*and and make the current item more visible:*/
	        addActive(x);
	      } else if (e.keyCode == 38) { //up
	        /*If the arrow UP key is pressed,
	        decrease the currentFocus variable:*/
	        currentFocus--;
	        /*and and make the current item more visible:*/
	        addActive(x);
	      } else if (e.keyCode == 13) {
	        /*If the ENTER key is pressed, prevent the form from being submitted,*/
	        e.preventDefault();
	        if (currentFocus > -1) {
	          /*and simulate a click on the "active" item:*/
	          if (x) x[currentFocus].click();
	        }
	      }
	  });
	  function addActive(x) {
	    /*a function to classify an item as "active":*/
	    if (!x) return false;
	    /*start by removing the "active" class on all items:*/
	    removeActive(x);
	    if (currentFocus >= x.length) currentFocus = 0;
	    if (currentFocus < 0) currentFocus = (x.length - 1);
	    /*add class "autocomplete-active":*/
	    x[currentFocus].classList.add("autocomplete-active");
	  }
	  function removeActive(x) {
	    /*a function to remove the "active" class from all autocomplete items:*/
	    for (var i = 0; i < x.length; i++) {
	      x[i].classList.remove("autocomplete-active");
	    }
	  }
	  function closeAllLists(elmnt) {		 
	    /*close all autocomplete lists in the document,
	    except the one passed as an argument:*/
	    var x = document.getElementsByClassName("autocomplete-items");
	    for (var i = 0; i < x.length; i++) {
	      if (elmnt != x[i] && elmnt != inp) {
	      x[i].parentNode.removeChild(x[i]);
	    }
	  }
	}
	/*execute a function when someone clicks in the document:*/
	document.addEventListener("click", function (e) {
	    closeAllLists(e.target);
	});
	}

$(document).ready(function() {
	
	$('#strRequestedBy').keyup(function(){
		var searchString = $('#strRequestedBy').val().trim();
		var tempData = searchString.split(',');
		
		var search = tempData[tempData.length-1];
		search = search.trim();
	
		if(search.length >=2){
			
			$.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/mst/getRequesterName",
		        data:{"searchString":search},
		        async:false,       		
				success : function(data) {					
					autocomplete(document.getElementById("strRequestedBy"),data);
				}
			});
		}else{			
			A = [];
			autocomplete(document.getElementById("strRequestedBy"),A);
		}
	});	
});

$(document).ready(function() {
	
	$('#strNameOfFiles').keyup(function(){
		var searchString = $('#strNameOfFiles').val().trim();
		var tempData = searchString.split(',');
		
		var search = tempData[tempData.length-1];
		search = search.trim();
	
		if(search.length >=2){
			
			$.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/mst/getNameOfFiles",
		        data:{"searchString":search},
		        async:false,       		
				success : function(data) {					
					autocomplete(document.getElementById("strNameOfFiles"),data);
				}
			});
		}else{			
			A = [];
			autocomplete(document.getElementById("strNameOfFiles"),A);
		}
	});	
});

$(document).ready(function() {
	
	$('#strTeamMembers').keyup(function(){
		var searchString = $('#strTeamMembers').val().trim();
		var tempData = searchString.split(',');
		
		var search = tempData[tempData.length-1];
		search = search.trim();
	
		if(search.length >=2){
			
			$.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/mst/getTeamMembers",
		        data:{"searchString":search},
		        async:false,       		
				success : function(data) {					
					autocomplete(document.getElementById("strTeamMembers"),data);
				}
			});
		}else{			
			A = [];
			autocomplete(document.getElementById("strTeamMembers"),A);
		}
	});	
});
$(document).ready(function() {
	
	$('#strModules').keyup(function(){
		var searchString = $('#strModules').val().trim();
		var tempData = searchString.split(',');
		
		var search = tempData[tempData.length-1];
		search = search.trim();
	
		if(search.length >=2){
			
			$.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/mst/getModules",
		        data:{"searchString":search},
		        async:false,       		
				success : function(data) {					
					autocomplete(document.getElementById("strModules"),data);
				}
			});
		}else{			
			A = [];
			autocomplete(document.getElementById("strModules"),A);
		}
	});	
});
