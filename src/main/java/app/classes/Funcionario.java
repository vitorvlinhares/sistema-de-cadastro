package app.classes;

import java.util.ArrayList;
import java.util.List;

import app.data_base.CodeValidation;

public final class Funcionario {

    private String nome;
    private String sobrenome;
    private String codigo;
    private String cargo;
    private int dependentes;     // quantidade planejada/máxima
    private double salario;
    private double bonus;

    // lista de dependentes para o funcionário
    private List<Dependente> dependentesList = new ArrayList<>();

    public Funcionario(String nome, String sobrenome, String codigo,
                       String cargo, int dependentes, double salario) {
        this.nome = nome;
        this.cargo = cargo;
        this.codigo = codigo;
        this.sobrenome = sobrenome;

        if (dependentes < 0) {
            throw new IllegalArgumentException("A quantidade de dependentes deve ser >= 0");
        }
        this.dependentes = dependentes;
        setBonus(dependentes);

        if (salario < 0) {
            throw new IllegalArgumentException("O valor do salário deve ser >= 0.0");
        }
        this.salario = salario;
    }

    public Funcionario(String nome, String sobrenome, String codigo,
                       String cargo, List<Dependente> dependentes, double salario) {
        this(nome, sobrenome, codigo, cargo,
             dependentes == null ? 0 : dependentes.size(), salario);
        this.dependentesList = (dependentes != null) ? new ArrayList<>(dependentes) : new ArrayList<>();
    }

    // --- getters/setters básicos ---
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) {
        // supondo que validate(codigo) == true QUANDO JÁ EXISTE
        if (CodeValidation.validate(codigo)) {
            throw new IllegalArgumentException("Código já existente");
        }
        this.codigo = codigo;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) {
        if (salario < 0) throw new IllegalArgumentException("O valor do salário deve ser >= 0.0");
        this.salario = salario;
    }

    public int getDependentes() { return dependentes; }
    public void setDependentes(int dependentes) {
        if (dependentes < 0) throw new IllegalArgumentException("A quantidade de dependentes deve ser >= 0");
        this.dependentes = dependentes;
        setBonus(dependentes);
    }

    public double getBonus() { return bonus; }
    public void setBonus(int dependentes) { this.bonus = 2 * dependentes; }

    // --- dependentes (agora com List) ---
    public List<Dependente> getDependentesList() { return dependentesList; }

    public void setDependentesList(List<Dependente> dependentesList) {
        this.dependentesList = (dependentesList != null) ? new ArrayList<>(dependentesList) : new ArrayList<>();
    }

    // helper opcional
    public void addDependente(Dependente d) {
        if (d != null) {
            if (this.dependentesList == null) this.dependentesList = new ArrayList<>();
            this.dependentesList.add(d);
        }
    }
}
