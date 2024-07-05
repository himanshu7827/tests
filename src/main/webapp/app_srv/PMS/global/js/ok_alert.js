function ok_alert(msg){ 

		
    var m = msg.split('.');
   // alert(m);
    var msg1=m[0];
    var msg2=m[1];
    
	if (msg=="error")
		{
		swal('Error','No records selected', "error");	
		}
	else 
		swal(msg1,msg2, "success");
	

}

