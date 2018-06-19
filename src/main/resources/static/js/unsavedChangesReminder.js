var unsavedChanges = false;
window.onbeforeunload = function()
{
	if (unsavedChanges)
	{
		return "Hay cambios sin guardar, seguro que quieres salir?";
	}
};
