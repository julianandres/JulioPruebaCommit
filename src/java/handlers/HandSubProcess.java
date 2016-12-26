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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
                ResultSet res = sentencia.executeQuery("SELECT * FROM SubProceso WHERE idProceso = '" + idProceso + "' ");
                while (res.next()) {
                    SubProceso sp = new SubProceso();
                    sp.setId(res.getString("id"));
                    String tm = res.getString("fecha");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
                    sp.setFecha(format.parse(tm));
                    sp.setNombre(res.getString("nombre"));
                    sp.setIdProceso(res.getString("idProceso"));
                    sp.setNumeroenProceso(res.getInt("numeroenProceso"));
                    if (res.getInt("estado") == 0) {
                        sp.setStateShow("No disponible");
                    }
                    if (res.getInt("estado") == 1) {
                        sp.setStateShow("Actual");
                    }
                    if (res.getInt("estado") == 2) {
                        sp.setStateShow("Finalizado");
                    }
                    GregorianCalendar fechaHoy = new GregorianCalendar();
                    GregorianCalendar config = new GregorianCalendar();
                    fechaHoy.setTime(new Date());
                    
                    Date datePrevia= addDays(sp.getFecha(),1);
                    Date datePost= addDays(sp.getFecha(),-1);
                    
                    config.setTime(datePrevia);
                    System.out.println(fechaHoy.getTime() + " fechaHoy");
                    System.out.println(config.getTime() + " fecha config");

                    int ts = config.compareTo(fechaHoy);
                    if(ts<0){
                        config.setTime(datePost);
                        ts = config.compareTo(fechaHoy);
                        if(ts>0){
                            //esta en el rango disponible
                            sp.setDisponibilidad(1);
                        
                        }else{
                            //rango pasado
                            sp.setDisponibilidad(2);
                        }
                    }else{
                       sp.setDisponibilidad(0);
                      //aun no se llega la fecha
                    }
                    
                    
                    sp.setEstado(res.getInt("estado"));
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

    public void cerrarConexion() {
        try {
            sentencia.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("hubo un error   " + ex.getMessage());
        }

    }
    public Date addDays(Date fecha, int dias) {
        Date dato = fecha;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fecha);
        gc.add(Calendar.DAY_OF_MONTH, dias);
        dato = gc.getTime();
        return dato;
    }
}
