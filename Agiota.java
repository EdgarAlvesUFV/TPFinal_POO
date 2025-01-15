import java.util.ArrayList;
import java.util.List;

public class Agiota implements Conta {
    private String cpf;
    private String nome;
    private String senha;
    private List<Cliente> listaClientes;
    private String descricao;
    private double saldo;
    private double juros;
    private boolean aceitaParcelado;
    private int maximoParcelas;
    private List<Double> listaAvaliacoes;
    private double notaTotal;

    //construtor
    public Agiota(String cpf, String nome, String senha, String descricao, double saldo, double juros,
                  boolean aceitaParcelado, int maximoParcelas) {
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.descricao = descricao;
        this.saldo = saldo;
        this.juros = juros;
        this.aceitaParcelado = aceitaParcelado;
        this.maximoParcelas = maximoParcelas;
        this.listaClientes = new ArrayList<>();
        this.listaAvaliacoes = new ArrayList<>();
        this.notaTotal = 0.0;
    }

    //getters e setters
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
    
    public List<Double> getListaAvaliacoes() { 
        return listaAvaliacoes; 
        }

    public void setListaAvaliacoes(List<Double> listaAvaliacoes) { 
        this.listaAvaliacoes = listaAvaliacoes; 
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

    public String verExtrato() {
        return "Extrato de " + nome + " - Saldo: " + saldo;
    }

    public String listarClientes() {
        StringBuilder sb = new StringBuilder("Clientes de " + nome + ": ");
        for (Cliente cliente : listaClientes) {
            sb.append(cliente.getNome()).append(", ");
        }
        return sb.toString();
    }

    public void avaliarCliente(int idConta) {
        //falta implementar
    }

    public void relatarCobranca(int idConta, String mensagem) {
        //falta implementar
    }
}
