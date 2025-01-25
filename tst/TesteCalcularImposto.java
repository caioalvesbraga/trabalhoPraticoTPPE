package tst;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import app.IRPF;

public class TesteCalcularImposto {

    IRPF irpf;

    @Before
    public void setup() {
        irpf = new IRPF();
    }

    @Test
    public void calcularImpostoApenasFaixa1() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 2000f);
        float imposto = IRPF.calcularImposto(2000f);
        assertEquals(0f, imposto, 0.01f);
    }

    @Test
    public void calcularImpostoAteFaixa2() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 2500f);
        float imposto = IRPF.calcularImposto(2500f);
        assertEquals(18.07f, imposto, 0.01f);
    }

    @Test
    public void calcularImpostoEmTodasAsFaixas() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 8000f);
        irpf.cadastrarContribuicaoPrevidenciaria(500f);
        float baseCalculo = irpf.calcularBaseCalculo();
        float imposto = IRPF.calcularImposto(baseCalculo);
        assertEquals(1166.4984f, imposto, 0.01f);
    }
}
