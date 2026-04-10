package app.data_base;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import app.classes.Dependente;
import app.classes.Funcionario;

public class FileJSONRead {


    public static void loadJSON(){

        File path = new File("database.json");
        JSONParser jsonParser = new JSONParser();

            try (FileReader reader = new FileReader(path)) {
                // Read JSON file

                Object obj = jsonParser.parse(reader);

                JSONArray userList = (JSONArray) obj;

                // Iterate over employee array
                userList.forEach(user -> parseUserObject((JSONObject) user));

            } catch (ParseException | IOException e) {

            }
        }

    private static void parseUserObject(JSONObject jsonObject) {

        ArrayList<Dependente> dependentes = new ArrayList<>();
        //Obtém os dados do atributo pedido em JSON
        String name = (String) jsonObject.get("name");
        String sobrenome = (String) jsonObject.get("sobrenome");
        String code = (String) jsonObject.get("code");
        String cargo = (String) jsonObject.get("cargo");
        double salario = (double) jsonObject.get("salary");

        //Recebe o Array guardado no objeto e armazena em um JSONArray
        JSONArray jsonArray = (JSONArray) jsonObject.get("dependentes");

        //Instancia e adiciona os dependentes armazenados no JSONArray
        jsonArray.forEach(obj -> {dependentes.add(new Dependente(obj.toString()));});

        //Instancia e adiciona o objeto armazenado no json para o UsuariosListArray

        UsuariosList.addFuncionario(new Funcionario(name,sobrenome, code,cargo,dependentes,salario));

    }

}
