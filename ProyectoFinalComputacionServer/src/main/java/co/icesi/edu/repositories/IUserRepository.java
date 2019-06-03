package co.icesi.edu.repositories;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.icesi.edu.model.Usuario;

public interface IUserRepository extends CrudRepository<Usuario, Long>{
	
	public Optional<Usuario> findByLogin(String login);
	
}
