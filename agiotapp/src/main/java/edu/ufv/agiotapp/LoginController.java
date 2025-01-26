package edu.ufv.agiotapp;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements ControladorTelas{

    @FXML private TextField userField;
    @FXML private PasswordField passwordField;
    @FXML private Label loginStatus;
    private ControladorConta controladorConta;

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
    private void handleLoginButton() {
    
        String username = userField.getText();
        String password = passwordField.getText();
        Conta conta = controladorConta.login(username, password);
        if (conta != null) {
            try {
                redirecionarParaMenu(conta);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loginStatus.setText("Usuário ou senha inválidos");
        }
    }

    @FXML
    private void switchToCadastro() throws IOException {
        Stage stage = (Stage) userField.getScene().getWindow();
        AgiotApp.setRoot("cadastro");
    }

    private void redirecionarParaMenu(Conta conta) throws IOException {
        
            Stage stage = (Stage) userField.getScene().getWindow();
            if (conta instanceof Cliente) {
                AgiotApp.setRootLoginCliente("clienteMenu", (Cliente) conta);
            } else if (conta instanceof Agiota) {
                AgiotApp.setRootLoginAgiota("agiotaMenu",(Agiota) conta);
            }
    }
}
