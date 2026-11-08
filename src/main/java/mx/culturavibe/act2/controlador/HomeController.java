package mx.culturavibe.act2.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String inicio() {
		return "redirect:/eventos";
	}
}
