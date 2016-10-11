/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataBase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author julian
 */
public class ConexionDB {
    
   public ConexionDB(){
      try{
       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
       Connection con = DriverManager.getConnection("jdbc:sqlserver://agromovil-server.database.windows.net:1433;databaseName=agromovildb",
                    "julianbolanos",",.940925jabort");
       Statement sentencia = con.createStatement();
       ResultSet rs = sentencia.executeQuery("select * from Todoitem");
          System.out.println("hola");
      }catch(Exception e){
          System.out.println("hubo un error   "+e.getMessage());
      }
    
   }
    
    
}
