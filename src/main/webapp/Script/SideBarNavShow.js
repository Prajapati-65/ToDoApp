function toogleNav() {
	var sideNav = document.getElementById("sideNavContent").style.width;
	if (sideNav == "0px") {
		openNav();
	} else {
		closeNav();
	}
}

function openNav() {
	
	if(screen.width>1000){
		document.getElementById("sideNavContent").style.width = "300px";
		document.getElementById("noteContainer").style.marginLeft = "300px";
	} else
		document.getElementById("sideNavContent").style.width = "300px";
}

function closeNav() {
	document.getElementById("sideNavContent").style.width = "0px";
	document.getElementById("noteContainer").style.marginLeft = "0px";
}

var cleanUp = function() {
	document.getElementById("ab").value = '';
}