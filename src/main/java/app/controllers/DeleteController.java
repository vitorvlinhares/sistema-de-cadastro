package app.controllers;

import java.io.IOException;
import java.util.Objects;

import app.data_base.CodeValidation;
import app.data_base.FileJSONWrite;
import app.data_base.UsuariosList;
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

public class DeleteController {

    @FXML private Button btApagarFuncionario;
    @FXML private TextField entradaCodigo;

    @FXML
    void deletarFuncionario(ActionEvent event) {
        try {
            String codigo = entradaCodigo.getText().trim();

            if (!CodeValidation.validate(codigo)) {
                info("Funcionário inexistente.");
                return;
            }

            // remove de forma segura
            boolean removed = UsuariosList.getFuncionarios()
                    .removeIf(f -> codigo.equalsIgnoreCase(f.getCodigo()));

            if (removed) {
                FileJSONWrite.createJSON();
                info("Funcionário apagado com sucesso!");

                Parent root = FXMLLoader.load(Objects.requireNonNull(
                        getClass().getResource("/app/view_list.fxml"),
                        "FXML não encontrado: /app/view_list.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root, 800, 500));
                stage.setTitle("Lista de funcionários cadastrados");
                stage.show();
            } else {
                warn("Funcionário não encontrado.");
            }

        } catch (IOException e) {
            error(e.getMessage());
        }
    }

    private void info(String msg) { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
    private void warn(String msg) { new Alert(Alert.AlertType.WARNING, msg).showAndWait(); }
    private void error(String msg) { new Alert(Alert.AlertType.ERROR, msg).showAndWait(); }
}
