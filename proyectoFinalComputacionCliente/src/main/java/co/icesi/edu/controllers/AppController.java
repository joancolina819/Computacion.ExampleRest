package co.icesi.edu.controllers;

import java.util.ArrayList;




import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import co.icesi.edu.model.FechaAux;
import co.icesi.edu.model.InvetarioMedicamento;
import co.icesi.edu.model.Medicamento;
import co.icesi.edu.model.Paciente;
import co.icesi.edu.model.Siministro;
import co.icesi.edu.model.Usuario;
import co.icesi.edu.model.AtencionUrgencias;

@Controller
public class AppController {

//	@Autowired
//	private IUserRepository iUserRepository;
	@Autowired
	private Delegado delegado;


	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	@PostMapping("/login")
	public String getLoginUser() {
		return "login";
	}
	
	@GetMapping("/")
	public String getHome() {
		return "index";
	}

//	@PostConstruct
//	private void inicializarValorPrueba() {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		Usuario usuario1 = new Usuario();
//		usuario1.setName("JOAN");
//		usuario1.setLastName("COLINA");
//		usuario1.setLogin("JOANCOLINA819");
//		usuario1.setPassword(passwordEncoder.encode("ONEPIECE1"));
//		usuario1.setState("Activo");
//		usuario1.setPatient("1144105457");
//		
//		Paciente paciente1 = new Paciente();
//		paciente1.setName(usuario1.getName());
//		paciente1.setLastName(usuario1.getLastName());
//		paciente1.setUser((long)1);
//		paciente1.setIdDocument("1144105457");
//		paciente1.setSupplies(new ArrayList<Siministro>());		
//		
//		
//		iUserRepository.save(usuario1);
//	}	
//	
	
	//----------------------------------------------------------------------------------------------------------------------
	//ENTREGAR MEDICAMENTOS
	//----------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("/entregarMedicamento")
	public String getEntregaMedicamento(Model model) {
		model.addAttribute("medicamentos", delegado.obtenerTodosMedicamentos());
		Siministro suministro = new Siministro();
		suministro.setDate(new Date());
		model.addAttribute("suministro", suministro );
		return "agregarMedicamento";
	}
	
	@PostMapping("/entregarMedicamento")
	public String guardarEntregaMedicamento(@Valid Siministro suministro, BindingResult bindingResult, Model model) {
			if (bindingResult.hasErrors()) {
				System.out.println(suministro);
				model.addAttribute("medicamentos", delegado.obtenerTodosMedicamentos());
				Siministro suministro1 = new Siministro();
				suministro1.setDate(new Date());
				model.addAttribute("suministro", suministro1 );
				return "agregarMedicamento";
			}else {
				try {
					delegado.EntregarMedicamentoAPaciente(suministro.getMedicine().getId(), suministro.getPatient().getIdDocument(), suministro.getQuantity(), suministro.getPathology(), suministro.getObservation(), suministro.getDate());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					}										
					return "index";					
			}

	//----------------------------------------------------------------------------------------------------------------------
	//LISTAR MEDICAMENTOS
	//----------------------------------------------------------------------------------------------------------------------

	@GetMapping("/listarEntregaMedicamentos")
	public String getListarMedicamentoEntregados(Model model) {
		List<Siministro> suministros = new ArrayList<Siministro>();
		FechaAux commandDate = new FechaAux();
		model.addAttribute("commandDate", commandDate);
		model.addAttribute("suministros", suministros );
		return "listarMedicamentos";
	}


	@PostMapping("/listarEntregaMedicamentos")
	public String getListarMedicamentoEntregadosPost(@Valid FechaAux commandDate, BindingResult bindingResult, Model model) {
		Date date = commandDate.getDate();
		List<Siministro> suministros = (List<Siministro>) delegado.obtenerSuministroPorFecha(date);
		model.addAttribute("commandDate", commandDate);
		model.addAttribute("suministros", suministros );
		return "listarMedicamentos";
	}

	
	//----------------------------------------------------------------------------------------------------------------------
	//REGISTRAR ATENCION PACIENTE
	//----------------------------------------------------------------------------------------------------------------------

	@GetMapping("/registrarAtencion")
	  public String getRegistrarAtencion(Model model) {
			
		AtencionUrgencias urgencyAttention = new AtencionUrgencias();
		urgencyAttention.setDate(new Date());
		urgencyAttention.setSupplies(new ArrayList<Siministro>());
		model.addAttribute("atencionUrgencias", urgencyAttention );
	    return "agregarAtencion";
	  }
	
	@PostMapping("/registrarAtencion")
	public String guardarRegistroAtencionUrgencias(@Valid AtencionUrgencias urgencyAttention, BindingResult bindingResult, Model model) {

			if (bindingResult.hasErrors()) {
				model.addAttribute("atencionUrgencias", urgencyAttention );
				return "agregarAtencion";
			}else 
				delegado.agregarAtencionUrgencias(	 urgencyAttention.getGeneralDescription(),  urgencyAttention.getProcedurePerformed(), urgencyAttention.getForwarded(), urgencyAttention.getObservations(),  urgencyAttention.getPlaceOfReferral(),  urgencyAttention.getPatient().getIdDocument());

				return "index";					
			
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//LISTAR ATENCIONES PACIENTES
	//----------------------------------------------------------------------------------------------------------------------

	
	@GetMapping("/listarAtenciones")
	  public String getListarAtenciones(Model model) {	
		List<AtencionUrgencias> atenciones = new ArrayList<AtencionUrgencias>();
		FechaAux commandDate = new FechaAux();
		model.addAttribute("commandDate", commandDate);
		model.addAttribute("atenciones", atenciones );
	    return "listarAtenciones";
	  }
	
	@PostMapping("/listarAtenciones")
	public String getListarAtencionPost(@Valid FechaAux commandDate, BindingResult bindingResult, Model model) {		
		Date date = commandDate.getDate();
		List<AtencionUrgencias> atenciones = delegado.obtenerAtencionPorFecha(date);
		model.addAttribute("commandDate", commandDate);
		model.addAttribute("atenciones", atenciones );
		return "listarAtenciones";
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//AGREGAR INVENTARIOS
	//----------------------------------------------------------------------------------------------------------------------

	
	@GetMapping("/gestionInventariosMedicamento")
	public String getGestionInventario(Model model) {
		model.addAttribute("medicamentos", delegado.obtenerTodosMedicamentos());
		InvetarioMedicamento inventarioMedicamento = new InvetarioMedicamento();
		model.addAttribute("inventarioMedicamento", inventarioMedicamento );
		return "gestionInventarios";
	}
	
	@PostMapping("/gestionInventariosMedicamentos")
	public String getGestionInventarioPOST(@Valid InvetarioMedicamento inventarioMedicamento, BindingResult bindingResult, Model model) {
		System.out.println("ENTROoooooooooooooooo: "+inventarioMedicamento.getLocation());
		delegado.agregarInventarioMedicamento(""+inventarioMedicamento.getQuantityAvailable(), inventarioMedicamento.getLocation(), inventarioMedicamento.getExpirationDate(), inventarioMedicamento.getMedicine().getName());
		return "index";
	}
	

	//----------------------------------------------------------------------------------------------------------------------
	//LISTAR INVENTARIOS
	//----------------------------------------------------------------------------------------------------------------------


		@GetMapping("/gestionInventariosMedicamentoListar")
		public String getGestionInventarioListar(Model model) {
			
			List<InvetarioMedicamento> inventarioMedicamento = new ArrayList<InvetarioMedicamento>();
			model.addAttribute("listInventario", inventarioMedicamento );
			return "gestionInventarioEdit";
		}
		
//		@PostMapping("/gestionInventariosMedicamentoListar")
//		public String getGestionInventarioListarPOST(@Valid InvetarioMedicamento inventarioMedicamento, BindingResult bindingResult, Model model) {
//			System.out.println("ENTROoooooooooooooooo: "+inventarioMedicamento.getLocation());
//			delegado.agregarInventarioMedicamento(""+inventarioMedicamento.getQuantityAvailable(), inventarioMedicamento.getLocation(), inventarioMedicamento.getExpirationDate(), inventarioMedicamento.getMedicine().getName());
//			return "index";
//		}
}
