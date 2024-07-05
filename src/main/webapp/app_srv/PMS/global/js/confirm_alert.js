	var msg2;
function  confirm_alert(msg,type){ 
//alert("in confirm alert"+type);
	var m = msg.split('.');
	//var flag=false;
	 
	    var msg1=m[0];
	    msg2=m[1];
	    
swal({   title: "Are you sure?",   text: msg1,   
				type: "warning",   showCancelButton: true,   
				confirmButtonColor: "#34A534",   
				confirmButtonText: "OK",   
				cancelButtonText: "Cancel",   
				closeOnConfirm: false,   
				closeOnCancel: true }, 
				function abc(isConfirm){   
					
					if (isConfirm) {
						swal('',msg2,"success");
						if(type=="save")
							{
						   		submit_form();
							}
						else if(type=="delete")
							{
							del_form();
							}
						else if(type=="modify")
						{
						mod_form();
						}
					
					
						} 
					else {     
						swal("Cancelled", "", "error");
					
						}
				 
					});


}
	
	

