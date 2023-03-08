package junitcuentatarjeta;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppTest {
    Cuenta c1;
    Debito d1;

    @Test
    @DisplayName("¿C1 es un objeto de la clase Cuenta?")
    public void claseCuenta() {
        c1 = new Cuenta("AA", "S");
        assertThat(c1, instanceOf(Cuenta.class));
    }

    @Test
    @DisplayName("¿Se creo la cuenta correctamente?")
    public void cuentaCorrecta() {
        c1 = new Cuenta("2020", "A");
        assertThat("La cuenta esta vacia", c1, is(notNullValue()));
    }

    @Test
    @DisplayName("¿Se ingresa correctamente una cantidad?")
    public void ingreso() throws Exception {
        c1 = new Cuenta("2021", "S");
        c1.ingresar("S", 20);
        assertEquals(20.0, c1.getSaldo());
    }

    @Test
    @DisplayName("¿Podriamos introducir una cantidad negativa con concepto?")
    public void ingresoNegativo(){
        c1 = new Cuenta("45123", "C");
        try {
	        c1.ingresar("A", -5);
		} catch (Exception e) {
			System.out.println("Has introducido una cantidad negativa en tu cuenta con concepto");
		}
        assertEquals(0,c1.getSaldo());

    }
    @Test
    @DisplayName("¿Podriamos introducir una cantidad negativa sin concepto?")
    public void ingresoNegativoSinConcepto(){
        c1 = new Cuenta("45123", "C");
        try {
	        c1.ingresar(-5);
		} catch (Exception e) {
			System.out.println("Has introducido una cantidad negativa en tu cuenta sin Concepto");
		}
        assertEquals(0,c1.getSaldo());
    }
    @Test
    @DisplayName("¿Se retiro correctamente una cantidad?")
    public void retiro(){
        c1 = new Cuenta("2142", "p");
        try {
			c1.ingresar(2);
	        c1.retirar("S", 2);

		} catch (Exception e) {
			System.out.println("No se ha podido retirar dinero");
		}
        assertEquals(0, c1.getSaldo());
    }

    @Test
    @DisplayName("¿Podriamos retirar una cantidad negativa?")
    public void retiroNegativo(){
        c1 = new Cuenta("154", "o");
        try {
			c1.ingresar("S",5);
	        c1.retirar("S",-1);

		} catch (Exception e) {
			System.out.println("No puedes retirar dinero que no tienes en tu cuenta sin concepto");
		}
        assertThat(c1.getSaldo(), lessThan(6.0));
    }

    @Test
    @DisplayName("¿Podriamos retirar mas dinero del que tenemos?")
    public void retirarDinero() {
        c1 = new Cuenta("95478", "PEW");
        try {
			c1.ingresar(10);
	        c1.retirar(9);
		} catch (Exception e) {
			System.out.println("No puedes retirar mas de lo que tienes");
		}
        assertEquals(1,c1.getSaldo());
    }
    //Tiene que fallar a proposito
    @Test
    @DisplayName("¿Podriamos retirar mas dinero del que tenemos con concepto?")
    public void retirarMasQueTenemos() {
        c1 = new Cuenta("95478", "PEW");
        try {
			c1.ingresar("L",10);
	        c1.retirar("S",11);
		} catch (Exception e) {
			System.out.println("No puedes retirar mas de lo que tienes con concepto");
		}
        assertEquals(10,c1.getSaldo());
    }
    @Test
    @DisplayName("¿Podriamos retirar mas dinero del que tenemos con concepto?")
    public void retirarMasQueTenemosSinConcepto() {
        c1 = new Cuenta("95478", "PEW");
        try {
			c1.ingresar(10);
	        c1.retirar(11);
		} catch (Exception e) {
			System.out.println("Saldo insuficiente");
		}
        assertEquals(10,c1.getSaldo());
    }
    @Test
    @DisplayName("¿Podriamos retirar una cantidad negativa?")
    public void retirarCantidadNegativa() {
        c1 = new Cuenta("95478", "PEW");
        try {
			c1.ingresar(10);
	        c1.retirar(-11);
		} catch (Exception e) {
			System.out.println("No podemos retirar una cantidad negativa sin concepto");
		}
        assertEquals(10,c1.getSaldo());
    }
    ///////////////////////////// TEST
    ///////////////////////////// DEBITO/////////////////////////////////////////
    @Test
    @DisplayName("¿Podemos retirar dinero de nuestra tarjeta?")
    public void retiradaCajero() throws Exception {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String fecha = formatter.format(date);
        c1 = new Cuenta("95478", "LS");
        Debito d1 = new Debito("20", "S", date);
        d1.mCuentaAsociada = c1;
        c1.ingresar(fecha, 2);
        d1.retirar(1.0);
        assertNotEquals(5, d1.getSaldo());
    }

    @Test
    @DisplayName("¿Podemos ingresar dinero a nuestra tarjeta?")
    public void ingresoCajero() throws Exception {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String fecha = formatter.format(date);
        c1 = new Cuenta("9750", "DAM");
        d1 = new Debito("20", "DAM", date);
        d1.mCuentaAsociada = c1;
        c1.ingresar(fecha, 4);
        // Ingresar realmente es retirar
        d1.ingresar(2);
        assertThat("No se ha podido ingresar", d1.getSaldo(), is(not(greaterThanOrEqualTo(4.0))));
    }

    @Test
    @DisplayName("¿Podemos pagar en establecimientos con nuestra tarjeta?")
    public void pagoSitios() throws Exception {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String fecha = formatter.format(date);
        c1 = new Cuenta("5050", "SAS");
        d1 = new Debito("50", "SAS", date);
        d1.mCuentaAsociada = c1;
        c1.ingresar("Pago", 5);
        d1.pagoEnEstablecimiento(fecha, 2);
        assertThat("Pago rechazado", d1.getSaldo(), is(equalTo(3.0)));

    }

    @Test
    @DisplayName("¿Podemos pagar en establecimientos con nuestra tarjeta si no tenemos fondos?")
    public void pagoSitiosSinDinero() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String fecha = formatter.format(date);
        c1 = new Cuenta("5050", "SAS");
        d1 = new Debito("50", "SAS", date);
        d1.mCuentaAsociada = c1;
        try {
			c1.ingresar("Pago", 1);
	        d1.pagoEnEstablecimiento(fecha, 4);
		} catch (Exception e) {
			System.out.println("Saldo insuficiente en la tarjeta");
		}
        assertFalse(c1.getSaldo() > d1.getSaldo());
    }

    @Test
    @DisplayName("Le introducimos 7 euros a nuestra cuenta mediante la tarjeta, ¿Tienen el mismo saldo la tarjeta y la cuenta?")
    public void saldoTarjeta() throws Exception {
        Date date = new Date();
        c1 = new Cuenta("4101", "PN");
        d1 = new Debito("4101", "PN", date);
        d1.setCuenta(c1);
        d1.mCuentaAsociada = c1;
        c1.ingresar("d", 7);
        d1.ingresar(7);
        assertThat(c1.getSaldo(), is(equalTo(d1.getSaldo())));
    }

    @Test
    @DisplayName("¿La tarjeta esta caducada?")
    public void tarjetaValida() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date f1 = formatter.parse("20/02/1990");
        d1 = new Debito("4587", "PWS", f1);
        Date f2 = formatter.parse("20/01/1990");
        boolean tarjetaInvalida = false;
        if (d1.mFechaDeCaducidad.before(f2)) {
            tarjetaInvalida = true;
        }
        assertFalse(tarjetaInvalida);
    }

    ///////////////////////// TEST MOVIMIENTOS/////////////////////////
    @Test
    @DisplayName("Hago dos movimiento metiendo lo mismo, ¿podrian ser el mismo?")
    public void movimientoIgual() throws ParseException {
        Movimiento m1 = new Movimiento();
        m1.setImporte(2);
        Movimiento m2 = new Movimiento();
        m2.setImporte(2);
        assertThat(m1.getImporte(), is(equalTo(m2.getImporte())));
    }

    @Test
    @DisplayName("Hago dos movimiento metiendo lo mismo en dos fechas, ¿Son el mismo dia?")
    public void movimientoDistintaFecha() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date f1 = formatter.parse("02/02/2002");
        Date f2 = formatter.parse("06/06/2002");
        Movimiento m1 = new Movimiento();
        m1.setImporte(2);
        Movimiento m2 = new Movimiento();
        m2.setImporte(2);
        m1.setFecha(f1);
        m2.setFecha(f2);
        assertFalse(m1.getFecha() == m2.getFecha());
    }

    @Test
    @DisplayName("Hago dos movimiento metiendo el mismo concepto, ¿son los mismos?")
    public void ConceptosIguales() throws ParseException {
        Movimiento m1 = new Movimiento();
        m1.setImporte(2);
        Movimiento m2 = new Movimiento();
        m2.setImporte(2);
        m1.setConcepto("S");
        m2.setConcepto("S");
        assertTrue(m1.getConcepto() == m2.getConcepto());
    }

    /////////////////////////// TEST
    /////////////////////////// CREDITO////////////////////////////////////////////
    Credito cr1;

    @Test
    @DisplayName("¿Podemos introducir correctamente dinero?")
    public void ingresarCredito() throws Exception {
        Date date = new Date();
        cr1 = new Credito("5050", "SADE", date, 0);
        c1 = new Cuenta("4101", "PN");
        cr1.mCuentaAsociada = c1;
        cr1.ingresar(20);
        assertEquals(20, cr1.getSaldo());
    }

    @Test
    @DisplayName("¿Podemos Retirar correctamente dinero?")
    public void RetirarCredito() throws Exception {
        Date date = new Date();
        cr1 = new Credito("5050", "SADE", date, 50);
        c1 = new Cuenta("4101", "PN");
        cr1.mCuentaAsociada = c1;
        cr1.ingresar(25);
        cr1.retirar(20);
        assertThat(cr1.getSaldo(), is(not(greaterThan(30.0))));
    }

    @Test
    @DisplayName("¿Podemos pagar en establecimientos?")
    public void pagoCreditoEstablecimiento() throws Exception {
        Date date = new Date();
        cr1 = new Credito("5050", "SADE", date, 0);
        c1 = new Cuenta("4101", "PN");
        cr1.mCuentaAsociada = c1;
        cr1.pagoEnEstablecimiento("SDS", 20);
        assertEquals(20, cr1.getSaldo());
    }

    @Test
    @DisplayName("¿El credito que tenemos disponible es menor a 80?")
    public void CreditoDisponible() throws Exception {
        Date date = new Date();
        cr1 = new Credito("5050", "SADE", date, 100);
        c1 = new Cuenta("4101", "PN");
        cr1.mCuentaAsociada = c1;
        cr1.ingresar(50);
        assertFalse(cr1.getCreditoDisponible() > 80);
    }
    //Va a fallar a proposito
    @Test
    @DisplayName("¿Podemos retirar credito si el valor es mayor al credito que tenemos?")
    public void creditoInsuficiente(){
    	Date date = new Date();
        cr1 = new Credito("5050", "SADE", date, 0);
        c1 = new Cuenta("4101", "PN");
        cr1.mCuentaAsociada = c1;
        try {
			cr1.retirar(60.0);
		} catch (Exception e) {
			System.out.println("Credito insuficiente en su tarjeta");
		}
        assertFalse(cr1.getCreditoDisponible()>c1.getSaldo());
    }
    @Test
    @DisplayName("¿Al liquidar, queda algun credito?")
    public void Liquidacion() throws Exception {
        Date date = new Date();
        cr1 = new Credito("5050", "SADE", date, 0);
        c1 = new Cuenta("4101", "PN");
        cr1.mCuentaAsociada = c1;
        cr1.liquidar(5, 2);
        assertEquals(0, cr1.getCreditoDisponible());

    }
}
