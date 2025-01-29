package edu.ufv.agiotapp;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AgiotaMenuController implements ControladorTelas{
    private ControladorConta controladorConta;
    private Agiota agiota;
    @FXML private Pane extratoPainel; 
    @FXML private Pane simuladorPainel;
    @FXML private Pane cobrancasPainel;
    @FXML private Pane listaAgiotasPainel;
    @FXML private Pane emprestimosPainel;
    @FXML private VBox boxMenuLateral; //set width 41
    @FXML private VBox listaAgiotasVBox;
    @FXML private VBox simularVbox;
    @FXML private VBox listaCobrancavBox;
    @FXML private VBox listaHistoricovBox;
    @FXML private VBox listaEmprestimosVBox;
    @FXML private GridPane extratoGridPane;
    @FXML private GridPane simuladorGridPane;
    @FXML private Label nomeCliente;
    @FXML private Label statusSimularLabel;
    @FXML private TextField simularValor;
    @FXML private TextField simularQtdParcelas;
    @FXML private ToggleGroup simuladorGrupo;
    @FXML private List<RadioButton> listaBotoes;
    @FXML private List<Agiota> listaAgiotas;
    @FXML private Label saldoContaLabel;

    @Override
    public void setControladorConta(ControladorConta controladorConta) {
        this.controladorConta = controladorConta;
    }
    @Override
    public void setControladorContaAgiota(ControladorConta controladorConta, Agiota agiota) {
        this.controladorConta = controladorConta;
        this.agiota = agiota;
        nomeCliente.setText(this.agiota.getNome());
    }
    @Override
    public void setControladorContaCliente(ControladorConta controladorConta, Cliente cliente) {
        this.controladorConta = controladorConta;        
    }

    @FXML
    private void initialize(){
        boxMenuLateral.setPrefWidth(41);
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
