/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author msi
 */
public class Node {
    int id;
        String label;
        List<Edge> edges;

        public Node(int id, String label) {
            this.id = id;
            this.label = label;
            this.edges = new ArrayList<>();
        }

        public void addEdge(Edge e) {
            edges.add(e);
        }
}
