package co.icesi.edu.controllers;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import co.icesi.edu.model.AtencionUrgencias;
import co.icesi.edu.model.InvetarioMedicamento;
import co.icesi.edu.model.Medicamento;
import co.icesi.edu.model.Paciente;
import co.icesi.edu.model.Siministro;
import co.icesi.edu.model.Usuario;
import co.icesi.edu.repositories.IInventarioMedicamentoRepository;
import co.icesi.edu.repositories.IMediccamentoRepository;
import co.icesi.edu.repositories.IPacienteRepository;
import co.icesi.edu.repositories.ISuministroRepository;
import co.icesi.edu.repositories.IUserRepository;
import co.icesi.edu.services.IEnfermeriaServices;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	private IPacienteRepository iPacienteRepository;
	
	@Autowired
	private IInventarioMedicamentoRepository iInventarioMedicamentoRepository;
	
	@Autowired
	private IMediccamentoRepository iMedicinaRepository;
	
	@Autowired
	private IUserRepository iUserRepository;
	
	@Autowired
	private ISuministroRepository iSuministroRepository;
	
	
	private IEnfermeriaServices IEnfermeriaServices;
	Paciente paciente1 = new Paciente();
	
	@Autowired
	  public RestController(IEnfermeriaServices IEnfermeriaServices) {
		this.IEnfermeriaServices = IEnfermeriaServices;
	}
	Medicamento medicamento1 = new Medicamento();
	
	@PostConstruct
	private void inicializarValorPrueba() {
	//	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		//Medicamento medicamento1 = new Medicamento();
		medicamento1.setName("MEDICAMENTO1");
		medicamento1.setLaboratory("LABORATORIO DE MEDICAMENTO 1");
		medicamento1.setAdministrationType("INYECCION");
		medicamento1.setContraindications("RES AL DIA");
		medicamento1.setGenericName("MEDICAMENTO GENERICO 1");
		medicamento1.setIndications("EN AYUNAS");
		medicamento1.setSupplies(new ArrayList<Siministro>());
		
			
		InvetarioMedicamento inventario = new InvetarioMedicamento();
		inventario.setLocation("LUGAR DE INVENARIO");
		inventario.setMedicine(medicamento1);
		inventario.setQuantityAvailable(50);
		Date date = new Date(10, 10, 25);		
		inventario.setExpirationDate(date);
		
		ArrayList<InvetarioMedicamento> inventarios = new ArrayList<InvetarioMedicamento>();
		inventarios.add(inventario);
		medicamento1.setInventories(inventarios);
		
		iMedicinaRepository.save(medicamento1);
		iInventarioMedicamentoRepository.save(inventario);	
		
		Usuario usuario1 = new Usuario();
		usuario1.setName("JOAN");
		usuario1.setLastName("COLINA");
		usuario1.setLogin("JOANCOLINA819");
		//usuario1.setPassword(passwordEncoder.encode("ONEPIECE1"));
		usuario1.setState("Activo");
		usuario1.setPatient("1144105457");
		
		Paciente paciente1 = new Paciente();
		paciente1.setName(usuario1.getName());
		paciente1.setLastName(usuario1.getLastName());
		//paciente1.setUser((long)1);
		paciente1.setIdDocument("1144105457");
		paciente1.setSupplies(new ArrayList<Siministro>());		
		
		
		iUserRepository.save(usuario1);
		iPacienteRepository.save(paciente1);
		
		Usuario usuario2 = new Usuario();
		usuario2.setName("JOAN 2");
		usuario2.setLastName("COLINA 2");
		usuario2.setLogin("JOANCOLINA2");
		//usuario2.setPassword(passwordEncoder.encode("ONEPIECE2"));
		usuario2.setState("Activo");
		usuario2.setPatient("1144105456");
		
		Paciente paciente2 = new Paciente();
		paciente2.setName(usuario2.getName());
		paciente2.setLastName(usuario2.getLastName());
		paciente2.setUser((long)2);
		paciente2.setIdDocument("1144105456");		

		iUserRepository.save(usuario2);
		iPacienteRepository.save(paciente2);
//		
//		Siministro suministro1 = new Siministro();
//		suministro1.setDate(new Date());
//		suministro1.setMedicine(medicamento1);
//		suministro1.setObservation("observaciones");
//		suministro1.setPathology("Patologia");
//		suministro1.setPatient(paciente1);
//		suministro1.setQuantity(3);
//		iSuministroRepository.save(suministro1);
		
	}

	@PostMapping("/entregarMedicamento")
	public List<Medicamento> guardarEntregaMedicamento() {	
		return IEnfermeriaServices.obtenerTodosMedicamentos();
	}
	@GetMapping("/entregarMedicamento")
	public List<Medicamento> getEntregaMedicamento() {	
	
		List<Medicamento> result= IEnfermeriaServices.obtenerTodosMedicamentos();
		return result;
	}
	
	@PostMapping("/entregarMedicamentoAPaciente")
	public List<Siministro> EntregarMEdicamentoAPaciente(@PathVariable Long IdentificadorMedicina,@PathVariable String IdentificadorPaciente,@PathVariable Integer CantidadMedicamento, @PathVariable String  patologiaPaciente,@PathVariable String  observaciones,@PathVariable Date  date) {	
		 try {
			 List<Siministro > result = new ArrayList<Siministro>();
			 result.add(IEnfermeriaServices.EntregarMedicamentoAPaciente(IdentificadorMedicina, IdentificadorPaciente, CantidadMedicamento, patologiaPaciente, observaciones, date));
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping("registrarAtencion/{descripcionGeneral}/{procedimientoRealizado}/{Remitido}/{observaciones}/{LugarDeRemision}/{identificadorPaciente}")
	public List<AtencionUrgencias> guardarRegistroAtencionUrgencias(@PathVariable String descripcionGeneral,@PathVariable String procedimientoRealizado,@PathVariable String Remitido, @PathVariable String  observaciones,@PathVariable String  LugarDeRemision,@PathVariable String  identificadorPaciente) {	
		//System.out.println("FUNCIONAAAAAAAAAAAAAA"); 
		List<AtencionUrgencias> respuesta = new ArrayList<AtencionUrgencias>();
		respuesta.add(IEnfermeriaServices.agregarAtencionUrgencias(descripcionGeneral, procedimientoRealizado, Remitido, observaciones, LugarDeRemision, identificadorPaciente));
		//IEnfermeriaServices.agregarAtencionUrgencias(descripcionGeneral, procedimientoRealizado, Remitido, observaciones, LugarDeRemision, identificadorPaciente);
		return respuesta;
	}
	
	
	
	//ESTE METODO FUNCIONA EN LA COMINUCACION
	@PostMapping("listarAtenciones/{date}")
	public List<AtencionUrgencias> getListarAtencionesPost(@PathVariable("date") String date) {	
	
		
		//Sun Jun 02 10:23:35 COT 2019
		String fechaTotal= date.toString();
		int anio= Integer.parseInt(fechaTotal.substring(fechaTotal.length()-4, fechaTotal.length()));
		String mes = fechaTotal.substring(4, 7);
		int dia = Integer.parseInt(fechaTotal.substring(8,10));
		int mess=0;
		switch (mes) {
		case "Jan":
			mess= 01;
			break;
		case "Feb":
			mess= 02;
			break;
		case "Mar":
			mess= 03;
			break;
		case "Apr":
			mess= 04;
			break;
		case "May":
			mess= 05;
			break;
		case "Jun":
			mess= 06;
			break;
		case "Jul":
			mess= 07;
			break;
		case "Aug":
			mess= 8;
			break;
		case "Sep":
			mess= 9;
			break;
		case "Oct":
			mess= 10;
			break;
		case "Nov":
			mess= 11;
			break;
		case "Dec":
			mess= 12;
			break;
		}
		String newDate = anio+"-"+mess+"-"+dia;
			Date nd = new Date(anio, mess, dia);
		
		System.out.println(" AQUIIIII  GFEUF "+nd.toString());

	    Date d= null;

		return IEnfermeriaServices.obtenerAtencionPorFecha(nd);
	}
	
	@PostMapping("listarEntregaMedicamentos/{date}")
	public List<Siministro> obtenerSuministroPorFecha(@PathVariable String date){	
		
	
		String fechaTotal= date.toString();
		int anio= Integer.parseInt(fechaTotal.substring(fechaTotal.length()-4, fechaTotal.length()));
		String mes = fechaTotal.substring(4, 7);
		int dia = Integer.parseInt(fechaTotal.substring(8,10));
		int mess=0;
		switch (mes) {
		case "Jan":
			mess= 01;
			break;
		case "Feb":
			mess= 02;
			break;
		case "Mar":
			mess= 03;
			break;
		case "Apr":
			mess= 04;
			break;
		case "May":
			mess= 05;
			break;
		case "Jun":
			mess= 06;
			break;
		case "Jul":
			mess= 07;
			break;
		case "Aug":
			mess= 8;
			break;
		case "Sep":
			mess= 9;
			break;
		case "Oct":
			mess= 10;
			break;
		case "Nov":
			mess= 11;
			break;
		case "Dec":
			mess= 12;
			break;
		}
		String newDate = anio+"-"+mess+"-"+dia;
			Date nd = new Date(anio, mess, dia);
		
		
			return IEnfermeriaServices.obtenerMedicamentoEntregadosPorFecha(nd);
	}
	

	
	@PostMapping("agregarInventario/{cantidad}/{location}/{date}/{medicamento}")
	public List<InvetarioMedicamento> guardarInventarioMedicamento(@PathVariable String cantidad,@PathVariable String location,@PathVariable String date, @PathVariable String  medicamento) {	
		
		String fechaTotal= date.toString();
		int anio= Integer.parseInt(fechaTotal.substring(fechaTotal.length()-4, fechaTotal.length()));
		String mes = fechaTotal.substring(4, 7);
		int dia = Integer.parseInt(fechaTotal.substring(8,10));
		int mess=0;
		switch (mes) {
		case "Jan":
			mess= 01;
			break;
		case "Feb":
			mess= 02;
			break;
		case "Mar":
			mess= 03;
			break;
		case "Apr":
			mess= 04;
			break;
		case "May":
			mess= 05;
			break;
		case "Jun":
			mess= 06;
			break;
		case "Jul":
			mess= 07;
			break;
		case "Aug":
			mess= 8;
			break;
		case "Sep":
			mess= 9;
			break;
		case "Oct":
			mess= 10;
			break;
		case "Nov":
			mess= 11;
			break;
		case "Dec":
			mess= 12;
			break;
		}
		String newDate = anio+"-"+mess+"-"+dia;
			Date nd = new Date(anio, mess, dia);
		
		Integer cantidadInt = Integer.parseInt(cantidad);
		List<InvetarioMedicamento> respuesta = new ArrayList<InvetarioMedicamento>();
		respuesta.add(IEnfermeriaServices.agreegarInventario(cantidadInt, location, nd, medicamento));
		//IEnfermeriaServices.agregarAtencionUrgencias(descripcionGeneral, procedimientoRealizado, Remitido, observaciones, LugarDeRemision, identificadorPaciente);
		return respuesta;
	}
	
	
	
	
}
