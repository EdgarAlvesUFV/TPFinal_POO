package edu.ufv.agiotapp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControladorConta {
    public BancoDeDados bancoDeDados;

    public ControladorConta(){
        this.bancoDeDados = new BancoDeDados();
        this.bancoDeDados.carregarContas();
    }

    public Conta login(String usuario, String senha){
        return bancoDeDados.login(usuario, senha);
    }

    private void redirecionarSistema(Conta conta, Scanner scanner) {
        if (conta == null) {
            System.out.println("Tentativas máxima de login realizadas, tente novamente mais tarde!");
        }
        else if ("Cliente".equals(conta.getTipoConta())) {
            clienteMenu((Cliente) conta, scanner);
        } else if ("Agiota".equals(conta.getTipoConta())) {
            agiotaMenu((Agiota) conta, scanner);
        }
    }

    private void clienteMenu(Cliente cliente, Scanner scanner) {
        int opcao;
        do {
            System.out.println("\nMenu do Cliente:");
            System.out.println("1 - Editar perfil");
            System.out.println("2 - Ver extrato");
            System.out.println("3 - Simular empréstimo");
            System.out.println("4 - Histórico de empréstimos");
            System.out.println("5 - Pagar parcela");
            System.out.println("6 - Ver histórico de cobranças");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.print("Digite o novo endereço: ");
                    String novoEndereco = scanner.nextLine();
                    System.out.print("Digite o novo telefone: ");
                    String novoTelefone = scanner.nextLine();
                    cliente.setEndereco(novoEndereco);
                    cliente.setTelefone(novoTelefone);
                    System.out.println("Perfil atualizado com sucesso!");
                    bancoDeDados.salvarContas();
                    break;

                case 2:
                    cliente.verExtrato();
                    break;

                case 3:
                    System.out.print("Digite o valor do empréstimo: ");
                    int valorEmprestimo = scanner.nextInt();
                    System.out.print("Digite a taxa de juros anual (%): ");
                    double taxaJuros = scanner.nextDouble();
                    System.out.print("Digite o prazo em meses: ");
                    int prazo = scanner.nextInt();
                    cliente.simularEmprestimo(valorEmprestimo, taxaJuros, prazo);
                    break;

                case 4:
                    System.out.println("Histórico de Empréstimos:");
                    System.out.println(cliente.historicoEmprestimos());
                    break;

                case 5:
                    System.out.print("Digite o ID da fatura: ");
                    int idFatura = scanner.nextInt();
                    System.out.print("Digite o ID da parcela: ");
                    int idParcela = scanner.nextInt();
                    String resultadoPagamento = cliente.pagarParcela(idFatura, idParcela);
                    System.out.println(resultadoPagamento);
                    break;

                case 6:
                    System.out.println("Histórico de Cobranças:");
                    System.out.println(cliente.verHistoricoCobranca());
                    break;

                case 0:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void agiotaMenu(Agiota agiota, Scanner scanner) {
        // Exibir opções do agiota
        System.out.println("Agiota Logou");
    }

    public boolean verificarCadastro(String cpf){
        for (Cliente cliente : bancoDeDados.getListaClientes()) {
            if (cliente.getCpf().equals(cpf)) {
                return true;
            }
        }
        for (Agiota agiota : bancoDeDados.getListaAgiotas()) {
            if (agiota.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    public void cadastrarCliente(String cpf, String senha, String nome, String endereco, String telefone,
        List<Parente> listaParentes){
        Cliente cliente = new Cliente(bancoDeDados.getProximoId(), cpf, nome, senha, endereco, telefone);
        cliente.setParentes(listaParentes);
        bancoDeDados.adicionarCliente(cliente);
        bancoDeDados.listarAgiotas();
        bancoDeDados.listarClientes();
        bancoDeDados.salvarContas();
    }
    public void cadastrarAgiota(String cpf, String senha, String nome, String descricao, Double saldo,
    Double juros, Boolean aceitaParcelado, int maxParcelas){
        Agiota agiota = new Agiota(bancoDeDados.getProximoId(), cpf, nome, senha, descricao, saldo, juros, aceitaParcelado, maxParcelas);
        bancoDeDados.adicionarAgiota(agiota);
        bancoDeDados.listarAgiotas();
        bancoDeDados.listarClientes();
        bancoDeDados.salvarContas();
    }

    private void cadastrarCliente(Scanner scanner){
        System.out.println("Digite seu CPF: ");
        String cpf = scanner.nextLine();
        if(verificarCadastro(cpf)){
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
        Cliente cliente = new Cliente(bancoDeDados.getProximoId(), cpf, nome, senha, endereco, telefone);
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
            cliente.setParentes(listaParentes);
        }
        bancoDeDados.adicionarCliente(cliente);
        bancoDeDados.salvarContas();
        clienteMenu(cliente, scanner);
    }

    private void cadastrarAgiota(Scanner scanner){
        System.out.println("Digite seu CPF: ");
        String cpf = scanner.nextLine();
        if(verificarCadastro(cpf)){
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
            agiota = new Agiota(bancoDeDados.getProximoId(), cpf, nome, senha, descricao, saldo, juros, aceitaParcelado, maximoParcelas);
        }
        else{
            //Criar construtor sem aceitaparcelado e maximo parcelas
            agiota = new Agiota(bancoDeDados.getProximoId(), cpf, nome, senha, descricao, saldo, juros);
        }
        bancoDeDados.adicionarAgiota(agiota);
        bancoDeDados.salvarContas();
        agiotaMenu(agiota, scanner);
    }

    public void menuCadastro(String opcaoCadastro, Scanner scanner){
        if (opcaoCadastro.equals("Cliente") ) {
            cadastrarCliente(scanner);
        } else if (opcaoCadastro.equals("Agiota") ) {
            cadastrarAgiota(scanner);
        }
    }
}
