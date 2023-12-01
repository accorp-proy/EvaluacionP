PrimeFaces.locales['es'] = {
	closeText : 'Cerrar',
	prevText : 'Anterior',
	nextText : 'Siguiente',
	monthNames : [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
			'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre',
			'Diciembre' ],
	monthNamesShort : [ 'Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago',
			'Sep', 'Oct', 'Nov', 'Dic' ],
	dayNames : [ 'Domingo', 'Lunes', 'Martes', 'Mi�rcoles', 'Jueves',
			'Viernes', 'S�bado' ],
	dayNamesShort : [ 'Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab' ],
	dayNamesMin : [ 'D', 'L', 'M', 'Mi', 'J', 'V', 'S' ],
	weekHeader : 'Semana',
	firstDay : 1,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : '',
	timeOnlyTitle : 'S�lo hora',
	timeText : 'Tiempo',
	hourText : 'Hora',
	minuteText : 'Minuto',
	secondText : 'Segundo',
	currentText : 'Fecha actual',
	ampm : false,
	month : 'Mes',
	week : 'Semana',
	day : 'D�a',
	allDayText : 'Todo el d�a'
};

// **JS EFFECTS**//
$(function() {
	var links = $('.sidebar-links > div');
	links.on('click', function() {
		links.removeClass('selected');
		$(this).addClass('selected');

	});
});

// **JS METHODS**//

function doLogin() {

	var username = document.getElementById('login-form:user');
	var password = document.getElementById('login-form:pass');
	if (username.value != '' && password.value != '') {

		$('form').fadeOut(500);
		$('.ui-g').addClass('form-success');
	}
}

function upperCase() {
	var x = document.getElementById("login-form:user").value;
	document.getElementById("login-form:user").value = x.toUpperCase();
}

function onSubmitEvent(e) {

	e = e || window.event;
	if (e.keyCode == 13) {
		var username = document.getElementById('login-form:user');
		var password = document.getElementById('login-form:pass');
		if (username.value != '' && password.value != '') {

			$('form').fadeOut(500);
			$('.ui-g').addClass('form-success');

		} else {
			valid = false;
		}

	}
}

function doGrowl(msg) {
	$.growl.warning({
		message : msg
	});
}
