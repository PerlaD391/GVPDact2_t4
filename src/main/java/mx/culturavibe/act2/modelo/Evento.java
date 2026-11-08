package mx.culturavibe.act2.modelo;

import java.time.LocalDate;
import java.util.List;

public record Evento(
		String id,
		String nombre,
		LocalDate fecha,
		String hora,
		String sede,
		String direccion,
		String ciudad,
		String estado,
		String categoria,
		String precio,
		String estadoVenta,
		String descripcion,
		List<String> artistas,
		String url
) {
}
