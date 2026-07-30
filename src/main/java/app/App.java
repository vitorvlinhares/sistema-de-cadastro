package app;

import app.data_base.FileJSONRead;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        var url = Objects.requireNonNull(
            App.class.getResource("/app/openning_interface.fxml"),
            "Não encontrei /app/openning_interface.fxml (deve estar em src/main/resources/app/)"
        );

        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Cadastro de funcionários");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        FileJSONRead.loadJSON();
        launch();

    }

    public void changeScene(String s) {
    }
}