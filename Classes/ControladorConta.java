package edu.edgar;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControladorConta {
    private BancoDeDados bancoDeDados;

    public ControladorConta(){
        this.bancoDeDados = new BancoDeDados();
        this.bancoDeDados.carregarContas();
    }

    public void login(){
        redirecionarSistema(bancoDeDados.login());
    }

    private void redirecionarSistema(Conta conta) {
        if (conta == null) {
            System.out.println("Tentativas máxima de login realizadas, tente novamente mais tarde!");
        }
        else if ("Cliente".equals(conta.getTipoConta())) {
            clienteMenu((Cliente) conta);
        } else if ("Agiota".equals(conta.getTipoConta())) {
            agiotaMenu((Agiota) conta);
        }
    }

    private void clienteMenu(Cliente cliente) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MenuCliente");
        System.out.println("Selecione uma das opções abaixo:");
        System.out.println("1 - Editar perfil\n2 - Ver extrato\n3 - Simular empréstimo\n4 - Histórico de empréstimos");
        System.out.println("5 - Pagar parcela\n6 - Ver histórico de cobranças");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        switch (opcao) {
            case 1:
                
                break;
            case 2:
                
                break;
            case 3:
                
                break;
            case 4:
                
                break;
            case 5:
                
                break;
            case 6:
                
                break;
            default:
                break;
        }

        scanner.close();
    }

    private void agiotaMenu(Agiota agiota) {
        // Exibir opções do agiota
        System.out.println("Agiota Logou");
    }

    private boolean verificarCadastro(String cpf){
        for (Cliente cliente : bancoDeDados.listarClientes()) {
            if (cliente.getCpf().equals(cpf)) {
                System.out.println("CPF já cadastrado");
                return true;
            }
        }
        for (Agiota agiota : bancoDeDados.listarAgiotas()) {
            if (agiota.getCpf().equals(cpf)) {
                System.out.println("CPF já cadastrado");
                return true;
            }
        }
        return false;
    }

    private void cadastrarCliente(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite seu CPF: ");
        String cpf = scanner.nextLine();
        if(verificarCadastro(cpf)){
            scanner.close();
            return;
        };
        System.out.println("Digite uma senha: ");
        String senha = scanner.nextLine();
        System.out.println("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.println("Digite seu endereço: ");
        String endereco = scanner.nextLine();
        System.out.println("Digite seu telefone: ");
        String telefone = scanner.nextLine();
        Cliente cliente = new Cliente(cpf, nome, senha, endereco, telefone);
        System.out.println("Deseja adicionar um parente:\n1 - Sim\n2 - Não ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        if (opcao == 1) {
            List<Parente> listaParentes = new ArrayList<>();
            while (true) {
                System.out.println("Digite o nome do parente: ");
                String nomeParente = scanner.nextLine();
                System.out.println("Digite o endereço do parente: ");
                String enderecoParente = scanner.nextLine();
                System.out.println("Digite telefone do parente: ");
                String telefoneParente = scanner.nextLine();
                System.out.println("Digite o parentesco: ");
                String parentesco = scanner.nextLine();
                listaParentes.add(new Parente(nomeParente, enderecoParente, telefoneParente, parentesco));
                System.out.println("Deseja adicionar mais um parente:\n1 - Sim\n2 - Não ");
                int opcaoParente = scanner.nextInt();
                scanner.nextLine();
                if (opcaoParente == 1) {
                    continue;
                }
                else{
                    break;
                }
            }
            cliente.setListaParentes(listaParentes);
        }
        bancoDeDados.adicionarCliente(cliente);
        bancoDeDados.salvarContas();
        scanner.close();
        clienteMenu(cliente);
    }

    private void cadastrarAgiota(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite seu CPF: ");
        String cpf = scanner.nextLine();
        if(verificarCadastro(cpf)){
            scanner.close();
            return;
        };
        System.out.println("Digite uma senha: ");
        String senha = scanner.nextLine();
        System.out.println("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.println("Digite a descrição do seu perfil: ");
        String descricao = scanner.nextLine();
        System.out.println("Digite o seu saldo disponível para empréstimo: ");
        double saldo = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Digite quanto irá cobrar de juros: ");
        double juros = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Aceita pagamento parcelado: \n1 - Sim\n2 - Não");
        int opcaoParcela = scanner.nextInt();
        scanner.nextLine();
        boolean aceitaParcelado = false;
        int maximoParcelas = 0;
        Agiota agiota;
        if (opcaoParcela == 1) {
            aceitaParcelado = true;
            System.out.println("Digite o número máximo de parcelas: ");
            maximoParcelas = scanner.nextInt();
            scanner.nextLine();
            agiota = new Agiota(cpf, nome, senha, descricao, saldo, juros, aceitaParcelado, maximoParcelas);
        }
        else{
            //Criar construtor sem aceitaparcelado e maximo parcelas
            agiota = new Agiota(cpf, nome, senha, descricao, saldo, juros);
        }
        bancoDeDados.adicionarAgiota(agiota);
        bancoDeDados.salvarContas();
        scanner.close();
        agiotaMenu(agiota);
    }

    public void menuCadastro(String opcaoCadastro){
        if (opcaoCadastro.equals("Cliente") ) {
            cadastrarCliente();
        } else if (opcaoCadastro.equals("Agiota") ) {
            cadastrarAgiota();
        }
    }
}
