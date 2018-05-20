package es.uji.ei1027.sape.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class MainController
{
	@RequestMapping({"", "/", "/index"})
	public String index()
	{
		return "index";
	}
	@RequestMapping("/privacy")
	public String privacy()
	{
		return "privacy";
	}
	@RequestMapping("/contact")
	public String contact()
	{
		return "contact";
	}
	@RequestMapping("/about")
	public String about()
	{
		return "about";
	}
}
