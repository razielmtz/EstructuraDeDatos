/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arreglo;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author lap
 */
public class Arreglo {

    /**
     * @param args the command line arguments
     */
    private static ArrayList<Integer> arreglo = new ArrayList<Integer>(); 
    
    public static void main(String[] args) {
        lecturaDatos("C:\\Users\\lap\\Documents\\Tec\\Cuarto Semestre\\AyD de Algoritmos\\archivo.txt"); //llama al método para crear arreglo pasandole valores de un archivo como parametro
        System.out.print("Arreglo sin ordernar: ");
        imprimeArreglo(); //Llama al método para Imprimir el arreglo sin ordenar primero
        heapSort();
        System.out.print("Arreglo ordenado con Heap Sort: ");
        imprimeArreglo(); //Llama al método imprime arreglo para mostrar el arreglo ya ordenado
        /*radixSort(); //Llama al método RadixSort para ordenar el arreglo
        System.out.print("Arreglo ordenando con Radix Sort: "); 
        imprimeArreglo(); //Llama al método imprime arreglo para mostrar el arreglo ya ordenado*/
    }
    
    public static void lecturaDatos(String archivo){ //Crea arreglo con datos de un archivo que recibe como parametro
        String texto; //Crea variable texto para almacenar lineas de archivo
        try(BufferedReader reader = new BufferedReader(new FileReader (archivo))){ //Crea lector de archivo
            String tamano = reader.readLine(); //se igual la variable n al string obtenido de la primera linea
            Integer.parseInt(tamano); //se convierte el valor de string a entero
            while((texto = reader.readLine())!= null){ //Mientras que haya lineas en el archivo por leer
                arreglo.add(Integer.parseInt(texto)); //añadir valor de cada linea como entero
            }
        }catch(Exception e){
             System.out.println("No se encontró archivo."); //Lanza excepcion si no se encuentra el archivo
        }
    }
    
    public static void imprimeArreglo(){ //método para imprimir el arreglo
    
        for (int i=0;i<arreglo.size();i++){ //desde indice = 0 hasta n
            System.out.print(arreglo.get(i) + " ");  //imprimir el valor del indice actual
        }
        System.out.println("\n");
    }
    public static void heapSort(){ //Método para ordenar arreglo con Heap Sort
        int tamano = arreglo.size(); //Guardar tamaño de arreglo en variable
        
        for(int i = tamano/2; i>0; i--){ //Crear monticulo(heap)  empezando con el indice de en medio 
            reheapyfy(i, tamano); //Se manda como parámetro el indice y el tamaño al método reaheapyfy
        }
        
        for(int i=tamano-1; i>0; i--){ //Recorrer elementos desde el final (para extraer numeros del heap)
            int auxiliar = arreglo.get(0); //Guardar primer valor en variable para intercambiar y mover al final
            arreglo.set(0,arreglo.get(i)); //Mover el valor de la posicion actual a la primera posicion
            arreglo.set(i, auxiliar); //Intercambiamos el valor y ponemos el valor de aux en la posicion actual
            reheapyfy(1, i); //Se manda como parametro 1 y el indice como tamaño
        }
    }
    
    public static void reheapyfy(int i, int tamano){ //método para ordenear
        int izq = (2*i); //El hijo izquierdo es igual al indice resultante de multiplicar el actual por 2
        int der = (2*i) + 1;//El hijo derecho es igual al indice resultante de multiplicar el actual por 2 y sumarle 1
        int maxNum; //se crea variable para almacenar el numero mas grande
        
        if ((izq <= tamano) && (arreglo.get(izq-1) > arreglo.get(i-1))){ //si el hijo izquierdo es el mayor
            maxNum = izq; //asiganar valor de hijo izquierdo al maximo numero
        }else{
            maxNum = i; // si no, el actual es el mayor
        }
        if ((der <= tamano) && (arreglo.get(der-1) > arreglo.get(maxNum-1))){ // si el hijo derecho es mayor que el numero mas grande
            maxNum = der; //asignar valor de hijo derecho a la variable del numero mayor
        }
        if (maxNum != i) //si el numero mayor todavia no es la raiz
        {
            int auxiliar = arreglo.get(i-1); //hacer swap 
            arreglo.set(i-1, arreglo.get(maxNum-1)); //se colo el maximo en la posicion
            arreglo.set(maxNum-1, auxiliar); //termina el cambio 
 
            reheapyfy(maxNum, tamano); //seguir haciendo la llamada mientras la condicion se cumpla
        }
    }
    
    public static void radixSort(){
        int i; //variable de indice para recorrer arreglos
        int maxNum = arreglo.get(0); //igualo el primero numero de arreglo al máximo numero
        int unidad = 1; //variable para obtener cada unidad de los numeros
        int tamano = arreglo.size(); //guardo el numero de valores del ArrayList en n
        int auxiliar[] = new int[tamano]; //creo variable auxiliar donde para almacenar valores temporalmente
        for (i = 1; i < tamano; i++){ //ciclo for para obtener máximo numero del arreglo
            if (arreglo.get(i) > maxNum) //si el valor de la posicion acutal es mayor al valor del maximo Numero
                maxNum = arreglo.get(i); //entonces ahora ese valor es el del maximo numero
        }
        while (maxNum / unidad > 0){ //mientras queden unidades para crear cubetas
            int[] cubeta = new int[10]; //crear cubeta para almacenar valores (del 0 al 9)
            for (i = 0; i < tamano; i++){ //recorrer arreglo desde la posicion 0 hasta la ultima posicion  
                cubeta[(arreglo.get(i) / unidad) % 10]++; //obtener el valor de la unidad actual y aumentar el valor del indice correspondiente al resultado obtenido
            }
            for (i = 1; i < 10; i++){  //Ayuda para proximamente obtener valores en posiciones de acuerdo a la unidad
                cubeta[i] += cubeta[i - 1]; //Suma el valor de cada indice del indice predecesor
            }
            for (i = tamano-1; i >= 0; i--){ //Obtiene valores ordenandolos de acuerdo a la unidad de cada indice
                auxiliar[--cubeta[(arreglo.get(i) / unidad) % 10]] = arreglo.get(i);//empieza desde el ultimo numero del ArrayList
                //luego obtiene el valor del indice actual para el arreglo auxiliar de acuerdo a la unidad evaluada
                //decrementa el valor del indice de la cubeta para no repetir indices si es que hay un valor de unidad repetido
            } 
            arreglo.clear(); //Borra todos los datos desordenaos del ArrayList
            for (i = 0; i < tamano; i++){ //Llenar ArrayList con valores ordenados
                arreglo.add(auxiliar[i]); //Se pasa cada valor del arreglo auxiliar, que ya están ordenados
            }
            unidad *= 10; //Aumenta unidad * 10 para ir pasando a cada unidad   
        }
    }
}
