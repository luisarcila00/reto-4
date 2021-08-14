package me.arsure.larcila.controller;

import java.sql.SQLException;
import java.util.List;

import me.arsure.larcila.model.dao.*;
import me.arsure.larcila.model.vo.*;

public class ReportesController {

    private ProyectoBancoDao proyectoBancoDao;
    private DeudasPorProyectoDao deudasPorProyectoDao;
    private ComprasDeLiderDao comprasDeLiderDao;

    public ReportesController() {    
        proyectoBancoDao = new ProyectoBancoDao();
        deudasPorProyectoDao = new DeudasPorProyectoDao();
        comprasDeLiderDao = new ComprasDeLiderDao();
    }   

    public List<ProyectoBancoVo> listarProyectosPorBanco(String banco) throws SQLException {
        return proyectoBancoDao.listarProyectosBanco(banco);
    }
    public List<DeudasPorProyectoVo> listarTotalAdeudadoPorProyectoSuperioresAlLimite(Double limiteInferior) throws SQLException {
        return deudasPorProyectoDao.listarTotalAdeudadoPorProyectoSuperioresLimite(limiteInferior);
    }
    public List<ComprasDeLiderVo> listarLideresQueMasGastan() throws SQLException {
        return comprasDeLiderDao.listarLideresQueGastan();
    }
}