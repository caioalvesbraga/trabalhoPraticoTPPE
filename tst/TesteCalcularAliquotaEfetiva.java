package tst;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import app.IRPF;

public class TesteCalcularAliquotaEfetiva {

    IRPF irpf;

    @Before
    public void setup() {
        irpf = new IRPF();
    }

    @Test
    public void calcularAliquotaEfetivaComImpostoZero() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 2000f);
        float aliquota = irpf.calcularAliquotaEfetiva();
        assertEquals(0f, aliquota, 0.01f);
    }

    @Test
    public void calcularAliquotaEfetivaComImposto() {
        irpf.criarRendimento("Salário", IRPF.TRIBUTAVEL, 8000f);
        irpf.cadastrarContribuicaoPrevidenciaria(500f);
        float aliquota = irpf.calcularAliquotaEfetiva();
        assertEquals(14.58123f, aliquota, 0.01f);
    }
}
