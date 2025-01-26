package edu.ufv.agiotapp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa implements Conta {
    private int idCliente;
    private String cpf;
    private String senha;
    private List<Parente> parentes;
    private List<Avaliacao> avaliacoes; 
    private List<Fatura> faturas; 
    private double notaTotal;
    private List<String> listaCobranca;
    private double saldo;

    public Cliente(int id, String cpf, String nome, String endereco, String telefone, String senha, 
                   double saldo, double notaTotal) {
        super(nome, endereco, telefone);
        this.idCliente = id;
        this.cpf = cpf;
        this.senha = senha;
        this.parentes = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();;
        this.faturas = new ArrayList<>();;
        this.notaTotal = notaTotal;
        this.listaCobranca = new ArrayList<>();;
        this.saldo = saldo;
    }

    public Cliente(int id, String cpf, String nome, String senha, String endereco, String telefone) {
        super(nome, endereco, telefone);
        this.idCliente = id;
        this.cpf = cpf;
        this.senha = senha;
        this.parentes = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();;
        this.faturas = new ArrayList<>();;
        this.notaTotal = 0.0;
        this.listaCobranca = new ArrayList<>();;
        this.saldo = 0.0;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Parente> getListaParentes() {
        return parentes;
    }

    public void setParentes(List<Parente> parentes) {
        this.parentes = parentes;
    }

    public List<Avaliacao> getListaAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<Fatura> getListaFaturas() {
        return faturas;
    }

    public void setFaturas(List<Fatura> faturas) {
        this.faturas = faturas;
    }

    public double getNotaTotal() {
        return notaTotal;
    }

    public void setNotaTotal(double notaTotal) {
        this.notaTotal = notaTotal;
    }

    public List<String> getHistoricoCobranca() {
        return listaCobranca;
    }

    public void setListaCobranca(List<String> listaCobranca) {
        this.listaCobranca = listaCobranca;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void adicionarParente(Parente p) {
        this.parentes.add(p);
    }

    public void removerParente(String nome) {
        this.parentes.removeIf(parente -> parente.getNome().equals(nome));
    }

    @Override
    public String getTipoConta() {
        return "Cliente";
    }

    @Override
    public void verExtrato() {
        System.out.println("Extrato do cliente: " + getNome() + ": Saldo = R$ " + saldo);
    }

    public double simularEmprestimo(double quantia, double taxaJurosAnual, int prazoMes) {
        double jurosMensais = taxaJurosAnual / 12 / 100;
        double parcela = (quantia * jurosMensais) / (1 - Math.pow(1 + jurosMensais, -prazoMes));
        double valorTotal = parcela * prazoMes;
        return valorTotal;
    }

    public void realizarEmprestimo(double valorEmprestimo, int quantidadeParcelas, Agiota agiota) {

        List<Parcela> parcelas = new ArrayList<>();
        double valorParcela = simularEmprestimo(valorEmprestimo, agiota.getJuros(), quantidadeParcelas) / quantidadeParcelas; 
        for (int i = 0; i < quantidadeParcelas; i++) {
            LocalDate dataVencimento = LocalDate.now().plusMonths(i + 1); // Vencimento a cada mês
            Parcela parcela = new Parcela(gerarIdParcela(), valorParcela, dataVencimento);
            parcelas.add(parcela);
        }
        Fatura novaFatura = new Fatura(gerarIdFatura(), parcelas, quantidadeParcelas, LocalDate.now(), valorEmprestimo, this.idCliente, agiota.getIdAgiota());
        this.faturas.add(novaFatura);
        this.saldo += valorEmprestimo;
    }

    // Método para gerar o ID da fatura (exemplo de implementação)
    private int gerarIdFatura() {
        return (int) (Math.random() * 1000); // Simples exemplo de geração
    }

    // Método para gerar o ID da parcela (exemplo de implementação)
    private int gerarIdParcela() {
        return (int) (Math.random() * 1000); // Simples exemplo de geração
    }

    public String historicoEmprestimos() {
        StringBuilder historico = new StringBuilder();
        for (Fatura fatura : this.faturas) {
            historico.append("Id Agiota: " + fatura.getIdContaAgiota() + "Valor: " + fatura.getValorTotal()).append("\n");
        }
        return historico.toString();
    }

    public String pagarParcela(int idFatura, int idParcela) {
        Fatura fatura = faturas.stream().filter(f -> f.getIdFatura() == idFatura).findFirst().orElse(null);
        if (fatura == null) {
            return "Fatura não encontrada.";
        }

        Parcela parcela = fatura.getListaParcelas().stream().filter(p -> p.getIdParcela() == idParcela).findFirst().orElse(null);
        if (parcela == null) {
            return "Parcela não encontrada.";
        }

        if (saldo >= parcela.getValor()) {
            saldo -= parcela.getValor();
            parcela.pagarParcela();
            return "Parcela paga com sucesso. Valor: R$ " + parcela.getValor();
        } else {
            return "Saldo insuficiente para pagar a parcela. Notificando agiota.";
        }
    }

    @Override
    public void receberAvaliacao(int estrelas, String comentario) {
        Avaliacao avaliacao = new Avaliacao(comentario, estrelas, this.idCliente);
        avaliacoes.add(avaliacao);
        System.out.println("Cliente avaliado com " + estrelas + " estrelas. Comentário: " + comentario);
    }

    public void adicionarCobranca(String cobranca) {
        this.listaCobranca.add(cobranca);
        System.out.println("Cobrança enviada");
    }

    public String verHistoricoCobranca() {
        StringBuilder historico = new StringBuilder();
        for (String cobranca : listaCobranca) {
            historico.append(cobranca).append("\n");
        }
        return historico.toString();
    }
}
