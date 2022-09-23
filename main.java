import java.io.IOException;

class Main {
  public static void main(String[] args) throws IOException {

    Grafo g = new Grafo("g_dijkstra.txt");
    System.out.println(g);
    g.dijkstra(0);
    
  }
}