package edu.umg;

import edu.umg.conexion.clsConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    private static Connection conn = null;


    private static void conexion() {
        try {
            // Establecer la conexión a la base de datos
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crud1", "root", "Obed2003.1");
        } catch (SQLException e) {
            System.out.println("Ups, hubo un error: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        conexion();
        clsConexion conexion = new clsConexion();


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Opciones:");
            System.out.println("1. Agregar persona");
            System.out.println("2. Consultar personas");
            System.out.println("3. Actualizar información de persona");
            System.out.println("4. Eliminar persona");
            System.out.println("5. Salir");

            String opcion = scanner.nextLine();

            if (opcion.equals("1")) {
                System.out.println("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.println("Apellido: ");
                String apellido = scanner.nextLine();
                System.out.println("Fecha de registro (YYYY-MM-DD): ");
                String fechaRegistro = scanner.nextLine();
                System.out.println("Sueldo base: ");
                double sueldoBase = Double.parseDouble(scanner.nextLine());
                System.out.println("Sexo (Masculino/Femenino/Otro): ");
                String sexo = scanner.nextLine();
                System.out.println("Edad: ");
                int edad = Integer.parseInt(scanner.nextLine());
                System.out.println("Longitud de casa: ");
                double longitud = Double.parseDouble(scanner.nextLine());
                System.out.println("Latitud de casa: ");
                double latitud = Double.parseDouble(scanner.nextLine());
                System.out.println("Comentarios: ");
                String comentarios = scanner.nextLine();
                agregarPersona(nombre, apellido, fechaRegistro, sueldoBase, sexo, edad, longitud, latitud, comentarios);
            } else if (opcion.equals("2")) {
                consultarPersonas();
            } else if (opcion.equals("3")) {
                System.out.println("Código de la persona a actualizar: ");
                int codigo = Integer.parseInt(scanner.nextLine());
                System.out.println("Nuevo sueldo base: ");
                double nuevoSueldoBase = Double.parseDouble(scanner.nextLine());
                System.out.println("Nuevos comentarios: ");
                String nuevosComentarios = scanner.nextLine();

                actualizarPersona(codigo, nuevoSueldoBase, nuevosComentarios);
            } else if (opcion.equals("4")) {

                System.out.println("Código de la persona a eliminar: ");
                int codigo = Integer.parseInt(scanner.nextLine());
                eliminarPersona(codigo);
            } else if (opcion.equals("5")) {
                break;
            }
        }

        // Cerrar la conexión a la base de datos
        if (conn != null)
        conn.close();
    }

    private static void agregarPersona(String nombre, String apellido, String fechaRegistro, double sueldoBase, String sexo, int edad, double longitud, double latitud, String comentarios) throws SQLException {
        if (conn == null) {
            conexion();
        }
        String query = "INSERT INTO tb_datos_tarea (nombre, apellido, fecha_registro, sueldo_base, sexo, edad, longitud, latitud, comentarios) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, fechaRegistro);
        ps.setDouble(4, sueldoBase);
        ps.setString(5, sexo);
        ps.setInt(6, edad);
        ps.setDouble(7, longitud);
        ps.setDouble(8, latitud);
        ps.setString(9, comentarios);

        ps.executeUpdate();
        System.out.println("Registro insertado con éxito");
    }

    private static void consultarPersonas() throws SQLException {
        if(conn == null) {
            conexion();
        }
        String query = "SELECT * FROM tb_datos_tarea";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println("Nombre: " + rs.getString("nombre") + " Apellido: " + rs.getString("apellido"));
        }
    }

    private static void actualizarPersona(int codigo, double sueldoBase, String comentarios) throws SQLException {
        if (conn == null) {
            conexion();
        }
        String query = "UPDATE tb_datos_tarea SET sueldo_base = ?, comentarios = ? WHERE codigo = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setDouble(1, sueldoBase);
        ps.setString(2, comentarios);
        ps.setInt(3, codigo);

        ps.executeUpdate();
        System.out.println("Registro actualizado con éxito");
    }

    private static void eliminarPersona(int codigo) throws SQLException {
        if (conn == null) {
            conexion();
        }
        String query = "DELETE FROM tb_datos_tarea WHERE codigo = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, codigo);

        ps.executeUpdate();
        System.out.println("Registro eliminado con éxito");
    }
}






/*public class Main {
    public static void main(String[] args) {
        clsConexion conexion = new clsConexion();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Opciones:");
            System.out.println("1. Agregar persona");
            System.out.println("2. Consultar personas");
            System.out.println("3. Actualizar información de persona");
            System.out.println("4. Eliminar persona");
            System.out.println("5. Salir");

            String opcion = scanner.nextLine();

            try {
                if (opcion.equals("1")) {
                    System.out.println("Ingrese nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingrese apellido: ");
                    String apellido = scanner.nextLine();
                    conexion.insertarDatos(nombre, apellido);
                } else if (opcion.equals("2")) {
                    conexion.leerDatos();
                } else if (opcion.equals("3")) {
                    System.out.println("Código de la persona a actualizar: ");
                    int codigo = Integer.parseInt(scanner.nextLine());
                    System.out.println("Nuevo sueldo base: ");
                    double nuevoSueldoBase = Double.parseDouble(scanner.nextLine());
                    System.out.println("Nuevos comentarios: ");
                    String nuevosComentarios = scanner.nextLine();
                    conexion.actualizarPersona(codigo, nuevoSueldoBase, nuevosComentarios);
                } else if (opcion.equals("4")) {
                    System.out.println("Código de la persona a eliminar: ");
                    int codigo = Integer.parseInt(scanner.nextLine());
                    conexion.eliminarPersona(codigo);
                } else if (opcion.equals("5")) {
                    break;
                } else {
                    System.out.println("Opción no válida.");
                }
            } catch (SQLException e) {
                System.out.println("Error al realizar la operación en la base de datos: " + e.getMessage());
            }
        }

        // Cerrar la conexión a la base de datos al finalizar
        conexion.cerrarConexion();
    }
}*/

