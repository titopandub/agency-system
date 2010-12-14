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
	element1.name = 'additional[' + rowI + '].name';
	element1.value = "";
	cell1.appendChild(element1);
	
	var cell2 = row.insertCell(2);
	var element2 = document.createElement("input");
	element2.type = "text";
	element2.name = 'additional[' + rowI + '].date';
	element2.value = "";
	cell2.appendChild(element2);
	
	var cell3 = row.insertCell(3);
	var element3 = document.createElement("input");
	element3.type = "text";
	element3.name = 'additional[' + rowI + '].cost';
	element3.value = "";
	cell3.appendChild(element3);
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