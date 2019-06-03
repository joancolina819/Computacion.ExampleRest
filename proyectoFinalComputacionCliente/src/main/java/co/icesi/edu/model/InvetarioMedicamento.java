package co.icesi.edu.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
public class InvetarioMedicamento {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NonNull
	private Integer quantityAvailable;
	@NonNull
	private String  location;
	@NonNull
	private Date expirationDate;
	
	//Referencia con medicamento
	@NonNull
	@ManyToOne(cascade = {CascadeType.ALL})
	private Medicamento medicine;
	
	//Esto soluciona error extraño
	@Override
	public String toString() {
		return "";
	}

}
