package co.icesi.edu.repositories;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import co.icesi.edu.model.AtencionUrgencias;
import co.icesi.edu.model.Siministro;

public interface IAtencionUrgenciasRepository extends CrudRepository<AtencionUrgencias, Long>{
	
	public List<AtencionUrgencias> findByDate(Date date);
	
//	public boolean agregarAtencionUrgencia(AtencionUrgencias atencion);
//	public AtencionUrgencias buscarAtencionUrgencia(Long consecutivo);
//	public Long generarConsecutivo();
//	public boolean registrarSuministro(AtencionUrgencias atencion, Siministro suministro);
//	public ArrayList<AtencionUrgencias> obtenerTodasAtenciones();
	
}
