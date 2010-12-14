function addRow(tableID) { 
	var table = document.getElementById(tableID);
	
	var rowCount = table.rows.length;
	var rowI = rowCount - 1;
	var row = table.insertRow(rowCount);
	
	var cell0 = row.insertCell(0);
    var element0 = document.createElement("input");
    element0.type = "checkbox";
    cell0.appendChild(element0);
	
	var cell1 = row.insertCell(1);
	var element1 = document.createElement("input");
	element1.type = "text";
	element1.name = 'tug[' + rowI + '].minimum';
	element1.value = "";
	cell1.appendChild(element1);
	
	var cell2 = row.insertCell(2);
	var element2 = document.createElement("input");
	element2.type = "text";
	element2.name = 'tug[' + rowI + '].maximum';
	element2.value = "";
	cell2.appendChild(element2);
	
	var cell3 = row.insertCell(3);
	var element3 = document.createElement("input");
	element3.type = "text";
	element3.name = 'tug[' + rowI + '].fixed';
	element3.value = "";
	cell3.appendChild(element3);
	
	var cell4 = row.insertCell(4);
	var element4 = document.createElement("input");
	element4.type = "text";
	element4.name = 'tug[' + rowI + '].var';
	element4.value = "";
	cell4.appendChild(element4);
}
	
function deleteRow(tableID) {
    try {
		var table = document.getElementById(tableID);
		var rowCount = table.rows.length;
		var i = 0;
		var j = 0;
 
        for(i=0; i<rowCount; i++) {
            var row = table.rows[i];
            var chkbox = row.cells[0].childNodes[0];
            if(null != chkbox && true == chkbox.checked) {
                table.deleteRow(i);
                rowCount--;
                i--;
            }
        }
		
		for(j=0; j<rowCount; j++) {
			var row = table.rows[j];
			var input1 = row.cells[1].childNodes[0];
			var input2 = row.cells[2].childNodes[0];
			var input3 = row.cells[3].childNodes[0];
			var input4 = row.cells[4].childNodes[0];
			var jIndex = j - 1;
			input1.name = 'tug[' + jIndex + '].minimum';
			input2.name = 'tug[' + jIndex + '].maximum';
			input3.name = 'tug[' + jIndex + '].fixed';
			input4.name = 'tug[' + jIndex + '].var';
		}
    } catch(e) {
		alert(e);
    }
}