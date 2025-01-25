package edu.ufv.agiotapp;
public class Parente extends Pessoa{
    String parentesco;

    public Parente(String nome, String endereco, String telefone, String parentesco) {
        super(nome, endereco, telefone);
        this.parentesco = parentesco;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
    
}