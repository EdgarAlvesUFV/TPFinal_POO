import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BancoDeDados {
    private List<Cliente> clientes;
    private List<Agiota> agiotas;
    private int contadorID = 0;

    public BancoDeDados() {
        this.clientes = new ArrayList<>();
        this.agiotas = new ArrayList<>();
    }

    public int getProximoId() {
        return ++contadorID;
    }

    public void salvarContas() {
        try (BufferedWriter writerClientes = new BufferedWriter(new FileWriter("clientes.txt"));
             BufferedWriter writerAgiotas = new BufferedWriter(new FileWriter("agiotas.txt"))) {
            for (Cliente cliente : clientes) {
                String parentesSerializados = serializarParentes(cliente.getListaParentes());
                String avaliacoesSerializadas = serializarAvaliacoes(cliente.getListaAvaliacoes());
                String faturasSerializadas = serializarFaturas(cliente.getListaFaturas());
                String historicoSerializado = serializarHistoricoCobranca(cliente.getHistoricoCobranca());
                writerClientes.write(cliente.getIdCliente() + "," + cliente.getCpf() + "," + cliente.getNome() + "," +
                cliente.getEndereco() + "," + cliente.getTelefone() + "," + cliente.getSenha() + "," +
                cliente.getSaldo() + "," + cliente.getNotaTotal() + "," + avaliacoesSerializadas + "," + 
                parentesSerializados + "," + faturasSerializadas + "," + historicoSerializado);
                writerClientes.newLine();
            }
        
            for (Agiota agiota : agiotas) {
                String clientesSerializados = serializarClientes(agiota.getListaClientes());
                String avaliacoesSerializadas = serializarAvaliacoes(agiota.getListaAvaliacoes());
                writerAgiotas.write(agiota.getIdAgiota() + "," + agiota.getCpf() + "," + agiota.getNome() + "," + 
                agiota.getSenha() + "," + agiota.getDescricao() + "," + agiota.getSaldo() + "," +
                agiota.getJuros() + "," + agiota.isAceitaParcelado() + "," + agiota.getMaximoParcelas() + "," + 
                agiota.getNotaTotal() + "," + avaliacoesSerializadas + "," + clientesSerializados);
                writerAgiotas.newLine();
            }
    
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public void carregarContas() {
        try (BufferedReader readerClientes = new BufferedReader(new FileReader("clientes.txt"));
             BufferedReader readerAgiotas = new BufferedReader(new FileReader("agiotas.txt"))) {
            // id, cpf, nome, endereco, telefone, senha, saldo, notaTotal, avaliacoes, parentes, faturas, historico
            // Carregando clientes
            String linha;
            while ((linha = readerClientes.readLine()) != null) {
                String[] partes = linha.split(",");
                System.out.println(partes[9] + "\n");
                Cliente cliente = new Cliente(
                        Integer.parseInt(partes[0]), // Id
                        partes[1], // CPF
                        partes[2], // nome
                        partes[3], // endereco
                        partes[4],  // telefone
                        partes[5], // senha
                        Double.parseDouble(partes[6]), //saldo
                        Double.parseDouble(partes[7]) // notaTotal
                );
                List<Avaliacao> avaliacoes = desserializarAvaliacoes(partes[8]);
                List<Parente> parentes = desserializarParentes(partes[9]);
                List<Fatura> faturas = desserializarFaturas(partes[10]);
                List<String> historico = desserializarHistoricoCobranca(partes[11]);
                cliente.setAvaliacoes(avaliacoes);
                cliente.setParentes(parentes);
                cliente.setFaturas(faturas);
                cliente.setListaCobranca(historico);
                clientes.add(cliente);
                getProximoId();
            }
        // id, cpf, nome, senha, descricao, saldo, taxa, aceitaParcelado, maximoParcelas, notaTotal, avaliacoes, clientes
            // Carregando agiotas
            while ((linha = readerAgiotas.readLine()) != null) {
                String[] partes = linha.split(",");
                Agiota agiota = new Agiota(
                        Integer.parseInt(partes[0]), // id
                        partes[1], // cpf
                        partes[2], // nome
                        partes[3], // senha
                        partes[4], // descricao
                        Double.parseDouble(partes[5]), // saldo
                        Double.parseDouble(partes[6]), // taxa
                        Boolean.parseBoolean(partes[7]), // aceitaParcelado
                        Integer.parseInt(partes[8]), // maximoParcelas
                        Double.parseDouble(partes[9]) // notaTotal
                );
                List<Avaliacao> listaAvaliacoes = desserializarAvaliacoes(partes[10]);  // listaAvaliacoes
                List<Cliente> listaClientes = desserializarClientes(partes[11]);// listaClientes
                agiota.setListaAvaliacoes(listaAvaliacoes);
                agiota.setListaClientes(listaClientes);
                agiotas.add(agiota);
                getProximoId();
            }
    
            System.out.println("Dados carregados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    private String serializarParentes(List<Parente> parentes) {
        StringBuilder builder = new StringBuilder();
        
        if (parentes == null || parentes.isEmpty()) {
            builder.append(" ");
        } else {
            for (Parente parente : parentes) {
                // Formato de cada parente: "nome:telefone"
                builder.append(parente.getNome()).append(":")
                       .append(parente.getEndereco()).append(":")
                       .append(parente.getTelefone()).append(":")
                       .append(parente.getParentesco()).append(";");
            }
            // Remove o último ponto e vírgula (;)
            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1);
            }
        }
        
        return builder.toString();
    }
    
    private String serializarAvaliacoes(List<Avaliacao> avaliacoes) {
        StringBuilder builder = new StringBuilder();
        
        if (avaliacoes == null ||avaliacoes.isEmpty()) {
            builder.append(" ");
        } else {
        for (Avaliacao avaliacao : avaliacoes) {
            builder.append(avaliacao.getDescricao()).append(":")
                   .append(avaliacao.getNota()).append(";");
            }
            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1);
            }
        }
        return builder.toString();
    }
    private String serializarFaturas(List<Fatura> faturas) {
        StringBuilder builder = new StringBuilder();
        if (faturas == null || faturas.isEmpty()) {
            builder.append(" ");
        } else {
        for (Fatura fatura : faturas) {

            String parcelasSerializadas = serializarParcelas(fatura.getListaParcelas());
            builder.append(fatura.getIdFatura()).append(":")  
                   .append(parcelasSerializadas).append(":")  
                   .append(fatura.getQuantidadeParcelas()).append(":")
                   .append(fatura.getDataEmissao()).append(":")  
                   .append(fatura.getValorTotal()).append(":")  
                   .append(fatura.getIdContaCliente()).append(":") 
                   .append(fatura.getIdContaAgiota()).append(";"); 
            }
            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1);
            }
        }
        return builder.toString();
    }
    
    private String serializarParcelas(List<Parcela> parcelas) {
        StringBuilder builder = new StringBuilder();
        if (parcelas == null || parcelas.isEmpty()) {
            builder.append(" ");
        } else {
        for (Parcela parcela : parcelas) {
            builder.append(parcela.getIdParcela()).append(":")
                   .append(parcela.getValor()).append(":")
                   .append(parcela.getDataVencimento()).append(":")
                   .append(parcela.getDataPagamento()).append(";");
            }
            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1);
            }
        }
        return builder.toString();
    }
    
    private String serializarClientes(List<Cliente> clientes) {
        StringBuilder builder = new StringBuilder();
        if (clientes == null || clientes.isEmpty()) {
            builder.append(" ");
        } else {
        for (Cliente cliente : clientes) {
            builder.append(cliente.getIdCliente()).append(";"); // Apenas o ID
            }
            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1); // Remove o último ";"
            }
        }
        return builder.toString();
    }
    
    private String serializarHistoricoCobranca(List<String> historicoCobranca) {
        if (historicoCobranca == null || historicoCobranca.isEmpty()) {
            return " "; 
        }
        return String.join(";", historicoCobranca);
    }
    
    private List<String> desserializarHistoricoCobranca(String historicoSerializado) {
        if (historicoSerializado == null || historicoSerializado.isEmpty()) {
            return new ArrayList<>();
        }
        String[] itens = historicoSerializado.split(";");
        return new ArrayList<>(Arrays.asList(itens)); 
    }


    private List<Cliente> desserializarClientes(String clientesSerializados) {
        List<Cliente> clientes = new ArrayList<>();
        if (clientesSerializados == null || clientesSerializados.isEmpty()) {
            return clientes; // Retorna lista vazia
        }
    
        String[] ids = clientesSerializados.split(";");
        for (String idStr : ids) {
            try {
                int idCliente = Integer.parseInt(idStr.trim());
    
                // Busca o cliente na lista de clientes
                for (Cliente cliente : this.listarClientes()) {
                    if (cliente.getIdCliente() == idCliente) {
                        clientes.add(cliente);
                        break; // Interrompe a busca após encontrar o cliente
                    }
                }
    
            } catch (NumberFormatException e) {
            }
        }
        return clientes;
    }    

    private List<Parente> desserializarParentes(String parentesSerializados) {
        List<Parente> parentes = new ArrayList<>();
        if (parentesSerializados == null || parentesSerializados.isEmpty()) {
            return parentes;
        }
    
        String[] parentesArray = parentesSerializados.split(";");
        for (String parenteStr : parentesArray) {
            String[] atributos = parenteStr.split(":");
            if (atributos.length == 4) {
                String nome = atributos[0];
                String endereco = atributos[1];
                String telefone = atributos[2];
                String parentesco = atributos[3];
                parentes.add(new Parente(nome, endereco, telefone, parentesco)); // Crie o objeto Parente
            }
        }
        return parentes;
    }
    
    private List<Avaliacao> desserializarAvaliacoes(String avaliacoesSerializados) {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        if (avaliacoesSerializados == null || avaliacoesSerializados.isEmpty()) {
            return avaliacoes;
        }
    
        String[] avaliacoesArray = avaliacoesSerializados.split(";");
        for (String avaliacoestr : avaliacoesArray) {
            String[] atributos = avaliacoestr.split(":");
            if (atributos.length == 3) {
                String descricao = atributos[0];
                int nota = Integer.parseInt(atributos[1]);
                int idconta = Integer.parseInt(atributos[2]);
                avaliacoes.add(new Avaliacao(descricao, nota, idconta)); // Crie o objeto Parente
            }
        }
        return avaliacoes;
    }

    private List<Fatura> desserializarFaturas(String faturasSerializadas) {
        List<Fatura> faturas = new ArrayList<>();
        if (faturasSerializadas == null || faturasSerializadas.isEmpty()) {
            return faturas;
        }
    
        String[] faturasArray = faturasSerializadas.split(";");
        for (String faturaStr : faturasArray) {
            String[] atributos = faturaStr.split(":");
            if (atributos.length == 7) {
                int idFatura = Integer.parseInt(atributos[0]);
                List<Parcela> parcelas = desserializarParcelas(atributos[1]);
                int quantidadeParcelas = Integer.parseInt(atributos[2]);
                LocalDate dataEmissao = LocalDate.parse(atributos[3]);
                double valorTotal = Double.parseDouble(atributos[4]);
                int idContaCliente = Integer.parseInt(atributos[5]);
                int idContaAgiota = Integer.parseInt(atributos[6]);
    
                // Cria o objeto Fatura
                Fatura fatura = new Fatura(idFatura, parcelas, quantidadeParcelas, dataEmissao, valorTotal, idContaCliente, idContaAgiota);
                faturas.add(fatura);
            }
        }
        return faturas;
    }

    private List<Parcela> desserializarParcelas(String parcelasSerializadas) {
        List<Parcela> parcelas = new ArrayList<>();
        if (parcelasSerializadas == null || parcelasSerializadas.isEmpty()) {
            return parcelas;
        }
    
        String[] parcelasArray = parcelasSerializadas.split(";");
        for (String parcelaStr : parcelasArray) {
            String[] atributos = parcelaStr.split(":");
            if (atributos.length == 4) {
                int idParcela = Integer.parseInt(atributos[0]);
                double valor = Double.parseDouble(atributos[1]);
                LocalDate dataVencimento = LocalDate.parse(atributos[2]);
                LocalDate dataPagamento = LocalDate.parse(atributos[3]);
    
                // Cria o objeto Parcela
                Parcela parcela = new Parcela(idParcela, valor, dataVencimento, dataPagamento);
                parcelas.add(parcela);
            }
        }
        return parcelas;
    }
    
    
    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    // Adicionar agiota
    public void adicionarAgiota(Agiota agiota) {
        agiotas.add(agiota);
    }

    // Listar clientes
    public List<Cliente> listarClientes() {
        return clientes;
    }

    // Listar agiotas
    public List<Agiota> listarAgiotas() {
        return agiotas;
    }

    public Conta login(Scanner scanner) {
        for (int i = 1; i <= 3;i++){
            System.out.println("Digite o CPF ou Nome de Usuário:");
            String identificador = scanner.nextLine();
    
            System.out.println("Digite a senha:");
            String senha = scanner.nextLine();
    
            Conta conta = autenticarConta(identificador, senha);
            if (conta != null) {
                System.out.println("Login bem-sucedido! Bem-vindo(a), " + (conta instanceof Cliente ? ((Cliente) conta).getNome() : ((Agiota) conta).getNome()));
                return conta;
            } else {
                System.out.println("Credenciais inválidas! " + (3-i) + " tentativas restantes.");
            }
        }
        Conta conta = null;
        scanner.close();
        return conta;
    }

    private Conta autenticarConta(String identificador, String senha) {
        for (Cliente cliente : this.listarClientes()) {
            if (cliente.getCpf().equals(identificador) && cliente.getSenha().equals(senha)) {
                return cliente;
            }
        }
        for (Agiota agiota : this.listarAgiotas()) {
            if (agiota.getCpf().equals(identificador) && agiota.getSenha().equals(senha)) {
                return agiota;
            }
        }
        return null;
    }
}
