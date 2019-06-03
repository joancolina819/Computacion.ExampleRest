package co.icesi.edu.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
public class AtencionUrgencias {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "Escoja una fecha pasada")
	@NotNull(message="Ingrese una fecha")
	@NonNull
	private Date date;
	
	@NotBlank(message = "Debe dar una descripcion general")
	@NonNull
	private String generalDescription;
	@NotBlank(message = "Debe indicar el procedimiento realizado")
	@NonNull
	private String procedurePerformed;
	
	@NonNull
	private String forwarded;
	
	@NotBlank(message = "Debe indicar alguna observacion")
	@NonNull
	private String observations;
	
	private String placeOfReferral;
	
	//Referencia a Suministros
	@OneToMany
	private List<Siministro> supplies;
	
	//Referencia a Paciente
	@NonNull
	@ManyToOne
	private Paciente patient;
}
