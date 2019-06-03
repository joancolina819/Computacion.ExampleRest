package co.icesi.edu.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
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
public class Siministro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Min(value= 1, message="Debe ingresar la cantidad de medicamento suministrado")
	@NotNull(message="Debe ingresar la cantidad de medicamento suministrado")
	private Integer quantity;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "Escoja una fecha pasada")
	@NotNull(message="Ingrese una fecha")
	@NonNull
	private Date date;
	
	@NotBlank(message="Debe indicar la patologia")
	@NonNull
	private String pathology;
	private String observation;
	
	
	//Referencia a paciente
	@NonNull
	@NotNull(message="Seleccione un paciente")
	@ManyToOne(cascade = {CascadeType.ALL})
	private Paciente patient;
	
	//Referencia a medicamento
	@NotNull(message="Seleccione un medicamento")
	@ManyToOne(cascade = {CascadeType.ALL})
	private Medicamento medicine;
	
	@Override
	public String toString() {
		return "";
	}
}
