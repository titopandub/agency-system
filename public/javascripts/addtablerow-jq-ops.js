// from http://www.mediacollege.com/internet/javascript/number/round.html
function roundNumber(number,decimals) {
  var newString;// The new rounded number
  decimals = Number(decimals);
  if (decimals < 1) {
    newString = (Math.round(number)).toString();
  } else {
    var numString = number.toString();
    if (numString.lastIndexOf(".") == -1) {// If there is no decimal point
      numString += ".";// give it one at the end
    }
    var cutoff = numString.lastIndexOf(".") + decimals;// The point at which to truncate the number
    var d1 = Number(numString.substring(cutoff,cutoff+1));// The value of the last decimal place that we'll end up with
    var d2 = Number(numString.substring(cutoff+1,cutoff+2));// The next decimal, after the last one we want
    if (d2 >= 5) {// Do we need to round up at all? If not, the string will just be truncated
      if (d1 == 9 && cutoff > 0) {// If the last digit is 9, find a new cutoff point
        while (cutoff > 0 && (d1 == 9 || isNaN(d1))) {
          if (d1 != ".") {
            cutoff -= 1;
            d1 = Number(numString.substring(cutoff,cutoff+1));
          } else {
            cutoff -= 1;
          }
        }
      }
      d1 += 1;
    } 
    if (d1 == 10) {
      numString = numString.substring(0, numString.lastIndexOf("."));
      var roundedNum = Number(numString) + 1;
      newString = roundedNum.toString() + '.';
    } else {
      newString = numString.substring(0,cutoff) + d1.toString();
    }
  }
  if (newString.lastIndexOf(".") == -1) {// Do this again, to the new string
    newString += ".";
  }
  var decs = (newString.substring(newString.lastIndexOf(".")+1)).length;
  for(var i=0;i<decimals-decs;i++) newString += "0";
  //var newNumber = Number(newString);// make it a number if you like
  return newString; // Output the result to the form field (change for your purposes)
}

function update_total() {
    var total = 0;
    $('.add-cost').each(function(i){
        price = $(this).val();
        if (!isNaN(price)) total += Number(price);
    });
	total = roundNumber(total,2);
    $('#subtotal').html(total);
}

function update_total_approval() {
    var total_approval = 0;
    $('.add-cost').each(function(i){
        price = $(this).html();
        if (!isNaN(price)) total_approval += Number(price);
    });
	total_approval = roundNumber(total_approval,2);
    $('#subtotal').html(total_approval);
}

function update_total_port() {
    var total_port = 0;
    $('.port-cost').each(function(i){
        price = $(this).html();
        if (!isNaN(price)) total_port += Number(price);
    });
	total_port = roundNumber(total_port,2);
    $('#subtotal-port').html(total_port);
}

function update_grandtotal() {
    var total = 0,
		subtotalPort = $('#subtotal-port').html(), 
		subtotalAdd = $('#subtotal').html();
	total = Number(subtotalPort) + Number(subtotalAdd);
	total = roundNumber(total,2);
	$('#total').html(total);
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
						grt: val.grt,
						owner: val.owner
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
					if(typeof val.owner != "undefined") {
						$("#ownerName").val(val.owner.name);
						$("#ownerPIC").val(val.owner.pic);
						$("#ownerEmail").val(val.owner.email);
					}
				}
			});
		}
    });
	
	var owner = [];
    $("#ownerName").autocomplete({
        source: function(req, add) {
			$.getJSON("/listOwner", req, function(data) {
				var suggestions = [];
				owner = [];
				$.each(data, function(i, val) {
					suggestions.push(val.name);
					owner.push({
						name: val.name,
						pic: val.pic,
						email: val.email
					});
				});
				add(suggestions);
			});
		},
		minLength: 2,
		select: function(event, ui) {
			$.each(owner, function(i, val) {
				if(val.name == ui.item.label) {
					$("#ownerPIC").val(val.pic);
					$("#ownerEmail").val(val.email);
				}
			});
		}
    });
	
	update_total();
	if($('.add-cost').html() != 0) {
		update_total_approval();
	}
	update_total_port();
	update_grandtotal();
	
	
    if ($(".delete").length < 2) $(".delete").hide();
    
    $('#addbutton').click(function() {
        var i = $('.item-row').length;
        var myElement = '<tr class="item-row">';
        myElement += '<td><span class="delete-wpr"><input type="text" name="additional['+i+'].name" value="" class="add-name" /><a href="javascript:;" class="delete">x</a></span></td>';
        myElement += '<td><input type="text" name="additional['+i+'].cost" value="" class="add-cost" /></td></tr>';
        $('.item-row:last').after(myElement);
        if ($(".delete").length > 0) $(".delete").show();
    });
    
    $('#calculate').click(function() {
        update_total();
		update_total_port();
		update_grandtotal();
    });
    
    $('.delete').live('click',function() {
        $(this).parents('.item-row').remove();
        if ($(".delete").length < 2) $(".delete").hide();
        $('.item-row').each(function(j) {
            $(this).find('input[name*=name]').attr("name",'additional['+j+'].name');
            $(this).find('input[name*=cost]').attr("name",'additional['+j+'].cost');
        });
        update_total();
    });
});