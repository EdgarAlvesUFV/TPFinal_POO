package edu.ufv.agiotapp;
public class Avaliacao {
    private String descricao;
    private int nota;
    private int idConta;

    public int getIdConta() {
        return idConta;
    }
    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }
    public Avaliacao(String descricao, int nota, int idConta){
        this.descricao = descricao;
        this.nota = nota;
        this.idConta = idConta;
    }
    public String getDescricao() {
        return descricao;
    }
    public int getNota() {
        return nota;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setNota(int nota) {
        this.nota = nota;
    }
}
