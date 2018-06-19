function selectAllCheckboxes(idPrefix, totalIDs, idSuffix, checked)
{
	for (currIndex = 0; currIndex < totalIDs; currIndex++)
	{
		currCheckBox = document.getElementById(idPrefix + currIndex + idSuffix);
		if (!currCheckBox.disabled)
		{
			currCheckBox.checked = checked;
		}
	}
}
