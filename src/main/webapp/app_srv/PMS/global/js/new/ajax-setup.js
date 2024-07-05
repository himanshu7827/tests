/**
 * 
 */

//Added by devesh on 09-10-23 to set global configuration for all ajax requests
$.ajaxSetup({
    beforeSend: function(xhr, settings) {
        // Check if contentType and processData are not explicitly set to false
        if (settings.contentType !== false && settings.processData !== false) {
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
        }
    }
});
//End of configuration