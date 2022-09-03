import java.io.IOException;

class Main {
  public static void main(String[] args) throws IOException {

    Grafo g_connected_comp = new Grafo("g_connected_comp.txt", 1);
    Grafo g_ciclo_direcionado = new Grafo("g_ciclo_direcionado.txt");
    //System.out.println(g_connected_comp);
    //System.out.println(g_connected_comp.connected_comp());
    System.out.println(g_ciclo_direcionado);
    System.out.println(g_ciclo_direcionado.has_cycle_oriented());

  }
}