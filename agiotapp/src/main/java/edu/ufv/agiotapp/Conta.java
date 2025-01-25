package edu.ufv.agiotapp;
public interface Conta {
    public String getTipoConta();
    public void verExtrato();
    public void receberAvaliacao(int estrelas, String comentario);
}