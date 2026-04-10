package app.data_base;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import app.classes.Funcionario;

public class FileJSONWrite {

    public static void createJSON(){

        JSONArray funcionarioArray = new JSONArray();


        for(Funcionario funcionario : UsuariosList.getFuncionarios())
        {
            JSONObject funcionarioOBJ = new JSONObject();
            JSONArray dependentesList = new JSONArray();

            //Percorre o array de dependentes e guarda em um JSONArray
            funcionario.getDependentesList().forEach(dependente ->{
                dependentesList.add(dependente.getName().toLowerCase());
            });


            //Cria os atributos do JSONObject
            funcionarioOBJ.put("name", funcionario.getNome());
            funcionarioOBJ.put("sobrenome", funcionario.getSobrenome());
            funcionarioOBJ.put("code", funcionario.getCodigo());
            funcionarioOBJ.put("cargo", funcionario.getCargo());
            funcionarioOBJ.put("salary", funcionario.getSalario());
            funcionarioOBJ.put("dependentes", dependentesList);

            funcionarioArray.add(funcionarioOBJ);
        }

        try {
            //Método para criação e escrita do arquivo .json
            FileWriter wF = new FileWriter("database.json");
            wF.write(funcionarioArray.toJSONString());
            wF.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}