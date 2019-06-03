package co.icesi.edu.model;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FechaAux {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "Escoja una fecha pasada")
	@NotNull(message="Ingrese una fecha")
	private Date date;
}
