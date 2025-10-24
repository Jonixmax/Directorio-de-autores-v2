package com.udb.autores.directorioautores.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase de utilidad para manejar la creación del EntityManagerFactory
 * y proveer EntityManagers para la aplicación.
 * Sigue el patrón Singleton para la fábrica.
 */
public class JPAUtil {

    // El nombre "AuthorsPU" DEBE COINCIDIR con el 'persistence-unit name'
    // que definimos en persistence.xml
    private static final String PERSISTENCE_UNIT_NAME = "AuthorsPU";

    // El EntityManagerFactory es "costoso" de crear.
    // Lo creamos UNA SOLA VEZ para toda la aplicación.
    private static EntityManagerFactory factory;

    /**
     * Inicializa el EntityManagerFactory.
     */
    private static void initFactory() {
        try {
            if (factory == null) {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            }
        } catch (Exception e) {
            // Error grave: la aplicación no puede conectarse a la BD
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar el EntityManagerFactory", e);
        }
    }

    /**
     * Método público para obtener un EntityManager (la conexión a la BD).
     * @return Un nuevo EntityManager para realizar operaciones.
     */
    public static EntityManager getEntityManager() {
        // Aseguramos que la fábrica esté inicializada
        initFactory();

        // Creamos y retornamos un nuevo EntityManager
        return factory.createEntityManager();
    }

    /**
     * Cierra el factory cuando la aplicación se detiene (opcional).
     * (Se puede llamar desde un ServletContextListener al apagar el servidor).
     */
    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}