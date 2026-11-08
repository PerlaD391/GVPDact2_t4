package mx.culturavibe.act2.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.culturavibe.act2.modelo.Integrante;

@RestController
@RequestMapping("/api/proyecto")
public class ProyectoController {

	@GetMapping
	public Map<String, String> proyecto() {
		return Map.of(
				"proyecto", "CulturaVibe",
				"iniciales", "GVPD",
				"integrante", "Perla Danae Gallardo Vasquez",
				"equipo", "13",
				"materia", "Programación Web",
				"actividad", "Act1. Introducción a Spring Boot"
		);
	}

	@GetMapping("/integrantes")
	public List<Integrante> integrantes() {
		return List.of(
				new Integrante("GVPD", "Perla Danae Gallardo Vasquez", "Desarrolladora"),
				new Integrante("LSR", "Ricardo Lemus Sánchez", "Desarrollador")
		);
	}

	@GetMapping("/saludo")
	public String saludo(@RequestParam(defaultValue = "visitante") String nombre) {
		return "¡Hola, " + nombre + "! Bienvenida a CulturaVibe.";
	}
}
