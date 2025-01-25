package edu.ufv.agiotapp;
import java.time.LocalDate;

public class Parcela {
    private int idParcela;
    private double valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;

    //construtor
    public Parcela(int idParcela, double valor, LocalDate dataVencimento) {
        this.idParcela = idParcela;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = null; // Inicialmente não paga
    }

    public Parcela(int idParcela, double valor, LocalDate dataVencimento, LocalDate dataPagamento) {
        this.idParcela = idParcela;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento; 
    }

    //getters e setters
    public int getIdParcela() { 
        return idParcela; 
        }

    public void setIdParcela(int idParcela) { 
        this.idParcela = idParcela; 
        }
    
    public double getValor() { 
        return valor; 
        }

    public void setValor(double valor) { 
        this.valor = valor; 
        }
    
    public LocalDate getDataVencimento() { 
        return dataVencimento; 
        }

    public void setDataVencimento(LocalDate dataVencimento) { 
        this.dataVencimento = dataVencimento; 
        }
    
    public LocalDate getDataPagamento() { 
        return dataPagamento; 
        }

    public void setDataPagamento(LocalDate dataPagamento) { 
        this.dataPagamento = dataPagamento; 
        }

    //metodos
    public void pagarParcela() {
        this.dataPagamento = LocalDate.now();
    }

    public String visualizarParcela() {
        return "Parcela " + idParcela + " - Valor: " + valor + " - Vencimento: " + dataVencimento;
    }
}
