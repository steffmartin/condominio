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
		$('#sidebar .collapse.show').toggleClass('show');
		$('#sidebar a[aria-expanded=true]').attr('aria-expanded', 'false');
	});
	if ($('header').width() < 768 ){
		$('#sidebarCollapse').click();
 }
});

//Remover placeholder nas telas somente leitura
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
});

// Botão para deletar linha em lista de formulário
$('.form-list table').on('click','button.delete',function(){
	element = $(this).closest($(this).data('delete'));
	oldId = element.attr('id');
	oldId = oldId.replace('row','');
	maior = oldId;
	element.parent().children(':not(.d-none):not(#row'+oldId+')').each(function(){
		esteId = $(this).attr('id');
		esteId = esteId.replace('row','');
		if(esteId > maior){
			maior = esteId;
		}
	});
	if(maior > oldId){
		last = $('#row'+maior);
		last.attr('id',"row"+oldId);
		last.find(':input').each(function(){
			name = $(this).attr('name');
			id = $(this).attr('id');
			if(name != null){
				$(this).attr('name',name.replace('['+maior+']','['+oldId+']'));
			}
			if(id != null){
				$(this).attr('id',id.replace(maior+'.',oldId+'.'));
			}
		});
	}
	element.collapse('hide');
	element.remove();
});

//Botão para incluir linha em lista de formulário
$('button.clone').on('click',function(){
	parent = $(this).data('parent');
	example = $(this).data('example');
	clone = $(example).clone();
	maior = -1;
	$(parent).children(':not(.d-none)').each(function(){
		esteId = $(this).attr('id');
		esteId = esteId.replace('row','');
		if(esteId > maior){
			maior = esteId;
		}
	});
	maior++;	
	clone.attr('id','row'+maior);
	clone.find(':input').each(function(){
		$(this).prop('disabled',false);
		name = $(this).attr('name');
		id = $(this).attr('id');
		if(name != null){
			$(this).attr('name',name.replace('[]','['+maior+']'));
		}
		if(id != null){
			$(this).attr('id',id.replace('.',maior+'.'));
		}
	});
	clone.removeClass("d-none");
	clone.appendTo($(parent));
	clone.collapse('show');
});

// Cálculo de campo totalizador, usar classes calc-add, calc-sub, calc-tot
$('input.calc-add,input.calc-sub').on('change keyup', function(){
	var tot = 0;
	$('input.calc-add').each(function(){
		tot += parseFloat($(this).val());
	});
	$('input.calc-sub').each(function(){
		tot -= parseFloat($(this).val());
	});
	$('input.calc-tot').val(tot.toFixed(2));
});