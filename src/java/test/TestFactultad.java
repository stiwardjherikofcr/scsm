/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import negocio.AdministrarMicrocurriculo;

/**
 *
 * @author Manuel
 */
public class TestFactultad {

    public static void main(String[] args) {
        AdministrarMicrocurriculo am = new AdministrarMicrocurriculo();
        am.listaMicrocurriculos(7, 1150606);
    }
}
