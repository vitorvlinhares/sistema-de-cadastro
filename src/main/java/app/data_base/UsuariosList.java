package app.data_base;

import java.util.ArrayList;

import app.classes.Funcionario;

public class UsuariosList {

    public static ArrayList<Funcionario> funcionarios = new ArrayList<>();

    public static ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }


    public static void addFuncionario(Funcionario funcionario){

        funcionarios.add(funcionario);


    }

    public static void createFuncionario(Funcionario funcionario) {

        if(app.data_base.CodeValidation.validate(funcionario))
        {
            System.out.println("Colocar Warning de nao pode criar");
        }
        else
        {
            System.out.println("Funcionário criado com sucesso");
            addFuncionario(funcionario);
        }
    }

    public static void deleteFuncionario(Funcionario funcionario)
    {
        funcionarios.remove(funcionario);
    }
}


