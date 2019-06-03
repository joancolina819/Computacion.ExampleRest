package co.icesi.edu;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import co.icesi.edu.controllers.Delegado;
import co.icesi.edu.model.AtencionUrgencias;
import co.icesi.edu.model.InvetarioMedicamento;
import co.icesi.edu.model.Medicamento;
import co.icesi.edu.model.Paciente;
import co.icesi.edu.model.Siministro;

/**
 * @author Joan David
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestRest {

	@InjectMocks
	private Delegado delegate = new Delegado();

	@Mock
	private RestTemplate rest;

	private AtencionUrgencias atenciona1;
	private AtencionUrgencias atenciona2;
	private AtencionUrgencias atenciona3;
	private Paciente paciente1;
	private Paciente paciente2;
	private Paciente paciente3;
	private Medicamento medicamento1;
	private Medicamento medicamento2;
	private Medicamento medicamento3;
	private Siministro suministro1;
	private Siministro suministro2;
	private Siministro suministro3;
	private InvetarioMedicamento inventario1;
	private InvetarioMedicamento inventario2;
	private Date date;

	@Before
	public void init() {

		date = new Date();
		paciente1 = new Paciente();
		paciente1.setName("Luis Fernando");
		paciente1.setLastName("Cruces Vidal");
		paciente1.setIdDocument("1116275221");

		paciente2 = new Paciente();
		paciente2.setName("Joan David");
		paciente2.setLastName("Colina Echevery");
		paciente2.setIdDocument("1144105457");

		paciente3 = new Paciente();
		paciente3.setName("Maria Fernanda");
		paciente3.setLastName("Tenorio Arcila");
		paciente3.setIdDocument("11111111");

		atenciona1 = new AtencionUrgencias();
		atenciona1.setDate(new Date());
		atenciona1.setForwarded("SI");
		atenciona1.setGeneralDescription("ATENCION PARA PESO");
		atenciona1.setObservations("NN");
		atenciona1.setPatient(paciente1);
		atenciona1.setPlaceOfReferral("Cali");
		atenciona1.setProcedurePerformed("procedimientoREaliado");
		atenciona1.setDate(date);

		atenciona2 = new AtencionUrgencias();
		atenciona2.setDate(new Date());
		atenciona2.setForwarded("SI");
		atenciona2.setGeneralDescription("ATENCION PARA PESO 2");
		atenciona2.setObservations("NN 2");
		atenciona2.setPatient(paciente2);
		atenciona2.setPlaceOfReferral("Cali 2");
		atenciona2.setProcedurePerformed("procedimientoREaliado 2");
		atenciona2.setDate(date);

		atenciona3 = new AtencionUrgencias();
		atenciona3.setDate(new Date());
		atenciona3.setForwarded("SI");
		atenciona3.setGeneralDescription("ATENCION PARA PESO 3");
		atenciona3.setObservations("NN 3");
		atenciona3.setPatient(paciente3);
		atenciona3.setPlaceOfReferral("Cali 3");
		atenciona3.setProcedurePerformed("procedimientoREaliado 3");
		atenciona3.setDate(date);

		medicamento1 = new Medicamento();
		medicamento1.setId(new Long(1));
		medicamento1.setName("PARACETAMOL 125G");
		medicamento1.setGenericName("PARACETAMOL");
		medicamento1.setLaboratory("CALI");
		medicamento1.setAdministrationType("ORAL");
		medicamento1.setContraindications("NN");
		medicamento1.setIndications("NN");

		medicamento2 = new Medicamento();
		medicamento2.setId(new Long(2));
		medicamento2.setName("ACETAMINOFEN 125G");
		medicamento2.setGenericName("ACETAMINOFEN");
		medicamento2.setLaboratory("CALI");
		medicamento2.setAdministrationType("ORAL");
		medicamento2.setContraindications("NN");
		medicamento2.setIndications("NN");

		medicamento3 = new Medicamento();
		medicamento3.setId(new Long(3));
		medicamento3.setName("APRONAX 125G");
		medicamento3.setGenericName("APRONAX");
		medicamento3.setLaboratory("CALI");
		medicamento3.setAdministrationType("ORAL");
		medicamento3.setContraindications("NN");
		medicamento3.setIndications("NN");

		suministro1 = new Siministro();
		suministro1.setMedicine(medicamento1);
		suministro1.setObservation("Observaciones");
		suministro1.setPathology("Patologia");
		suministro1.setPatient(paciente1);
		suministro1.setQuantity(2);
		suministro1.setDate(date);

		suministro2 = new Siministro();
		suministro2.setMedicine(medicamento2);
		suministro2.setObservation("Observaciones");
		suministro2.setPathology("Patologia");
		suministro2.setPatient(paciente2);
		suministro2.setQuantity(2);
		suministro2.setDate(date);

		suministro3 = new Siministro();
		suministro3.setMedicine(medicamento3);
		suministro3.setObservation("Observaciones");
		suministro3.setPathology("Patologia");
		suministro3.setPatient(paciente3);
		suministro3.setQuantity(2);
		suministro3.setDate(date);
		
		
		inventario1= new InvetarioMedicamento();
		inventario1.setExpirationDate(new Date());
		inventario1.setLocation("AQUI");
		inventario1.setMedicine(medicamento1);
		inventario1.setQuantityAvailable(2);
		
//		inventario2.setExpirationDate(new Date());
//		inventario2.setLocation("AQUI22");
//		inventario2.setMedicine(medicamento2);
//		inventario2.setQuantityAvailable(3);
//		

	}

//	@Test
//	public void testAgregarAtencion() {
//		List<AtencionUrgencias> atenciones = new ArrayList<AtencionUrgencias>();
//		atenciones.add(atenciona1);
//		Mockito.when(rest.exchange("http://localhost:8080/registrarAtencion/Atencion/Revision/SI/NN/Cali/1116275221",
//				HttpMethod.POST, null, new ParameterizedTypeReference<List<AtencionUrgencias>>() {
//				})).thenReturn(new ResponseEntity<List<AtencionUrgencias>>(atenciones, HttpStatus.OK));
//
//		atenciones = delegate.agregarAtencionUrgencias("Atencion", "Revision", "SI", "NN", "Cali", "1116275221");
//		assertEquals(1, atenciones.size());
//		assertTrue(atenciones.contains(atenciona1));
//	}

	@Test
	public void testAgregarMedicamento() {
		List<Siministro> medicamento = new ArrayList<Siministro>();
		medicamento.add(suministro1);
		Mockito.when(rest.exchange(
				"http://localhost:8080/entregarMedicamentoAPaciente/1/11144105457/3/patologia/Observaciones/" + date,
				HttpMethod.POST, null, new ParameterizedTypeReference<List<Siministro>>() {
				})).thenReturn(new ResponseEntity<List<Siministro>>(medicamento, HttpStatus.OK));

		medicamento = delegate.EntregarMedicamentoAPaciente(new Long(1), "11144105457", 3, "patologia", "Observaciones",
				date);
		assertEquals(1, medicamento.size());
		assertTrue(medicamento.contains(suministro1));
	}
	
	@Test
	public void testAgregarInventario() {
		List<InvetarioMedicamento> inventario = new ArrayList<InvetarioMedicamento>();
		inventario.add(inventario1);
		Mockito.when(rest.exchange(
				  "http://localhost:8080/agregarInventario/"+inventario1.getQuantityAvailable()+"/"+inventario1.getLocation()+"/"+inventario1.getExpirationDate()+"/"+inventario1.getMedicine().getName(),
				  HttpMethod.POST,
				  null,
				  new ParameterizedTypeReference<List<InvetarioMedicamento>>(){})).thenReturn(new ResponseEntity<List<InvetarioMedicamento>>(inventario, HttpStatus.OK));

		inventario = delegate.agregarInventarioMedicamento(""+inventario1.getQuantityAvailable(), inventario1.getLocation(), inventario1.getExpirationDate(), inventario1.getMedicine().getName());
		assertEquals(1, inventario.size());
		assertTrue(inventario.contains(inventario1));
	}

	@Test
	public void testFiltrarAtencionPorFecha() {
		List<AtencionUrgencias> atenciones = new ArrayList<AtencionUrgencias>();
		atenciones.add(atenciona1);
		atenciones.add(atenciona2);
		atenciones.add(atenciona3);
		Mockito.when(rest.exchange("http://localhost:8080/listarAtenciones/" + date, HttpMethod.POST, null,
				new ParameterizedTypeReference<List<AtencionUrgencias>>() {
				})).thenReturn(new ResponseEntity<List<AtencionUrgencias>>(atenciones, HttpStatus.OK));

		atenciones = delegate.obtenerAtencionPorFecha(date);

		assertTrue(atenciones.size() == 3);
		assertTrue(atenciones.contains(atenciona1));
		assertTrue(atenciones.contains(atenciona2));
		assertTrue(atenciones.contains(atenciona3));
	}

	@Test
	public void testObtenerTodosMedicamentos() {
		List<Medicamento> medicamentos = new ArrayList<Medicamento>();
		medicamentos.add(medicamento1);
		medicamentos.add(medicamento2);
		// medicamentos.add(medicamento3);
		Mockito.when(rest.exchange("http://localhost:8080/entregarMedicamento/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Medicamento>>() {
				})).thenReturn(new ResponseEntity<List<Medicamento>>(medicamentos, HttpStatus.OK));

		medicamentos = delegate.obtenerTodosMedicamentos();

		assertTrue(medicamentos.size() == 2);
		assertTrue(medicamentos.contains(medicamento1));
		assertTrue(medicamentos.contains(medicamento2));
		assertTrue(!medicamentos.contains(medicamento3));
	}

	@Test
	public void testListarSuministroByDate() {
		List<Siministro> suministros = new ArrayList<Siministro>();
		suministros.add(suministro1);
		suministros.add(suministro2);

		Mockito.when(rest.exchange("http://localhost:8080/listarEntregaMedicamentos/" + date, HttpMethod.POST, null,
				new ParameterizedTypeReference<List<Siministro>>() {
				})).thenReturn(new ResponseEntity<List<Siministro>>(suministros, HttpStatus.OK));
		suministros = delegate.obtenerSuministroPorFecha(date);
		assertTrue(suministros.size() == 2);
		assertTrue(suministros.contains(suministro1));
		assertTrue(suministros.contains(suministro2));
		assertTrue(!suministros.contains(suministro3));
	}

}
