package edu.ufv.agiotapp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Path pathCliente = Paths.get("agiotapp","src", "main", "java", "edu", "ufv", "agiotapp", "Dados", "clientes.txt").toAbsolutePath();
        Path pathAgiotas = Paths.get("agiotapp","src", "main", "java", "edu", "ufv", "agiotapp", "Dados", "agiotas.txt").toAbsolutePath();

        try (BufferedWriter writerClientes = Files.newBufferedWriter(pathCliente);
             BufferedWriter writerAgiotas = Files.newBufferedWriter(pathAgiotas)) {
            for (Cliente cliente : clientes) {
                String parentesSerializados = serializarParentes(cliente.getListaParentes());
                String avaliacoesSerializadas = serializarAvaliacoes(cliente.getListaAvaliacoes());
                String faturasSerializadas = serializarFaturas(cliente.getListaFaturas());
                String historicoSerializado = serializarHistoricoCobranca(cliente.getHistoricoCobranca());
                writerClientes.write(cliente.getIdCliente() + "§" + cliente.getCpf() + "§" + cliente.getNome() + "§" +
                cliente.getEndereco() + "§" + cliente.getTelefone() + "§" + cliente.getSenha() + "§" +
                cliente.getSaldo() + "§" + cliente.getNotaTotal() + "§" + avaliacoesSerializadas + "§" + 
                parentesSerializados + "§" + faturasSerializadas + "§" + historicoSerializado);
                writerClientes.newLine();
            }
        
            for (Agiota agiota : agiotas) {
                String clientesSerializados = serializarClientes(agiota.getListaClientes());
                String avaliacoesSerializadas = serializarAvaliacoes(agiota.getListaAvaliacoes());
                writerAgiotas.write(agiota.getIdAgiota() + "§" + agiota.getCpf() + "§" + agiota.getNome() + "§" + 
                agiota.getSenha() + "§" + agiota.getDescricao() + "§" + agiota.getSaldo() + "§" +
                agiota.getJuros() + "§" + agiota.isAceitaParcelado() + "§" + agiota.getMaximoParcelas() + "§" + 
                agiota.getNotaTotal() + "§" + avaliacoesSerializadas + "§" + clientesSerializados);
                writerAgiotas.newLine();
            }
    
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public void carregarContas() {
        Path pathCliente = Paths.get("agiotapp","src", "main", "java", "edu", "ufv", "agiotapp", "Dados", "clientes.txt").toAbsolutePath();
        Path pathAgiotas = Paths.get("agiotapp","src", "main", "java", "edu", "ufv", "agiotapp", "Dados", "agiotas.txt").toAbsolutePath();

        try (BufferedReader readerClientes = Files.newBufferedReader(pathCliente);
             BufferedReader readerAgiotas = Files.newBufferedReader(pathAgiotas)) {
            // id, cpf, nome, endereco, telefone, senha, saldo, notaTotal, avaliacoes, parentes, faturas, historico
            // Carregando clientes
            String linha;
            while ((linha = readerClientes.readLine()) != null) {
                String[] partes = linha.split("§");
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
                cliente.sethistorico(historico);
                clientes.add(cliente);
                getProximoId();
            }
        // id, cpf, nome, senha, descricao, saldo, taxa, aceitaParcelado, maximoParcelas, notaTotal, avaliacoes, clientes
            // Carregando agiotas
            while ((linha = readerAgiotas.readLine()) != null) {
                String[] partes = linha.split("§");
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
            return " ";
        }

        for (Fatura fatura : faturas) {
            String parcelasSerializadas = serializarParcelas(fatura.getListaParcelas());

            builder.append(fatura.getIdFatura()).append(":")
                .append("[") // Adicionamos colchetes para indicar as parcelas corretamente
                .append(parcelasSerializadas)
                .append("]").append(":") // Fecha as parcelas
                .append(fatura.getQuantidadeParcelas()).append(":")
                .append(fatura.getDataEmissao()).append(":")
                .append(fatura.getValorTotal()).append(":")
                .append(fatura.getIdContaCliente()).append(":")
                .append(fatura.getIdContaAgiota()).append(";");
        }

        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1); // Remove o último ";"
        }
        
        return builder.toString();
    }

    
    private String serializarParcelas(List<Parcela> parcelas) {
        if (parcelas == null || parcelas.isEmpty()) {
            return " ";
        }
    
        StringBuilder builder = new StringBuilder();
        for (Parcela parcela : parcelas) {
            builder.append(parcela.getIdParcela()).append(":")
                   .append(parcela.getValor()).append(":")
                   .append(parcela.getDataVencimento() != null ? parcela.getDataVencimento() : "null").append(":")
                   .append(parcela.getDataPagamento() != null ? parcela.getDataPagamento() : "null").append(",");
        }
        // Remove o último ","
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
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
                for (Cliente cliente : this.getListaClientes()) {
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
        
        if (faturasSerializadas == null || faturasSerializadas.trim().isEmpty()) {
            System.out.println("String de faturas vazia ou nula");
            return faturas;
        }
    
        System.out.println("String de faturas recebida: " + faturasSerializadas);
    
        String[] faturasArray = faturasSerializadas.split(";");
    
        for (String faturaStr : faturasArray) {
            System.out.println("Processando fatura: " + faturaStr);
    
            // Encontra a parte das parcelas usando colchetes []
            int startParcelas = faturaStr.indexOf("[");
            int endParcelas = faturaStr.indexOf("]");
    
            if (startParcelas == -1 || endParcelas == -1) {
                System.err.println("Erro: formato de parcelas inválido na fatura - " + faturaStr);
                continue;
            }
    
            // Extrai as parcelas corretamente
            String parcelasStr = faturaStr.substring(startParcelas + 1, endParcelas);
            List<Parcela> parcelas = desserializarParcelas(parcelasStr);
    
            // Remove as parcelas da string para evitar divisão errada
            String restante = faturaStr.substring(0, startParcelas) + faturaStr.substring(endParcelas + 2);
    
            String[] atributos = restante.split(":");
    
            if (atributos.length < 6) {
                System.err.println("Erro: número insuficiente de atributos na fatura - " + faturaStr);
                continue;
            }
    
            try {
                int idFatura = Integer.parseInt(atributos[0]);
                int quantidadeParcelas = Integer.parseInt(atributos[1]);
                LocalDate dataEmissao = atributos[2].equals("null") ? null : LocalDate.parse(atributos[2]);
                double valorTotal = Double.parseDouble(atributos[3]);
                int idContaCliente = Integer.parseInt(atributos[4]);
                int idContaAgiota = Integer.parseInt(atributos[5]);
    
                // Criando o objeto Fatura corretamente
                Fatura fatura = new Fatura(idFatura, parcelas, quantidadeParcelas, dataEmissao, valorTotal, idContaCliente, idContaAgiota);
                faturas.add(fatura);
    
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter valores numéricos na fatura: " + faturaStr);
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Erro inesperado ao desserializar fatura: " + faturaStr);
                e.printStackTrace();
            }
        }
        return faturas;
    }
    
    
    private List<Parcela> desserializarParcelas(String parcelasSerializadas) {
        List<Parcela> parcelas = new ArrayList<>();
    
        if (parcelasSerializadas == null || parcelasSerializadas.trim().isEmpty() || parcelasSerializadas.equals(" ")) {
            System.out.println("String de parcelas vazia ou nula");
            return parcelas;
        }
    
        System.out.println("String de parcelas recebida: " + parcelasSerializadas);
    
        String[] parcelasArray = parcelasSerializadas.split(",");
        for (String parcelaStr : parcelasArray) {
            System.out.println("Processando parcela: " + parcelaStr);
    
            String[] atributos = parcelaStr.split(":");
    
            if (atributos.length < 4) {
                System.err.println("Erro: número insuficiente de atributos na parcela - " + parcelaStr);
                continue;
            }
    
            try {
                int idParcela = Integer.parseInt(atributos[0]);
                double valor = Double.parseDouble(atributos[1]);
                LocalDate dataVencimento = atributos[2].equals("null") ? null : LocalDate.parse(atributos[2]);
                LocalDate dataPagamento = atributos[3].equals("null") ? null : LocalDate.parse(atributos[3]);
    
                Parcela parcela = new Parcela(idParcela, valor, dataVencimento, dataPagamento);
                parcelas.add(parcela);
    
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter valores numéricos na parcela: " + parcelaStr);
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Erro inesperado ao desserializar parcela: " + parcelaStr);
                e.printStackTrace();
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
    public void listarClientes() {
        for (Cliente cliente : this.clientes){
            System.out.println(cliente.getNome());
        }
    }

    // Listar agiotas
    public void listarAgiotas() {
        for (Agiota agiota : this.agiotas){
            System.out.println(agiota.getNome());
        }
    }

    public Conta login(String identificador, String senha) {
        Conta conta = autenticarConta(identificador, senha);
        if (conta != null) {
            System.out.println("Login bem-sucedido! Bem-vindo(a), " + 
                (conta instanceof Cliente ? ((Cliente) conta).getNome() : ((Agiota) conta).getNome()));
            return conta;
        } else {
            return null;
        }
    }

    private Conta autenticarConta(String identificador, String senha) {
        for (Cliente cliente : this.getListaClientes()) {
            if (cliente.getCpf().equals(identificador) && cliente.getSenha().equals(senha)) {
                return cliente;
            }
        }
        for (Agiota agiota : this.getListaAgiotas()) {
            if (agiota.getCpf().equals(identificador) && agiota.getSenha().equals(senha)) {
                return agiota;
            }
        }
        return null;
    }

    public List<Agiota> getListaAgiotas(){
        return this.agiotas;
    }

    public List<Cliente> getListaClientes(){
        return this.clientes;
    }
}
