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