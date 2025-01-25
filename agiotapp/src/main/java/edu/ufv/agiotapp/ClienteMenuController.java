package edu.ufv.agiotapp;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ClienteMenuController implements ControladorTelas{
    private ControladorConta controladorConta;

    @FXML private Pane extratoPainel; 
    @FXML private Pane simuladorPainel;
    @FXML private Pane cobrancasPainel;
    @FXML private Pane listaAgiotasPainel;
    @FXML private Pane emprestimosPainel;
    @FXML private VBox boxMenuLateral; //set width 41

    @Override
    public void setControladorConta(ControladorConta controladorConta) {
        this.controladorConta = controladorConta;
    }

    @FXML
    private void initialize(){
        boxMenuLateral.setPrefHeight(41);
    }

    @FXML 
    private void hoverMenuIn(){
        boxMenuLateral.setPrefWidth(210);
    }

    @FXML 
    private void hoverMenuOut(){
        boxMenuLateral.setPrefWidth(41);
    }

    @FXML 
    private void ativarPainelExtrato(){
        extratoPainel.toFront();
    } 
    @FXML 
    private void ativarPainelEmprestimo(){
        emprestimosPainel.toFront();
    } 
    @FXML 
    private void ativarPainelSimulador(){
        simuladorPainel.toFront();
    } 
    @FXML 
    private void ativarPainelCobranca(){
        cobrancasPainel.toFront();
    } 
    @FXML 
    private void ativarPainelListaAgiotas(){
        listaAgiotasPainel.toFront();
    } 
}
