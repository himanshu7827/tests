$(function(){
var cnt=0;
    var ul = $('#upload ul');
//var ul = $('#upload table');

    $('#drop a').click(function(){
    	
        // Simulate a click on the file input button
        // to show the file browser dialog
        $(this).parent().find('input').click();
    });

    // Initialize the jQuery File Upload plugin
    $('#upload').fileupload({
    	

        // This element will accept file drag/drop uploading
        dropZone: $('#drop'),

        // This function is called when a file is added to the queue;
        // either via the browse button, or via drag/drop:
      
        add: function (e, data) {
        	
        	//alert("count=="+cnt);
            var tpl = $('<li class="working"><p></p><span></span></li>');
        	// var tpl = $('<input type="text" value="0" data-width="48" data-height="48"'+
            // ' data-fgColor="#0788a5" data-readOnly="1" data-bgColor="#3e4043" /><tr><td></td><span></span>');
            if(cnt==0)
            	{
            	$('#showfile').show();
            	}
            if(cnt<5)
        	{//alert("file type==="+(data.files[0].name).split(".")[1].toUpperCase())
            	if( (data.files[0].size/1024<=10000) &&((data.files[0].name).split(".")[1].toUpperCase()=="PDF"))
            	{//alert("add now");
            // Append the file name and file size
            tpl.find('p').text(data.files[0].name)
                         .append('<i>&nbsp&nbsp' + formatFileSize(data.files[0].size) + '</i>');
            cnt++;
            // Add the HTML to the UL element
            data.context = tpl.appendTo(ul);
            
            // Initialize the knob plugin
        //     tpl.find('input').knob();
            	}
        	}
            else
            	
            	{
            	alert("Can't upload more than 5 files");
            	exit();
            	}

            // Listen for clicks on the cancel icon
            tpl.find('span').click(function(){

                if(tpl.hasClass('working')){
                    jqXHR.abort();
                    
                }

               // tpl.fadeOut(function(){
                   // tpl.remove();
                   // cnt--;
                //});

            });

            // Automatically upload the file once it is added to the queue
            var jqXHR = data.submit();
        },

        done: function (e, data) {
        	// $("input[name='doc_ids[]']").push(data.result);
        	//alert("data.result==="+data.result);
     	   if (data.result == 1){
     	       	alert("Please upload only PDF files");
     	       	}
     	 if (data.result == 2){
     	       	alert ("File size exceeds 10 MB");
     	       	}
     	  if (data.result == 3){
     	       	alert("There was some error uploading files");
     	       	}
     	  if (data.result == 0){
     		   if(data.files[0].size/1024>10000)
     			   {
     				alert ("File size exceeds 10 MB");
     			   }
     		  if((data.files[0].name).split(".")[1].toUpperCase()!="PDF")
     			  {
     				alert("Please upload only PDF files");
     			  }
     	   }
//     	       	else {
//     	       		alert("File Uploaded Successfully");
//     	  
//     	      
//     	       	}
            
        },
        progress: function(e, data){

            // Calculate the completion percentage of the upload
            var progress = parseInt(data.loaded / data.total * 100, 10);

            // Update the hidden input field and trigger a change
            // so that the jQuery knob plugin knows to update the dial
            data.context.find('input').val(progress).change();

            if(progress == 100){
                data.context.removeClass('working');
            }
        },

        fail:function(e, data){
            // Something has gone wrong!
            data.context.addClass('error');
        }

    });

    // Prevent the default action when a file is dropped on the window
    $(document).on('drop dragover', function (e) {
        e.preventDefault();
    });

    // Helper function that formats the file sizes
    function formatFileSize(bytes) {
        if (typeof bytes !== 'number') {
            return '';
        }

        if (bytes >= 1000000000) {
            return (bytes / 1000000000).toFixed(2) + ' GB';
        }

        if (bytes >= 1000000) {
            return (bytes / 1000000).toFixed(2) + ' MB';
        }

        return (bytes / 1000).toFixed(2) + ' KB';
    }

});