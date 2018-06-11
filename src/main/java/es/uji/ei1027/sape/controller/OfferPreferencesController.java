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
import es.uji.ei1027.sape.dao.dto.PreferenciaAlumnoDTODao;
import es.uji.ei1027.sape.domain.PreferencesContainer;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
@Controller
@RequestMapping("/offerPreferences")
public class OfferPreferencesController
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
		Utils.debugLog("OfferPreferences LIST");
		Usuario user = Utils.getUser(session);
		if (user.esAlumno())
		{
			PreferencesContainer preferencesContainer = new PreferencesContainer();
			preferencesContainer.setPreferences( preferenciaAlumnoDTODao.getAllFromStudent(user.getId()) );
			model.addAttribute("preferencesContainer", preferencesContainer);
			return "students/offers/listPreferences";
		}
		return "error/401";
	}
	@RequestMapping(value="", method=RequestMethod.POST)
	public String create(@ModelAttribute("offersSelection") OffersSelection offersSelection, HttpSession session)
	{
		Utils.debugLog("OfferPreferences CREATE");
		if (Utils.isStudent(session))
		{
			int studentID = Utils.getUser(session).getId();
			PreferenciaAlumno newPreference = new PreferenciaAlumno();
			newPreference.setOrden(preferenciaAlumnoDao.countFromStudent(studentID));
			newPreference.setFechaUltimoCambio(Utils.now());
			newPreference.setIDAlumno(studentID);
			for (Integer selectedOfferID : offersSelection.getSelectedOffers())
			{
				newPreference.incrementOrden();
				newPreference.setIdOfertaProyecto(selectedOfferID);
				Utils.debugLog("Selected offer with ID: " + selectedOfferID);
				preferenciaAlumnoDao.create(newPreference);
			}
			return "redirect:offers";
		}
		return "error/401";
	}
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@ModelAttribute("preferencesContainer") PreferencesContainer preferencesContainer, HttpSession session)
	{
		Utils.debugLog("OfferPreferences UPDATE");
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
			}
			return "redirect:../offerPreferences";
		}
		return "error/401";
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