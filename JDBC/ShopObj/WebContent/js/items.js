$(document).ready(function(){
	builderIndex();
	calculateMoney();
	$("#addItemButton").click(
		function(){
			$("#items").children("div").last().after($("#items").children("div").first().clone());
			$("#items").children("div").find("button").click(
				function(){
					$(this).parent().parent().remove();
					if($("#items").children("div").size()==1){
						$("#items").find("button").attr("disabled",true);
					}
					builderIndex();
					calculateMoney();
				}
			);
			$("#items").find("button").attr("disabled",false);
			builderIndex();	
			$(".money").change(
				function(){
					calculateMoney();
				}
			);
			calculateMoney();
		}
	);
	$(".money").change(
		function(){
			calculateMoney();
		}
	);
});// JavaScript Document

function builderIndex(){
	$.each($("#items").children(),function(i,val){
		$("#items").children("div").eq(i).children().eq(0).find("select").attr("name","items["+i+"].item");		
		$("#items").children("div").eq(i).children().eq(1).find("input").attr("name","items["+i+"].amount");				
		$("#items").children("div").eq(i).children().eq(2).find("input").attr("name","items["+i+"].comment");
				
	});	
}
function calculateMoney(){
	var totalMoney=0;
	$.each($(".money"),function(i,val){
		totalMoney+=parseFloat($(".money").eq(i).val());
	});
	$("#totalMoney").attr("value",totalMoney);
}