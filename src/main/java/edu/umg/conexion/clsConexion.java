package edu.umg.conexion;

import java.sql.*;
import java.util.Scanner;

public class clsConexion {
        private Connection conn = null;

        private void conexion(){
            try{
                // Establecer la conexión a la base de datos
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crud1", "root", "Obed2003.1");
            } catch (SQLException e){
                System.out.println("Ups, hubo un error: " + e.getMessage());
            }
        }

        public void leerDatos() throws SQLException {
            conexion();
            String query = "SELECT * FROM tb_datos_tarea";
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                ps = conn.prepareStatement(query);
                rs = ps.executeQuery();

                while (rs.next()){
                    System.out.println("Nombre: " + rs.getString("nombre") + " Apellido: " + rs.getString("apellido"));
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                conn.close();
            }
        }

        public void insertarDatos() throws SQLException {
            conexion();
            Scanner sc = new Scanner(System.in);
            System.out.println("Ingrese nombre: ");
            String nombre = sc.nextLine();
            System.out.println("Ingrese apellido: ");
            String apellido = sc.nextLine();

            String query = "INSERT INTO tb_datos_tarea (nombre, apellido) VALUES (?, ?)";
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(query);
                ps.setString(1, nombre);
                ps.setString(2, apellido);
                ps.executeUpdate();
                System.out.println("Datos insertados correctamente");
            } finally {
                if (ps != null) {
                    ps.close();
                }
                conn.close();
            }
        }
    }


