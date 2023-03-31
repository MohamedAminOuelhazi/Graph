/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphe;

/**
 *
 * @author msi
 */
public class Arc {
   private Sommet sommetOrigine;
    private Sommet sommetDestination;
    private int poids;

    Arc(Sommet sommetOrigine, Sommet sommetDestination, int poids) {
        this.sommetOrigine = sommetOrigine;
        this.sommetDestination = sommetDestination;
        this.poids = poids;
    }

   
    // Getters et Setters

    public Sommet getSommetOrigine() {
        return sommetOrigine;
    }

    public Sommet getSommetDestination() {
        return sommetDestination;
    }

    public int getPoids() {
        return poids;
    }

    public  void setSommetOrigine(Sommet sommetOrigine) {
        this.sommetOrigine = sommetOrigine;
    }

    public  void setSommetDestination(Sommet sommetDestination) {
        this.sommetDestination = sommetDestination;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }
    
    
}