package com.udb.autores.directorioautores.model;

import com.udb.autores.directorioautores.model.LiteraryGenre;
// Importarás tu clase de utilidad de JPA (ej: JPAUtil.java)
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Modelo para gestionar las operaciones CRUD de la entidad LiteraryGenre (Genero).
 * Se conecta a la base de datos usando JPA.
 */
public class LiteraryGenreModel {

    /**
     * Obtiene todos los géneros literarios de la base de datos.
     * @return Una lista de objetos LiteraryGenre.
     */
    public List<LiteraryGenre> getAllGenres() {
        // Obtenemos el EntityManager (nuestro manejador de BD)
        // Nota: Necesitarás una clase de utilidad (ej: JPAUtil) para esto.
        EntityManager em = JPAUtil.getEntityManager();

        try {
            // Creamos una consulta (Query) usando JPQL (similar a SQL)
            // "SELECT g FROM LiteraryGenre g" significa:
            // "Selecciona todo 'g' donde 'g' es una entidad LiteraryGenre"
            TypedQuery<LiteraryGenre> query = em.createQuery(
                    "SELECT g FROM LiteraryGenre g",
                    LiteraryGenre.class
            );

            // Ejecutamos la consulta y devolvemos la lista de resultados
            return query.getResultList();

        } catch (Exception e) {
            // Manejo de errores (en un proyecto real, usa un logger)
            e.printStackTrace();
            return null;
        } finally {
            // Siempre cerramos el EntityManager
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Busca un género por su ID.
     * Útil para cuando agregamos un autor.
     * @param id El ID del género a buscar.
     * @return El objeto LiteraryGenre encontrado, o null si no existe.
     */
    public LiteraryGenre findGenreById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // em.find() es la forma más rápida de buscar por llave primaria
            return em.find(LiteraryGenre.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}