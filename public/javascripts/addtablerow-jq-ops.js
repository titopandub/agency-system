function update_total() {
    var total = 0;
    $('.add-cost').each(function(i){
        price = $(this).val();
        if (!isNaN(price)) total += Number(price);
    });
    $('#subtotal').html(total);
}

function update_total_port() {
    var total_port = 0;
    $('.port-cost').each(function(i){
        price = $(this).html();
        if (!isNaN(price)) total_port += Number(price);
    });
    $('#subtotal-port').html(total_port);
}

$(document).ready(function() {
	//attach autocomplete
	var vessel = [];
    $("#vessel").autocomplete({
        source: function(req, add) {
			$.getJSON("/listVessel", req, function(data) {
				var suggestions = [];
				vessel = [];
				$.each(data, function(i, val) {
					suggestions.push(val.name);
					vessel.push({
						name: val.name,
						grt: val.grt
					});
				});
				add(suggestions);
			});
		},
		minLength: 2,
		select: function(event, ui) {
			$.each(vessel, function(i, val) {
				if(val.name == ui.item.label) {
					$("#grt").val(val.grt);
				}
			});
		}
    });
	
	update_total();
	update_total_port();
	
    if ($(".delete").length < 2) $(".delete").hide();
    
    $('#addbutton').click(function() {
        var i = $('.item-row').length;
        var myElement = '<tr class="item-row">';
        myElement += '<td><div class="delete-wpr"><input type="text" name="additional['+i+'].name" value="" class="add-name" /><a href="javascript:;" class="delete">x</a></td>';
        myElement += '<td><input type="text" name="additional['+i+'].date" value="" class="add-date" /></td>';
        myElement += '<td><input type="text" name="additional['+i+'].cost" value="" class="add-cost" /></td></tr>';
        $('.item-row:last').after(myElement);
        if ($(".delete").length > 0) $(".delete").show();
    });
    
    $('#calculate').click(function() {
        update_total();
		update_total_port();
    });
    
    $('.delete').live('click',function() {
        $(this).parents('.item-row').remove();
        if ($(".delete").length < 2) $(".delete").hide();
        $('.item-row').each(function(j) {
            $(this).find('input[name*=name]').attr("name",'additional['+j+'].name');
            $(this).find('input[name*=date]').attr("name",'additional['+j+'].date');
            $(this).find('input[name*=cost]').attr("name",'additional['+j+'].cost');
        });
        update_total();
    });
});