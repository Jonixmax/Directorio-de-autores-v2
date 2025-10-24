package com.udb.autores.directorioautores.model;

import com.udb.autores.directorioautores.model.Author;
// Importarás tu clase de utilidad de JPA (ej: JPAUtil.java)
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Modelo para gestionar las operaciones CRUD de la entidad Author (Autor).
 * Se conecta a la base de datos usando JPA.
 */
public class AuthorModel {

    /**
     * Obtiene todos los autores de la base de datos.
     * @return Una lista de objetos Author.
     */
    public List<Author> getAllAuthors() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // "SELECT a FROM Author a" (Obtener todos los autores)
            TypedQuery<Author> query = em.createQuery(
                    "SELECT a FROM Author a",
                    Author.class
            );
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Guarda un nuevo autor en la base de datos (Create).
     * @param author El objeto Author a persistir.
     */
    public void saveAuthor(Author author) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            // Para operaciones de escritura (INSERT, UPDATE, DELETE)
            // necesitamos una transacción.
            tx = em.getTransaction();
            tx.begin(); // Iniciar transacción

            em.persist(author); // Guarda el nuevo autor

            tx.commit(); // Confirmar transacción
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Revertir si hay error
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Actualiza un autor existente en la base de datos (Update).
     * @param author El objeto Author con los datos actualizados.
     */
    public void updateAuthor(Author author) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            em.merge(author); // 'merge' actualiza un objeto existente

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Elimina un autor de la base de datos (Delete).
     * @param author El objeto Author a eliminar.
     */
    public void deleteAuthor(Author author) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            // Para eliminar, el objeto debe estar "manejado" por el EntityManager.
            // Si no lo está, primero lo buscamos y luego lo borramos.
            Author toDelete = em.find(Author.class, author.getId());
            if (toDelete != null) {
                em.remove(toDelete); // Elimina el autor
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Busca autores filtrados por un género específico (para AJAX).
     * @param genreId El ID del género por el cual filtrar.
     * @return Una lista de autores que pertenecen a ese género.
     */
    public List<Author> findAuthorsByGenre(int genreId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Consulta JPQL con un parámetro (:genreId)
            TypedQuery<Author> query = em.createQuery(
                    "SELECT a FROM Author a WHERE a.literaryGenre.id = :genreId",
                    Author.class
            );
            // Asignamos el valor al parámetro
            query.setParameter("genreId", genreId);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Busca un autor por su nombre exacto.
     * Útil para la validación de duplicados[cite: 67].
     * @param name El nombre a buscar.
     * @return El Author si se encuentra, o null.
     */
    public Author findAuthorByName(String name) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Author> query = em.createQuery(
                    "SELECT a FROM Author a WHERE a.name = :name",
                    Author.class
            );
            query.setParameter("name", name);
            // Usamos getSingleResult, pero puede fallar si no hay resultados.
            // Es mejor obtener una lista y revisar si está vacía.
            List<Author> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);

        } catch (javax.persistence.NoResultException e) {
            return null; // No se encontró
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