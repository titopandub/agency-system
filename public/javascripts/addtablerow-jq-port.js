
$(document).ready(function() {
    if ($(".delete").length < 2) $(".delete").hide();
    
    $('#addbutton').click(function() {
        var i = $('.item-row').length;
        var myElement = '<tr class="item-row">';
        myElement += '<td><div class="delete-wpr"><input type="text" name="tug['+i+'].minimum" value="" class="tug" /><a href="#" class="delete">x</a></div></td>';
        myElement += '<td><input type="text" name="tug['+i+'].maximum" value="" class="tug" /></td>';
        myElement += '<td><input type="text" name="tug['+i+'].fixed" value="" class="tug" /></td>';
        myElement += '<td><input type="text" name="tug['+i+'].var" value="" class="tug" /></td></tr>';
        $('.item-row:last').after(myElement);
        if ($(".delete").length > 0) $(".delete").show();
    });
    
    $('.delete').live('click',function() {
        $(this).parents('.item-row').remove();
        if ($(".delete").length < 2) $(".delete").hide();
        $('.item-row').each(function(j) {
	        $(this).find('input[name*=minimum]').attr("name",'tug['+j+'].minimum');
            $(this).find('input[name*=maximum]').attr("name",'tug['+j+'].maximum');
            $(this).find('input[name*=fixed]').attr("name",'tug['+j+'].fixed');
            $(this).find('input[name*=var]').attr("name",'tug['+j+'].var');
	    });
    });
});



