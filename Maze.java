import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class Maze {


  public GraphList grafo;
  private static final int INF = 999999;
  int qtdColuna;
  int qtdLinha;
  char[][] labirinto = new char[qtdLinha][qtdColuna];

  public Maze(){
    
  }
  public Maze(String fileName) throws IOException {

    LineNumberReader lnr = new LineNumberReader(new FileReader(fileName));
    BufferedReader reader = new BufferedReader(new FileReader(fileName));

    lnr.skip(INF);

    qtdLinha = lnr.getLineNumber();
    String line;
    line = reader.readLine();
    qtdColuna = line.length();
    this.grafo = new GraphList(qtdLinha * qtdColuna);

    int i = 0;

    labirinto = new char[qtdLinha][qtdColuna];
    
    labirinto[i] = line.toCharArray();
    i++;
    // SALVANDO LABIRINTO EM MATRIZ DE CARACTER
    while (reader.ready()) {
      line = reader.readLine();
      labirinto[i] = line.toCharArray();
      i++;
    }

    
    System.out.println(this.toString());
    
    
    reader.close();
    lnr.close();
    
  }

  
  public String toString() {
    String str = "";
    for (int u = 0; u < labirinto.length; u++) {
      for (int v = 0; v < labirinto[u].length; v++) {
        str += labirinto[u][v];
      }
      str += "\n";
    }
    return str;
  }
  
  
  public void imprimirResultado(ArrayList <Integer> caminho) {
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_YELLOW = "\u001B[33m";

    for (int u = 0; u < labirinto.length; u++) {
      for (int v = 0; v < labirinto[u].length; v++) {
        if (caminho.contains(u * qtdColuna + v)) {
          System.out.printf(ANSI_YELLOW
                           + "%c"
                           + ANSI_RESET, '*');
        } else {
          System.out.printf("%c", labirinto[u][v]);
        }
      }
      System.out.println();
    }

  }

  

  public void transformaGrafo() {
    //TRANSFORMA LABIRINTO EM GRAFO
    int source;
    int sink;
    int s = 0;
    int d = 0;

    for (int u = 0; u < qtdLinha; u++) {
      for (int v = 0; v < qtdColuna; v++) {

        if (labirinto[u][v] == ' ' || labirinto[u][v] == 'S' || labirinto[u][v] == 'E') {
          source = u * qtdColuna + v;
          sink = source + 1;
          if (labirinto[u][v] == 'S') {
            s = source;
          }
          if (labirinto[u][v] == 'E') {
            d = source;
          }
          if (v < qtdColuna - 1
              && (labirinto[u][v + 1] == ' ' || labirinto[u][v + 1] == 'S' || labirinto[u][v + 1] == 'E')) {
            this.grafo.addEdgeUnoriented((source), (sink), 1);
          }
          if (u < qtdLinha - 1
              && (labirinto[u + 1][v] == ' ' || labirinto[u + 1][v] == 'S' || labirinto[u + 1][v] == 'E')) {
            this.grafo.addEdgeUnoriented((source), ((u + 1) * qtdColuna + v), 1);
          }
        }
      }
    }

    ArrayList<Integer> caminho = new ArrayList<>();
    caminho = this.grafo.bellmanfordMelhorado(s, d);

    this.imprimirResultado(caminho);


  }


}


