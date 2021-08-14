package me.arsure.larcila.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.arsure.larcila.model.vo.ProyectoBancoVo;
import me.arsure.larcila.util.JDBCUtilities;

public class ProyectoBancoDao {

    public List<ProyectoBancoVo> listarProyectosBanco(String banco) throws SQLException {
        List<ProyectoBancoVo> respuesta = new ArrayList<>();
        var conn = JDBCUtilities.getConnection();
        PreparedStatement stmt = null;
        ResultSet rset = null;
        try {
            var query = "SELECT ID_Proyecto as ID, CONSTRUCTORA, CIUDAD, p.Clasificacion as CLASIFICACION, ESTRATO, p.Banco_Vinculado, Nombre || ' ' || Primer_Apellido || ' ' || Segundo_Apellido as LIDER "        
                    + "FROM Proyecto p "
                    + "JOIN Tipo t ON t.ID_Tipo = p.ID_Tipo JOIN Lider l ON l.ID_Lider = p.ID_Lider "
                    + "WHERE Lower(Banco_Vinculado) LIKE ? "
                    + "ORDER BY p.Fecha_Inicio DESC , p.Ciudad ASC, p.Constructora";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + banco.toLowerCase() + "%");
            rset = stmt.executeQuery();

            while (rset.next()) {
                var vo = new ProyectoBancoVo();
                vo.setBanco(rset.getString("Banco_Vinculado"));
                vo.setLider(rset.getString("LIDER"));  
                vo.setId(rset.getInt("ID"));
                vo.setConstructora(rset.getString("CONSTRUCTORA"));
                vo.setCiudad(rset.getString("CIUDAD"));
                vo.setClasificacion(rset.getString("CLASIFICACION"));
                vo.setEstrato(rset.getInt("ESTRATO"));
                          

                respuesta.add(vo);
            }
        } finally {
            if (rset != null) {
                rset.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return respuesta;
    }
}
