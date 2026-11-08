package mx.culturavibe.act2.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mx.culturavibe.act2.dto.EventoDTO;
import mx.culturavibe.act2.modelo.Evento;
import mx.culturavibe.act2.servicio.EventoService;

@Controller
@RequestMapping("/eventos")
public class EventoVistaController {

	private final EventoService eventoService;

	@Value("${culturavibe.nombre}")
	private String nombreProyecto;

	@Value("${culturavibe.autora}")
	private String autora;

	public EventoVistaController(EventoService eventoService) {
		this.eventoService = eventoService;
	}

	private void datosComunes(Model model) {
		model.addAttribute("nombreProyecto", nombreProyecto);
		model.addAttribute("autora", autora);
	}

	@GetMapping
	public String lista(@RequestParam(required = false) String categoria, Model model) {
		List<Evento> eventos = (categoria == null || categoria.isBlank())
				? eventoService.listar()
				: eventoService.porCategoria(categoria);
		List<String> categorias = eventoService.listar().stream()
				.map(Evento::categoria)
				.distinct()
				.sorted()
				.toList();
		model.addAttribute("eventos", eventos);
		model.addAttribute("categorias", categorias);
		model.addAttribute("categoriaSeleccionada", categoria);
		datosComunes(model);
		return "eventos/lista";
	}

	@GetMapping("/{id}")
	public String detalle(@PathVariable String id, Model model) {
		return eventoService.buscarPorId(id)
				.map(evento -> {
					model.addAttribute("evento", evento);
					datosComunes(model);
					return "eventos/detalle";
				})
				.orElseGet(() -> {
					model.addAttribute("eventos", eventoService.listar());
					model.addAttribute("categorias", eventoService.listar().stream()
							.map(Evento::categoria)
							.distinct()
							.sorted()
							.toList());
					model.addAttribute("error", "No existe un evento con id " + id);
					datosComunes(model);
					return "eventos/lista";
				});
	}

	@GetMapping("/nuevo")
	public String formulario(Model model) {
		model.addAttribute("eventoDTO", EventoDTO.vacio());
		datosComunes(model);
		return "eventos/formulario";
	}

	@PostMapping
	public String guardar(@ModelAttribute EventoDTO eventoDTO, Model model) {
		Evento creado = eventoService.agregar(eventoDTO);
		model.addAttribute("evento", creado);
		datosComunes(model);
		return "eventos/creado";
	}
}
