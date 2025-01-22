import java.util.ArrayList;
import java.util.List;

public class Agiota implements Conta {
    private int idAgiota;
    private String cpf;
    private String nome;
    private String senha;
    private List<Cliente> listaClientes;
    private String descricao;
    private double saldo;
    private double juros;
    private boolean aceitaParcelado;
    private int maximoParcelas;
    private List<Avaliacao> avaliacoes;
    private double notaTotal;

    //construtor
    public Agiota(int idAgiota, String cpf, String nome, String senha, String descricao, double saldo, double juros,
                   boolean aceitaParcelado, int maximoParcelas, double notaTotal) {
        this.idAgiota = idAgiota;
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.descricao = descricao;
        this.saldo = saldo;
        this.juros = juros;
        this.aceitaParcelado = aceitaParcelado;
        this.maximoParcelas = maximoParcelas;
        this.listaClientes = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.notaTotal = notaTotal;
    }

    public Agiota(int idAgiota, String cpf, String nome, String senha, String descricao, double saldo, double juros,
                   boolean aceitaParcelado, int maximoParcelas) {
        this.idAgiota = idAgiota;
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.descricao = descricao;
        this.saldo = saldo;
        this.juros = juros;
        this.aceitaParcelado = aceitaParcelado;
        this.maximoParcelas = maximoParcelas;
        this.listaClientes = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.notaTotal = 0.0;
    }

    public Agiota(int idAgiota, String cpf, String nome, String senha, String descricao, double saldo, double juros) {
        this.idAgiota = idAgiota;
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.descricao = descricao;
        this.saldo = saldo;
        this.juros = juros;
        this.aceitaParcelado = false;
        this.maximoParcelas = 0;
        this.listaClientes = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.notaTotal = 0.0;
    }

    //getters e setters

    public int getIdAgiota() {
        return idAgiota;
    }

    public void setIdAgiota(int idAgiota) {
        this.idAgiota = idAgiota;
    }

    public String getCpf() { 
        return cpf; 
        }

    public void setCpf(String cpf) { 
        this.cpf = cpf; 
        }
    
    public String getNome() { 
        return nome; 
        }

    public void setNome(String nome) { 
        this.nome = nome; 
        }    

    public String getSenha() { 
        return senha; 
        }

    public void setSenha(String senha) { 
        this.senha = senha; 
        }
    
    public List<Cliente> getListaClientes() { 
        return listaClientes;
        }

    public void setListaClientes(List<Cliente> listaClientes) { 
        this.listaClientes = listaClientes; 
        }
    
    public String getDescricao() { 
        return descricao; 
        }

    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
        }

    public double getSaldo() { 
        return saldo; 
        }

    public void setSaldo(double saldo) { 
        this.saldo = saldo; 
        }

    public double getJuros() { 
        return juros; 
        }

    public void setJuros(double juros) { 
        this.juros = juros; 
        }

    public boolean isAceitaParcelado() { 
        return aceitaParcelado; 
        }

    public void setAceitaParcelado(boolean aceitaParcelado) { 
        this.aceitaParcelado = aceitaParcelado;
        }
    
    public int getMaximoParcelas() { 
        return maximoParcelas; 
        }

    public void setMaximoParcelas(int maximoParcelas) { 
        this.maximoParcelas = maximoParcelas; 
        }
    
    public List<Avaliacao> getListaAvaliacoes() {
        return avaliacoes;
        }

    public void setListaAvaliacoes(List<Avaliacao> listaAvaliacoes) { 
        this.avaliacoes = listaAvaliacoes; 
        }
    
    public double getNotaTotal() { 
        return notaTotal; 
        }

    public void setNotaTotal(double notaTotal) { 
        this.notaTotal = notaTotal; 
        }

    //metodos
    public String getTipoConta() {
        return "Agiota";
    }

    @Override
    public void verExtrato() {
        System.out.println("Extrato do cliente: " + getNome() + ": Saldo = R$ " + saldo);
    }

    public String listarClientes() {
        StringBuilder sb = new StringBuilder("Clientes de " + nome + ": ");
        for (Cliente cliente : listaClientes) {
            sb.append(cliente.getNome()).append(", ");
        }
        return sb.toString();
    }

    @Override
    public void receberAvaliacao(int estrelas, String comentario) {
        Avaliacao avaliacao = new Avaliacao(comentario, estrelas, this.idAgiota);
        avaliacoes.add(avaliacao);
        System.out.println("Agiota avaliado com " + estrelas + " estrelas. Comentário: " + comentario);
    }

    public void relatarCobranca(int idConta, String mensagem) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getIdCliente() == idConta) {
                cliente.adicionarCobranca(mensagem);
            }
        }
    }
}
