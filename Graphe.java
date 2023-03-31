package graphe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Graphe {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Entrez le nombre de sommets : ");
        int n = sc.nextInt();

        int[][] matrice = new int[n][n];

        System.out.println("Entrez la matrice d'adjacence : ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrice[i][j] = sc.nextInt();
            }
        }
     
        // Affichage de la matrice d'adjacence
        System.out.println("Affichage de la matrice d'adjacence");
        for(int i = 0 ;i<n ; i++){
            for(int j = 0 ;j<n ; j++){
               System.out.printf(matrice[i][j] + " ") ;
             }
            System.out.println(); 
              }
        
         //vérification si le graphe est non orienté ou orienté
        if (isAdjacencyMatrix(matrice)){
            System.out.println("le graphe est non orienté");
            // Vérification de la connexité du graphe
            boolean connexe = estConnexe(n,matrice);
            if (connexe) {
                System.out.println("Le graphe est connexe.");
            // Recherche d'un arbre couvrant de poids minimum en utilisant l'algorithme de PRIM
                int[][] arbreMin = prim(matrice);
                System.out.println("Arbre couvrant de poids minimum : ");
                afficherMatrice(arbreMin);
            
            // Recherche d'un arbre couvrant de poids maximum en utilisant l'algorithme de PRIM     
                int[][] arbreMax = arbreCouvrantMaximum(matrice);
                System.out.println("Arbre couvrant de poids maximum : ");
                afficherMatrice(arbreMax);
            } else {
                System.out.println("Le graphe n'est pas connexe.");
            } 
                                                                          
        } else if (isAdjacencyMatrix(matrice) == false) {
            
            // Vérification de la forte connexité du graphe
            boolean fortementConnexe = estFortementConnexe(matrice);
            if (fortementConnexe) {
                System.out.println("Le graphe est fortement connexe.");
            } else {
                System.out.println("Le graphe n'est pas fortement connexe.");
                System.out.println("Voici le graphe réduit :");  
                Graph matriceToGraphe = matriceToGraphe(matrice);
                Graph grapheReduit = reduireGraphe(matriceToGraphe);
                int[][] grapheR= grapheToMatrice(grapheReduit);
                afficherMatrice(grapheR);
                matrice = grapheR;
                n = grapheR.length;
            }

            // Recherche d'un sommet racine 
            System.out.println("Entrez le sommet racine : ");
            int racine = sc.nextInt();

            // Recherche d'une arborescence de plus courts chemins en utilisant l'algorithme de DIJKSTRA *****************
            int[] distances = dijkstra(matrice, n, racine);
            System.out.println("Distances depuis le sommet racine : ");
            for (int i = 0; i < n; i++) {
                System.out.println(i + " : " + distances[i]);
            }
        } else {
            System.out.println("Type de graphe invalide.");
        }
        sc.close();
    }

    private static boolean isAdjacencyMatrix(int[][] matrix) {
    int n = matrix.length;
    // Vérification que la matrice est carrée
    if (n != matrix[0].length) {
        return false;
    }
    // Vérification que la matrice ne contient que des 0 ou des 1
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (matrix[i][j] != 0 && matrix[i][j] != 1) {
                return false;
            }
        }
    }
    // Vérification que la matrice est symétrique
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (matrix[i][j] != matrix[j][i]) {
                return false;
            }
        }
    }
    // Vérification que la diagonale principale ne contient que des 0
    for (int i = 0; i < n; i++) {
        if (matrix[i][i] != 0) {
            return false;
        }
    }
    // Si toutes les conditions sont satisfaites, alors la matrice est une matrice d'adjacence
    return true;
}
    
    private static boolean estConnexe(int n , int [][] matrice ) {
        boolean[] marque = new boolean[n];
        dfs(0,n, marque,matrice);
        for (int i = 0; i < n; i++) {
            if (!marque[i]) {
                return false;
            }
        }
        return true;
    }

    private static void dfs(int i ,int n , boolean[] marque , int[][] matrice) {
        marque[i] = true;
        for (int j = 0; j < n; j++) {
            if (matrice[i][j] != 0 && !marque[j]) {
                dfs(j,n, marque ,matrice);
            }
        }
    }

    private static int[][] prim(int[][] matrice) {
        int n = matrice.length;
        int[][] arbre = new int[n][n];
        boolean[] visite = new boolean[n];
        visite[0] = true;
        for (int k = 1; k < n; k++) {
            int minPoids = Integer.MAX_VALUE;
            int iMin = -1;
            int jMin = -1;
            for (int i = 0; i < n; i++) {
                if (visite[i]) {
                    for (int j = 0; j < n; j++) {
                        if (!visite[j] && matrice[i][j] != 0 && matrice[i][j] < minPoids) {
                            minPoids = matrice[i][j];
                            iMin = i;
                            jMin = j;
                        }
                    }
                }
            }
            arbre[iMin][jMin] = minPoids;
            arbre[jMin][iMin] = minPoids;
            visite[jMin] = true;
        }
        return arbre;
    }

    private static void afficherMatrice(int[][] arbreMin) {
        int n = arbreMin.length;
        System.out.println("Affichage de la matrice : ");
        for(int i = 0 ;i<n ; i++){
            for(int j = 0 ;j<n ; j++){
               System.out.printf(arbreMin[i][j] + " ") ;
             }
            System.out.println(); 
              }    }   
    
    private static int[][] arbreCouvrantMaximum(int [][] matrice) {
        int n = matrice.length;
        int[][] matriceInverse = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrice[i][j] != 0) {
                    matriceInverse[i][j] = -matrice[i][j];
                }
            }
        }
        int[][] arbre = prim(matriceInverse);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (arbre[i][j] != 0) {
                    arbre[i][j] = -arbre[i][j];
                }
            }
        }
        return arbre;
    }

    private static boolean estFortementConnexe(int[][] matriceAdjacence) {
    int n = matriceAdjacence.length;

    // Vérifier la connectivité du graphe en partant du premier sommet
    boolean[] visites = new boolean[n];
    visites[0] = true;
    parcoursEnProfondeur(matriceAdjacence, visites, 0);
    for (boolean visite : visites) {
        if (!visite) {
            return false; // Le graphe n'est pas connexe
        }
    }

    // Vérifier la connectivité du graphe en partant de chaque sommet
    for (int i = 0; i < n; i++) {
        visites = new boolean[n];
        visites[i] = true;
        parcoursEnProfondeur(matriceAdjacence, visites, i);
        for (boolean visite : visites) {
            if (!visite) {
                return false; // Le graphe n'est pas fortement connexe
            }
        }
    }

    return true; // Le graphe est fortement connexe
}

    private static void parcoursEnProfondeur(int[][] matriceAdjacence, boolean[] visites, int sommet) {
    for (int voisin = 0; voisin < matriceAdjacence.length; voisin++) {
        if (matriceAdjacence[sommet][voisin] != 0 && !visites[voisin]) {
            visites[voisin] = true;
            parcoursEnProfondeur(matriceAdjacence, visites, voisin);
        }
    }
}
    
    public static Graph matriceToGraphe(int[][] matriceAdjacence) {
    int n = matriceAdjacence.length;
    Graph graphe = new Graph();
    
    // Ajouter les arcs du graphe en fonction de sa nature (orienté ou non)
    for(int i=0; i<n; i++) {
        for(int j=0; j<n; j++) {
            if(matriceAdjacence[i][j] != 0) {
                Graph.ajouterArc(Graph.getSommets().get(i), Graph.getSommets().get(i), matriceAdjacence[i][j]);   
            }
        }
    }
    return graphe;
}

    
     
    private static Graph reduireGraphe(Graph g) {
        // méthode pour réduire le graphe en contractant les nœuds de degré 1
        
        Graph result = new Graph();
        Map<Integer, Integer> nodeMap = new HashMap<>(); // pour mapper les nœuds originaux aux nœuds réduits
        int nodeId = 0;
        
        // supprimer les nœuds de degré 1 et contracter leurs arêtes
        for (int i = 0; i < g.nodes.size(); i++) {
            Node node = g.nodes.get(i);
            if (node.edges.size() == 1) {
                Edge edge = node.edges.get(0);
                Node neighbor = edge.getOtherNode(node);
                if (!nodeMap.containsKey(neighbor.id)) {
                    nodeMap.put(neighbor.id, nodeId);
                    result.addNode(new Node(nodeId++, neighbor.label));
                }
                result.addEdge(new Edge(nodeMap.get(node.id), nodeMap.get(neighbor.id), edge.weight));
            } else {
                nodeMap.put(node.id, nodeId);
                result.addNode(new Node(nodeId++, node.label));
            }
        }
        return result;
    }

    // classe pour représenter un nœud dans le graphe
    

    // classe pour représenter une arête dans le graphe
   

    // classe pour représenter un graphe
    

    public static int[][] grapheToMatrice(Graph graphe) {
    int n = graphe.getNbSommets();
    int[][] matrice = new int[n][n];
    List<Sommet> sommets = graphe.getSommets();

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (i == j) {
                matrice[i][j] = 0; // La diagonale est toujours nulle dans une matrice d'adjacence
            } else {
                Sommet sommetI = sommets.get(i);
                Sommet sommetJ = sommets.get(j);
                if (arcExiste(sommetI, sommetJ)) {
                    matrice[i][j] = Graphe.getPoidsArc(sommetI, sommetJ); // Si l'arc existe, on récupère son poids
                } else {
                    matrice[i][j] = 0; // Si l'arc n'existe pas, le poids est 0
                }
            }
        }
    }

    return matrice;
}

    public boolean arcExiste(Sommet s1, Sommet s2) {
        List<Arc> arcs = null;
    for (Arc arc : arcs) {
        if (arc.getSommetOrigine().equals(s1) && arc.getSommetDestination().equals(s2)) {
            return true;
        }
    }
    return false;
}

    
    public static int getPoidsArc(Sommet sommetDepart, Sommet sommetArrivee) {
        List<Arc> arcs = null;
    for (Arc arc : arcs) { // parcours tous les arcs du graphe
        if (arc.getSommetOrigine().equals(sommetDepart) && arc.getSommetDestination().equals(sommetArrivee)) {
            return arc.getPoids(); // renvoie le poids de l'arc s'il existe
        }
    }
    return 0; // renvoie 0 si l'arc n'existe pas
}

}