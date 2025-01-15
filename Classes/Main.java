package edu.edgar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ControladorConta cont = new ControladorConta();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecione uma opção: ");
        System.out.println("1 - Login:\n2 - Cadastrar");
        int opcao = scanner.nextInt();
        if (opcao == 1) {
            cont.login();
        }
        else if (opcao == 2){
            System.out.println("1 - Cadastrar Cliente:\n2 - Cadastrar Agiota");
            opcao = scanner.nextInt();
            if (opcao == 1) {
                cont.menuCadastro("Cliente");
            }
            else{
                cont.menuCadastro("Agiota");
            }
        }
        scanner.close();
    }
}