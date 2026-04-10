package app.controllers;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import app.classes.Funcionario;
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

public class SalarioController {

    @FXML private Button btAlterar;
    @FXML private TextField entradaCodigo;
    @FXML private TextField entradaSalario;

    @FXML
    void alterarSalario(ActionEvent event) {
        try {
            String codigo  = entradaCodigo.getText().trim();
            String sSalario = entradaSalario.getText().trim();

            if (codigo.isEmpty() || sSalario.isEmpty()) {
                warn("Informe o código e o novo salário.");
                return;
            }

            double novoSalario;
            try {
                novoSalario = Double.parseDouble(sSalario);
                if (novoSalario < 0) {
                    warn("O salário deve ser ≥ 0.");
                    return;
                }
            } catch (NumberFormatException nfe) {
                warn("Valor de salário inválido.");
                return;
            }

            Optional<Funcionario> opt = UsuariosList.getFuncionarios().stream()
                    .filter(f -> codigo.equalsIgnoreCase(f.getCodigo()))
                    .findFirst();

            if (opt.isEmpty()) {
                info("Funcionário inexistente!");
                return;
            }

            Funcionario f = opt.get();
            f.setSalario(novoSalario);
            FileJSONWrite.createJSON(); // persiste

            info("Salário atualizado!");

            // Volta para a lista
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/app/view_list.fxml"),
                    "FXML não encontrado: /app/view_list.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Lista de funcionários atualizada");
            stage.setScene(new Scene(root, 800, 500));
            stage.show();

        } catch (IOException e) {
            error(e.getMessage());
        }
    }

    private void info(String m) { new Alert(Alert.AlertType.INFORMATION, m).showAndWait(); }
    private void warn(String m) { new Alert(Alert.AlertType.WARNING, m).showAndWait(); }
    private void error(String m) { new Alert(Alert.AlertType.ERROR, m).showAndWait(); }
}
