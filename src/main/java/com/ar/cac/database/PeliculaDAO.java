package com.ar.cac.database;

import java.sql.Connection; // Importa la clase Connection para manejar la conexión a la base de datos.
import java.sql.PreparedStatement; // Importa la clase PreparedStatement para ejecutar consultas SQL precompiladas.
import java.sql.ResultSet; // Importa la clase ResultSet para manejar los resultados de las consultas SQL.
import java.sql.SQLException; // Importa la clase SQLException para manejar excepciones de SQL.
import java.util.ArrayList; // Importa la clase ArrayList para usar listas dinámicas.
import java.util.List; // Importa la interfaz List para trabajar con listas.

import com.ar.cac.movies.Pelicula;

public class PeliculaDAO { // Declara la clase PeliculaDAO.

  // Método para insertar una película en la base de datos.
    public Long insertPelicula(Pelicula pelicula) {
        String INSERT_PELICULA_SQL = "INSERT INTO peliculas (titulo, duracion, genero, imagen) VALUES (?, ?, ?, ?)";
        // Usa try-with-resources para asegurar que los recursos se cierran automáticamente.
        try (Connection connection = DatabaseConnection.getConnection(); // Obtiene una conexión a la base de datos.
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PELICULA_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) { // Prepara la consulta SQL para insertar la película.
            // el id_pelicula es autoincremental, por lo que no es necesario pasarlo como parámetro

            preparedStatement.setString(1, pelicula.getTitulo()); // Establece el valor del primer parámetro (titulo).
            preparedStatement.setString(2, pelicula.getDuracion()); // Establece el valor del segundo parámetro (duracion).
            preparedStatement.setString(3, pelicula.getGenero()); // Establece el valor del tercer parámetro (genero).
            preparedStatement.setString(4, pelicula.getImagen()); // Establece el valor del cuarto parámetro (imagen).

            int result = preparedStatement.executeUpdate(); // Ejecuta la consulta y devuelve el número de filas afectadas.
            if (result > 0) {
                // levantar el id de la pelicula insertada y retornarlo 
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    System.out.println("Pelicula insertada exitosamente. ID: " + id); // Imprime un mensaje si la inserción fue exitosa.
                    return id;
                } else {
                    System.out.println("Error al obtener el ID de la pelicula insertada."); // Imprime un mensaje si hubo un error en la inserción.
                    return null;
                }
            } else {
                System.out.println("Error al insertar la pelicula."); // Imprime un mensaje si hubo un error en la inserción.
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar la pelicula: " + e.getMessage()); // Maneja cualquier excepción SQL y muestra el mensaje de error.
            return null;
        }
    }


    // Método para obtener todas las películas de la base de datos.
    public List<Pelicula> getAllPeliculas() {
        String SELECT_ALL_PELICULAS_SQL = "SELECT * FROM peliculas";
        List<Pelicula> peliculas = new ArrayList<>(); // Crea una lista para almacenar las películas.
        // Usa try-with-resources para asegurar que los recursos se cierran automáticamente.
        try (Connection connection = DatabaseConnection.getConnection(); // Obtiene una conexión a la base de datos.
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PELICULAS_SQL)) { // Prepara la consulta SQL para seleccionar todas las películas.
            ResultSet resultSet = preparedStatement.executeQuery(); // Ejecuta la consulta y obtiene los resultados.
            while (resultSet.next()) { // Itera sobre los resultados de la consulta.
                Long idPelicula = resultSet.getLong("id_pelicula"); // Obtiene el valor de la columna 'id_pelicula'.
                String titulo = resultSet.getString("titulo"); // Obtiene el valor de la columna 'titulo'.
                String duracion = resultSet.getString("duracion"); // Obtiene el valor de la columna 'duracion'.
                String genero = resultSet.getString("genero"); // Obtiene el valor de la columna 'genero'.
                String imagen = resultSet.getString("imagen"); // Obtiene el valor de la columna 'imagen'.

                Pelicula pelicula = new Pelicula(idPelicula, titulo, duracion, genero, imagen); // Crea un nuevo objeto Pelicula con los valores obtenidos.
                peliculas.add(pelicula); // Añade la película a la lista.
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las peliculas: " + e.getMessage()); // Maneja cualquier excepción SQL y muestra el mensaje de error.
        }
        return peliculas; // Devuelve la lista de películas.
    }
    // metodo para obtener una pelicula por id
    public Pelicula getPeliculaById(Long id) {
        Pelicula pelicula = null; // Crea una variable para almacenar la película.
        // Usa try-with-resources para asegurar que los recursos se cierran automáticamente.
        try (Connection connection = DatabaseConnection.getConnection(); // Obtiene una conexión a la base de datos.
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM peliculas WHERE id_pelicula = ?")) { // Prepara la consulta SQL para seleccionar una película por id.
            preparedStatement.setLong(1, id); // Establece el valor del primer parámetro (id).
            ResultSet resultSet = preparedStatement.executeQuery(); // Ejecuta la consulta y obtiene los resultados.
            if (resultSet.next()) { // Verifica si hay resultados.
                Long idPelicula = resultSet.getLong("id_pelicula"); // Obtiene el valor de la columna 'id_pelicula'.
                String titulo = resultSet.getString("titulo"); // Obtiene el valor de la columna 'titulo'.
                String duracion = resultSet.getString("duracion"); // Obtiene el valor de la columna 'duracion'.
                String genero = resultSet.getString("genero"); // Obtiene el valor de la columna 'genero'.
                String imagen = resultSet.getString("imagen"); // Obtiene el valor de la columna 'imagen'.

                pelicula = new Pelicula(idPelicula, titulo, duracion, genero, imagen); // Crea un nuevo objeto Pelicula con los valores obtenidos.
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la pelicula: " + e.getMessage()); // Maneja cualquier excepción SQL y muestra el mensaje de error.
        }
        return pelicula; // Devuelve la película.
    }
    public void updatePelicula(Pelicula pelicula) {
        if (pelicula.getIdPelicula() == null) {
            throw new IllegalArgumentException("El ID de la película no puede ser nulo para actualizarla.");
        }
    
        // Define la consulta SQL para actualizar una película en la tabla 'peliculas'.
        String UPDATE_PELICULA_SQL = "UPDATE peliculas SET titulo = ?, duracion = ?, genero = ?, imagen = ? WHERE id_pelicula = ?";
        // Usa try-with-resources para asegurar que los recursos se cierran automáticamente.
        try (Connection connection = DatabaseConnection.getConnection(); // Obtiene una conexión a la base de datos.
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PELICULA_SQL)) { // Prepara la consulta SQL para actualizar la película.
            preparedStatement.setString(1, pelicula.getTitulo()); // Establece el valor del primer parámetro (titulo).
            preparedStatement.setString(2, pelicula.getDuracion()); // Establece el valor del segundo parámetro (duracion).
            preparedStatement.setString(3, pelicula.getGenero()); // Establece el valor del tercer parámetro (genero).
            preparedStatement.setString(4, pelicula.getImagen()); // Establece el valor del cuarto parámetro (imagen).
            preparedStatement.setLong(5, pelicula.getIdPelicula()); // Establece el valor del quinto parámetro (id).
    
            int result = preparedStatement.executeUpdate(); // Ejecuta la consulta y devuelve el número de filas afectadas.
            if (result > 0) {
                System.out.println("Pelicula actualizada exitosamente."); // Imprime un mensaje si la actualización fue exitosa.
            } else {
                System.out.println("Error al actualizar la pelicula."); // Imprime un mensaje si hubo un error en la actualización.
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la pelicula: " + e.getMessage()); // Maneja cualquier excepción SQL y muestra el mensaje de error.
        }
    }
    
    // metodo para eliminar una pelicula
    public void deletePelicula(Long id) {
        // Define la consulta SQL para eliminar una película de la tabla 'peliculas'.
        String DELETE_PELICULA_SQL = "DELETE FROM peliculas WHERE id_pelicula = ?";
        // Usa try-with-resources para asegurar que los recursos se cierran automáticamente.
        try (Connection connection = DatabaseConnection.getConnection(); // Obtiene una conexión a la base de datos.
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PELICULA_SQL)) { // Prepara la consulta SQL para eliminar la película.
            preparedStatement.setLong(1, id); // Establece el valor del primer parámetro (id).

            int result = preparedStatement.executeUpdate(); // Ejecuta la consulta y devuelve el número de filas afectadas.
            if (result > 0) {
                System.out.println("Pelicula eliminada exitosamente."); // Imprime un mensaje si la eliminación fue exitosa.
            } else {
                System.out.println("Error al eliminar la pelicula."); // Imprime un mensaje si hubo un error en la eliminación.
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la pelicula: " + e.getMessage()); // Maneja cualquier excepción SQL y muestra el mensaje de error.
        }
    }
    // metodo para buscar peliculas por titulo
    public List<Pelicula> searchPeliculasByTitulo(String titulo) {
        String SEARCH_PELICULAS_SQL = "SELECT * FROM peliculas WHERE titulo LIKE ?";
        List<Pelicula> peliculas = new ArrayList<>(); // Crea una lista para almacenar las películas.
        // Usa try-with-resources para asegurar que los recursos se cierran automáticamente.
        try (Connection connection = DatabaseConnection.getConnection(); // Obtiene una conexión a la base de datos.
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_PELICULAS_SQL)) { // Prepara la consulta SQL para buscar películas por título.
            preparedStatement.setString(1, "%" + titulo + "%"); // Establece el valor del primer parámetro (titulo).
            ResultSet resultSet = preparedStatement.executeQuery(); // Ejecuta la consulta y obtiene los resultados.
            while (resultSet.next()) { // Itera sobre los resultados de la consulta.
                Long idPelicula = resultSet.getLong("id_pelicula"); // Obtiene el valor de la columna 'id_pelicula'.
                String tituloPelicula = resultSet.getString("titulo"); // Obtiene el valor de la columna 'titulo'.
                String duracion = resultSet.getString("duracion"); // Obtiene el valor de la columna 'duracion'.
                String genero = resultSet.getString("genero"); // Obtiene el valor de la columna 'genero'.
                String imagen = resultSet.getString("imagen"); // Obtiene el valor de la columna 'imagen'.

                Pelicula pelicula = new Pelicula(idPelicula, tituloPelicula, duracion, genero, imagen); // Crea un nuevo objeto Pelicula con los valores obtenidos.
                peliculas.add(pelicula); // Añade la película a la lista.
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar las peliculas: " + e.getMessage()); // Maneja cualquier excepción SQL y muestra el mensaje de error.
        }
        return peliculas; // Devuelve la lista de películas.
    }

  
}

