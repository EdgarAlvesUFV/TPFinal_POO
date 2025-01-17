import java.util.ArrayList;

public class Cliente extends Pessoa implements Conta {
    private String cpf;
    private String senha;
    private ArrayList<Parente> parentes; // lista parentes
    private ArrayList<Avaliacao> avaliacoes; // lista de avaliações
    private ArrayList<Fatura> faturas; // lista de faturas
    private double notaTotal;
    private ArrayList<String> listaCobranca;
    private double saldo;

    public Cliente(String nome, String endereco, String telefone, String cpf, String senha, ArrayList<Parente> parentes,
                   ArrayList<Avaliacao> avaliacoes, ArrayList<Fatura> faturas, double notaTotal, ArrayList<String> listaCobranca, double saldo) {
        super(nome, endereco, telefone);
        this.cpf = cpf;
        this.senha = senha;
        this.parentes = parentes;
        this.avaliacoes = avaliacoes;
        this.faturas = faturas;
        this.notaTotal = notaTotal;
        this.listaCobranca = listaCobranca;
        this.saldo = saldo;
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

    public ArrayList<Parente> getParentes() {
        return parentes;
    }

    public void setParentes(ArrayList<Parente> parentes) {
        this.parentes = parentes;
    }

    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(ArrayList<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public ArrayList<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(ArrayList<Fatura> faturas) {
        this.faturas = faturas;
    }

    public double getNotaTotal() {
        return notaTotal;
    }

    public void setNotaTotal(double notaTotal) {
        this.notaTotal = notaTotal;
    }

    public ArrayList<String> getListaCobranca() {
        return listaCobranca;
    }

    public void setListaCobranca(ArrayList<String> listaCobranca) {
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

    public void simularEmprestimo(int quantia, double taxaJurosAnual, int prazoMes) {
        double jurosMensais = taxaJurosAnual / 12 / 100;
        double parcela = (quantia * jurosMensais) / (1 - Math.pow(1 + jurosMensais, -prazoMes));
        double valorTotal = parcela * prazoMes;

        System.out.println("Simulação de empréstimo:");
        System.out.println("Quantia: R$ " + quantia);
        System.out.println("Parcelas: " + prazoMes);
        System.out.println("Valor mensal: R$ " + parcela);
        System.out.println("Total a pagar: R$ " + valorTotal);
    }

    public void realizarEmprestimo(int quantia, Agiota agiota) {
        double jurosMensais = agiota.getJuros() / 100;
        int prazoMes = agiota.getMaximoParcelas();
        double parcela = (quantia * jurosMensais) / (1 - Math.pow(1 + jurosMensais, -prazoMes));
        double valorTotal = parcela * prazoMes;

        System.out.println("Empréstimo realizado com o agiota: " + agiota.getNome());
        System.out.println("Valor total a pagar: R$ " + valorTotal);
        System.out.println("Parcelas: " + prazoMes);
        System.out.println("Valor mensal: R$ " + parcela);

        saldo += quantia;
        listaCobranca.add("Empréstimo de R$ " + quantia + " com o agiota: " + agiota.getNome());
    }

    public String historicoEmprestimos() {
        StringBuilder historico = new StringBuilder();
        for (String cobranca : listaCobranca) {
            historico.append(cobranca).append("\n");
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

    public void avaliarAgiota(int idConta, int estrelas, String comentario) {
        Avaliacao avaliacao = new Avaliacao(idConta, estrelas, comentario);
        avaliacoes.add(avaliacao);
        System.out.println("Agiota avaliado com " + estrelas + " estrelas. Comentário: " + comentario);
    }

    public String verHistoricoCobranca() {
        StringBuilder historico = new StringBuilder();
        for (String cobranca : listaCobranca) {
            historico.append(cobranca).append("\n");
        }
        return historico.toString();
    }
}
