import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//import com.sun.tools.classfile.StackMapTable_attribute.verification_type_Info;

public class GraphMatrix {
  public int countNodes;
  public int countEdges;
  public int[][] adjMatrix;
  int Inf = 999999999;
  public GraphMatrix(int numNodes) {
    this.countNodes = numNodes;
    this.countEdges = 0;
    this.adjMatrix = new int[numNodes][numNodes];
  }

  public GraphMatrix(String fileName) throws IOException {
    File file = new File(fileName);
    FileReader reader = new FileReader(file);
    BufferedReader bufferedReader = new BufferedReader(reader);

    // Read header
    String[] line = bufferedReader.readLine().split(" ");
    this.countNodes = (Integer.parseInt(line[0]));
    int fileLines = (Integer.parseInt(line[1]));
    // Create and fill adjMatrix with read edges
    this.adjMatrix = new int[this.countNodes][this.countNodes];
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

  // CONSTRUTOR PARA CRIAR GRAFO NAO ORIENTADO
  public GraphMatrix(String fileName, int teste) throws IOException {
    File file = new File(fileName);
    FileReader reader = new FileReader(file);
    BufferedReader bufferedReader = new BufferedReader(reader);

    // Read header
    String[] line = bufferedReader.readLine().split(" ");
    this.countNodes = (Integer.parseInt(line[0]));
    int fileLines = (Integer.parseInt(line[1]));
    // Create and fill adjMatrix with read edges
    this.adjMatrix = new int[this.countNodes][this.countNodes];
    for (int i = 0; i < fileLines; ++i) {
      String[] edgeInfo = bufferedReader.readLine().split(" ");
      int source = Integer.parseInt(edgeInfo[0]);
      int sink = Integer.parseInt(edgeInfo[1]);
      int weight = Integer.parseInt(edgeInfo[2]);
      addEdgeUnoriented(source, sink, weight);
    }
    bufferedReader.close();
    reader.close();
  }

  public float density() {
    // E = quantidade de arestas que tem
    // M = maximo de arestas possíveis()
    // E / (M*(M-1))

    float e = this.countEdges;
    float m = this.countNodes;

    return e / (m * (m - 1));
  }
  
  public void addEdge(int source, int sink, int weight) {
    if (source < 0 || source >= this.adjMatrix.length || sink < 0 || sink >= this.adjMatrix.length || weight <= 0) {
      System.err.println("Invalid edge!\nsource: " + source + "\nsink: " + sink + "\nweight: " + weight);

      return;
    }

    this.countEdges++;
    this.adjMatrix[source][sink] = weight;
  }

  public void addEdgeUnoriented(int source, int sink, int weight) {
    if (source < 0 || source >= this.adjMatrix.length || sink < 0 || sink >= this.adjMatrix.length || weight <= 0) {
      System.err.println("Invalid edge!\nsource: " + source + "\nsink: " + sink + "\nweight: " + weight);

      return;
    }

    this.countEdges++;
    this.countEdges++;
    this.adjMatrix[source][sink] = weight;
    this.adjMatrix[sink][source] = weight;
  }
  
  public int degree(int node) {
    // Retorna o grau do nó node;
    
    // Retorna a quantidade de pesos dentro daquela linha, exemple:
    // 0 5 4 6 => degree = 3, pois de 4 colunas 3 estão preenchidas(estão != 0)
    
    int degree = 0;
    
    for (int j = 0; j < this.adjMatrix[node].length; j++) {
      if (this.adjMatrix[node][j] != 0) {
        degree++;
      }
    }

    return degree;
  }

  public int highestDegree() {
    int hightTest2 = 0;
    
    for (int i = 0; i < this.adjMatrix.length; i++) {
      if (this.degree(i) > hightTest2) {
        hightTest2 = this.degree(i);
      }
    }

    return hightTest2;

  }

  public int lowestDegree() {
    int hightTest2 = this.adjMatrix.length + 1;

    for (int i = 0; i < this.adjMatrix.length; i++) {
      if (this.degree(i) < hightTest2) {
        hightTest2 = this.degree(i);
      }
    }

    return hightTest2;
  }


  
  public boolean conexo() {
    return this.busca_largura(0).size() == this.countNodes;
  }

  public void dfs_rec_aux(int u, ArrayList<Integer> R, ArrayList<Integer> desc) {

    desc.set(u, 1);

    R.add(u);
    for (int i = 0; i < desc.size(); i++) {
      if (this.adjMatrix[u][i] != 0 && desc.get(i) == 0) {
        dfs_rec_aux(u, R, desc);
      }
    }
  }

  public ArrayList<Integer> dfs_rec(int s) {
    ArrayList<Integer> desc = new ArrayList<>();
    ArrayList<Integer> R = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      desc.add(0);
    }
    this.dfs_rec_aux(s, desc, R);
    return R;

  }

  public String toString() {
    String str = "";

    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix[i].length; j++) {
        str += this.adjMatrix[i][j] + "\t";
      }
      str += "\n";
    }

    return str;
  }

  public GraphMatrix complement() {
    GraphMatrix graph = new GraphMatrix(this.countNodes);

    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix[i].length; j++) {
        if (this.adjMatrix[i][j] == 0 && i != j) {
          graph.addEdge(i, j, 1);
        }
      }
    }

    return graph;
  }

  public boolean subgraph(GraphMatrix g2) {
    if (g2.countEdges > this.countEdges || g2.countNodes > this.countNodes) {
      return false;
    }

    for (int i = 0; i < g2.adjMatrix.length; i++) {
      for (int j = 0; j < g2.adjMatrix[i].length; j++) {
        if (g2.adjMatrix[i][j] != 0 && this.adjMatrix[i][j] == 0) {
          return false;
        }
      }
    }

    return true;
  }

  public boolean oriented() {
    // retorna true se o grafo é orientado

    for (int i = 0; i < this.adjMatrix.length / 2; i++) {
      for (int j = 0; j < this.adjMatrix[i].length / 2; j++) {
        if (this.adjMatrix[i][j] != this.adjMatrix[j][i]) {
          return true;
        }
      }
    }
    return false;
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
        if (this.adjMatrix[u][i] != 0 && desc.get(i) == 0) {

          Q.add(i);
          R.add(i);
          desc.set(i, 1);
        }
      }
    }
    return R;
  }

  public ArrayList<Integer> busca_profundidade(int s) {

    ArrayList<Integer> desc = new ArrayList<>();
    ArrayList<Integer> S = new ArrayList<>();
    ArrayList<Integer> R = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      desc.add(0);
    }
    S.add(s);
    R.add(s);
    desc.set(s, 1);
    int u = 0;
    while (S.size() > 0) {
      for (int i = 0; i < desc.size(); i++) {
        u = S.get(S.size() - 1);
        if (this.adjMatrix[u][i] != 0 && desc.get(i) == 0) {
          S.add(i);
          R.add(i);
          desc.set(i, 1);
          i = -1;
        }
      }
      S.remove(S.size() - 1);
    }
    return R;
  }

  private void ord_top_aux(int u, ArrayList<Integer> desc, ArrayList<Integer> R) {

    desc.set(u, 1);

    for (int i = 0; i < desc.size(); i++) {
      if (this.adjMatrix[u][i] != 0 && desc.get(i) == 0) {
        ord_top_aux(i, desc, R);
      }
    }

    R.add(0, u);

  }

  public ArrayList<Integer> ord_top() {
    ArrayList<Integer> desc = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      desc.add(0);
    }

    ArrayList<Integer> R = new ArrayList<>();

    for (int i = 0; i < desc.size(); i++) {
      if (desc.get(i) == 0) {
        ord_top_aux(i, desc, R);
      }
    }
    return R;

  }

  private void connected_comp_aux(int u, ArrayList<Integer> desc, int comp) {
    desc.set(u, comp);
    for (int i = 0; i < desc.size(); i++) {
      if (this.adjMatrix[u][i] != 0 && desc.get(i) == 0) {
        connected_comp_aux(i, desc, comp);
      }
    }

  }

  public ArrayList<Integer> connected_comp() {
    ArrayList<Integer> desc = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      desc.add(0);
    }

    int comp = 0;
    for (int i = 0; i < desc.size(); i++) {
      if (desc.get(i) == 0) {
        comp = comp + 1;
        connected_comp_aux(i, desc, comp);
      }
    }
    return desc;
  }

  public boolean has_cycle_oriented(int s) {

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
        if (this.adjMatrix[u][i] != 0) {
          if (desc.get(i) == 0) {

            Q.add(i);
            R.add(i);
            desc.set(i, 1);
          } else {
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean has_cycle_oriented() {
    boolean ciclo = false;
    ArrayList<Integer> desc = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      desc.add(0);
    }
    for (int i = 0; this.countNodes > i; i++) {
      if (desc.get(i) == 0) {
        desc.set(i, 1);
        ciclo = has_cycle_oriented(i);
        if (ciclo == true)
          return ciclo;

      }
    }
    return ciclo;
  }

  public int menorDist(int u, ArrayList <Integer> Q) {
    int min = Q.get(Q.size()-1);
    int pmin=999999;
    for (int i = Q.get(0); i < this.countNodes; i++) {
      if(Q.contains(i) && this.adjMatrix[u][i]!=0){
        
        if (this.adjMatrix[u][i] < pmin) {
          min = i;
          pmin=this.adjMatrix[u][i];
        }
      }
    }
    
    return min;
  }

  public void dijkstra(int s) {
    int Inf = 9999999;
    ArrayList<Integer> dist = new ArrayList<>();
    ArrayList<Integer> Q = new ArrayList<>();
    ArrayList<Integer> pred = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      dist.add((int) Inf);
      Q.add(i);
      pred.add(-1);
    }
    dist.set(s, 0);
    int u = s;
    Q.remove(0);
    while (Q.size()>0) {

      for(int v=0; v<this.countNodes;v++){
        if(this.adjMatrix[u][v]>0){
          if (dist.get(v)>dist.get(u) + this.adjMatrix[u][v]){
            dist.set(v,dist.get(u) + this.adjMatrix[u][v]);
            pred.set(v,u);
          }
        }
      }
      u = this.menorDist(u,Q);
      Q.remove(Integer.valueOf(u));
      
    }

    System.out.println(dist);
    System.out.println(pred);
  }

  public int bellmanford(int s) {
    int Inf = 9999999;
    ArrayList<Integer> dist = new ArrayList<>();
    ArrayList<Integer> pred = new ArrayList<>();
    for (int i = 0; i < this.countNodes; i++) {
      dist.add((int) Inf);
      pred.add(-1);
    }
    dist.set(s, 0);

    boolean trocou;
    for (int i = 0; i < this.countNodes; i++) {
      trocou = false;
      for (int v = 0; v < this.countNodes; v++) {
        for (int u = 0; u < this.countNodes; u++) {
          if (this.adjMatrix[u][v] != 0) {
            if (dist.get(v) > dist.get(u) + this.adjMatrix[u][v]) {
              dist.set(v, dist.get(u) + this.adjMatrix[u][v]);
              pred.set(v, u);
              trocou = true;
            }
          }
        }
      }
      if (trocou == false) {

        System.out.println(dist);
        System.out.println(pred);
        return 1;
      }
    }

    System.out.println(dist);
    System.out.println(pred);
    return 0;
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

  
  
  public ArrayList<Integer> caminhoEnc(int v,int[] pred, int d,int s) {
    
    int aux = d;
    int i = v;
    int j = 0;
    ArrayList <Integer> caminho = new ArrayList<>();
    while (aux != s) {
      i--;
      j++;
      caminho.add(0, aux);
      aux = pred[aux];
    }
    
    caminho.add(0, s);
    i = i + j;
    return caminho;

  }

  public ArrayList <Integer> floydWarshall(int s, int d) {
    System.out.println("\n\nIniciando Floyd Warshal:\n");
    float startTime = System.nanoTime();
    int Inf = 999999999;
    
    ArrayList <Integer> caminho = new ArrayList<>();
    

    int[][] dist = new int[this.countNodes][this.countNodes];
    int[][] pred = new int[this.countNodes][this.countNodes];
    for (int i = 0; i < this.adjMatrix.length; ++i) {
      for (int j = 0; j < this.adjMatrix[i].length; ++j) {
        if (i == j) {
          dist[i][j] = 0;
          pred[i][j] = -1;
        } else if (this.adjMatrix[i][j] != 0) { // Edge (i, j) exists
          dist[i][j] = this.adjMatrix[i][j];
          pred[i][j] = i;
        } else {
          dist[i][j] = Inf;
          pred[i][j] = -1;
        }
      }
    }

    for (int k = 0; k < this.countNodes; ++k) {
      for (int i = 0; i < this.countNodes; ++i) {
        for (int j = 0; j < this.countNodes; ++j) {
          if (dist[i][j] > dist[i][k] + dist[k][j]) {
            dist[i][j] = dist[i][k] + dist[k][j];
            pred[i][j] = pred[k][j];
          }
        }
      }
    }
    
    float endTime = System.nanoTime();
    float tempo = (endTime - startTime) / 1000000000;

    if (pred[s][d] != -1) {
      
      caminho = caminhoEnc(this.countNodes, pred[s], d, s);
          
      System.out.println("Caminho encontrado: " + caminho);
      System.out.println("Custo: " + dist[s][d]);
    } else {
      System.out.println("Não existe caminho entre a origem e o destino selecionados.");
    }
    System.out.printf("Tempo: %.4f segundos\n\n", tempo);
    
    return caminho;    
  }
  

}