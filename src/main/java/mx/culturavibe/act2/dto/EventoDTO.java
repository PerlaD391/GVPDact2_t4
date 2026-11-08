package mx.culturavibe.act2.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import mx.culturavibe.act2.modelo.Evento;

public record EventoDTO(
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
		String artistas,
		String url
) {

	public static EventoDTO vacio() {
		return new EventoDTO(null, "", null, "", "", "", "", "", "", "", "onsale", "", "", "");
	}

	public static EventoDTO desde(Evento evento) {
		return new EventoDTO(
				evento.id(),
				evento.nombre(),
				evento.fecha(),
				evento.hora(),
				evento.sede(),
				evento.direccion(),
				evento.ciudad(),
				evento.estado(),
				evento.categoria(),
				evento.precio(),
				evento.estadoVenta(),
				evento.descripcion(),
				String.join(", ", evento.artistas()),
				evento.url()
		);
	}

	public Evento toEvento(String id) {
		List<String> listaArtistas = (artistas == null || artistas.isBlank())
				? List.of()
				: Arrays.stream(artistas.split(","))
						.map(String::trim)
						.filter(nombre -> !nombre.isEmpty())
						.toList();
		return new Evento(
				id,
				nombre,
				fecha,
				hora,
				sede,
				direccion,
				ciudad,
				estado,
				categoria,
				precio,
				estadoVenta,
				descripcion,
				listaArtistas,
				url
		);
	}
}
