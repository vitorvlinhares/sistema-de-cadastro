package app.controllers;

import java.io.IOException;
import java.util.Objects;

import app.data_base.CodeValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OpenningController {

    @FXML private Button btCriarLogiin;   // pode renomear depois se quiser
    @FXML private Button btEntrar;

    @FXML private TextField entradaCodigoCadastrado;
    @FXML private TextField entradaNomeCadastrado; // se não usar, pode remover

    @FXML
    void abrirCadastro(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/app/novo_cadastro.fxml"),
                "FXML não encontrado: /app/novo_cadastro.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cadastro de Funcionários");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    @FXML
    void abrirViewFuncionario(ActionEvent event) throws IOException {
        String codigo = entradaCodigoCadastrado.getText().trim();

        if (codigo.isEmpty()) {
            warn("Informe o código do funcionário.");
            return;
        }

        // CodeValidation.validate deve retornar TRUE se o funcionário EXISTE
        if (CodeValidation.validate(codigo)) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/app/view_list.fxml"),
                    "FXML não encontrado: /app/view_list.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Lista de funcionários cadastrados");
            stage.setScene(new Scene(root, 800, 500));
            stage.show();
        } else {
            info("Funcionário não cadastrado!");
        }
    }

    private void info(String m) { new Alert(Alert.AlertType.INFORMATION, m).showAndWait(); }
    private void warn(String m) { new Alert(Alert.AlertType.WARNING, m).showAndWait(); }
}
