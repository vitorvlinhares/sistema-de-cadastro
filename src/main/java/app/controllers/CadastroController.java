package app.controllers;

import java.io.IOException;
import java.util.Objects;

import app.classes.Funcionario;
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

public class CadastroController {

    @FXML private Button btCadastrar;
    @FXML private TextField entradaCargo;
    @FXML private TextField entradaCodigo;
    @FXML private TextField entradaNome;
    @FXML private TextField entradaQuantidade;
    @FXML private TextField entradaSalario;
    @FXML private TextField entradaSobrenome;

    @FXML
    void criarFuncionario(ActionEvent event) {
        try {
            String codigo = entradaCodigo.getText().trim();

            if (CodeValidation.validate(codigo)) {
                info("Funcionário já cadastrado");
                return;
            }

            Funcionario f = new Funcionario(
                entradaNome.getText().trim(),
                entradaSobrenome.getText().trim(),
                codigo,
                entradaCargo.getText().trim(),
                Integer.parseInt(entradaQuantidade.getText().trim()),
                Double.parseDouble(entradaSalario.getText().trim())
            );

            UsuariosList.createFuncionario(f);
            FileJSONWrite.createJSON(); // se não for estático, use new FileJSONWrite().createJSON()
            info("Funcionário cadastrado!");

            // Decide próxima tela
            boolean semDependentes = (f.getDependentes() == 0);
            String path = semDependentes ? "/app/view_list.fxml" : "/app/dependente.fxml";

            Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource(path), "FXML não encontrado: " + path));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, semDependentes ? 800 : 400, semDependentes ? 500 : 300));
            stage.setTitle(semDependentes ? "Lista de funcionários cadastrados" : "Adicionar Dependente");
            stage.show();

        } catch (NumberFormatException | IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    private void info(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}

