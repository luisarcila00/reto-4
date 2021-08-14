package me.arsure.larcila.view;
import me.arsure.larcila.controller.ReportesController;
import me.arsure.larcila.model.vo.*;


import java.sql.SQLException;

public class ReportesView {
    private ReportesController controller;

    public ReportesView() {
        controller = new ReportesController();
    }

    private String repitaCaracter(Character caracter, Integer veces) {
        var respuesta = "";
        for (int i = 0; i < veces; i++) {
            respuesta += caracter;
        }
        return respuesta;
    }


    public void proyectosFinanciadosPorBanco(String banco) {
        try {
            System.out.println(repitaCaracter('=', 36) + " LISTADO DE PROYECTOS POR BANCO " + repitaCaracter('=', 37));
            if (banco != null && !banco.isBlank()) {
                System.out.println(String.format("%3s %-25s %-20s %-15s %-7s %-30s", "ID", "CONSTRUCTORA", "CIUDAD",
                        "CLASIFICACION", "ESTRATO", "LIDER"));
                System.out.println(repitaCaracter('-', 105));
                // TODO Imprimir en pantalla la información del proyecto

                var lista = controller.listarProyectosPorBanco(banco);
                for (ProyectoBancoVo proyecto : lista) {
                    System.out.printf("%3s %-25s %-20s %-15s %7s %-30s %n", proyecto.getId(),
                            proyecto.getConstructora(), proyecto.getCiudad(), proyecto.getClasificacion(),
                            proyecto.getEstrato(), proyecto.getLider());
                }

            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }
    }

    public void totalAdeudadoPorProyectosSuperioresALimite(Double limiteInferior) {
        try {
            System.out.println(repitaCaracter('=', 1) + " TOTAL DEUDAS POR PROYECTO " + repitaCaracter('=', 1));
            if (limiteInferior != null) {
                System.out.println(String.format("%3s %15s", "ID", "VALOR "));
                System.out.println(repitaCaracter('-', 29));
                // TODO Imprimir en pantalla la información del total adeudado

                var lista = controller.listarTotalAdeudadoPorProyectoSuperioresAlLimite(limiteInferior);
                for (DeudasPorProyectoVo proyecto : lista) {
                    System.out.printf("%3d %,15.1f %n", proyecto.getId(), proyecto.getValor());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
            e.printStackTrace();
        }
    }

    public void lideresQueMasGastan() {
        try{
        System.out.println(repitaCaracter('=', 6) + " 10 LIDERES MAS COMPRADORES " + repitaCaracter('=', 7));
        System.out.println(String.format("%-25s %15s", "LIDER", "VALOR  "));
        System.out.println(repitaCaracter('-', 41));
        // TODO Imprimir en pantalla la información de los líderes
        var lista = controller.listarLideresQueMasGastan();
        for (ComprasDeLiderVo proyecto : lista) {
            System.out.printf("%-25s %,15.1f %n", proyecto.getLider(), proyecto.getValor());
        }
    } catch (SQLException e) {
        System.err.println("Error: " + e);
        e.printStackTrace();
    }
    }
}