package mx.culturavibe.act2.modelo;

import java.util.List;

public record ResumenDashboard(
		int totalEventos,
		int totalArtistas,
		List<String> categorias,
		Evento proximoEvento,
		List<String> recordatorios
) {
}
