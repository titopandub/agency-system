function addRow(tableID) { 
	var table = document.getElementById(tableID);
	
	var rowCount = table.rows.length;
	var rowI = rowCount - 1;
	var row = table.insertRow(rowCount);
	
	var cell1 = row.insertCell(0);
	var element1 = document.createElement("input");
	element1.type = "text";
	element1.name = 'tug[' + rowI + '].minimum';
	element1.value = "";
	cell1.appendChild(element1);
	
	var cell2 = row.insertCell(1);
	var element2 = document.createElement("input");
	element2.type = "text";
	element2.name = 'tug[' + rowI + '].maximum';
	element2.value = "";
	cell2.appendChild(element2);
	
	var cell3 = row.insertCell(2);
	var element3 = document.createElement("input");
	element3.type = "text";
	element3.name = 'tug[' + rowI + '].fixed';
	element3.value = "";
	cell3.appendChild(element3);
	
	var cell4 = row.insertCell(3);
	var element4 = document.createElement("input");
	element4.type = "text";
	element4.name = 'tug[' + rowI + '].var';
	element4.value = "";
	cell4.appendChild(element4);
	    
	var cell5 = row.insertCell(4);
	var element5 = document.createElement("input");
	element5.type = "checkbox";
	element5.name = "chkbox";
	element5.value = "";
	cell5.appendChild(element5);
}
	
function deleteRow(tableID) {
    try {
	    var table = document.getElementById(tableID);
	    var rowCount = table.rows.length;
	    var rowI = rowCount - 1;
	    var deletecell = table.deleteRow(rowI);
    } catch(e) {
        alert(e);
    }
}