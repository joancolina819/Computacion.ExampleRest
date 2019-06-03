package co.icesi.edu.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.icesi.edu.model.AtencionUrgencias;
import co.icesi.edu.model.InvetarioMedicamento;
import co.icesi.edu.model.Medicamento;
import co.icesi.edu.model.Siministro;

@Service
public class Delegado {

	@Autowired
	private RestTemplate rt;
	
	

	//-------------------------------------------------------------------------------------------------------------------------------------------------------
	//ATENCION
	//-------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public List<AtencionUrgencias> agregarAtencionUrgencias(String Descripcion,  String procedimientoRealizado ,String remitido, String observaciones,  String lugarRemision,  String identificacion) {
		
		
		ResponseEntity<List<AtencionUrgencias>> response = rt.exchange(
				"http://localhost:8080/registrarAtencion/"+Descripcion+"/"+procedimientoRealizado+"/"+remitido+"/"+observaciones+"/"+lugarRemision+"/"+identificacion,
				HttpMethod.POST,
				null,
				new ParameterizedTypeReference<List<AtencionUrgencias>>(){});
		
		return response.getBody();
	}
	
	public List<AtencionUrgencias> obtenerAtencionPorFecha(Date date) {
		String dateAux= date+"";
	//System.out.println(date.toString()+" Lucho");
		ResponseEntity<List<AtencionUrgencias>> response = rt.exchange(
				  "http://localhost:8080/listarAtenciones/"+dateAux,
				  HttpMethod.POST,
				  null,
				  new ParameterizedTypeReference<List<AtencionUrgencias>>(){});
				List<AtencionUrgencias> atencionUrgencias = response.getBody();
		//AtencionUrgencias resultado = rt.getForObject("http://localhost:8080/listarAtenciones", AtencionUrgencias.class);

		return atencionUrgencias;
	}

	//-------------------------------------------------------------------------------------------------------------------------------------------------------
	//MEDICAMENTO
	//-------------------------------------------------------------------------------------------------------------------------------------------------------

	public List<Siministro> obtenerSuministroPorFecha(Date date){
		String dateAux= date+"";
		ResponseEntity<List<Siministro>> response = rt.exchange(
				  "http://localhost:8080/listarEntregaMedicamentos/"+dateAux,
				  HttpMethod.POST,
				  null,
				  new ParameterizedTypeReference<List<Siministro>>(){});
				List<Siministro> siministros = response.getBody();
		return siministros;
	}
	
	public List<Medicamento> obtenerTodosMedicamentos() {
	
		ResponseEntity<List<Medicamento>> response = rt.exchange(
				  "http://localhost:8080/entregarMedicamento/",
				  HttpMethod.GET,
				  null,
				  new ParameterizedTypeReference<List<Medicamento>>(){});
				List<Medicamento> Medicamentos = response.getBody();
			
				return Medicamentos;
				
	}
	
	
	public List<Siministro> EntregarMedicamentoAPaciente(Long id, String documento, Integer cantidad, String patologia, String obsrvaciones, Date date) {
			ResponseEntity<List<Siministro>> response = rt.exchange(
					  "http://localhost:8080/entregarMedicamentoAPaciente/"+id+"/"+documento+"/"+cantidad+"/"+patologia+"/"+obsrvaciones+"/"+date,
					  HttpMethod.POST,
					  null,
					  new ParameterizedTypeReference<List<Siministro>>(){});
			
			List<Siministro> medicamentos = response.getBody();
			
			return medicamentos;
		}

	public List<InvetarioMedicamento> agregarInventarioMedicamento(String cantidad, String location, Date date, String medicamento){
		String dateAux=""+date;
		ResponseEntity<List<InvetarioMedicamento>> response = rt.exchange(
				  "http://localhost:8080/agregarInventario/"+cantidad+"/"+location+"/"+dateAux+"/"+medicamento,
				  HttpMethod.POST,
				  null,
				  new ParameterizedTypeReference<List<InvetarioMedicamento>>(){});
		
		List<InvetarioMedicamento> medicamentos = response.getBody();
		return medicamentos;
	}

	
	
	
	@Bean
	public RestTemplate resTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
