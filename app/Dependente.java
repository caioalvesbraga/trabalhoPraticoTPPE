// Arquivo: Dependente.java
package app;

public class Dependente {
    private String nome;
    private String parentesco;

    public Dependente(String nome, String parentesco) {
        this.nome = nome;
        this.parentesco = parentesco;
    }

    public String getNome() {
        return nome;
    }

    public String getParentesco() {
        return parentesco;
    }

    public boolean isElegivelPensao() {
        return parentesco.toLowerCase().contains("filh") || parentesco.toLowerCase().contains("alimentand");
    }
}
