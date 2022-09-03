import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//import com.sun.tools.classfile.StackMapTable_attribute.verification_type_info;

public class Grafo {
  public int countNodes;
  public int countEdges;
  public int[][] adjMatrix;

  public Grafo(int numNodes) {
    this.countNodes = numNodes;
    this.countEdges = 0;
    this.adjMatrix = new int[numNodes][numNodes];
  }

  public Grafo(String fileName) throws IOException {
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

  public Grafo(String fileName, int teste) throws IOException {
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

    /*
     * int values[] = new int[this.adjMatrix.length];
     * 
     * for (int i = 0; i < this.adjMatrix.length; i++) {
     * int value = 0;
     * for (int j = 0; j < this.adjMatrix[i].length; j++) {
     * if (this.adjMatrix[i][j] != 0) {
     * value++;
     * }
     * }
     * values[i] = value;
     * }
     * 
     * int hight = values[0];
     * 
     * for (int x = 0; x < values.length; x++) {
     * if (values[x] > hight) {
     * hight = values[x];
     * }
     * }
     */
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

  public Grafo complement() {
    Grafo graph = new Grafo(this.countNodes);

    for (int i = 0; i < this.adjMatrix.length; i++) {
      for (int j = 0; j < this.adjMatrix[i].length; j++) {
        if (this.adjMatrix[i][j] == 0 && i != j) {
          graph.addEdge(i, j, 1);
        }
      }
    }

    return graph;
  }

  public boolean subgraph(Grafo g2) {
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

  public float density() {
    // E = quantidade de arestas que tem
    // M = maximo de arestas possíveis()
    // E / (M*(M-1))

    float e = this.countEdges;
    float m = this.countNodes;

    return e / (m * (m - 1));
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

  public boolean conexo() {
    return this.busca_largura(0).size() == this.countNodes;
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
}