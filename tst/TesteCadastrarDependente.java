package tst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import app.IRPF;

public class TesteCadastrarDependente {

	IRPF irpf;
	
	@Before
	public void setup() {
		irpf = new IRPF();
	}
	
	@Test
	public void testCadastro1Dependente() {
		irpf.cadastrarDependente("Miguel", "Filho");
		assertEquals(1, irpf.getNumDependentes());
		assertTrue(irpf.getParentesco("Miguel").equalsIgnoreCase("filho"));
	}
	
	
	@Test
	public void testCadastro2Dependente() {
		irpf.cadastrarDependente("Miguel", "Filho");
		irpf.cadastrarDependente("Maria", "Filho");
		assertEquals(2, irpf.getNumDependentes());
	}
	
	@Test
	public void testCadastro3Dependente() {
		irpf.cadastrarDependente("Miguel", "Filho");
		irpf.cadastrarDependente("Maria", "Filho");
		irpf.cadastrarDependente("Carlos", "Filho");
		assertEquals(3, irpf.getNumDependentes());
	}
	
	@Test
public void obterUmDependente() {
    IRPF irpf = new IRPF();
    irpf.cadastrarDependente("Miguel", "filho"); // Cadastrar dependente

    // Verificar se o dependente foi cadastrado e retornado corretamente
    assertNotNull(irpf.getDependente("Miguel"));
    assertTrue(irpf.getDependente("Miguel").equalsIgnoreCase("Miguel"));
}

@Test
public void obterDoisDependentes() {
    IRPF irpf = new IRPF();
    irpf.cadastrarDependente("Miguel", "filho"); // Cadastrar primeiro dependente
    irpf.cadastrarDependente("Ana", "filha");   // Cadastrar segundo dependente

    // Verificar se os dependentes foram cadastrados e retornados corretamente
    assertNotNull(irpf.getDependente("Miguel"));
    assertNotNull(irpf.getDependente("Ana"));
    assertTrue(irpf.getDependente("Miguel").equalsIgnoreCase("Miguel"));
    assertTrue(irpf.getDependente("Ana").equalsIgnoreCase("Ana"));
}
	@Test
	public void dependenteInexistente() {
		String dependente = irpf.getDependente("Jose");
		String parentesco = irpf.getParentesco(dependente);
		
		assertNull(dependente);
		assertNull(parentesco);
	}

}
