(function($) {
     $.ajaxRequest = {
  /**
   * Central AJAX caller gateway.
   */
 ajax: function(objConfig) {
    
     $.ajax({
     type: objConfig.type, //mandatory field
     url: objConfig.url, //mandatory
     headers: (objConfig.headers != undefined)?objConfig.headers:{}, 
     async: (objConfig.async != undefined)?objConfig.async:true,
     data : (objConfig.data != undefined)?objConfig.data:{},
//     dataType : (objConfig.dataType != undefined)?objConfig.dataType:'json',
     cache: (objConfig.cache != undefined)?objConfig.cache:true,
     contentType: (objConfig.contentType != undefined)?objConfig.contentType:'application/x-www-form-urlencoded',
        
           success: function(data) {
        	  // console.log('This is for global ajax request');
        	   //console.log(data);
        	   if(data == 'Unauthorised Access'){
        		   window.location.href= "/PMS/Homepage";
        	   }else{
        		   objConfig.success(data); //Invoke success callback method.   
        	   } 
           },
           error: function(data, textStatus, jqXHR) {
        	   if(data.responseText == 'Unauthorised Access'){
        		   window.location.href= "/PMS/Homepage";
        	   }else{
                objConfig.error(data, textStatus, jqXHR);   
        	   }
           }
     });
 }
     }
}(jQuery));