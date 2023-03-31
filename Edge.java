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
public class Edge {
    int from, to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public Node getOtherNode(Node node) {
            if (node.id == from) {
                return new Node(to, "");
            } else {
                return new Node(from, "");
            }
        }
}
