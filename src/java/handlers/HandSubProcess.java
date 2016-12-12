/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package handlers;

import models.SubProceso;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author JULIAN
 */
public class HandSubProcess {
    Connection con;
    Statement sentencia;

    public HandSubProcess() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://mobileagroserverdatabase.database.windows.net:1433;database=DataBaseMovileAgro;user=julianbolanos@mobileagroserverdatabase;password=,.940925jabort;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
            sentencia = con.createStatement();

        } catch (Exception e) {
            System.out.println("hubo un error   " + e.getMessage());
        }

    }

    public List<SubProceso> listarSubProcesosbyIdProceso(String idProceso) {
        List<SubProceso> resultado = new ArrayList<>();
        if (sentencia != null) {
            try {
                ResultSet res = sentencia.executeQuery("SELECT * FROM SubProceso WHERE idProceso = ");
                while (res.next()) {
                       SubProceso sp= new SubProceso();
                       sp.setId(res.getString("id"));
                       sp.setFecha(res.getDate("fecha"));
                       sp.setNombre(res.getString("nombre"));
                       sp.setIdProceso(res.getString("idProceso"));
                       sp.setNumeroenProceso(res.getInt("numeroenProceso"));
                       sp.setEstado(res.getInt("estado"));
                       resultado.add(sp);
                }
              sentencia.close();
              con.close();
            } catch (SQLException ex) {
                System.out.println("hubo un error   " + ex.getMessage());
            }
        }
        
        return resultado;
    }
    public void cerrarConexion(){
        try {
            sentencia.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("hubo un error   " + ex.getMessage());
        }
    
    }
}
