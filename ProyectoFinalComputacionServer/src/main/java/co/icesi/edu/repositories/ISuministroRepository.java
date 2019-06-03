package co.icesi.edu.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.icesi.edu.model.Siministro;


public interface ISuministroRepository extends CrudRepository<Siministro, Long>{
	
	public List<Siministro> findByDate(Date date);
	
}
