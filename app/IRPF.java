// Arquivo: IRPF.java
package app;

import java.util.ArrayList;
import java.util.List;

public class IRPF {

    public static final boolean TRIBUTAVEL = true;
    public static final boolean NAOTRIBUTAVEL = false;

    private List<Rendimento> rendimentos;
    private List<Dependente> dependentes;
    private List<Deducao> deducoes;
    private int numContribuicoesPrevidenciarias;
    private float totalContribuicoesPrevidenciarias;
    private float totalPensaoAlimenticia;

    public IRPF() {
        this.rendimentos = new ArrayList<>();
        this.dependentes = new ArrayList<>();
        this.deducoes = new ArrayList<>();
        this.numContribuicoesPrevidenciarias = 0;
        this.totalContribuicoesPrevidenciarias = 0f;
        this.totalPensaoAlimenticia = 0f;
    }

    // Métodos relacionados a deduções
    public void cadastrarDeducaoIntegral(String nome, float valor) {
        deducoes.add(new Deducao(nome, valor));
    }

    public float getDeducao(String nome) {
        return deducoes.stream()
                .filter(d -> d.getNome().equalsIgnoreCase(nome))
                .map(Deducao::getValor)
                .findFirst()
                .orElse(0f);
    }

    public String getOutrasDeducoes(String nome) {
        return deducoes.stream()
                .filter(d -> d.getNome().equalsIgnoreCase(nome))
                .map(Deducao::getNome)
                .findFirst()
                .orElse(null);
    }

    public float getDeducao() {
        float totalDeducoes = deducoes.stream().map(Deducao::getValor).reduce(0f, Float::sum);
        totalDeducoes += totalContribuicoesPrevidenciarias;
        totalDeducoes += dependentes.size() * 189.59f;
        totalDeducoes += totalPensaoAlimenticia;
        return totalDeducoes;
    }

    public float getTotalOutrasDeducoes() {
        // Soma todas as deduções cadastradas na lista "deducoes"
        return deducoes.stream().map(Deducao::getValor).reduce(0f, Float::sum);
    }

    // Métodos relacionados a contribuições previdenciárias
    public void cadastrarContribuicaoPrevidenciaria(float contribuicao) {
        numContribuicoesPrevidenciarias++;
        totalContribuicoesPrevidenciarias += contribuicao;
    }

    public int getNumContribuicoesPrevidenciarias() {
        return numContribuicoesPrevidenciarias;
    }

    public float getTotalContribuicoesPrevidenciarias() {
        return totalContribuicoesPrevidenciarias;
    }

    // Métodos relacionados a rendimentos
    public void criarRendimento(String nome, boolean tributavel, float valor) {
        rendimentos.add(new Rendimento(nome, tributavel, valor));
    }

    public float getTotalRendimentos() {
        return rendimentos.stream()
                .map(Rendimento::getValor)
                .reduce(0f, Float::sum);
    }

    public float getTotalRendimentosTributaveis() {
        return rendimentos.stream()
                .filter(Rendimento::isTributavel)
                .map(Rendimento::getValor)
                .reduce(0f, Float::sum);
    }

    public int getNumRendimentos() {
        return rendimentos.size();
    }

    // Métodos relacionados a dependentes
    public void cadastrarDependente(String nome, String parentesco) {
        dependentes.add(new Dependente(nome, parentesco));
    }

    public int getNumDependentes() {
        return dependentes.size();
    }

    public String getDependente(String nome) {
        return dependentes.stream()
                .filter(d -> d.getNome().equalsIgnoreCase(nome))
                .map(Dependente::getNome)
                .findFirst()
                .orElse(null);
    }

    public String getParentesco(String dependente) {
        return dependentes.stream()
                .filter(d -> d.getNome().equalsIgnoreCase(dependente))
                .map(Dependente::getParentesco)
                .findFirst()
                .orElse(null);
    }

    public void cadastrarPensaoAlimenticia(String dependente, float valor) {
        dependentes.stream()
                .filter(d -> d.getNome().equalsIgnoreCase(dependente) && d.isElegivelPensao())
                .findFirst()
                .ifPresent(d -> totalPensaoAlimenticia += valor);
    }

    public float getTotalPensaoAlimenticia() {
        return totalPensaoAlimenticia;
    }

    // Métodos relacionados ao cálculo de imposto
    public float calcularBaseCalculo() {
        float rendimentosTributaveis = getTotalRendimentosTributaveis();
        float deducoesTotais = getDeducao();
        return Math.max(rendimentosTributaveis - deducoesTotais, 0);
    }

    public static float calcularImposto(float baseCalculo) {
        final float[] limites = {2259.20f, 2826.65f, 3751.05f, 4664.68f};
        final float[] aliquotas = {0.075f, 0.15f, 0.225f, 0.275f};

        float imposto = 0;
        float valorRestante = baseCalculo;

        for (int i = limites.length - 1; i >= 0; i--) {
            if (valorRestante > limites[i]) {
                float valorNaFaixa = valorRestante - limites[i];
                imposto += valorNaFaixa * aliquotas[i];
                valorRestante = limites[i];
            }
        }

        return imposto;
    }

    public float calcularAliquotaEfetiva() {
        float baseCalculo = calcularBaseCalculo();
        float imposto = calcularImposto(baseCalculo);
        float rendimentosTributaveis = getTotalRendimentosTributaveis();
        if (rendimentosTributaveis == 0) return 0;
        return (imposto / rendimentosTributaveis) * 100;
    }
}
