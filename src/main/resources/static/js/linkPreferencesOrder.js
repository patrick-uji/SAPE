function handleOrderChange(orderSelect)
{
	var selectedIndex = orderSelect.selectedIndex;
	var linkedSelectID = lastOrderIDs[selectedIndex];
	var linkedSelect = document.getElementById(linkedSelectID);
	var lastSelectedIndex = lastIDOrders[orderSelect.id];
	linkedSelect.selectedIndex = lastSelectedIndex;
	lastIDOrders[orderSelect.id] = selectedIndex;
	lastOrderIDs[selectedIndex] = orderSelect.id;
	lastIDOrders[linkedSelectID] = lastSelectedIndex;
	lastOrderIDs[lastSelectedIndex] = linkedSelectID;
}
