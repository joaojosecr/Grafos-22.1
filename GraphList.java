import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphList {

  private int countNodes;
  private int countEdges;
  private ArrayList<ArrayList<Edge>> adjList;
  private ArrayList<Edge> edgeList;
  private static final int INF = 999999;

  public GraphList() {
    
  }
  public GraphList(int countNodes) {
    this.countNodes = countNodes;
    adjList = new ArrayList<>(this.countNodes);
    for (int i = 0; i < this.countNodes; ++i) {
      adjList.add(new ArrayList<Edge>());
    }
    edgeList = new ArrayList<>();
  }

  public GraphList(String fileName) throws IOException {
    File file = new File(fileName);
    FileReader reader = new FileReader(file);
    BufferedReader bufferedReader = new BufferedReader(reader);

    // Read header
    String[] line = bufferedReader.readLine().split(" ");
    this.countNodes = (Integer.parseInt(line[0]));
    int fileLines = (Integer.parseInt(line[1]));

    // Create and fill adjList with read edges
    adjList = new ArrayList<>(this.countNodes);
    for (int i = 0; i < this.countNodes; ++i) {
      adjList.add(new ArrayList<Edge>());
    }
    edgeList = new ArrayList<>();
    // Adds one edge at time
    for (int i = 0; i < fileLines; ++i) {
      String[] edgeInfo = bufferedReader.readLine().split(" ");
      int source = Integer.parseInt(edgeInfo[0]);
      int sink = Integer.parseInt(edgeInfo[1]);
      int weight = Integer.parseInt(edgeInfo[2]);
      addEdge(source, sink, weight);
    }
    bufferedReader.close();
    reader.close();
  }

  public void addEdge(int source, int sink, int weight) {
    if (source < 0 || source > this.countNodes - 1 || sink < 0 || sink > this.countNodes - 1 || weight <= 0) {
      System.err.println("Invalid edge: (" + source + "," + sink + "," + weight + ")");
      
      return;
    }
    adjList.get(source).add(new Edge(source, sink, weight));
    edgeList.add(new Edge(source, sink, weight));
    this.countEdges++;
  }

  public void addEdgeUnoriented(int source, int sink, int weight) {
    addEdge(source, sink, weight);
    addEdge(sink, source, weight);
  }

  public int degree(int u) {
    if (u < 0 || u > this.countNodes - 1)
      System.err.println("Invalid node: " + u);
    return this.adjList.get(u).size();
  }

  public int highestDegree() {
    int highest = 0;
    for (int u = 0; u < this.adjList.size(); ++u) {
      int degreeNodeU = this.degree(u);
      if (highest < degreeNodeU)
        highest = degreeNodeU;
    }
    return highest;
  }

  public int lowestDegree() {
    int lowest = this.countNodes;
    for (int u = 0; u < this.adjList.size(); ++u) {
      int degreeNodeU = this.degree(u);
      if (lowest > degreeNodeU)
        lowest = degreeNodeU;
    }
    return lowest;
  }

  public GraphList complement() {
    GraphList graph = new GraphList(this.countNodes);

    for (int i = 0; i < this.adjList.size(); i++) {
      for (int j = 0; j < this.countNodes; j++) {
        boolean addEdgeUV = true;
        for (int idx = 0; idx < this.adjList.get(i).size(); ++idx) {
          int v2 = this.adjList.get(i).get(idx).getSink();
          if (v2 == j) { // Edge (u, v) exists and should not be added
            addEdgeUV = false;
            break;
          }
        }
        if (addEdgeUV && i != j) {
          graph.addEdge(i, j, 1); // It assumes edges are unweighted
        }
      }
    }

    return graph;
  }

  public float density() {
    return (float) this.countEdges / (this.countNodes * (this.countNodes - 1));
  }

  public boolean subgraph(GraphList g2) {
    if (g2.countNodes > this.countNodes || g2.countEdges > this.countEdges)
      return false;
    for (int u = 0; u < g2.adjList.size(); ++u) {
      boolean foundEdge = false;
      for (int idx = 0; idx < g2.adjList.get(u).size(); ++idx) {
        int v = g2.adjList.get(u).get(idx).getSink();
        // Check if edge (u,v) exists in this graph
        for (int idx2 = 0; idx2 < this.adjList.get(u).size(); ++idx2) {
          int v2 = this.adjList.get(u).get(idx2).getSink();
          if (v == v2) { // Edge exists
            foundEdge = true;
            break;
          }
        }
        if (!foundEdge)
          return false;
      }
    }
    return true;
  }

  // FAZER
  public ArrayList<Integer> bfs(int s) {
    // TDOO tarefa da parte 1
    return new ArrayList<>();
  }

  public ArrayList<Integer> dfs(int s) {
    // TDOO tarefa da parte 1
    return new ArrayList<>();
  }

  public boolean connected() {
    // TDOO tarefa da parte 1
    return false;
  }




  public boolean isOriented() {
    for (int u = 0; u < this.adjList.size(); ++u) {
      for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
        int v = this.adjList.get(u).get(idx).getSink();
        boolean hasEdgeVU = false;
        for (int idx2 = 0; idx2 < this.adjList.get(v).size(); ++idx2) {
          int u2 = this.adjList.get(v).get(idx2).getSink();
          if (u == u2) {
            hasEdgeVU = true;
            break;
          }
        }
        if (!hasEdgeVU) {
          return true;
        }
      }
    }
    return false;
  }

  public ArrayList<Integer> dfsRec(int s) {
    int[] desc = new int[this.countNodes];
    ArrayList<Integer> R = new ArrayList<>();
    dfsRecAux(s, desc, R);
    return R;
  }

  public void dfsRecAux(int u, int[] desc, ArrayList<Integer> R) {
    desc[u] = 1;
    R.add(u);
    for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
      int v = this.adjList.get(u).get(idx).getSink();
      if (desc[v] == 0) {
        dfsRecAux(v, desc, R);
      }
    }
  }

  public ArrayList<Edge> kruskal() {
    ArrayList<Edge> T = new ArrayList<Edge>(this.countNodes - 1);
    int[] F = new int[this.countNodes];
    // makeset(u)
    for (int u = 0; u < this.countNodes; ++u)
      F[u] = u;
    edgeList.sort(null);
    for (int idx = 0; idx < edgeList.size(); ++idx) {
      int u = edgeList.get(idx).getSource();
      int v = edgeList.get(idx).getSink();
      if (F[u] != F[v]) { // findset(u) != findset(v)
        T.add(edgeList.get(idx));
        // Save some iterations if tree is already built
        if (T.size() == countNodes - 1)
          break;
        // union(u, v)
        int k = F[v];
        for (int i = 0; i < F.length; ++i) {
          if (F[i] == k) {
            F[i] = F[u];
          }
        }
      }
    }
    return T;
  }

  public ArrayList<Edge> prim() {
    ArrayList<Edge> T = new ArrayList<Edge>(this.countNodes - 1);
    int s = 0;
    int[] dist = new int[this.countNodes];
    int[] parent = new int[this.countNodes];
    // Q represents the nodes that were not connected yet
    ArrayList<Integer> Q = new ArrayList<Integer>(this.countNodes);
    for (int u = 0; u < this.countNodes; ++u) {
      dist[u] = INF;
      parent[u] = -1;
      Q.add(u);
    }
    dist[s] = 0;
    while (Q.size() != 0) {
      int u = -1;
      int min = INF;
      for (int idx = 0; idx < Q.size(); ++idx) {
        int i = Q.get(idx);
        if (dist[i] < min) {
          min = dist[i];
          u = i;
        }
      }
      // Node u is gonna be connected
      Q.remove((Integer) u);
      for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
        int v = this.adjList.get(u).get(idx).getSink();
        int w = this.adjList.get(u).get(idx).getWeight();
        if (Q.contains(v) && w < dist[v]) {
          dist[v] = w;
          parent[v] = u;
        }
      }
    }
    // Recover the tree from parent array
    for (int u = 0; u < parent.length; ++u) {
      if (parent[u] != -1) {
        T.add(new Edge(u, parent[u], 1));
      }
    }
    return T;
  }
  
  public String toString() {
    String str = "";
    for (int u = 0; u < this.adjList.size(); ++u) {
        str += u + ": ";
        for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
            str += this.adjList.get(u).get(idx) + "\t";
        }
        str += "\n";
    }
    return str;
}

  public boolean conexo() {
    return this.busca_largura(0).size() == this.countNodes;
  }

  public ArrayList<Integer> busca_largura(int s) {

    ArrayList<Integer> desc = new ArrayList<>();
    ArrayList<Integer> Q = new ArrayList<>();
    ArrayList<Integer> R = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      desc.add(0);
    }
    Q.add(s);
    R.add(s);
    desc.set(s, 1);
    int u = 0;

    while (Q.size() > 0) {
      u = Q.get(0);
      Q.remove(0);

      for (int i = 0; i < desc.size(); i++) {
        if (this.adjList.get(u).get(i).getWeight() != 0 && desc.get(i) == 0) {
          Q.add(i);
          R.add(i);
          desc.set(i, 1);
        }
      }
    }
    return R;
  }

  public ArrayList<Integer> dijkstra(int s,int d) {
    System.out.println("\n\nIniciando Dijkstra:\n");
    float startTime = System.nanoTime();
    int u;
    int Inf = 9999999;
    int min = Inf;
    
    ArrayList<Integer> caminho = new ArrayList<>();
    ArrayList<Integer> dist = new ArrayList<>();
    ArrayList<Integer> Q = new ArrayList<>();
    ArrayList<Integer> pred = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      Q.add(i);
      dist.add((int) Inf);
      pred.add(-1);
    }
    dist.set(s, 0);
    while (Q.size() > 0) {
      min = Inf;
      u = Q.get(0);

      for (int i : Q) {
        if (dist.get(i) < min) {
          min = dist.get(i);
          u = i;
        }
      }
      Q.remove(Integer.valueOf(u));
      
      for (Edge e : adjList.get(u)) {
        if (dist.get(e.v)>dist.get(u) + e.w){
          dist.set(e.v,dist.get(u) + e.w);
          pred.set(e.v,u);
        }
      }
      
    }
    

    // System.out.println(dist);
    // System.out.println(pred);       
    float endTime = System.nanoTime();
    float tempo = (endTime - startTime) / 1000000000;
    if (pred.get(d) != -1) {  
      caminho = caminhoEncontrado(pred, d, s);      
      System.out.println("Caminho encontrado: " + caminho);
      System.out.println("Custo: " + dist.get(d));
    } else {
      System.out.println("Não existe caminho entre a origem e o destino selecionados.");
    }
    System.out.printf("Tempo: %.4f segundos\n\n", tempo);
    
    return caminho;    
  }

  public ArrayList<Integer> caminhoEncontrado(ArrayList<Integer> pred, int d, int s) {
    ArrayList<Integer> caminho = new ArrayList<>();
    int aux = d;

    while (aux != s) {
      caminho.add(0, aux);
      aux = pred.get(aux);
    }
    caminho.add(0, s);

    return caminho;

  }

  
  public ArrayList<Integer> bellmanford(int s, int d) {
    System.out.println("\n\nIniciando Bellman Ford:\n");
    float startTime = System.nanoTime();

    int Inf = 9999999;
    
    ArrayList<Integer> caminho = new ArrayList<>();
    ArrayList<Integer> dist = new ArrayList<>();
    ArrayList<Integer> pred = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      dist.add((int) Inf);
      pred.add(-1);
    }
    dist.set(s, 0);
    
    
    for (int i = 0; i < this.countNodes; i++) {
      
      for (int j = 0; j < this.countEdges; j++) {
        if (dist.get(edgeList.get(j).v) > dist.get(edgeList.get(j).u) + edgeList.get(j).w) {
          dist.set(edgeList.get(j).v, dist.get(edgeList.get(j).u) + edgeList.get(j).w);
          pred.set(edgeList.get(j).v, edgeList.get(j).u);
      
        }
      }
      
    }
    float endTime = System.nanoTime();
    float tempo = (endTime - startTime) / 1000000000;
    if (pred.get(d) != -1) {  
      caminho = caminhoEncontrado(pred, d, s);      
      System.out.println("Caminho encontrado: " + caminho);
      System.out.println("Custo: " + dist.get(d));
    } else {
      System.out.println("Não existe caminho entre a origem e o destino selecionados.");
    }
    System.out.printf("Tempo: %.4f segundos\n\n", tempo);
    
    return caminho;    
  }


  public ArrayList<Integer> bellmanfordMelhorado(int s, int d) {
    System.out.println("\n\nIniciando Bellman Ford melhorado:\n");
    float startTime = System.nanoTime();

    int Inf = 9999999;
    
    ArrayList<Integer> caminho = new ArrayList<>();
    ArrayList<Integer> dist = new ArrayList<>();
    ArrayList<Integer> pred = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      dist.add((int) Inf);
      pred.add(-1);
    }
    dist.set(s, 0);
    boolean trocou = false;

    for (int i = 0; i < this.countNodes; i++) {
      trocou = false;
      for (int j = 0; j < this.countEdges; j++) {
        if (dist.get(edgeList.get(j).v) > dist.get(edgeList.get(j).u) + edgeList.get(j).w) {
          dist.set(edgeList.get(j).v, dist.get(edgeList.get(j).u) + edgeList.get(j).w);
          pred.set(edgeList.get(j).v, edgeList.get(j).u);
          trocou = true;
        }
      }
      if (trocou == false) {
        // System.out.println(dist);
        // System.out.println(pred);        
        break;
      }

    }
    float endTime = System.nanoTime();
    float tempo = (endTime - startTime) / 1000000000;
    if (pred.get(d) != -1) {  
      caminho = caminhoEncontrado(pred, d, s);      
      System.out.println("Caminho encontrado: " + caminho);
      System.out.println("Custo: " + dist.get(d));
    } else {
      System.out.println("Não existe caminho entre a origem e o destino selecionados.");
    }
    System.out.printf("Tempo: %.4f segundos\n\n", tempo);
    
    return caminho;    
  }

  public int getCountNodes() {
    return countNodes;
  }

  public void setCountNodes(int countNodes) {
    this.countNodes = countNodes;
  }

  public int getCountEdges() {
    return countEdges;
  }

  public void setCountEdges(int countEdges) {
    this.countEdges = countEdges;
  }

  public ArrayList<ArrayList<Edge>> getAdjList() {
    return adjList;
  }

  public void setAdjList(ArrayList<ArrayList<Edge>> adjList) {
    this.adjList = adjList;
  }

}
