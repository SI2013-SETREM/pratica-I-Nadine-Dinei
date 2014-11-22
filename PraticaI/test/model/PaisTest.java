/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nadine
 */
public class PaisTest {

    @Test
    public void testPais() {
        int PaiCodigo;
        String PaiAlfa2 = "AA";
        String PaiAlfa3 = "AAA";
        int PaiBacenIbge = 1234;
        int PaiISO3166 = 12345;
        String PaiNome = "Teste";
        Pais pais = new Pais();
        pais.setPaiAlfa2(PaiAlfa2);
        pais.setPaiAlfa3(PaiAlfa3);
        pais.setPaiBacenIbge(PaiBacenIbge);
        pais.setPaiISO3166(PaiISO3166);
        pais.setPaiNome(PaiNome);
        pais.insert();
        PaiCodigo = pais.getPaiCodigo();
        pais.load(PaiCodigo);
        assertEquals(PaiCodigo + PaiBacenIbge + PaiISO3166 + PaiAlfa2 + PaiAlfa3 + PaiNome, pais.getPaiCodigo() + pais.getPaiBacenIbge() + pais.getPaiISO3166() + pais.getPaiAlfa2() + pais.getPaiAlfa3() + pais.getPaiNome());
    }

}
