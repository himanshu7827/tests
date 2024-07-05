$(document).ready(function(){
$(document).on("keydown", disableF5);
});
function disableF5(e) { if ((e.which || e.keyCode) == 116 ) e.preventDefault(); };



