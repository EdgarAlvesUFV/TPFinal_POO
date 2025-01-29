package edu.ufv.agiotapp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class AgiotApp extends Application {

    private static Scene scene;
    private ControladorConta controladorContas;

    public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
        controladorContas = new ControladorConta();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        stage.setTitle("AgiotApp");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/edu/ufv/agiotapp/AgiotAppIconNoBG.png")));
        
        LoginController loginController = loader.getController();
        loginController.setControladorConta(controladorContas);
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
        stage.setUserData(controladorContas);
    }

    static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AgiotApp.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        scene.setRoot(root);
        
        Object controller = fxmlLoader.getController();
        if (controller instanceof ControladorTelas) {
            ControladorTelas controladorAware = (ControladorTelas) controller;
            ControladorConta controladorConta = (ControladorConta) scene.getWindow().getUserData();
            controladorAware.setControladorConta(controladorConta);
        }
    }

    static void setRootLoginCliente(String fxml, Cliente conta) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AgiotApp.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        scene.setRoot(root);
        
        Object controller = fxmlLoader.getController();
        if (controller instanceof ControladorTelas) {
            ControladorTelas controladorAware = (ControladorTelas) controller;
            ControladorConta controladorConta = (ControladorConta) scene.getWindow().getUserData();
            controladorAware.setControladorContaCliente(controladorConta, conta);
        }
    }

    static void setRootLoginAgiota(String fxml, Agiota conta) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AgiotApp.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        scene.setRoot(root);
        
        Object controller = fxmlLoader.getController();
        if (controller instanceof ControladorTelas) {
            ControladorTelas controladorAware = (ControladorTelas) controller;
            ControladorConta controladorConta = (ControladorConta) scene.getWindow().getUserData();
            controladorAware.setControladorContaAgiota(controladorConta, conta);
        }
    }

    public static void main(String[] args) {
        launch();
    }

}