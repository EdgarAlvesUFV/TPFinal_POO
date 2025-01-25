package edu.ufv.agiotapp;
import java.time.LocalDate;
import java.util.List;

public class Fatura {
    private int idFatura;
    private List<Parcela> listaParcelas;
    private int quantidadeParcelas;
    private LocalDate dataEmissao;
    private double valorTotal;
    private int idContaCliente;
    private int idContaAgiota;

    //construtor
    public Fatura(int idFatura, List<Parcela> listaParcelas, int quantidadeParcelas, LocalDate dataEmissao,
                  double valorTotal, int idContaCliente, int idContaAgiota) {
        this.idFatura = idFatura;
        this.listaParcelas = listaParcelas;
        this.quantidadeParcelas = quantidadeParcelas;
        this.dataEmissao = dataEmissao;
        this.valorTotal = valorTotal;
        this.idContaCliente = idContaCliente;
        this.idContaAgiota = idContaAgiota;
    }

    //getters e setters
    public int getIdFatura() { 
        return idFatura; 
        }

    public void setIdFatura(int idFatura) { 
        this.idFatura = idFatura; 
        }
    
    public List<Parcela> getListaParcelas() { 
        return listaParcelas; 
        }

    public void setListaParcelas(List<Parcela> listaParcelas) { 
        this.listaParcelas = listaParcelas; 
        }
    
    public int getQuantidadeParcelas() { 
        return quantidadeParcelas; 
        }

    public void setQuantidadeParcelas(int quantidadeParcelas) { 
        this.quantidadeParcelas = quantidadeParcelas; 
        }
    
    public LocalDate getDataEmissao() { 
        return dataEmissao; 
        }

    public void setDataEmissao(LocalDate dataEmissao) { 
        this.dataEmissao = dataEmissao; 
        }
    
    public double getValorTotal() { 
        return valorTotal; 
        }

    public void setValorTotal(double valorTotal) { 
        this.valorTotal = valorTotal; 
        }
    
    public int getIdContaCliente() { 
        return idContaCliente; 
        }

    public void setIdContaCliente(int idContaCliente) { 
        this.idContaCliente = idContaCliente; 
        }
    
    public int getIdContaAgiota() { 
        return idContaAgiota; 
        }

    public void setIdContaAgiota(int idContaAgiota) { 
        this.idContaAgiota = idContaAgiota; 
        }
}
