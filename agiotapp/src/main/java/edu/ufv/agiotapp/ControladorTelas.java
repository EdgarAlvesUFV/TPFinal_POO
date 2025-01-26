package edu.ufv.agiotapp;

public interface ControladorTelas {
    void setControladorConta(ControladorConta controladorConta);
    void setControladorContaCliente(ControladorConta controladorConta, Cliente cliente);
    void setControladorContaAgiota(ControladorConta controladorConta, Agiota agiota);
}
