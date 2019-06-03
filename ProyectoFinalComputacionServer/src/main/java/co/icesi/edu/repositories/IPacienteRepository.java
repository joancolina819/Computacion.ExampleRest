package co.icesi.edu.repositories;

import org.springframework.data.repository.CrudRepository;

import co.icesi.edu.model.Paciente;


public interface IPacienteRepository extends CrudRepository<Paciente, String>{
	
}
