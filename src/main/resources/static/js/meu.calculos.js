// Cálculo de campo totalizador, usar classes calc-add, calc-sub, calc-tot
$('input.calc-add,input.calc-sub').on('change keyup', function(){
	var tot = 0;
	$('input.calc-add').each(function(){
		tot += parseFloat($(this).val() || 0);
	});
	$('input.calc-sub').each(function(){
		tot -= parseFloat($(this).val() || 0);
	});
	$('input.calc-tot').val(tot.toFixed(2));
});