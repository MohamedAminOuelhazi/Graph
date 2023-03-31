
package graphe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {
        List<Sommet> sommets;
        List<Node> nodes;
        List<Edge> edges;

        public Graph() {
            this.nodes = new ArrayList<>();
            this.edges = new ArrayList<>();
        }

    

        public void addNode(Node node) {
            nodes.add(node);
        }

        public void addEdge(Edge edge) {
            edges.add(edge);
            nodes.get(edge.from).addEdge(edge);
            nodes.get(edge.to).addEdge(edge);
        }
        public int getNbSommets() {
        return this.sommets.size();
}

        public  List<Sommet> getSommets() {
            return this.sommets;
}
        
       public static void ajouterArc(Sommet sommetOrigine, Sommet sommetDestination, int poids) {
        List<Arc> arcs = new ArrayList<>();
        Arc arc = new Arc(sommetOrigine, sommetDestination, poids);
        arcs.add(arc);
    }

}
