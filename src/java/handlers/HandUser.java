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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Usuario;

/**
 *
 * @author JULIAN
 */
public class HandUser {

    Connection con;
    Statement sentencia;

    public HandUser() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://agromovil-server.database.windows.net:1433;databaseName=agromovildb",
                    "julianbolanos", ",.940925jabort");
            sentencia = con.createStatement();

        } catch (Exception e) {
            System.out.println("hubo un error   " + e.getMessage());
        }

    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> resultado = new ArrayList<Usuario>();
        if (sentencia != null) {
            try {
                ResultSet res = sentencia.executeQuery("SELECT * FROM Usuarios");
                while (res.next()) {
                       Usuario us= new Usuario();
                       us.setId(res.getInt("idUsuario"));
                       us.setEmail(res.getString("correo"));
                       us.setLogin(res.getString("login"));
                       us.setNombre(res.getString("nombre"));
                       us.setPassword(res.getString("password"));
                       us.setTipo(res.getString("tipoUsuario"));
                       resultado.add(us);
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