function toogleNav(){
	var sideNav=document.getElementById("sideNavContent").style.width;
	if(sideNav=="0px"){
		openNav();
	}
	else{
		closeNav();
	}
}

function openNav() {
    document.getElementById("sideNavContent").style.width = "250px";
    document.getElementById("noteContainer").style.marginLeft = "250px";
}


function closeNav() {
    document.getElementById("sideNavContent").style.width = "0px";
    document.getElementById("noteContainer").style.marginLeft = "0px";
}