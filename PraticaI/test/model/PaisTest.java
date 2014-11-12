/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nadine
 */
public class PaisTest {

    public PaisTest() {
    }

    @Test
    public void testSomeMethod() {
    }

    @Test
    public void testPais() {
        String PaiAlfa2 = "HH";
        String PaiAlfa3 = "HHH";
        int PaiBacenIbge = 12345;
        int PaiISO3166 = 1234;
        String PaiNome = "HUHUUUEEE";
        Pais p = new Pais();
        p.setPaiAlfa2(PaiAlfa2);
        p.setPaiAlfa3(PaiAlfa3);
        p.setPaiBacenIbge(PaiBacenIbge);
        p.setPaiISO3166(PaiISO3166);
        p.setPaiNome(PaiNome);
        p.insert();
        int paiCodigo = p.getPaiCodigo();
        p.load(paiCodigo);
        assertEquals(paiCodigo + PaiAlfa2 + PaiAlfa3 + PaiNome + PaiBacenIbge + PaiISO3166, p.getPaiCodigo() + p.getPaiAlfa2() + p.getPaiAlfa3() + p.getPaiNome() + p.getPaiBacenIbge() + p.getPaiISO3166());
    }

}
