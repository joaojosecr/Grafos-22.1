import java.io.IOException;
import java.util.Scanner;

class Main {
  public static void main(String[] args) throws IOException {

    int opcao = 0;
    Scanner scan = new Scanner(System.in);
    String filename="toy.txt";
    
    
    while (opcao != 3) {
      System.out.println("\n\nInforme uma tarefa: \n1 - Caminho minimo\n2 - Labirinto\n3 - Sair");
      opcao = scan.nextInt();

      switch (opcao) {
        case 1:
          int selecGraf = 7;
          System.out.print("\n\nCaminho minimo selecionado...\n");
          GraphList gl = new GraphList();
          do {
            System.out.println("\nSelecione o grafo:");
            System.out.println("1 - toy");
            System.out.println("2 - facebook_combined");
            System.out.println("3 - rg300_4730");
            System.out.println("4 - rome99c");
            System.out.println("5 - USA-road-dt.DC");
            System.out.println("6 - USA-road-dt.NY");
            System.out.println("7 - sair..");
            selecGraf = scan.nextInt();
            switch (selecGraf) {
              case 1:
                filename = "toy";
                gl = new GraphList("cm\\" + filename + ".txt");
                break;
              case 2:
                filename = "facebook_combined";
                gl = new GraphList("cm\\" + filename + ".txt");
                break;
              case 3:
                filename = "rg300_4730";
                gl = new GraphList("cm\\" + filename + ".txt");
                break;
              case 4:
                filename = "rome99c";
                gl = new GraphList("cm\\" + filename + ".txt");
                break;
              case 5:
                filename = "USA-road-dt.DC";
                gl = new GraphList("cm\\" + filename + ".txt");
                break;
              case 6:
                filename = "USA-road-dt.NY";
                gl = new GraphList("cm\\" + filename + ".txt");
                break;
              case 7:
                System.out.println("\nSair selecionado...\n");
                break;
              default:
                System.out.println("\nArquivo selecionado inválido.\n");
                break;

            }
          } while (selecGraf > 7);

          if (selecGraf < 7) {

            // ESCOLHENDO ORIGEM EXISTENTE NO GRAFO
            int origem = 0;
            do {
              System.out.println("\nDigite uma origem entre 0 e " + (gl.getCountNodes() - 1));
              origem = scan.nextInt();
              if (origem < 0 || origem > gl.getCountNodes() - 1) {
                System.out.println("\nValor de origem inválido");
              }
            } while (origem < 0 || origem > gl.getCountNodes() - 1);

            // ESCOLHENDO DESTINO EXISTENTE NO GRAFO
            int destino = 0;
            do {
              System.out.println("\nDigite um destino entre 0 e " + (gl.getCountNodes() - 1));
              destino = scan.nextInt();
              if (destino < 0 || destino > gl.getCountNodes()) {
                System.out.println("\nValor de destino inválido!");
              }
            } while (destino < 0 || destino > gl.getCountNodes());

            // ESCOLHENDO METODO 
            int metodo = 0;
            while (metodo != 5) {
              System.out.println(
                  "\n\nInformeo metodo: \n1 - Dijkstra\n2 - Bellman Ford\n3 - Bellman Ford Melhorado\n4 - Floyd Warshal \n5 - Sair");
              metodo = scan.nextInt();

              switch (metodo) {
                case 1:
                  gl.dijkstra(origem, destino);
                  break;
                case 2:
                  gl.bellmanford(origem, destino);
                  break;
                case 3:
                  gl.bellmanfordMelhorado(origem, destino);
                  break;
                case 4:
                  GraphMatrix gm = new GraphMatrix("cm\\" + filename + ".txt");
                  gm.floydWarshall(origem, destino);
                  break;
                case 5:
                  System.out.println("\nSair selecionado...\n");
                  break;
                default:
                  System.out.println("\nValor inválido!\n");
                  break;
              }
            }
          }
          break;
        case 2:
          System.out.print("\n\nLabirinto selecionado...\n");
          Maze maze = new Maze();
          int lab = 9;
          do {

            System.out.println("Selecione um labirinto:");

            System.out.println("1 - toy");
            System.out.println("2 - maze3");
            System.out.println("3 - maze10");
            System.out.println("4 - maze20");
            System.out.println("5 - maze30");
            System.out.println("6 - maze40");
            System.out.println("7 - maze50");
            System.out.println("8 - sair..");
            lab = scan.nextInt();
            switch (lab) {
              case 1:
                filename = "toy";
                maze = new Maze("maze\\" + filename + ".txt");
                maze.transformaGrafo();
                break;
              case 2:
                filename = "maze3";
                maze = new Maze("maze\\" + filename + ".txt");
                maze.transformaGrafo();
                break;
              case 3:
                filename = "maze10";
                maze = new Maze("maze\\" + filename + ".txt");
                maze.transformaGrafo();
                break;
              case 4:
                filename = "maze20";
                maze = new Maze("maze\\" + filename + ".txt");
                maze.transformaGrafo();
                break;
              case 5:
                filename = "maze30";
                maze = new Maze("maze\\" + filename + ".txt");
                maze.transformaGrafo();
                break;
              case 6:
                filename = "maze40";
                maze = new Maze("maze\\" + filename + ".txt");
                maze.transformaGrafo();
                break;
              case 7:
                filename = "maze50";
                maze = new Maze("maze\\" + filename + ".txt");
                maze.transformaGrafo();
                break;
              case 8:
                System.out.println("\nSair selecionado...\n");
                break;
              default:
                System.out.println("\nErro: Arquivo invalido.\n");
                break;
            }
          } while (lab != 8);
          break;
        case 3:
          System.out.println("\nSair selecionado...\n");
          break;
        default:
          System.out.println("\nValor inválido!\n");
      }

    }
    
    scan.close();
    
  }

}