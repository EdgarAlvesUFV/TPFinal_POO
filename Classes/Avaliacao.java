package edu.edgar;
public class Avaliacao {
    private String descricao;
    private int nota;

    public Avaliacao(String descricao, int nota){
        this.descricao = descricao;
        this.nota = nota;
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
