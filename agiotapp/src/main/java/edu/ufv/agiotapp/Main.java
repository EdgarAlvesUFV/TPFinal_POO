package edu.ufv.agiotapp;
import java.util.Scanner;
/*      
public class Main {
    public static void main(String[] args) {
        ControladorConta controladorConta = new ControladorConta();
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            System.out.println("========================");
            System.out.println("   Sistema de Contas   ");
            System.out.println("========================");
            System.out.println("1 - Login");
            System.out.println("2 - Cadastrar Cliente");
            System.out.println("3 - Cadastrar Agiota");
            System.out.println("4 - Listar Clientes");
            System.out.println("5 - Listar Agiotas");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    controladorConta.login(scanner);
                    break;
                case 2:
                    controladorConta.menuCadastro("Cliente", scanner);
                    break;
                case 3:
                    controladorConta.menuCadastro("Agiota", scanner);
                    break;
                case 4:
                    System.out.println("Lista de Clientes:");
                    for (Cliente cliente : controladorConta.bancoDeDados.listarClientes()) {
                        System.out.println(cliente.getNome());
                    }
                    break;
                case 5:
                    System.out.println("Lista de Agiotas:");
                    for (Agiota agiota : controladorConta.bancoDeDados.listarAgiotas()) {
                        System.out.println(agiota.getNome());
                    }
                    break;
                case 6:
                    System.out.println("Saindo do sistema...");
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        scanner.close();
    }
}
*/