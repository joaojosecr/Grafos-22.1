import java.io.IOException;

class Main {
  public static void main(String[] args) throws IOException{


    String grafo = "g2.txt";
    System.out.println(grafo);
    Grafo g2 = new Grafo(grafo);
    System.out.println(g2);
    
    g2.ord_top();
   

    
  }
}