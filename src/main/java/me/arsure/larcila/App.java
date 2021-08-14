package me.arsure.larcila;

import me.arsure.larcila.view.ReportesGUI;
//import me.arsure.larcila.view.ReportesView;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        //var reportesView = new ReportesView();
        //var banco = "Conavi";
        //reportesView.proyectosFinanciadosPorBanco(banco);    
        //var limiteInferior = 50_000d;
        //reportesView.totalAdeudadoPorProyectosSuperioresALimite(limiteInferior);
        //reportesView.lideresQueMasGastan();
         var frm = new ReportesGUI();
         frm.setVisible(true);
    }
}