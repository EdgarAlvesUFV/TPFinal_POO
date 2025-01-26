package edu.ufv.agiotapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CadastroController implements ControladorTelas{

    ObservableList<String> tipoConta = FXCollections.observableArrayList("Cliente", "Agiota");

    private ControladorConta controladorConta;
    private List<Parente> listaParentes;
    private boolean aceitaParcelado;
    private boolean camposPreenchidos;
    private int qtdParentes;
    @FXML private ChoiceBox<String> boxTipoConta;
    @FXML private StackPane stackPane;
    @FXML private VBox vBoxCliente, vBoxAgiota, boxParentes, boxParcelado;
    @FXML private TextField cpfField, nomeField, telefoneField, nomeParenteField, telefoneParenteField;
    @FXML private TextField parentescoField, saldoContaField, jurosField, maxParcelasField;
    @FXML private PasswordField senhaField;
    @FXML private TextArea enderecoArea, enderecoParenteArea, descricaoArea;
    @FXML private RadioButton parceladoSim, parceladoNao;
    @FXML private RadioButton parenteSim, parenteNao;
    @FXML private ToggleGroup Parente,Parcelado;
    @FXML private Label statusCPF, statusNome, statusSenha, statusDescricao, statusSaldo, statusJuros;
    @FXML private Label statusMaxParcelas, parentesLabel, statusEndereco, statusTelefone;
   
    @Override
    public void setControladorConta(ControladorConta controladorConta) {
        this.controladorConta = controladorConta;
    }
    @Override
    public void setControladorContaAgiota(ControladorConta controladorConta, Agiota agiota) {
        
    }
    @Override
    public void setControladorContaCliente(ControladorConta controladorConta, Cliente cliente) {
        
    }

    @FXML
    private void initialize(){
        boxTipoConta.setValue("Cliente");
        boxTipoConta.setItems(tipoConta);
        
        boxTipoConta.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Cliente".equals(newValue)) {
                vBoxCliente.setVisible(true);
                vBoxAgiota.setVisible(false);
                listaParentes = new ArrayList<>();
                qtdParentes = 0;
            } else if ("Agiota".equals(newValue)) {
                vBoxCliente.setVisible(false);
                vBoxAgiota.setVisible(true);
            }
        });

        Parente.selectedToggleProperty().addListener((observable2, oldValue2, newValue2) -> {
            boxParentes.setVisible(newValue2 == parenteSim);
        });
        Parcelado.selectedToggleProperty().addListener((observable2, oldValue2, newValue2) -> {
            boxParcelado.setVisible(newValue2 == parceladoSim);
            aceitaParcelado = newValue2 == parceladoSim;
        });

        // Define visibilidade inicial
        listaParentes = new ArrayList<>();
        vBoxCliente.setVisible(true);
        vBoxAgiota.setVisible(false);
    }

    @FXML
    private void voltarTelaLogin() throws IOException{
        AgiotApp.setRoot("login");
    }

    @FXML 
    private void verificarCadastro(){
        String cpf = cpfField.getText();
        if (controladorConta.verificarCadastro(cpf)){
            statusCPF.setText("Este CPF já está cadastrado");
        } else {
            statusCPF.setText("");
        }
    }

    @FXML 
    private void adicionarParente(){
        String nome = nomeParenteField.getText();
        String endereco = enderecoParenteArea.getText();
        String telefone = telefoneParenteField.getText();
        String parentesco = parentescoField.getText();
        Parente parente = new Parente(nome, endereco, telefone, parentesco);
        listaParentes.add(parente);
        qtdParentes++;
        nomeParenteField.setText("");
        enderecoParenteArea.setText("");
        telefoneParenteField.setText("");
        parentescoField.setText("");
        parentesLabel.setText("Parentes: " + qtdParentes);

    }

    private boolean verificarPreenchimento(String campoTexto){
        return campoTexto == null || campoTexto.trim().isEmpty();
    }
    

    @FXML
    private void realizarCadastro() throws IOException{
        String cpf = cpfField.getText();
        String nome = nomeField.getText();
        String senha = senhaField.getText();
        this.camposPreenchidos = true;

        if (verificarPreenchimento(cpf)) {
            statusCPF.setText("Campo Obrigatório");
            camposPreenchidos = false;
        } else {
            statusCPF.setText("");
        }
        if (verificarPreenchimento(nome)) {
            statusNome.setText("Campo Obrigatório");
            camposPreenchidos = false;
        } else {
            statusNome.setText("");
        }
        if (verificarPreenchimento(senha)) {
            statusSenha.setText("Campo Obrigatório");
            camposPreenchidos = false;
        } else {
            statusSenha.setText("");
        } 
        
        if ("Cliente".equals(boxTipoConta.getValue())) {
            String endereco = enderecoArea.getText();
            endereco = endereco.replaceAll("\n", " ").replaceAll("\r", " ");
            String telefone = telefoneField.getText();
        
            if (verificarPreenchimento(endereco)) {
                statusEndereco.setText("Campo Obrigatório");
                camposPreenchidos = false;
            } else {
                statusEndereco.setText("");
            }
            if (verificarPreenchimento(telefone)) {
                statusTelefone.setText("Campo Obrigatório");
                camposPreenchidos = false;
            } else {
                statusTelefone.setText("");
            }
        
            if (camposPreenchidos) {
                controladorConta.cadastrarCliente(cpf, senha, nome, endereco, telefone, listaParentes);
                AgiotApp.setRoot("clienteMenu");
            }
        
        } else if ("Agiota".equals(boxTipoConta.getValue())) {
            String descricao = descricaoArea.getText();
            descricao = descricao.replaceAll("\n", " ").replaceAll("\r", " ");
            Double saldo = null;
            Double juros = null;
            Integer qtdParcelas = null;
        
            if (verificarPreenchimento(descricao)) {
                statusDescricao.setText("Campo Obrigatório");
                camposPreenchidos = false;
            } else {
                statusDescricao.setText("");
            }
            try {
                saldo = Double.parseDouble(saldoContaField.getText());
                statusSaldo.setText("");
            } catch (NumberFormatException e) {
                statusSaldo.setText("Campo Inválido");
                camposPreenchidos = false;
            }
            try {
                juros = Double.parseDouble(jurosField.getText());
                statusJuros.setText("");
            } catch (NumberFormatException e) {
                statusJuros.setText("Campo Inválido");
                camposPreenchidos = false;
            }
            try {
                qtdParcelas = Integer.parseInt(maxParcelasField.getText());
                statusMaxParcelas.setText("");
            } catch (NumberFormatException e) {
                statusMaxParcelas.setText("Campo Inválido");
                camposPreenchidos = false;
            }
        
            if (camposPreenchidos) {
                controladorConta.cadastrarAgiota(cpf, senha, nome, descricao, saldo, juros, aceitaParcelado, qtdParcelas);
                AgiotApp.setRoot("agiotaMenu");
            }
        }
    }
}