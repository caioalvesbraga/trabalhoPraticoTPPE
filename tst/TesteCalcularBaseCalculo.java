package tst;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import app.IRPF;

public class TesteCalcularBaseCalculo {

    IRPF irpf;

    @Before
    public void setup() {
        irpf = new IRPF();
    }

    @Test
    public void calcularBaseComDeducoesMenoresQueRendimentosTributaveis() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 8000f);
        irpf.cadastrarContribuicaoPrevidenciaria(500f);
        irpf.cadastrarPensaoAlimenticia("Filho", 1500f);
        irpf.cadastrarDependente("Filho", "Filho");
        float baseCalculo = irpf.calcularBaseCalculo();
        assertEquals(5810.41f, baseCalculo, 0.01f);
    }

    @Test
    public void calcularBaseComDeducoesMaioresQueRendimentosTributaveis() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 8000f);
        irpf.cadastrarContribuicaoPrevidenciaria(10000f);
        float baseCalculo = irpf.calcularBaseCalculo();
        assertEquals(0f, baseCalculo, 0.01f);
    }
}
