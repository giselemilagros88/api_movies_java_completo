package com.ar.cac.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/peliculas_cac_java"; // Cambia esto a la URL de tu base de datos
    private static final String USER = "root"; // Cambia esto a tu usuario
    private static final String PASSWORD = ""; // Cambia esto a tu contraseña

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Asegúrate de que el controlador JDBC esté disponible en tu proyecto
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Imprime el error en la consola para diagnóstico
        }
        return connection;
    }
}
