var flag=false//全局变量
function show_menu(){
		var title1=document.getElementById("title");
		if(flag){
			title1.style.display="none";
			flag=false;
		}else{
			title1.style.display="block";
			flag=true;
		}
}
function show_menu1(){
		var title1=document.getElementById("title");
			title1.style.display="none";
			flag=false;
}
