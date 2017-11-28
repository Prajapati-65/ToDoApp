function toggleSideBar() {
	var sideNav = document.getElementById("sideToggle").style.width;
	if (sideNav == "0px") {
		openNav();
	} else {
		closeNav();
	}
}

function openNav() {
	document.getElementById("sideToggle").style.width = "250px";
	document.getElementById("noteContainer").style.marginLeft = "250px";
}

 
function closeNav() {
	document.getElementById("sideToggle").style.width = "0px";
	document.getElementById("noteContainer").style.marginLeft = "0px";
}
