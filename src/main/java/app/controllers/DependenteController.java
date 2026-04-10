package app.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import app.classes.Dependente;
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

public class DependenteController {

    @FXML private Button btVoltar;
    @FXML private Button btAdicionarDependente;
    @FXML private TextField entradaCodigo2;
    @FXML private TextField entradaNomeDependente;

    @FXML
    void criarDependente(ActionEvent event) {
        try {
            String codigo  = entradaCodigo2.getText().trim();
            String nomeDep = entradaNomeDependente.getText().trim();

            if (codigo.isEmpty() || nomeDep.isEmpty()) {
                warn("Preencha o código e o nome do dependente.");
                return;
            }

            // CodeValidation.validate deve retornar TRUE quando o funcionário EXISTE
            if (!CodeValidation.validate(codigo)) {
                warn("Código inválido ou funcionário não encontrado.");
                return;
            }

            // encontra o funcionário pelo código
            Funcionario func = UsuariosList.getFuncionarios().stream()
                    .filter(f -> codigo.equalsIgnoreCase(f.getCodigo()))
                    .findFirst().orElse(null);

            if (func == null) {
                warn("Funcionário não encontrado.");
                return;
            }

            // pega/cria a lista atual de dependentes
            List<Dependente> lista = func.getDependentesList();
            if (lista == null) {
                lista = new java.util.ArrayList<>();
                func.setDependentesList(new java.util.ArrayList<>(lista));
            }

            // evita duplicado (por nome)
            boolean jaExiste = lista.stream()
                    .anyMatch(d -> d.getName().equalsIgnoreCase(nomeDep));
            if (jaExiste) {
                warn("Esse dependente já foi adicionado para este funcionário.");
                return;
            }

            // (opcional) respeitar limite declarado em func.getDependentes()
            int limite = func.getDependentes();
            if (limite > 0 && lista.size() >= limite) {
                warn("Número máximo de dependentes já atingido.");
                return;
            }

            // cria e adiciona o dependente
            lista.add(new Dependente(nomeDep));

            // persiste e feedback
            FileJSONWrite.createJSON(); // se não for estático, use new FileJSONWrite().createJSON();
            info("Dependente adicionado!");
            entradaNomeDependente.clear();

        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    @FXML
    void abrirViewList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/app/view_list.fxml"),
                        "FXML não encontrado: /app/view_list.fxml")
        );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 500));
        stage.setTitle("Lista de funcionários cadastrados");
        stage.show();
    }

    private void info(String m) { new Alert(Alert.AlertType.INFORMATION, m).showAndWait(); }
    private void warn(String m) { new Alert(Alert.AlertType.WARNING, m).showAndWait(); }
    private void error(String m) { new Alert(Alert.AlertType.ERROR, m).showAndWait(); }
}
