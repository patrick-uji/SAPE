package es.uji.ei1027.sape.controller;
import es.uji.ei1027.sape.Utils;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import es.uji.ei1027.sape.domain.Login;
import es.uji.ei1027.sape.model.Usuario;
import es.uji.ei1027.sape.dao.UsuarioDao;
import org.springframework.stereotype.Controller;
import es.uji.ei1027.sape.validation.LoginValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
@Controller
public class LoginController
{
	private UsuarioDao usuarioDao;
	@Autowired
	public void setUsuarioDao(UsuarioDao usuarioDao)
	{
		this.usuarioDao = usuarioDao;
	}
	@RequestMapping("/login")
	public String view(HttpSession session, Model model)
	{
		Utils.debugLog("Login VIEW");
		Usuario user = Utils.getUser(session);
		if (user == null)
		{
			model.addAttribute("login", new Login());
			return "login";
		}
		else
		{
			Utils.debugLog("User already logged in. Redirecting to dashboard...");
			return "redirect:users/dashboard";
		}
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String create(@ModelAttribute("login") Login login, HttpSession session, BindingResult bindingResult)
	{
		Utils.debugLog("Login CREATE");
		if (Utils.validate(new LoginValidator(), login, bindingResult))
		{
			Usuario user = usuarioDao.get(login.getEmail());
			if( user != null && Utils.hashPassword(login.getPassword()).equals(user.getPassword()) )
			{
				session.setAttribute("user", user);
				return "redirect:users/dashboard";
			}
			else
			{
				bindingResult.rejectValue("password", "badpw", "Contraseña invalida");
			}
		}
		return "login";
	}
	@RequestMapping("/logout")
	public String delete(HttpSession session)
	{
		Utils.debugLog("Login DELETE");
		session.invalidate();
		return "redirect:index";
	}
}
