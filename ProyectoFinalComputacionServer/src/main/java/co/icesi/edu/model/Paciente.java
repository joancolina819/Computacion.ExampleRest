package co.icesi.edu.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
public class Paciente {

	@Id
	private String idDocument;
	@NonNull
	private String name;
	@NonNull
	private String lastName;
	@NonNull
	private String state;
	
	private String academicProgram;
	private String academicDependence;
	
	//Referencia con suministros
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Siministro> supplies;
	
	//Referencia con Atencion
	@OneToMany(cascade = {CascadeType.ALL})
	private List<AtencionUrgencias> attentions;
	
	//Referencia con usuario
	@NonNull
	private Long user;
	
	@Override
	public String toString() {
		return "";
	}
}
