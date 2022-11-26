package es.iescarrillo.daw.endes.fibonacci;

public class Fibonacci {

	public int metodo() {
		//Declaro la variable inicial
		  int num1 = 0;
		  //Declaro e inicializo el primer numero
		  int num2 = 1;
		  //Declaro e inicializo el final de la serie
		  int serie = 10;
		  //Declaro e inicio la variable acumulador 
		  int acumulador= 1;
		  
	        // Muestro el valor inicial de nuestra serie
	        System.out.println(num1);
	        //Itero mientras i sea menor a serie
	        for (int i = 1; i < serie; i++) {
	            // Muestro el valor de acumulador primero ya que busco que se me repita el primer 1
	            System.out.println(acumulador);           
	            //Sumamos el numero 1 con el 2
	            acumulador = num1 + num2;
	            //Actualizamos el valor de num1 con el valor de num2
	            num1 = num2;
	            //Actualizamos el valor de numero 2 con el que tenga acumulador
	            num2 = acumulador;
	        }
	return acumulador;
	}

}
