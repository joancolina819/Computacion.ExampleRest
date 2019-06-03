package co.icesi.edu.services;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import co.icesi.edu.model.InvetarioMedicamento;
import co.icesi.edu.model.Medicamento;
import co.icesi.edu.model.Paciente;
import co.icesi.edu.model.Siministro;
import co.icesi.edu.model.AtencionUrgencias;
import co.icesi.edu.repositories.IInventarioMedicamentoRepository;
import co.icesi.edu.repositories.IMediccamentoRepository;
import co.icesi.edu.repositories.IPacienteRepository;
import co.icesi.edu.repositories.ISuministroRepository;
import co.icesi.edu.repositories.IAtencionUrgenciasRepository;
import co.icesi.edu.repositories.IUserRepository;


@Service
public class EnfermeriaServices implements IEnfermeriaServices {
		
	private IPacienteRepository iPacienteRepository;
	private IInventarioMedicamentoRepository iInventarioMedicamentoRepository;
	private ISuministroRepository iSuministroRepository;
	private IAtencionUrgenciasRepository iAtencionUrgenciasRepository;
	private IMediccamentoRepository iMedicinaRepository;
	private IUserRepository iUserRepository;
	
	@Autowired
	public EnfermeriaServices(IAtencionUrgenciasRepository iAtencionUrgenciasRepository, IPacienteRepository iPacienteRepository,ISuministroRepository iSuministroRepository, IMediccamentoRepository iMedicinaRepository,IInventarioMedicamentoRepository iInventarioMedicamentoRepository, IUserRepository iUserRepository) {
		this.iAtencionUrgenciasRepository = iAtencionUrgenciasRepository;
		this.iPacienteRepository = iPacienteRepository;
		this.iSuministroRepository = iSuministroRepository;
		this.iMedicinaRepository = iMedicinaRepository;
		this.iInventarioMedicamentoRepository = iInventarioMedicamentoRepository;
		this.iUserRepository = iUserRepository;
	}
	
	
	@Override
	public Siministro EntregarMedicamentoAPaciente(Long IdentificadorMedicina, String IdentificadorPaciente, int CantidadMedicamento, String patologiaPaciente, String observaciones, Date date) throws Exception {
		//Debemos encontrar al paciente
		Optional<Paciente> pacienteAux = iPacienteRepository.findById(IdentificadorPaciente);
		//Debemos encontrar la medicina
		Optional<Medicamento> medicinaAux = iMedicinaRepository.findById(IdentificadorMedicina);
		Paciente paciente =  pacienteAux.get();		
		Medicamento medicina = medicinaAux.get();
		///Continuar de acuerdo a SuministroRepository
		Siministro ResultadoSuministro = null;
			
		if(cantidadInventarioMedicamentoDisponible(medicina, CantidadMedicamento)) {		
			//Verificamos que existe medicamento en la cantidad necesario y creamos el suminsitro
			ResultadoSuministro = new Siministro();
			ResultadoSuministro.setMedicine(medicina);
			ResultadoSuministro.setObservation(observaciones);
			ResultadoSuministro.setPathology(patologiaPaciente);
			ResultadoSuministro.setPatient(paciente);
			ResultadoSuministro.setQuantity(CantidadMedicamento);
			ResultadoSuministro.setDate(date);
			//Agregamos el suministro al medicamento y al paciente
			paciente.getSupplies().add(ResultadoSuministro);
			medicina.getSupplies().add(ResultadoSuministro);
			//Actualizamos el inventario
			actualizarInventario(medicina,CantidadMedicamento);
			
		}		
		return ResultadoSuministro;
	}
	
	//metodo tomado del trabajo del monitor Joan
	
	private void actualizarInventario(Medicamento medicina, int CantidadMedicamento)	{
		List<InvetarioMedicamento> inventarios = medicina.getInventories();
		List<InvetarioMedicamento> inventarioActualizado = new ArrayList<>();
		Date today = Calendar.getInstance().getTime();
		int CantidadPorRestar = CantidadMedicamento;
		for (InvetarioMedicamento lote : inventarios) {
			if(lote.getExpirationDate().compareTo(today) <= 0) {
				int quantityAvailable = lote.getQuantityAvailable();
				if(CantidadPorRestar >= quantityAvailable) {
					CantidadPorRestar -= quantityAvailable;
					lote.setQuantityAvailable(0);
				}else {
					lote.setQuantityAvailable((quantityAvailable - CantidadPorRestar));
					CantidadPorRestar = 0;
				}
			}
			inventarioActualizado.add(lote);
			if(CantidadPorRestar == 0) {
				break;
			}
		}			
		medicina.setInventories(inventarioActualizado);
		iMedicinaRepository.save(medicina);
		
		
	}

	
	//---------------------
	@Override
	public AtencionUrgencias agregarAtencionUrgencias(String descripcionGeneral, String procedimientoRealizado, String Remitido, String observaciones, String LugarDeRemision, String identificadorPaciente) {
		//Encontramos al paciente
		Optional<Paciente> PacienteAux = iPacienteRepository.findById(identificadorPaciente);
		Paciente patient = PacienteAux.get();		

		//Creamos la atencion		
		AtencionUrgencias newAtencionUrgencias = new AtencionUrgencias();
		newAtencionUrgencias.setDate(new Date());
		newAtencionUrgencias.setForwarded(Remitido);
		newAtencionUrgencias.setGeneralDescription(descripcionGeneral);
		newAtencionUrgencias.setObservations(observaciones);
		newAtencionUrgencias.setPatient(patient);
		newAtencionUrgencias.setPlaceOfReferral(LugarDeRemision);
		newAtencionUrgencias.setProcedurePerformed(procedimientoRealizado);
		newAtencionUrgencias.setSupplies(new ArrayList<Siministro>());
	
		//guardamos la atencion
		iAtencionUrgenciasRepository.save(newAtencionUrgencias);
		
		return newAtencionUrgencias;
	}


	@Override
	public List<Siministro> obtenerMedicamentoEntregadosPorFecha(Date date) {
		return iSuministroRepository.findByDate(date);
	}


	@Override
	public List<AtencionUrgencias> obtenerAtencionesUrgenciasPorFecha(Date date) {
		return iAtencionUrgenciasRepository.findByDate(date);
	}


	//Metodo tomado del trabajo del monitor Joan
	private boolean cantidadInventarioMedicamentoDisponible(Medicamento medicamento, int cantidadMedicamendo)  {	
			int inventarioTotal = 0;
			Date fechaActual = Calendar.getInstance().getTime();
			for (InvetarioMedicamento inventario : medicamento.getInventories()) {
				if(inventario.getExpirationDate().compareTo(fechaActual) <= 0) 
					inventarioTotal += inventario.getQuantityAvailable();
			}
			if(cantidadMedicamendo < inventarioTotal) 
				return true;
			else 
				return false;
				
	}
	
	
	
	public List<Siministro> obtenerSuministroPorFecha(Date date) {
		List<Siministro> suministrosResultado = new ArrayList<Siministro>();
		for (Siministro suministro : iSuministroRepository.findAll()) {	
			if(suministro.getDate().getDate() == date.getDate()) 			
				suministrosResultado.add(suministro);
			
		}
		return suministrosResultado;		
	}
	
	public List<AtencionUrgencias> obtenerAtencionPorFecha(Date date) {
		List<AtencionUrgencias> atenciones = new ArrayList<AtencionUrgencias>();
		for (AtencionUrgencias urgencia : iAtencionUrgenciasRepository.findAll()) {			
			if(urgencia.getDate().getDate() == date.getDate()) 		
				atenciones.add(urgencia);		
		}
		return atenciones;		
	}
	

	@Override
	public List<Paciente> obtenerTodosPacientes() {
		return (List<Paciente>) iPacienteRepository.findAll();
	}

	@Override
	public List<Medicamento> obtenerTodosMedicamentos() {
		return (List<Medicamento>) iMedicinaRepository.findAll();
	}


	@Override
	public List<Siministro> obtenerTodosSuministros() {
		return (List<Siministro>) iSuministroRepository.findAll();
	}


	@Override
	public InvetarioMedicamento agreegarInventario(Integer cantidad, String localizacion, Date date,
			String medicamento) {
		InvetarioMedicamento newInvetario = new InvetarioMedicamento();
		newInvetario.setLocation(localizacion);
		Medicamento m = new Medicamento();
		m.setName(medicamento);
		m.setGenericName(medicamento);
		newInvetario.setMedicine(m);
		newInvetario.setQuantityAvailable(cantidad);
		newInvetario.setExpirationDate(date);
		return newInvetario;
	}

	

}
