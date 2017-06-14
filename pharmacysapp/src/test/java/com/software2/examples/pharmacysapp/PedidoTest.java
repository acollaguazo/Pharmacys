/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.software2.examples.pharmacysapp;
import com.software2.examples.pharmacysapp.Producto;
import com.software2.examples.pharmacysapp.DetallePedido;
import com.software2.examples.pharmacysapp.Pago;  
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Stephany
 */
public class PedidoTest {
    
    private static ArrayList<Producto> catalogo;
    private static ArrayList<DetallePedido> carrito;
    
    public static void initCatalogo() {
        
       
        catalogo = new ArrayList<Producto>();
        carrito = new ArrayList<DetallePedido>();
        
        Producto producto1 = new Producto("Dicloflenaco","Medicina",4,1,10.5);
        Producto producto2 = new Producto("Buscapina","Medicina",20,5,5.6);
        Producto producto3 = new Producto("Analgan","Medicina",10,2,2.50);
        Producto producto4 = new Producto("Redoxon","Medicina",30,5,7.80);
        
        catalogo.add(producto1);
        catalogo.add(producto2);
        catalogo.add(producto3);
        catalogo.add(producto4);
    }
    
    
    public Producto obtener_producto_catalogo(ArrayList<Producto> productos, String clave){
        Producto encontrado = null;
        
        for (Producto producto : productos) {
             if(producto.getNombre().equals(clave))
                 encontrado = producto;
            }
        return encontrado;
    
    }
    
    @Before
    public void beforeEachTest() {
        initCatalogo();
    }

    @After
    public void afterEachTest() {
      
    }

    @Test
    public void testIntegracion_producto_catalogo() {
        System.out.println("----Test 1----"); 
        System.out.println("\n(INICIAL) El catálogo de la farmacia es: " + catalogo.size());
        System.out.println(catalogo.toString());
        Producto producto5 = new Producto("Ciprofloxacina","Medicina",50,15,11.42);
        String resultado = producto5.crear_producto();
        catalogo.add(producto5);
        System.out.println("\n(FIN) El catálogo de la farmacia es: " + catalogo.size());
        System.out.println(catalogo.toString()); 
        
        assertEquals("Se creo el producto exitosamente!", resultado); //experado,obtenido
        
        System.out.println("----Test 1----\n"); 
    }
    
    @Test  
    public void testIntegracion_catalogo_carrito() {
        System.out.println("----Test 2----");        
        Producto p = obtener_producto_catalogo(catalogo,"Redoxon"); //simulo que lo agrego al carrito
        DetallePedido detalle1 = new DetallePedido(p,5); //Simulo que selecciono 5
        carrito.add(detalle1);
        //visualizar el pedido con los productos seleccionados.
        System.out.println("*******Carrito*****");
        System.out.println(carrito.toString());
        assertEquals(1, carrito.size()); //experado,obtenido
        System.out.println("----Test 2----\n"); 
    }
    
   
    
    @Test  
    public void testIntegracion_PagoSubtotal() {
        System.out.println("----Test 3----");      
        Producto p1 = obtener_producto_catalogo(catalogo,"Analgan");
        Producto p2 = obtener_producto_catalogo(catalogo,"Buscapina");
        DetallePedido detalle1 = new DetallePedido(p1,1);
        DetallePedido detalle2 = new DetallePedido(p2,1);
        carrito.add(detalle1);
        carrito.add(detalle2);
        //visualizar el pedido con los productos seleccionados.
        System.out.println(carrito.toString());
        Pago pa=new Pago();
        //Ingresa el tipo de pago
        pa.crear_pago(true,"");
        Cliente client=new Cliente("Juan", 1, pa);
        System.out.println(client.InfoPer());
        System.out.println( pa.validar_pago(pa));
        double subt=detalle1.subtotal +detalle2.subtotal;
        System.out.println("El subtotal a pagar es: " + subt);
        assertEquals(true, pa.getTipo());//experado,obtenido
        System.out.println("----Test 3----\n");      
    }
    
    @Test
	public void testIntegracion_ConfirmarPedido(){
		//Calendar calendario = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:ii:ss");
		String stringHora = "15:50:23";
		Date Hora = sdf.parse(stringHora);
		System.out.println("----Test 4----");
		Producto p1 = obtener_producto_catalogo(catalogo,"Analgan");
		Producto p2 = obtener_producto_catalogo(catalogo,"Dicloflenaco");
		DetallePedido detalle1 = new DetallePedido(p1,1);
        DetallePedido detalle2 = new DetallePedido(p2,1);
		carrito.add(detalle1);
        carrito.add(detalle2);
		//visualizar el pedido con los productos seleccionados.
        System.out.println(carrito.toString());
        Pago pa=new Pago();
		Cliente client=new Cliente("Kerly", 1, pa);
        System.out.println(client.InfoPer());
        System.out.println( pa.validar_pago(pa));
        double subt=detalle1.subtotal +detalle2.subtotal;
        System.out.println("El subtotal a pagar es: " + subt);
		Pedido pe = new Pedido(carrito,Hora,client);
		System.out.println(pe.ValidHora(pe));
		assertEquals(true, pe.ValidHora(pe));
		System.out.println("----Test 4----\n");  
	}
	
	@Test
	//Revisar la funcionalidad de obtener el recargo y total a pagar.
	//F1: Obtener recargo
	//F2: Total a pagar
	//El usuario ingresa al sistema, agrega productos al carrito, visualiza el pedido.Despues da clic en siguiente, donde ngresa nombre, sector, tipo de pago, y da clic en confirmar pedido. Se genera el recargo y el total a pagar.
	//productos seleccionados=analgan, cantidad=1, pago=efectivo, nombre="Juan",sector=norte, horario=hora actual
	//El usuario da clic en confirmar pedido, se muestra el sunbtotal, el recargo y el total a pagar.
	//El usuario da clic en confirmar pedido y se muestra el recargo que tiene junto al total a pagar.
	public void  testIntegracion_ObtenerRecargo(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:ii:ss");
		String stringHora = "15:50:23";
		Date Hora = sdf.parse(stringHora);
		System.out.println("----Test 4----");
		Producto p1 = obtener_producto_catalogo(catalogo,"Analgan");
		DetallePedido detalle1 = new DetallePedido(p1,1);
		carrito.add(detalle1);
		Pago pa=new Pago();
		Cliente client=new Cliente("Juan", 1, pa);
        System.out.println(client.InfoPer());
        System.out.println( pa.validar_pago(pa));
		double subt=detalle1.subtotal +detalle2.subtotal;
		System.out.println("El subtotal a pagar es: " + subt);
		System.out.println("Tiene recargo de $4");
		Pedido pe = new Pedido(carrito,Hora,client);
		double recargo = pe.GetRecargo(client,subt);
		double total = pe.TotalPedido();
		System.out.println("El total a pagar es: " + total);
		assertEquals(2,pe.getRecargo(client,subt))
		System.out.println("----Test 5----\n");  
	}

}
