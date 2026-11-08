package mx.culturavibe.act2.servicio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import mx.culturavibe.act2.modelo.Evento;
import mx.culturavibe.act2.modelo.ProximoEvento;
import mx.culturavibe.act2.modelo.ResumenDashboard;

@Service
public class DashboardService {

	private final EventoService eventoService;

	public DashboardService(EventoService eventoService) {
		this.eventoService = eventoService;
	}

	public ResumenDashboard resumen() {
		Evento proximo = eventoService.proximo();
		List<String> categorias = eventoService.listar().stream()
				.map(Evento::categoria)
				.distinct()
				.toList();
		long totalArtistas = eventoService.listar().stream()
				.flatMap(evento -> evento.artistas().stream())
				.distinct()
				.count();
		List<String> recordatorios = eventoService.proximosEnDias(7).stream()
				.map(evento -> {
					long dias = ChronoUnit.DAYS.between(LocalDate.now(), evento.fecha());
					String cuando = dias == 0 ? "hoy" : "en " + dias + (dias == 1 ? " día" : " días");
					return "El evento '" + evento.nombre() + "' es " + cuando
							+ " (" + evento.ciudad() + ", " + evento.estado() + ").";
				})
				.toList();
		return new ResumenDashboard(
				eventoService.listar().size(),
				(int) totalArtistas,
				categorias,
				proximo,
				recordatorios
		);
	}

	public ProximoEvento proximoEvento() {
		Evento proximo = eventoService.proximo();
		long dias = ChronoUnit.DAYS.between(LocalDate.now(), proximo.fecha());
		return new ProximoEvento(proximo, Math.max(dias, 0));
	}
}
