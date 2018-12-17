/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristicaspractica3;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

/**
 *
 * @author Miguel Gonzalez Garcia y Roberto Martinez Fernandez
 */
public class MetaheuristicasPractica3 {

    static final String FICHERO_PARAM = "Parametros.txt"; //Ruta del fichero con los parametros para el programa.
    static final int TAM_POBLACION_ESTACIONARIO = 25;
    static final int TAM_POBLACION_GENERACIONAL = 10;
    static final int ITERACIONES1 = 100;
    static final int ITERACIONES2 = 500;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try{
            DataInputStream datos = new DataInputStream(new BufferedInputStream(new FileInputStream(FICHERO_PARAM)));
            String s;
            
            s = datos.readLine();
            String[] linea = s.split(" "); //Se obtiene la ruta al Fichero de Datos
            String rutaDatos = linea[1];
            Problema problema = new Problema(rutaDatos);
            
            s = datos.readLine();
            linea = s.split(" "); //Se obtiene la Semilla
            int sem = Integer.parseInt(linea[1]);
            
            s = datos.readLine();
            linea = s.split(" "); //Se obtiene la Semilla
            String algoritmo = linea[1];
            
            long startTime, endTime;
            switch(algoritmo){
                case "AME2":
                    //Se obtiene una poblacion inicial aleatoria para el algoritmo de 100 iteraciones.
                    Poblacion poblacionAME2_100 = new Poblacion(TAM_POBLACION_ESTACIONARIO, sem, problema);
                    poblacionAME2_100.ordenarPoblacion();
                    
                    //Se copia esa poblacion para el algoritmo de 500 iteraciones
                    Solucion[] aux1 = new Solucion[poblacionAME2_100.getTam()];
                        for(int i=0; i<poblacionAME2_100.getTam(); i++){
                            aux1[i] = new Solucion(poblacionAME2_100.individuo(i));
                        }
                    Poblacion poblacionAME2_500 = new Poblacion(poblacionAME2_100, aux1);
                    
                    //Ejecucion para 100 iteraciones.
                    startTime = System.currentTimeMillis();
                    AME2 ame2_100 = new AME2(poblacionAME2_100, sem, ITERACIONES1, problema, rutaDatos);
                    ame2_100.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados.
                    System.out.print("\n\nALGORITMO MEMETICO ESTACIONARIO AME2 (100 iteraciones):\n");
                    ame2_100.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
            
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para 500 iteraciones.
                    startTime = System.currentTimeMillis();
                    AME2 ame2_500 = new AME2(poblacionAME2_500, sem, ITERACIONES2, problema, rutaDatos);
                    ame2_500.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados.
                    System.out.print("\n\nALGORITMO MEMETICO ESTACIONARIO (500 iteraciones):\n");
                    ame2_500.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
                break;
                case "AMGAll":
                    //Se obtiene una poblacion inicial aleatoria para el algoritmo de 100 iteraciones.
                    Poblacion poblacionAMGAll_100 = new Poblacion(TAM_POBLACION_GENERACIONAL, sem, problema);
                    poblacionAMGAll_100.ordenarPoblacion();
                    
                    //Se copia esa poblacion para el algoritmo de 500 iteraciones
                    Solucion[] aux2 = new Solucion[poblacionAMGAll_100.getTam()];
                        for(int i=0; i<poblacionAMGAll_100.getTam(); i++){
                            aux2[i] = new Solucion(poblacionAMGAll_100.individuo(i));
                        }
                    Poblacion poblacionAMGAll_500 = new Poblacion(poblacionAMGAll_100, aux2);
                    
                    //Ejecucion para 100 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMGAll amgall_100 = new AMGAll(poblacionAMGAll_100, sem, ITERACIONES1, problema, rutaDatos);
                    amgall_100.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL AMGAll (100 iteraciones/Busqueda para toda la poblacion):\n");
                    amgall_100.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
            
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para 500 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMGAll amgall_500 = new AMGAll(poblacionAMGAll_500, sem, ITERACIONES2, problema, rutaDatos);
                    amgall_500.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL (500 iteraciones/Busqueda para toda la poblacion):\n");
                    amgall_500.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
                break;
                case "AMG10":
                    //Se obtiene una poblacion inicial aleatoria para el algoritmo de 100 iteraciones.
                    Poblacion poblacionAMG10_100 = new Poblacion(TAM_POBLACION_GENERACIONAL, sem, problema);
                    poblacionAMG10_100.ordenarPoblacion();
                    
                    //Se copia esa poblacion para el algoritmo de 500 iteraciones
                    Solucion[] aux3 = new Solucion[poblacionAMG10_100.getTam()];
                        for(int i=0; i<poblacionAMG10_100.getTam(); i++){
                            aux3[i] = new Solucion(poblacionAMG10_100.individuo(i));
                        }
                    Poblacion poblacionAMG10_500 = new Poblacion(poblacionAMG10_100, aux3);
                    
                    //Ejecucion para 100 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMG10 amg10_100 = new AMG10(poblacionAMG10_100, sem, ITERACIONES1, problema, rutaDatos);
                    amg10_100.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL AMG10 (100 iteraciones/Busqueda para un 10% de la poblacion):\n");
                    amg10_100.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
            
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para 500 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMG10 amg10_500 = new AMG10(poblacionAMG10_500, sem, ITERACIONES2, problema, rutaDatos);
                    amg10_500.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL (500 iteraciones/Busqueda para un 10% de la poblacion):\n");
                    amg10_500.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
                break;
                case "AMG3":
                    //Se obtiene una poblacion inicial aleatoria para el algoritmo de 100 iteraciones.
                    Poblacion poblacionAMG3_100 = new Poblacion(TAM_POBLACION_GENERACIONAL, sem, problema);
                    poblacionAMG3_100.ordenarPoblacion();
                    
                    //Se copia esa poblacion para el algoritmo de 500 iteraciones
                    Solucion[] aux4 = new Solucion[poblacionAMG3_100.getTam()];
                        for(int i=0; i<poblacionAMG3_100.getTam(); i++){
                            aux4[i] = new Solucion(poblacionAMG3_100.individuo(i));
                        }
                    Poblacion poblacionAMG3_500 = new Poblacion(poblacionAMG3_100, aux4);
                    
                    //Ejecucion para 100 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMG3 amg3_100 = new AMG3(poblacionAMG3_100, sem, ITERACIONES1, problema, rutaDatos);
                    amg3_100.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL AMG3 (100 iteraciones/Busqueda para los 3 mejores individuos de la poblacion):\n");
                    amg3_100.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
            
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para 500 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMG3 amg3_500 = new AMG3(poblacionAMG3_500, sem, ITERACIONES2, problema, rutaDatos);
                    amg3_500.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL (500 iteraciones/Busqueda para los 3 mejores individuos de la poblacion):\n");
                    amg3_500.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
                break;
                default:
                    //Se obtiene una poblacion inicial aleatoria para el algoritmo AME2 de 100 iteraciones.
                    Poblacion poblacionAME2_100_2 = new Poblacion(TAM_POBLACION_ESTACIONARIO, sem, problema);
                    poblacionAME2_100_2.ordenarPoblacion();
                    
                    //Se copia esa poblacion para el algoritmo AME2 de 500 iteraciones
                    Solucion[] aux5 = new Solucion[poblacionAME2_100_2.getTam()];
                        for(int i=0; i<poblacionAME2_100_2.getTam(); i++){
                            aux5[i] = new Solucion(poblacionAME2_100_2.individuo(i));
                        }
                    Poblacion poblacionAME2_500_2 = new Poblacion(poblacionAME2_100_2, aux5);
                    
                    //Se obtiene una poblacion inicial aleatoria para el algoritmo AMGAll de 100 iteraciones.
                    Poblacion poblacionAMGAll_100_2 = new Poblacion(TAM_POBLACION_GENERACIONAL, sem, problema);
                    poblacionAMGAll_100_2.ordenarPoblacion();
                    
                    //Se copia esa poblacion para el algoritmo AMGAll de 500 iteraciones
                    Solucion[] aux6 = new Solucion[poblacionAMGAll_100_2.getTam()];
                        for(int i=0; i<poblacionAMGAll_100_2.getTam(); i++){
                            aux6[i] = new Solucion(poblacionAMGAll_100_2.individuo(i));
                        }
                    Poblacion poblacionAMGAll_500_2 = new Poblacion(poblacionAMGAll_100_2, aux6);
                    
                    //Se obtiene una poblacion inicial aleatoria para el algoritmo AMG10 de 100 iteraciones.
                    Poblacion poblacionAMG10_100_2 = new Poblacion(TAM_POBLACION_GENERACIONAL, sem, problema);
                    poblacionAMG10_100_2.ordenarPoblacion();
                    
                    //Se copia esa poblacion para el algoritmo AMG10 de 500 iteraciones
                    Solucion[] aux7 = new Solucion[poblacionAMG10_100_2.getTam()];
                        for(int i=0; i<poblacionAMG10_100_2.getTam(); i++){
                            aux7[i] = new Solucion(poblacionAMG10_100_2.individuo(i));
                        }
                    Poblacion poblacionAMG10_500_2 = new Poblacion(poblacionAMG10_100_2, aux7);
                    
                    //Se obtiene una poblacion inicial aleatoria para el algoritmo AMG3 de 100 iteraciones.
                    Poblacion poblacionAMG3_100_2 = new Poblacion(TAM_POBLACION_GENERACIONAL, sem, problema);
                    poblacionAMG3_100_2.ordenarPoblacion();
                    
                    //Se copia esa poblacion para el algoritmo AMG3 de 500 iteraciones
                    Solucion[] aux8 = new Solucion[poblacionAMG3_100_2.getTam()];
                        for(int i=0; i<poblacionAMG3_100_2.getTam(); i++){
                            aux8[i] = new Solucion(poblacionAMG3_100_2.individuo(i));
                        }
                    Poblacion poblacionAMG3_500_2 = new Poblacion(poblacionAMG3_100_2, aux8);
                    
                    //Ejecucion para AME2 100 iteraciones.
                    startTime = System.currentTimeMillis();
                    AME2 ame2_100_2 = new AME2(poblacionAME2_100_2, sem, ITERACIONES1, problema, rutaDatos);
                    ame2_100_2.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados AME2 100 iteraciones.
                    System.out.print("\n\nALGORITMO MEMETICO ESTACIONARIO ame2 (100 iteraciones):\n");
                    ame2_100_2.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
            
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para AME2 500 iteraciones.
                    startTime = System.currentTimeMillis();
                    AME2 ame2_500_2 = new AME2(poblacionAME2_500_2, sem, ITERACIONES2, problema, rutaDatos);
                    ame2_500_2.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados AME2 500 iteraciones.
                    System.out.print("\n\nALGORITMO MEMETICO ESTACIONARIO ame2 (500 iteraciones):\n");
                    ame2_500_2.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
                    
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para AMGAll 100 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMGAll amgall_100_2 = new AMGAll(poblacionAMGAll_100_2, sem, ITERACIONES1, problema, rutaDatos);
                    amgall_100_2.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados AMGAll 100 iteraciones.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL amgall (100 iteraciones/Busqueda para toda la poblacion):\n");
                    amgall_100_2.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
            
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para AMGAll 500 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMGAll amgall_500_2 = new AMGAll(poblacionAMGAll_500_2, sem, ITERACIONES2, problema, rutaDatos);
                    amgall_500_2.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados AMGAll 500 iteraciones.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL amgall (500 iteraciones/Busqueda para toda la poblacion):\n");
                    amgall_500_2.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
                    
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para AMG10 100 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMG10 amg10_100_2 = new AMG10(poblacionAMG10_100_2, sem, ITERACIONES1, problema, rutaDatos);
                    amg10_100_2.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados AMG10 100 iteraciones.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL amg10 (100 iteraciones/Busqueda para un 10% de la poblacion):\n");
                    amg10_100_2.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
            
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para AMG10 500 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMG10 amg10_500_2 = new AMG10(poblacionAMG10_500_2, sem, ITERACIONES2, problema, rutaDatos);
                    amg10_500_2.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados AMG10 500 iteraciones.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL amg10 (500 iteraciones/Busqueda para un 10% de la poblacion):\n");
                    amg10_500_2.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
                    
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para AMG3 100 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMG3 amg3_100_2 = new AMG3(poblacionAMG3_100_2, sem, ITERACIONES1, problema, rutaDatos);
                    amg3_100_2.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados AMG3 100 iteraciones.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL amg3 (100 iteraciones/Busqueda para los 3 mejores individuos de la poblacion):\n");
                    amg3_100_2.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
            
                    System.out.print("___________________________________________\n");
                    
                    //Ejecucion para AMG3 500 iteraciones.
                    startTime = System.currentTimeMillis();
                    AMG3 amg3_500_2 = new AMG3(poblacionAMG3_500_2, sem, ITERACIONES2, problema, rutaDatos);
                    amg3_500_2.Ejecutar();
                    endTime = System.currentTimeMillis() - startTime;
                    
                    //Obtención de resultados AMG3 500 iteraciones.
                    System.out.print("\n\nALGORITMO MEMETICO GENERACIONAL amg3 (500 iteraciones/Busqueda para los 3 mejores individuos de la poblacion):\n");
                    amg3_500_2.mejorSolucion().MostrarSolucion();
                    System.out.println("\nTiempo de ejecucion: " + endTime + " ms.");
                break;
            }
        }catch(Exception e){
            System.err.printf(e.getMessage()+"\n");
        }
    }
    
}
