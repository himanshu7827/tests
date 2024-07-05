$('document').ready(function(){
	
	
	var encCategoryId = $('#encCategoryId').val();
	var encMonthlyProgressId = $('#encMonthlyProgressId').val();
	//alert("encCategoryId="+encCategoryId+"encMonthlyProgressId="+encMonthlyProgressId);
	$.ajax({
        type: "POST",
        url: "/PMS/getPrevNextBtnController",
        async:false,
        data: {"encCategoryId":encCategoryId,"encMonthlyProgressId":encMonthlyProgressId},			
		success : function(res) {
			
			var mainDiv="";
			if(res.strPrevController && res.strNextController){
				mainDiv+="<div class='padded pull-left'><button type='button' class='btn btn-primary font_14' onclick=goPrev('"+res.strPrevController+"','"+res.encPrevCategoryId+"')><span  aria-hidden='true'><< Prev</span></button></div>";
				mainDiv+="<div class='padded pull-right'><button type='button' class='btn btn-primary font_14' onclick=goNext('"+res.strNextController+"','"+res.encNextCategoryId+"')><span aria-hidden='true'>Next >></span></button></div>";
			}else if(res.strNextController && !res.strPrevController){
				mainDiv+="<div class='padded pull-right'><button type='button' class='btn btn-primary font_14' onclick=goNext('"+res.strNextController+"','"+res.encNextCategoryId+"')><span aria-hidden='true'>Next >></span></button></div>";
			}else if(res.strPrevController && !res.strNextController){
				mainDiv+="<div class='padded pull-left'><button type='button' class='btn btn-primary font_14' onclick=goPrev('"+res.strPrevController+"','"+res.encPrevCategoryId+"')><span  aria-hidden='true'><< Prev</span></button></div>";
				mainDiv+="<div class='padded pull-right'><button type='button' class='btn btn-primary font_14' onclick=showPreview('"+res.categoryType+"')><span aria-hidden='true'>Preview</span></button></div>";
}			else if(!res.strPrevController && !res.strNextController){
			mainDiv+="<div class='padded pull-right'><button type='button' class='btn btn-primary font_14' onclick=showPreview('"+res.categoryType+"')><span aria-hidden='true'>Preview</span></button></div>";

}
			
			
			$("#mainPrevNext").append(mainDiv);
			
		},
		error : function(e) {
			
			
		}


	
	});
	
})
function goPrev(requestURL,categId)
{
	var encParentId=$('#encMonthlyProgressId').val();
	var categoryId=categId;
	if(requestURL){
		openWindowWithPost('GET','/PMS/'+requestURL,{"encMonthlyProgressId":encParentId,"encCategoryId":categoryId},'_self');
		}else{
			console.log("ÚRL Not mapped");
		}
}
function goNext(requestURL,categId)
{
	var encParentId=$('#encMonthlyProgressId').val();
	var categoryId=categId;
	if(requestURL){
		openWindowWithPost('GET','/PMS/'+requestURL,{"encMonthlyProgressId":encParentId,"encCategoryId":categoryId},'_self');
		}else{
			console.log("ÚRL Not mapped");
		}
}

function backToMainPage(encProjectId, encCategoryId,encGroupId){
	if(encProjectId){
		window.location.href= "/PMS/projectDetails/"+encProjectId+"/"+encCategoryId;
	}
}
function showPreview(type){
	
	var encParentId=$('#encMonthlyProgressId').val();
	if(encParentId){
		if(type=="G"){
			
			openWindowWithPost('POST','/PMS/getGroupPreviewDetails',{"encMonthlyProgressId":encParentId},'_self');

		}
		else if(type=="P"){
			
			openWindowWithPost('POST','/PMS/getPreviewDetails',{"encMonthlyProgressId":encParentId},'_self');
	
			}
		}
	else{
			console.log("ÚRL Not mapped");
		}
}

$(document).ready(function(){
	$('#previewDetailsBtn').click(function(){
		var encProgressId=$('#encMonthlyProgressId').val();
		openWindowWithPost('POST','/PMS/getPreviewDetails',{"encMonthlyProgressId":encProgressId},'_self');
	});
	
	$('.previewDetailsBtn').click(function(){
		var encProgressId=$('#encMonthlyProgressId').val();
		openWindowWithPost('POST','/PMS/getPreviewDetails',{"encMonthlyProgressId":encProgressId},'_self');
	});
});
