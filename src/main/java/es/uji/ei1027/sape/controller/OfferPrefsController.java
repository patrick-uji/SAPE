package es.uji.ei1027.sape.controller;
import java.util.List;
import java.util.HashSet;
import java.util.Collections;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.model.Usuario;
import org.springframework.stereotype.Controller;
import es.uji.ei1027.sape.domain.OffersSelection;
import es.uji.ei1027.sape.model.PreferenciaAlumno;
import es.uji.ei1027.sape.dto.PreferenciaAlumnoDTO;
import es.uji.ei1027.sape.dao.PreferenciaAlumnoDao;
import es.uji.ei1027.sape.domain.PreferencesContainer;
import es.uji.ei1027.sape.dao.PreferenciaAlumnoDTODao;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
@Controller
@RequestMapping("/offerPrefs")
public class OfferPrefsController
{
	private PreferenciaAlumnoDao preferenciaAlumnoDao;
	private PreferenciaAlumnoDTODao preferenciaAlumnoDTODao;
    @Autowired
    public void setPreferenciaAlumnoDao(PreferenciaAlumnoDao preferenciaAlumnoDao)
    {
    	this.preferenciaAlumnoDao = preferenciaAlumnoDao;
    }
    @Autowired
    public void setPreferenciaAlumnoDTODao(PreferenciaAlumnoDTODao preferenciaAlumnoDTODao)
    {
    	this.preferenciaAlumnoDTODao = preferenciaAlumnoDTODao;
    }
	@RequestMapping
	public String list(HttpSession session, Model model)
	{
		Utils.debugLog("OfferPrefs LIST");
		Usuario user = Utils.getUser(session);
		if (user.esEstudiante())
		{
			PreferencesContainer preferencesContainer = new PreferencesContainer();
			preferencesContainer.setPreferences( preferenciaAlumnoDTODao.getAllFromStudent(user.getId()) );
			model.addAttribute("preferencesContainer", preferencesContainer);
			return "offers/listPref";
		}
		else
		{
			return "error/401";
		}
	}
	@RequestMapping(value="", method=RequestMethod.POST)
	public String create(@ModelAttribute("offersSelection") OffersSelection offersSelection, HttpSession session)
	{
		Utils.debugLog("OfferPrefs CREATE");
		if (Utils.isStudent(session))
		{
			int studentID = Utils.getUser(session).getId();
			PreferenciaAlumno newPreference = new PreferenciaAlumno();
			newPreference.setOrden(preferenciaAlumnoDao.countFromStudent(studentID));
			newPreference.setAbierta(true);
			newPreference.setFechaUltimoCambio(Utils.now());
			newPreference.setIDEstudiante(studentID);
			for (Integer selectedOfferID : offersSelection.getSelectedOffers())
			{
				newPreference.incrementOrden();
				newPreference.setIDOfertaProyecto(selectedOfferID);
				Utils.debugLog("Selected offer with ID: " + selectedOfferID);
				preferenciaAlumnoDao.create(newPreference);
			}
			return "redirect:offers";
		}
		else
		{
			return "error/401";
		}
	}
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@ModelAttribute("preferencesContainer") PreferencesContainer preferencesContainer, HttpSession session)
	{
		Utils.debugLog("OfferPrefs UPDATE");
		if (Utils.isStudent(session))
		{
			List<PreferenciaAlumnoDTO> preferences = preferencesContainer.getPreferences();
			if (hasValidOrdering(preferences))
			{
				Collections.sort(preferences);
				deleteSelectedPreferences(preferences, preferencesContainer.getSelectedPreferences());
				for (PreferenciaAlumnoDTO currPreference : preferences)
				{
					preferenciaAlumnoDao.update(currPreference.getId(), new String[] {"orden"}, currPreference.getOrden());
				}
				return "redirect:../offerPrefs";
			}
			else
			{
				return "redirect:../offerPrefs";
			}
		}
		else
		{
			return "error/401";
		}
	}
	private boolean hasValidOrdering(List<PreferenciaAlumnoDTO> preferences)
	{
		int currPreferenceOrder;
		HashSet<Integer> seenOrders = new HashSet<Integer>();
		for (PreferenciaAlumnoDTO currPreference : preferences)
		{
			currPreferenceOrder = currPreference.getOrden();
			if (currPreferenceOrder > preferences.size())
			{
				//ERROR: Order must be in range!
				Utils.debugLog("Order must be in range!");
				return false;
			}
			else if (seenOrders.contains(currPreferenceOrder))
			{
				//ERROR: Orders cannot be repeated!
				Utils.debugLog("ERROR: Orders cannot be repeated!");
				return false;
			}
			else
			{
				Utils.debugLog("Selected Preference with ID " + currPreference.getId() + " will now have order: " + currPreference.getOrden());
				seenOrders.add(currPreferenceOrder);
			}
		}
		return true;
	}
	private void deleteSelectedPreferences(List<PreferenciaAlumnoDTO> preferences, List<Integer> selectedPreferences)
	{
		int minIndex = preferences.size();
		PreferenciaAlumnoDTO currPreference;
		for (Integer currSelectedPreferenceID : selectedPreferences)
		{
			for (int currPreferenceIndex = 0; currPreferenceIndex < preferences.size(); currPreferenceIndex++)
			{
				currPreference = preferences.get(currPreferenceIndex);
				if (currPreference.getId() == currSelectedPreferenceID)
				{
					preferenciaAlumnoDao.delete(currPreference.getId());
					preferences.remove(currPreferenceIndex);
					if (currPreferenceIndex < minIndex)
					{
						minIndex = currPreferenceIndex;
					}
					break;
				}
			}
		}
		for (int currPreferenceIndex = minIndex; currPreferenceIndex < preferences.size(); currPreferenceIndex++)
		{
			preferences.get(currPreferenceIndex).setOrden(currPreferenceIndex + 1);
		}
	}
}
