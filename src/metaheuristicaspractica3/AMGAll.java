/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicaspractica3;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

/**
 *
 * @author Miguel Gonzalez Garcia y Roberto Martinez Fernandez
 */
public class AMGAll {
    static final int MAX_GENERACIONES = 1000;

    private Poblacion poblacion;
    private Random rand;
    private Solucion[] padres;
    private Solucion[] hijos;
    private Solucion mejorSol;
    private Poblacion nuevaGen;
    private int numGeneracion;
    private int MAX_ITERACIONES;
    private boolean mejorIncluido;
    private String rutaLog;
    
    public AMGAll(Poblacion _poblacion, int sem, int num_iter, Problema prob, String rutaDatos) {
        poblacion = _poblacion;
        rand = new Random(sem);
        numGeneracion = 0;
        padres = new Solucion[2];
        hijos = new Solucion[2];
        for (int i = 0; i < 2; i++) {
            padres[i] = new Solucion(prob);
            hijos[i] = new Solucion(prob);
        }
        mejorSol = new Solucion(poblacion.individuo(0));
        nuevaGen = new Poblacion(poblacion.getTam(), sem, prob);
        MAX_ITERACIONES = num_iter;
        mejorIncluido = false;
        rutaLog = rutaDatos + "_AMGAll_" + num_iter + "_" + sem + ".log";
    }
    
    public void Ejecutar() {
        while (numGeneracion < MAX_GENERACIONES) {
            mejorIncluido = false;
            for(int i=0; i<poblacion.getTam()/2; i++){
                numGeneracion++;
                Seleccion();
                if(rand.nextFloat() < 0.7){
                    Cruce();
                    Mutacion();
                    nuevaGen.reemplazarIndividuo(hijos[0], i*2);
                    nuevaGen.reemplazarIndividuo(hijos[1], (i*2)+1);
                }else{
                    nuevaGen.reemplazarIndividuo(padres[0], i*2);
                    nuevaGen.reemplazarIndividuo(padres[1], (i*2)+1);
                }
            }
            //Se comprueba si el mejor individuo permenece en la nueva generacion.
            if(nuevaGen.individuo(0).igualdad(mejorSol)){
                mejorIncluido = true;
            }
            if(numGeneracion%10 == 0){
               PrimerMejor(); 
            }
            nuevaGen.ordenarPoblacion();
            Reemplazamiento();
            if(mejorSol.getCoste()>poblacion.individuo(0).getCoste()){
                mejorSol = new Solucion(poblacion.individuo(0));            
            }
            log(!(numGeneracion == 1)); //Si es la primera generacion, creara el fichero o lo sobreescribira si contiene datos de ejecuciones anteriores.           
        }
        System.out.printf("Generacion final: %d\n", numGeneracion);
    }
    
    private void Seleccion() {
        int[] val = new int[2];
        int seleccionado = -1;
        for (int i = 0; i <= 1; i++) {
            do {
                val[0] = rand.nextInt(poblacion.getTam());
            } while (val[0] == seleccionado);
            do {
                val[1] = rand.nextInt(poblacion.getTam());
            } while (val[0] == val[1] || val[1] == seleccionado);
            if (poblacion.individuo(val[0]).getCoste() < poblacion.individuo(val[1]).getCoste()) {
                padres[i] = poblacion.individuo(val[0]);
                seleccionado = val[0];
            } else {
                padres[i] = poblacion.individuo(val[1]);
                seleccionado = val[1];
            }
        }
    }

    private void Cruce() {
        int corte1 = 0, corte2 = 0, tmp;
        //Seleccion de los puntos de corte
        do {
            corte1 = rand.nextInt(padres[0].getTam());
            corte2 = rand.nextInt(padres[0].getTam());
            if (corte2 < corte1) {
                tmp = corte1;
                corte1 = corte2;
                corte2 = tmp;
            }
        } while ((corte1 - corte2 >= poblacion.getTam() - 1) || (corte1 == corte2));
        operadorPMX(corte1, corte2);
    }
    
    private void operadorPMX(int corte1, int corte2) {
        int iteradorPadre, iteradorGenes, tam = padres[0].getTam();
        boolean adjudicados[] = new boolean[tam];
        int genes[] = new int[tam];
        int cont;
        for(int j=0; j<2; j++){
            cont = 0;
            for(int i=0; i<tam; i++){
                adjudicados[i] = false;
            }
            for(int i=corte1; i<corte2; i++){
                genes[i] = padres[j].getValorPermutacion(i);
                adjudicados[genes[i]] = true;
                cont++;
            }
            iteradorGenes = corte2;
            iteradorPadre = corte2;
            while(cont < tam){
                while(adjudicados[padres[(j+1)%2].getValorPermutacion(iteradorPadre)]){
                    iteradorPadre = padres[j].getIndex(padres[(j + 1) % 2].getValorPermutacion(iteradorPadre));
                }
                genes[iteradorGenes] = padres[(j + 1) % 2].getValorPermutacion(iteradorPadre);
                adjudicados[genes[iteradorGenes]] = true;
                iteradorGenes = (iteradorGenes + 1) % tam;
                cont++;
                iteradorPadre = iteradorGenes;
            }
            for(int i=0; i<tam; i++){
                hijos[j].setValorPermutacion(i, genes[i]);
            }
            hijos[j].Coste();
        }
    }
    
    private void Mutacion() {
        int valor;
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < hijos[j].getTam(); i++) {
                if (rand.nextFloat() < 0.001 * hijos[j].getTam()) {
                    do {
                        valor = rand.nextInt(hijos[j].getTam());
                    } while (valor == i);
                    hijos[j].Intercambio(i, valor);
                    hijos[j].CosteIntercambio(i, valor);
                }
            }
        }

    }
    
    private void Reemplazamiento() {
        Solucion[] aux = new Solucion[poblacion.getTam()];
        for(int i=0; i<poblacion.getTam(); i++){
            aux[i] = new Solucion(nuevaGen.individuo(i));
        }
        
        //Se consigue la elite de 1 individuo (Si no esta ya incluido)
        if(mejorIncluido){
            aux[poblacion.getTam()-1] = new Solucion(poblacion.individuo(0));
        }
         
        //Se reemplaza la nueva generacion
        poblacion = new Poblacion(poblacion, aux);
        poblacion.ordenarPoblacion();
    }
    
    private void log(boolean PrimeraEscritura) {
        try {
            File archivo = new File(rutaLog);

            FileWriter escribir = new FileWriter(archivo, PrimeraEscritura);

            escribir.write("GENERACION: " + numGeneracion + "\nMejor Solucion: ");
            for (int i = 0; i < mejorSol.getTam(); i++) {
                escribir.write(" " + mejorSol.getValorPermutacion(i));
            }
            escribir.write("\n");
            escribir.write("Coste: "+mejorSol.getCoste()+"\n\n");
            escribir.close();

        } catch (Exception e) {
            System.err.println("Error al escribir");
        }
    }

    private void PrimerMejor(){
        int[] dlb = new int[nuevaGen.individuo(0).getTam()];
        boolean end, mejora;
        int contIter, costeAct, costeTmp;
        for(int k=0; k<nuevaGen.getTam(); k++){
            end = false;
            for(int i=0; i<nuevaGen.individuo(k).getTam(); i++){
                dlb[i]=0;
            }
            contIter = 0;
            costeAct = nuevaGen.individuo(k).getCoste();
            while(!end && contIter < MAX_ITERACIONES){
                for(int i=0; i<nuevaGen.individuo(k).getTam(); i++){
                    if(dlb[i]==0){
                        mejora = false;
                        for(int j=0; j<nuevaGen.individuo(k).getTam(); j++){
                            nuevaGen.individuo(k).Intercambio(i, j);
                            costeTmp = nuevaGen.individuo(k).CosteIntercambio(i, j);
                            if(costeTmp < costeAct){
                                costeAct = costeTmp;
                                dlb[i] = 0;
                                dlb[j] = 0;
                                mejora = true;
                            }else{
                                nuevaGen.individuo(k).Intercambio(j, i);
                                nuevaGen.individuo(k).CosteIntercambio(j, i);
                            }
                        }
                        if(!mejora){
                            dlb[i]=1;
                        }
                    }
                    contIter++;
                }
                end=true;
                for(int i=0; i<nuevaGen.individuo(k).getTam(); i++){
                    if(dlb[i]==0){
                        end=false;
                    }
                }
            }
        }
    }
    
    public Solucion individuo(int index) {
        return poblacion.individuo(index);
    }
    
    public Solucion mejorSolucion() {
        return mejorSol;
    }
}