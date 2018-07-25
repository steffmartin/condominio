// Validação do formulário no FRONT-END, aplicar a classe 'needs-validation'
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

//Remover placeholder nas telas somente leitura (alterado para CSS "fieldset:disabled input::placeholder")
$(document).ready(function() {
$("fieldset:disabled").find(':input').removeAttr('placeholder');
});

//Modal de excluir com conteúdo e formulário dinâmico
$('#modalExcluir').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget)
	  var idObj = button.data('idobj') // Lê info dos atributos data-*
	  var obs = button.data('obs')
	  var action = button.data('modal-action');
	  var modal = $(this)
	  modal.find('form #idObj').val(idObj)
	  modal.find('.modal-body span').text(obs)
	  if(action != null){
		  modal.find('form').attr('action',action);  
	  }	  
	});

//Accordion usando SELECT com formulário dinâmico
$('select[name=form-accordion-select]').change(function(){
	var form = $(this).data('form');
	var parent = $(this).data('parent');
	var opcao = $( "option:selected", this );
    var target = $(opcao).data('target');
    var action = $(opcao).data('form-action');
    $(parent).find(':input').prop('disabled',true);
    $(target).find(':input').prop('disabled',false);
    $(parent).collapse('hide');
    $(target).collapse('show');
    $(form).attr('action',action);
}).trigger('change');