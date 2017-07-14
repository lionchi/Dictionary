package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by gavri on 14.07.2017.
 */
public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    public static Session getSession(){
        return sessionFactory.openSession();
    }

    public static void init(){
        try{
            sessionFactory = new Configuration().
                    configure("hibernate.cfg.xml").
                    buildSessionFactory();
        }
        catch (Throwable e){
            throw  new ExceptionInInitializerError(e);
        }
    }

    public  static void shutdown(){sessionFactory.close();}
}
