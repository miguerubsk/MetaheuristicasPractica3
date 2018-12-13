/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicaspractica3;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Miguel Gonzalez Garcia y Roberto Martinez Fernandez
 */
public class Poblacion {
    
    private Solucion[] individuos;
    private int tam;
    private int semilla;
    
    public Poblacion(int _tam, int _semilla, Problema prob){
        tam = _tam;
        individuos = new Solucion[tam];
        semilla = _semilla;
        Random rand = new Random(semilla);
        for(int i=0; i<tam; i++){
            individuos[i] = new Solucion(prob);
            individuos[i].GenerarAleatoria(rand);
        }
    }
    
    //Constructor copia para sustituir las generaciones
    public Poblacion(Poblacion p, Solucion[] s){
        tam = p.tam;
        individuos = new Solucion[tam];
        semilla = p.semilla;
        Random rand = new Random(semilla);
        individuos = Arrays.copyOf(s, tam);
    }
    
    public Solucion individuo(int index){
        return individuos[index];
    }
    
    //Reemplaza al individuo en la posicion "index".
    public void reemplazarIndividuo(Solucion individuo, int index){
        individuos[index] = individuo;
    }
    
    public int getTam(){
        return tam;
    }
    
    public void ordenarPoblacion(){
        burbuja();
    }
    
    private void burbuja(){
    boolean fin=false;
    Solucion aux;
        while(!fin){
            fin=true;
            for(int i=0; i<tam-1; i++){
                if(individuos[i+1].getCoste()<individuos[i].getCoste()){
                    aux = individuos[i+1];
                    individuos[i+1] = individuos[i];
                    individuos[i] = aux;
                    fin = false;
                }
            }
        }
}
      
}
