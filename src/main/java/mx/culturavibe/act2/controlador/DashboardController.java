package mx.culturavibe.act2.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.culturavibe.act2.modelo.ProximoEvento;
import mx.culturavibe.act2.modelo.ResumenDashboard;
import mx.culturavibe.act2.servicio.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@GetMapping("/resumen")
	public ResumenDashboard resumen() {
		return dashboardService.resumen();
	}

	@GetMapping("/proximo-evento")
	public ProximoEvento proximoEvento() {
		return dashboardService.proximoEvento();
	}
}
