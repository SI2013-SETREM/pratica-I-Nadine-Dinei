
package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nadine
 */
public class ProdutoTest {

    public ProdutoTest() {
    }

    @Test
    public void testProduto() {
        String PrdNome = "Produto";
        String PrdDescricao = "Descrição detalhada";
        double PrdPreco = 10;
        Produto p = new Produto();
        //p.setPrdCodigo(PrdCodigo);
        p.setPrdDescricao(PrdDescricao);
        p.setPrdNome(PrdNome);
        p.setPrdPreco(PrdPreco);
        p.insert();
        int PrdCodigo = p.getPrdCodigo();
        p.load(PrdCodigo);
        assertEquals(PrdNome + PrdDescricao + PrdPreco, p.getPrdNome() + p.getPrdDescricao() + p.getPrdPreco());
    }

}
