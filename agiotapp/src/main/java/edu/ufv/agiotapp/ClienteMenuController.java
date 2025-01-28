package edu.ufv.agiotapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class ClienteMenuController implements ControladorTelas{
    private ControladorConta controladorConta;
    private Cliente cliente;
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

    //nome descrição nota valor
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void setControladorConta(ControladorConta controladorConta) {
        this.controladorConta = controladorConta;
    }
    @Override
    public void setControladorContaAgiota(ControladorConta controladorConta, Agiota agiota) {
        
    }
    @Override
    public void setControladorContaCliente(ControladorConta controladorConta, Cliente cliente) {
        this.controladorConta = controladorConta;
        this.cliente = cliente;
        nomeCliente.setText(this.cliente.getNome());
    }


    private void exibirExtrato() {
        HBox hBox = new HBox();
        hBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        listaHistoricovBox.getChildren().clear();
    
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(Region.USE_COMPUTED_SIZE, 60);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(Region.USE_COMPUTED_SIZE);
        col1.setMinWidth(400);
        col1.setMaxWidth(400);
        gridPane.getColumnConstraints().addAll(col1);
        int i = 0;
        for (String cobranca : cliente.getHistoricoCobranca()) {
            RowConstraints row1 = new RowConstraints();
            row1.setMinHeight(20);
            row1.setPrefHeight(30);
            gridPane.getRowConstraints().add(row1);
    
            Text text = new Text(cobranca);
            GridPane.setMargin(text, new Insets(0, 0, 10, 10));
            gridPane.add(text, 0, i); // Corrigido: coluna 0 e linha i
            i++; // Incrementa a linha para a próxima cobrança
        }
        hBox.getChildren().addAll(gridPane);
        HBox.setMargin(gridPane, new Insets(0, 40, 0, 0));
        listaHistoricovBox.getChildren().add(hBox);
    }
    
    
    @FXML 
    private void simularEmprestimo(){
        Double valorSimular = null;
        Integer parcelasSimular = null;
        boolean camposPreenchidos = true;
        try {
            valorSimular = Double.parseDouble(simularValor.getText());
        } catch (NumberFormatException e) {
            statusSimularLabel.setText("Campo Inválido!");
            camposPreenchidos = false;
        }
        try {
            parcelasSimular = Integer.parseInt(simularValor.getText());
        } catch (NumberFormatException e) {
            statusSimularLabel.setText("Campo Inválido!");
            camposPreenchidos = false;
        }
        if (camposPreenchidos) {
            simularEmprestimo(valorSimular, parcelasSimular);
        }
        simuladorGridPane.setGridLinesVisible(true);
    }

    private void simularEmprestimo(Double valor, int quantidadeParcelas){
        HBox hBox = new HBox();
        hBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        simuladorGridPane.getChildren().clear();
        simuladorGridPane.setMinWidth(590);
        ObservableList<ColumnConstraints> colunas = simuladorGridPane.getColumnConstraints();

        if (colunas.size() >= 5) {
            colunas.get(0).setMaxWidth(136);
            colunas.get(0).setMinWidth(136);
            colunas.get(1).setMaxWidth(140);
            colunas.get(1).setMinWidth(140);
            colunas.get(2).setMaxWidth(72); 
            colunas.get(2).setMinWidth(72); 
            colunas.get(3).setMaxWidth(111);
            colunas.get(3).setMinWidth(111);
            colunas.get(4).setMaxWidth(130);
            colunas.get(4).setMinWidth(130);
        }
        int i = 0;
        listaBotoes = new ArrayList<>();
        listaAgiotas = new ArrayList<>();
        simuladorGrupo = new ToggleGroup();
    
        for (Agiota agiota : controladorConta.bancoDeDados.getListaAgiotas()){
            if (agiota.getMaximoParcelas() <= quantidadeParcelas && agiota.getSaldo() >= valor){
                if (i != 0){
                    RowConstraints row1 = new RowConstraints();
                    row1.setMinHeight(10);
                    row1.setPrefHeight(30);
                    simuladorGridPane.getRowConstraints().add(row1);
                }
                
                Text nome = new Text(agiota.getNome());
                GridPane.setMargin(nome, new Insets(0, 0, 10, 10));
                simuladorGridPane.add(nome, 0, i);
                Text valorDisponivel = new Text(String.format("R$ %.2f", agiota.getSaldo()));
                GridPane.setMargin(valorDisponivel, new Insets(0, 0, 10, 10));
                simuladorGridPane.add(valorDisponivel, 1, i);
                Text jurosAoMes = new Text(String.format("%.2f%%", agiota.getJuros()));
                GridPane.setMargin(jurosAoMes, new Insets(0, 0, 10, 10));
                simuladorGridPane.add(jurosAoMes, 2, i);
                Text totalPagar = new Text(String.format("R$ %.2f", cliente.simularEmprestimo(valor, agiota.getJuros(), quantidadeParcelas)));
                GridPane.setMargin(totalPagar, new Insets(0, 0, 10, 10));
                simuladorGridPane.add(totalPagar, 3, i);
                RadioButton radioButton = new RadioButton();
                radioButton.setToggleGroup(simuladorGrupo);
                listaBotoes.add(radioButton);
                listaAgiotas.add(agiota);
                simuladorGridPane.add(radioButton, 4, i); 
                i++;
            }
        }
        hBox.getChildren().addAll(simuladorGridPane);
        simularVbox.getChildren().add(hBox);
    }
    

    @FXML
    private void realizarEmprestimo(){
        Toggle selectedToggle = simuladorGrupo.getSelectedToggle();
        if (selectedToggle != null) {
            int index = listaBotoes.indexOf(selectedToggle);
            if (index != -1 ) {
                Agiota agiotaSelecionado = listaAgiotas.get(index); 
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmação de Empréstimo");
                alert.setContentText(String.format("Deseja confirmar seu empréstimo de R$ %.2f com o agiota %s", Double.parseDouble(simularValor.getText()), agiotaSelecionado.getNome()));
                Optional<ButtonType> resultado = alert.showAndWait();
                if (resultado.get() == ButtonType.OK){
                    controladorConta.realizarEmprestimo(this.cliente,  Double.parseDouble(simularValor.getText()), Integer.parseInt(simularQtdParcelas.getText()), agiotaSelecionado);
                    simuladorGridPane.getRowConstraints().clear();
                }
            }
        }
    }

    private void exibirAgiota(String nome, String descricao, double nota, double valor){
        HBox hBox = new HBox();
        hBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(Region.USE_COMPUTED_SIZE, 60);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(Region.USE_COMPUTED_SIZE);
        col1.setMinWidth(400);
        col1.setMaxWidth(400);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(Region.USE_COMPUTED_SIZE);
        gridPane.getColumnConstraints().addAll(col1, col2);

        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(30);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(30);
        gridPane.getRowConstraints().addAll(row1, row2);

        Text text1 = new Text(nome);
        GridPane.setMargin(text1, new Insets(0, 0, 10, 10));
        gridPane.add(text1, 0, 0);

        TextFlow textFlow = new TextFlow();
        Text text2 = new Text(truncateText(descricao, 200));
        
        textFlow.getChildren().add(text2);
        textFlow.setPrefWidth(200); // Defina a largura preferida do TextFlow 
        textFlow.setTextAlignment(TextAlignment.JUSTIFY); // Alinhar o texto conforme necessário
        GridPane.setMargin(textFlow, new Insets(10, 0, 0, 10));
        gridPane.add(textFlow, 0, 1);
        for (int i = 0; i < 5; i++) {
            ImageView smallImageView;
            if (i < nota) {
                smallImageView = new ImageView(new Image(getClass().getResourceAsStream("/edu/ufv/agiotapp/estrela1.png")));
            } else {
                smallImageView = new ImageView(new Image(getClass().getResourceAsStream("/edu/ufv/agiotapp/estrela0.png")));
            }
            smallImageView.setFitHeight(20);
            smallImageView.setFitWidth(20);
    
            GridPane.setMargin(smallImageView, new Insets(10, 0, 0, 10 + i * 30));
            gridPane.add(smallImageView, 1, 0);
        }
        Text text3 = new Text(String.format("Valor disponível: R$%.2f", valor));
        GridPane.setMargin(text3, new Insets(20, 0, 0, 10));
        gridPane.add(text3, 1, 1);

        hBox.getChildren().addAll(gridPane);
        HBox.setMargin(gridPane, new Insets(0, 40, 0, 0));
        listaAgiotasVBox.getChildren().add(hBox);
    }

    private void exibirCobrancas(){
        HBox hBox = new HBox();
        hBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(Region.USE_COMPUTED_SIZE, 60);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(Region.USE_COMPUTED_SIZE);
        col1.setMinWidth(400);
        col1.setMaxWidth(400);
        int i = 0;
        for (String cobranca : cliente.getHistoricoCobranca()){

            RowConstraints row1 = new RowConstraints();
            row1.setMinHeight(10);
            row1.setPrefHeight(30);
            gridPane.getRowConstraints().add(row1);
                
            Text text = new Text(cobranca);
            GridPane.setMargin(text, new Insets(0, 0, 10, 10));
            simuladorGridPane.add(text, 0, i);
        }
        hBox.getChildren().addAll(gridPane);
        HBox.setMargin(gridPane, new Insets(0, 40, 0, 0));
        listaCobrancavBox.getChildren().add(hBox);
    }

    private static String truncateText(String text, int maxChars) {
        if (text.length() > maxChars) {
            return text.substring(0, maxChars);
        }
        return text;
    }

    private void exibirEmprestimos() {
        HBox hBox = new HBox();
        hBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        listaEmprestimosVBox.getChildren().clear();
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(Region.USE_COMPUTED_SIZE, 60);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(Region.USE_COMPUTED_SIZE);
        col1.setMinWidth(200);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(Region.USE_COMPUTED_SIZE);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(Region.USE_COMPUTED_SIZE); // Corrigir a definição de col3
        gridPane.getColumnConstraints().addAll(col1, col2, col3);
        System.out.println(cliente.getListaFaturas());
        int i = 0;
        for (Fatura fatura : this.cliente.getListaFaturas()) {
            RowConstraints row1 = new RowConstraints();
            row1.setPrefHeight(30);
            System.out.println("Aqui" + fatura.getValorTotal());
            ImageView smallImageView;
            smallImageView = new ImageView(new Image(getClass().getResourceAsStream("/edu/ufv/agiotapp/notaPositivo.png")));
            smallImageView.setFitHeight(20);
            smallImageView.setFitWidth(20);
            smallImageView.setOnMouseClicked(event -> enviarNota(1, fatura.getIdContaAgiota()));
            smallImageView.setCursor(Cursor.HAND); // Corrigir definição de cursor
            smallImageView.setPickOnBounds(true);
            GridPane.setMargin(smallImageView, new Insets(10, 20, 0, 10));
            gridPane.add(smallImageView, 0, i);
    
            smallImageView = new ImageView(new Image(getClass().getResourceAsStream("/edu/ufv/agiotapp/notaNegativo.png")));
            smallImageView.setFitHeight(20);
            smallImageView.setFitWidth(20);
            smallImageView.setOnMouseClicked(event -> enviarNota(-1, fatura.getIdContaAgiota()));
            smallImageView.setCursor(Cursor.HAND); // Corrigir definição de cursor
            smallImageView.setPickOnBounds(true);
            GridPane.setMargin(smallImageView, new Insets(10, 30, 0, 50));
            gridPane.add(smallImageView, 0, i);
    
            Text text1 = new Text(String.format("%s | R$ %s | Parcelas: %s", fatura.getDataEmissao(), fatura.getValorTotal(), fatura.getQuantidadeParcelas()));
            GridPane.setMargin(text1, new Insets(0, 0, 0, 0));
            gridPane.add(text1, 2, i);
    
            // Adiciona o label "Avaliar o agiota" + encontrarAgiotaPorId(fatura.getIdContaAgiota())
            Text text3 = new Text("Avaliar o agiota: " + encontrarAgiotaPorId(fatura.getIdContaAgiota()).getNome());
            GridPane.setMargin(text3, new Insets(10, 0, 0, 10));
            gridPane.add(text3, 0, i + 1, 3, 1);
    
            gridPane.getRowConstraints().add(row1);
            i++;
            int j = 1;
            for (Parcela parcela : fatura.getListaParcelas()) {
                RowConstraints row2 = new RowConstraints();
                row2.setPrefHeight(30);
    
                Text text2 = new Text(String.format("R$%.2f | Parcelas: %s/%s | Vencimento: %s", parcela.getValor(), j, fatura.getQuantidadeParcelas(), parcela.getDataVencimento()));
                GridPane.setMargin(text2, new Insets(0, 0, 0, 0));
                gridPane.add(text2, 2, i);
                gridPane.getRowConstraints().add(row2);
                i++;
                j++;
            }
            i++; // Incrementa i para garantir que o label "Avaliar o agiota" não se sobreponha às parcelas
        }
        hBox.getChildren().addAll(gridPane);
        HBox.setMargin(gridPane, new Insets(0, 40, 0, 0));
        listaEmprestimosVBox.getChildren().add(hBox);
    }
    

    private void enviarNota(double i, int idAgiota){
        controladorConta.enviarNota(encontrarAgiotaPorId(idAgiota), i);
    }

    private Agiota encontrarAgiotaPorId(int idAgiota) {
        for (Agiota agiota : controladorConta.bancoDeDados.getListaAgiotas()) {
            if (agiota.getIdAgiota() == idAgiota) {
                return agiota;
            }
        }
        return null; // Retorna null se nenhum agiota for encontrado com o ID fornecido
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
        saldoContaLabel.setText(String.format("Saldo: R$ %.2f ", cliente.getSaldo()));
        exibirExtrato();
    } 
    @FXML 
    private void ativarPainelEmprestimo(){
        emprestimosPainel.toFront();
        exibirEmprestimos();
    } 
    @FXML 
    private void ativarPainelSimulador(){
        simuladorPainel.toFront();
    } 
    @FXML 
    private void ativarPainelCobranca(){
        cobrancasPainel.toFront();
        exibirCobrancas();
    } 
    @FXML 
    private void ativarPainelListaAgiotas(){
        listaAgiotasPainel.toFront();
        listaAgiotasVBox.getChildren().clear();
        for (Agiota agiota : controladorConta.bancoDeDados.getListaAgiotas()){
            exibirAgiota(agiota.getNome(), agiota.getDescricao(), agiota.getNotaTotal(), agiota.getSaldo());
        }
    }
}
