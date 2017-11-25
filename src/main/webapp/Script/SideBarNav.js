function toggleSide() {
	var sideNav = document.getElementById("sideToggle").style.width;
	if (sideNav == "0px") {
		document.getElementById("sideToggle").style.width = "250px";
		document.getElementById("noteWrapper").style.marginLeft = "250px";
	} else {
		document.getElementById("sideToggle").style.width = "0px";
		document.getElementById("noteWrapper").style.marginLeft = "0px";
	}
}