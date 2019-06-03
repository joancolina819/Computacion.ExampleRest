package co.icesi.edu.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Medicamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NonNull
	private String name;
	@NonNull
	private String genericName;
	@NonNull
	private String laboratory;
	@NonNull
	private String administrationType;
	@NonNull
	private String indications;
	
	private String contraindications;
	
	//Referencia con suministros
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Siministro> supplies;
	@JsonIgnore
	//Referencia con Inventario
	@OneToMany(cascade = {CascadeType.ALL})
	private List<InvetarioMedicamento> inventories;
	
	
}
