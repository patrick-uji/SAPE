package es.uji.ei1027.sape.domain;
import java.util.List;
import es.uji.ei1027.sape.dto.PreferenciaAlumnoDTO;
public class PreferencesContainer
{
	private List<Integer> selectedPreferences;
	private List<PreferenciaAlumnoDTO> preferences;
	public List<PreferenciaAlumnoDTO> getPreferences()
	{
		return preferences;
	}
	public void setPreferences(List<PreferenciaAlumnoDTO> preferences)
	{
		this.preferences = preferences;
	}
	public List<Integer> getSelectedPreferences()
	{
		return selectedPreferences;
	}
	public void setSelectedPreferences(List<Integer> selectedPreferences)
	{
		this.selectedPreferences = selectedPreferences;
	}
	public boolean isEmpty()
	{
		return preferences.isEmpty();
	}
}
