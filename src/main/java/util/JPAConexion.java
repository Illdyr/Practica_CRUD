package util;

import jakarta.persistence.*;


public class JPAConexion
{
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_db");

    public static EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }
    public static void close()
        {
        emf.close();
        }
}
