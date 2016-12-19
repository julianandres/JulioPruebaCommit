/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import models.Proceso;

/**
 *
 * @author JULIAN
 */
public class HandProcess {
    Connection con;
    Statement sentencia;

    public HandProcess() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://mobileagroserverdatabase.database.windows.net:1433;database=DataBaseMovileAgro;user=julianbolanos@mobileagroserverdatabase;password=,.940925jabort;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
            sentencia = con.createStatement();

        } catch (Exception e) {
            System.out.println("hubo un error   " + e.getMessage());
        }

    }

    public List<Proceso> listarProcesosByIdUsuario(String idusuario) {
        List<Proceso> resultado = new ArrayList<>();
        if (sentencia != null) {
            try {
                
                ResultSet res = sentencia.executeQuery("SELECT * FROM Proceso WHERE idUsuario = '"+idusuario+"'");
                while (res.next()) {
                       Proceso sp= new Proceso();
                       sp.setId(res.getString("id"));
                       sp.setNombre(res.getString("nombre"));
                       sp.setIdUsuario(res.getString("idUsuario"));
                       //sp.setFechaInicio(res.getDate("fechaInicio"));
                       String tm = res.getString("fechaInicio");
                       DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
                       sp.setFechaInicio(format.parse(tm));
                       tm = res.getString("fechaFin");
                       sp.setFechaFin(format.parse(tm));
                       sp.setDuracionSemanas(res.getInt("duracionSemanas"));
                       sp.setNumeroSubprocesos(res.getInt("numeroSubprocesos"));
                       sp.setState(res.getBoolean("state"));
                       sp.setSubProcesoActual(res.getInt("subProcesoActual"));
                       resultado.add(sp);
                }
              sentencia.close();
              con.close();
            } catch (Exception ex) {
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
