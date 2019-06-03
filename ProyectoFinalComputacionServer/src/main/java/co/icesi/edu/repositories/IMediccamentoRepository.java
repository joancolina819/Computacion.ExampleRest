package co.icesi.edu.repositories;

import org.springframework.data.repository.CrudRepository;

import co.icesi.edu.model.Medicamento;
import co.icesi.edu.model.Paciente;
import co.icesi.edu.model.Siministro;



public interface IMediccamentoRepository extends CrudRepository<Medicamento, Long>{
	
//	public boolean crearMedicamento(Medicamento medicamento);
//	public Medicamento existenciaMedicamento(Long consegutivo);
//	public Medicamento disponibilidadMedicamento(Long consecutivo, int cantidad);
//	public Siministro dispensarMedicamento(Medicamento medicamento,Paciente paciente, int cantidad, String descripcion);
//	public Long generarConsecutivoMedicamento();
//	public Long generarConsecutivoSuministro();
//	public Siministro verificarSuministro(Long consecutivo);
//	public void actualizarInventario(Medicamento medicamento, int cantidad);
//	public void actualizarMedicamento(Medicamento medicamento);
//	public boolean insertarSuministro(Siministro suministro);
}
