package persona.persona;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TestPersona {
	@Test
	@DisplayName ("¿Es mayor de edad o menor?")
	public void comprobarMayorEdad() {
		Persona menorEdad=new Persona();
		Persona mayorEdad=new Persona();
		menorEdad.setEdad(17);
		mayorEdad.setEdad(19);
		assertTrue(mayorEdad.esMayorDeEdad(), "La persona 1 no mayor de edad");
		assertFalse(menorEdad.esMayorDeEdad(), "La persona 2 es mayor de edad");
	}
	@Test
	@DisplayName ("¿Tiene peso ideal?")
	public void comprobarPesoIdeal() {
		Persona pesoIdeal=new Persona();
		pesoIdeal.setPeso(55);
		pesoIdeal.setAltura(1.55);
		assertEquals(0, pesoIdeal.calcularIMC(), "La persona no tiene peso ideal");
	}
	@Test
	@DisplayName ("¿Tiene sobrepeso")
	public void comprobarSobrepeso() {
		Persona sobrePeso=new Persona();
		sobrePeso.setPeso(90);
		sobrePeso.setAltura(1.50);
		assertEquals(1, sobrePeso.calcularIMC(),"La persona no tiene sobrepeso" );
	}
	@Test
	@DisplayName ("¿Tiene Infrapeso?")
	public void comprobarInfraPeso() {
		Persona infraPeso=new Persona();
		infraPeso.setPeso(50);
		infraPeso.setAltura(1.80);
		assertEquals(-1, infraPeso.calcularIMC(), "La persona no tiene infrapeso");
	}
	
}
