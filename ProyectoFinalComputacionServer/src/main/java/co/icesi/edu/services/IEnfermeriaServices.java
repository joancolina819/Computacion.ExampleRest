package co.icesi.edu.services;
import java.util.Date;

import java.util.List;

import co.icesi.edu.model.Medicamento;
import co.icesi.edu.model.Paciente;
import co.icesi.edu.model.Siministro;
import co.icesi.edu.model.AtencionUrgencias;
import co.icesi.edu.model.InvetarioMedicamento;


public interface IEnfermeriaServices {

	public InvetarioMedicamento agreegarInventario(Integer cantidad, String localizacion, Date date, String medicamento);
	public Siministro EntregarMedicamentoAPaciente(Long IdentificadorMedicina, String IdentificadorPaciente, int CantidadMedicamento, String patologiaPaciente, String observaciones, Date date) throws Exception;
	
	public AtencionUrgencias agregarAtencionUrgencias(String descripcionGeneral,String procedimientoRealizado, String Remitido, String observaciones, String LugarDeRemision, String identificadorPaciente );
	
	public List<AtencionUrgencias> obtenerAtencionesUrgenciasPorFecha(Date date);
	public List<Medicamento> obtenerTodosMedicamentos();
	public List<Siministro> obtenerTodosSuministros();
	public List<AtencionUrgencias> obtenerAtencionPorFecha(Date date);
	public List<Siministro> obtenerSuministroPorFecha(Date date);
	public List<Siministro> obtenerMedicamentoEntregadosPorFecha(Date date);
	
	public List<Paciente> obtenerTodosPacientes();
	
	
	
	
	
	
	
	
}
