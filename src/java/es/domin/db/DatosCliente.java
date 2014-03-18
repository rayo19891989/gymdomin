/*
 * Copyright (C) 2014 Javier García Escobedo (javiergarbedo.es)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.domin.db;
import es.domin.beans.Cliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatosCliente {

    private static Connection connection;

    public static void connect(String databaseServer, String databaseName, String databaseUser, String databasePassword) {
        String strConection = "jdbc:mysql://" + databaseServer + "/" + databaseName;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(strConection, databaseUser, databasePassword);
            createTables();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1049) {
                Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE, "Base de Datos no encontrada: {0} - {1}/{2}", new Object[]{strConection, databaseUser, databasePassword});
                createDatabase(databaseServer, databaseName, databaseUser, databasePassword);
                Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                        "Base de Datos Cliente creada");
                try {
                    connection = DriverManager.getConnection(strConection, databaseUser, databasePassword);
                    createTables();
                } catch (SQLException ex1) {
                    Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean isConnected() {
        if(connection!=null) {
            return true;
        } else {
            return false;
        }
    }

    private static void createDatabase(String databaseServer, String databaseName, String databaseUser, String databasePassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String strConecction = "jdbc:mysql://" + databaseServer;
            connection = DriverManager.getConnection(strConecction, databaseUser, databasePassword);
            Statement statement = connection.createStatement();
            String sql = "CREATE DATABASE " + databaseName;
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE, "Executing SQL statement: {0}", sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE, "SQL result: {0}", result);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void createTables() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS cliente ("
                    + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(50), "
                    + "apellidos VARCHAR(100), "
                    + "numtelefono VARCHAR(50), "
                    + "direccion VARCHAR(255), "
                    + "ciudad VARCHAR(50), "
                    + "birth_date DATE, "
                    + "photo_file_name VARCHAR(50))";
             Statement statement = connection.createStatement();
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void insertCliente(Cliente cliente) {
        //Formato para campos de tipo fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("''yyyy-MM-dd''");
        Date birthDate = cliente.getBirthDate();
        String birthDateSql = null;
        if (birthDate != null) {
            birthDateSql = dateFormat.format(birthDate);
        }

        try {
            String sql = "INSERT INTO cliente "
                    //No se incluye el ID, ya que es autonumérico
                    + "(nombre, apellidos, numtelefono, direccion,ciudad, birth_date,photo_file_name) "
                    + "VALUES ("
                    + "'" + cliente.getName() + "', "
                    + "'" + cliente.getSurnames() + "', "
                    + "'" + cliente.getPhoneNumber() + "', "
                    + "'" + cliente.getAddress()+ "', "
                    + "'" + cliente.getCity()+ "', "
                    + birthDateSql + ", "
                    + "'" + cliente.getPhotoFileName() + "')";
            Statement statement = connection.createStatement();
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE, "Executing SQL statement: {0}", sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE, "SQL result: {0}", result);
        } catch (SQLException ex) {
            Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Cliente> getClienteList() {
        ArrayList<Cliente> clienteList = new ArrayList();
        try {
            String sql = "SELECT * FROM cliente";
            Statement statement = connection.createStatement();
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            ResultSet rs = statement.executeQuery(sql);
            boolean result = rs.isBeforeFirst();
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
            while (rs.next()) {
                int columnIndex = 1;
                int id = rs.getInt(columnIndex++);
                String nombre = rs.getString(columnIndex++);
                String apellidos = rs.getString(columnIndex++);
                String numtelefono = rs.getString(columnIndex++);
                String direccion = rs.getString(columnIndex++);
                String ciudad = rs.getString(columnIndex++);
                Date birthDate = rs.getDate(columnIndex++);
                String photoFileName = rs.getString(columnIndex++);
                Cliente cliente = new Cliente(id, nombre, apellidos, 
                        numtelefono, direccion,ciudad,birthDate, photoFileName);
                clienteList.add(cliente);
            }
            return clienteList;
        } catch (SQLException ex) {
            Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Cliente getClienteByID(int clienteId) {
        Cliente cliente = null;
        try {
            String sql = "SELECT * FROM cliente WHERE id=" + clienteId;
            Statement statement = connection.createStatement();
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            ResultSet rs = statement.executeQuery(sql);
            boolean result = rs.isBeforeFirst();
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
            if (rs.next()) {
                int columnIndex = 1;
                int id = rs.getInt(columnIndex++);
                String nombre = rs.getString(columnIndex++);
                String apellidos = rs.getString(columnIndex++);
                String numtelefono = rs.getString(columnIndex++);
                String direccion = rs.getString(columnIndex++);
                String ciudad = rs.getString(columnIndex++);
                Date birthDate = rs.getDate(columnIndex++);
                String photoFileName = rs.getString(columnIndex++);
                cliente = new Cliente(id, nombre, apellidos, 
                        numtelefono, direccion, 
                        ciudad,birthDate, photoFileName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cliente;
    }

    public static void updateCliente(Cliente cliente) {
        //Formato para campos de tipo fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("''yyyy-MM-dd''");
        Date birthDate = cliente.getBirthDate();
        String birthDateSql = null;
        if (birthDate != null) {
            birthDateSql = dateFormat.format(birthDate);
        }
        try {
            String sql = "UPDATE cliente SET "
                    + "nombre='" + cliente.getName() + "', "
                    + "numtelefono='" + cliente.getPhoneNumber()+ "', "
                    + "direccion='" + cliente.getAddress()+ "', "
                    + "ciudad='" + cliente.getCity()+ "', "
                    + "birth_date=" + birthDateSql + ", "
                    + "photo_file_name='" + cliente.getPhotoFileName() + "' "
                    + "WHERE id=" + cliente.getId();
            Statement statement = connection.createStatement();
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteClienteById(String id) {
        try {
            String sql = "DELETE FROM cliente WHERE id=" + id;
            Statement statement = connection.createStatement();
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                    "Ejecutar SQL: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(DatosCliente.class.getName()).log(Level.FINE,
                    "Resultado SQL: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(DatosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
