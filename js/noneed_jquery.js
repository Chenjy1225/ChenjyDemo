
function hasClass(element, classname) {

	if(element.className.match(new RegExp('(\\s|^)' + classname + '(\\s|$)')) != null) {

		return true;
		
	} else {
		
		return false;
		
	}

}

function addClass(element, classname) {

	element.className += " " + classname;

}

function removeClass(element, classname) {

	element.className = element.className.replace(new RegExp('(\\s|^)' + classname + '(\\s|$)'), ' ');

}

function toggleClass(element, classname) {

	if(hasClass(element, classname)) {

		removeClass(element, classname);

	} else {

		addClass(element, classname);

	}

}