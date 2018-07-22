// Validação do formulário
(function() {
	'use strict';
	window.addEventListener('load', function() {
		var forms = document.getElementsByClassName('needs-validation');
		var validation = Array.prototype.filter.call(forms, function(form) {
			form.addEventListener('submit', function(event) {
				if (form.checkValidity() === false) {
					event.preventDefault();
					event.stopPropagation();
				}
				form.classList.add('was-validated');
			}, false);
		});
	}, false);
})();
// Funcionamento do Sidebar
$(document).ready(function() {
	$("#sidebar").mCustomScrollbar({
		theme : "minimal"
	});
	$('#sidebarCollapse').on('click', function() {
		$('#sidebar,#sidebarCollapse').toggleClass('active');
		$('.collapse.show').toggleClass('show');
		$('a[aria-expanded=true]').attr('aria-expanded', 'false');
	});
	if ($('header').width() < 768 ){
		$('#sidebarCollapse').click();
 }
});
//Modal de excluir
$('#modalExcluir').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget)
	  var idObj = button.data('idobj') // Lê info dos atributos data-*
	  var obs = button.data('obs')
	  var modal = $(this)
	  modal.find('form #idObj').val(idObj)
	  modal.find('.modal-body span').text(obs)
	  
	});
//Accordion no select + Action do formulário + desativar campos
$('select[name=accordion-select]').change(function(){
	var opcao = $( "select[name=accordion-select] option:selected" );
    var target = $(opcao).data('target');
    var parent = $(opcao).data('parent');
    $(parent).find(':input').prop('disabled',true);
    $(target).find(':input').prop('disabled',false);
    $(parent).collapse('hide');
    $(target).collapse('show');
    var action = $(opcao).data('form-action');
    var form = $(opcao).data('form');
    $(form).attr('action',action);
}).trigger('change');