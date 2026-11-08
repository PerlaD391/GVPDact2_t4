package mx.culturavibe.act2.servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mx.culturavibe.act2.dto.EventoDTO;
import mx.culturavibe.act2.modelo.Evento;

@Service
public class EventoService {

	private final List<Evento> eventos = new ArrayList<>(List.of(
			new Evento("ev-1", "Concierto Sinfónico de Gala",
					LocalDate.of(2026, 11, 12), "20:00", "Palacio de Bellas Artes",
					"Av. Juárez s/n, Centro Histórico",
					"Ciudad de México", "CDMX", "Música", "$450", "onsale",
					"Gala sinfónica con obras de compositores mexicanos e invitados internacionales.",
					List.of("Orquesta Sinfónica Nacional"),
					"https://www.ticketmaster.com.mx"),
			new Evento("ev-2", "Festival Internacional de Cine",
					LocalDate.of(2026, 11, 22), "18:00", "Conjunto Santander",
					"Av. Periférico Norte 1695",
					"Guadalajara", "JAL", "Cine", "$180", "onsale",
					"Proyecciones de cine nacional e internacional con funciones al aire libre.",
					List.of("Cineastas nacionales e internacionales"),
					"https://www.ticketmaster.com.mx"),
			new Evento("ev-3", "Noche de Jazz en el Barrio Antiguo",
					LocalDate.of(2026, 11, 25), "21:00", "Barrio Antiguo",
					"Calle Morelos 300, Barrio Antiguo",
					"Monterrey", "NL", "Música", "$250", "onsale",
					"Una noche de jazz en vivo con músicos locales y artistas invitados.",
					List.of("Jazz Quartet MX"),
					"https://www.ticketmaster.com.mx"),
			new Evento("ev-4", "Exposición de Arte Contemporáneo",
					LocalDate.of(2026, 11, 28), "11:00", "Museo Amparo",
					"Av. 2 Oriente 607, Centro Histórico",
					"Puebla", "PUE", "Arte", "Gratis", "onsale",
					"Muestra de arte contemporáneo de creadores mexicanos.",
					List.of("Artistas contemporáneos mexicanos"),
					"https://www.ticketmaster.com.mx"),
			new Evento("ev-5", "Feria del Libro",
					LocalDate.of(2026, 12, 2), "10:00", "Centro Cultural",
					"Av. Morelos Sur 100, Centro",
					"Cuernavaca", "MOR", "Libros", "Gratis", "onsale",
					"Venta y presentación de libros con autores invitados y talleres.",
					List.of("Autores invitados"),
					"https://www.ticketmaster.com.mx"),
			new Evento("ev-6", "Festival de Teatro Clásico",
					LocalDate.of(2026, 12, 5), "19:00", "Teatro Principal",
					"Av. Costera Miguel Alemán 100",
					"Acapulco", "GRO", "Teatro", "$320", "onsale",
					"Puesta en escena de clásicos del teatro universal.",
					List.of("Compañía Nacional de Teatro"),
					"https://www.ticketmaster.com.mx")
	));

	public List<Evento> listar() {
		return eventos.stream()
				.sorted(Comparator.comparing(Evento::fecha))
				.toList();
	}

	public Optional<Evento> buscarPorId(String id) {
		return eventos.stream()
				.filter(evento -> evento.id().equalsIgnoreCase(id))
				.findFirst();
	}

	public List<Evento> porEstado(String estado) {
		return listar().stream()
				.filter(evento -> evento.estado().equalsIgnoreCase(estado))
				.toList();
	}

	public List<Evento> porCategoria(String categoria) {
		return listar().stream()
				.filter(evento -> evento.categoria().equalsIgnoreCase(categoria))
				.toList();
	}

	public Evento proximo() {
		LocalDate hoy = LocalDate.now();
		return listar().stream()
				.filter(evento -> !evento.fecha().isBefore(hoy))
				.findFirst()
				.orElse(listar().get(0));
	}

	public List<Evento> proximosEnDias(int dias) {
		LocalDate hoy = LocalDate.now();
		LocalDate limite = hoy.plusDays(dias);
		return listar().stream()
				.filter(evento -> !evento.fecha().isBefore(hoy)
						&& !evento.fecha().isAfter(limite))
				.toList();
	}

	public Evento agregar(EventoDTO dto) {
		String id = (dto.id() == null || dto.id().isBlank())
				? "ev-" + (eventos.size() + 1)
				: dto.id();
		Evento evento = dto.toEvento(id);
		eventos.add(evento);
		return evento;
	}
}
