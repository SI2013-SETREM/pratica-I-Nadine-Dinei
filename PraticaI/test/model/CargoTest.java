/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Nadine
 */
public class CargoTest {
    @Test
    public void testCargo() {
        String crgNome = "Gerente de Projeto";
        Cargo c = new Cargo();
        c.setCrgNome(crgNome);
        c.insert();
        int crgcodigo = c.getCrgCodigo();
        c.load(crgcodigo);
        assertEquals(crgcodigo + crgNome, c.getCrgCodigo() + c.getCrgNome());
    }
}
