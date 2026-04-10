package app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import app.classes.Funcionario;
import app.data_base.UsuariosList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewListController implements Initializable {

    @FXML private Button btApagarFuncionario;
    @FXML private Button btNovoFuncionario;
    @FXML private Button btAtualizar;

    @FXML private TableView<Funcionario> viewFuncionario;

    @FXML private TableColumn<Funcionario, String>  colunaCodigo;
    @FXML private TableColumn<Funcionario, String>  colunaNome;
    @FXML private TableColumn<Funcionario, String>  colunaSobrenome;
    @FXML private TableColumn<Funcionario, Integer> colunaNumeroDependente;
    @FXML private TableColumn<Funcionario, Double>  colunaSalario;

    private final ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // mapeia colunas -> getters: getCodigo, getNome, getSobrenome, getDependentes, getSalario
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaSobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
        colunaNumeroDependente.setCellValueFactory(new PropertyValueFactory<>("dependentes"));
        colunaSalario.setCellValueFactory(new PropertyValueFactory<>("salario")); // <-- era "bonus" no seu

        viewFuncionario.setItems(funcionarios);
        refreshTable();
    }

    private void refreshTable() {
        funcionarios.setAll(UsuariosList.getFuncionarios());
        viewFuncionario.refresh();
    }

    // --- Navegação ---

    @FXML
    void abrirCadastro(ActionEvent event) throws IOException {
        trocarCena(event, "/app/novo_cadastro.fxml", 800, 500, "Cadastro de funcionários");
    }

    @FXML
    void apagarFuncionario(ActionEvent event) throws IOException {
        trocarCena(event, "/app/deleteInterface.fxml", 300, 300, "Apagar Funcionário");
    }

    @FXML
    void abrirAlterarSalario(ActionEvent event) throws IOException {
        // (renomeei o método para ficar mais claro; antes chamava atualizarLista)
        trocarCena(event, "/app/salarioInterface.fxml", 400, 400, "Alterar Salário");
    }

    // Se você quer apenas recarregar a lista na própria tela:
    @FXML
    void atualizarLista(ActionEvent event) {
        refreshTable();
    }

    // helper para trocar de cena
    private void trocarCena(ActionEvent e, String fxmlPath, int w, int h, String titulo) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource(fxmlPath), "FXML não encontrado: " + fxmlPath));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root, w, h));
        stage.show();
    }
}
