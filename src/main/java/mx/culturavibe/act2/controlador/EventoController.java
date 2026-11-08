package mx.culturavibe.act2.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mx.culturavibe.act2.dto.EventoDTO;
import mx.culturavibe.act2.modelo.Evento;
import mx.culturavibe.act2.servicio.EventoService;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

	private final EventoService eventoService;

	public EventoController(EventoService eventoService) {
		this.eventoService = eventoService;
	}

	@GetMapping
	public List<Evento> listar() {
		return eventoService.listar();
	}

	@GetMapping("/{id}")
	public Evento porId(@PathVariable String id) {
		return eventoService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "No existe un evento con id " + id));
	}

	@GetMapping("/estado/{estado}")
	public List<Evento> porEstado(@PathVariable String estado) {
		return eventoService.porEstado(estado);
	}

	@PostMapping
	public ResponseEntity<Evento> crear(@RequestBody EventoDTO dto) {
		Evento creado = eventoService.agregar(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}
}
