package lk.ijse.dep9.util;

import lk.ijse.dep9.dto.StudentDTO;
import lk.ijse.dep9.entity.Student;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateUtil {
    
    private final static SessionFactory sessionFactory=buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .loadProperties("/application.properties")
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClasses(Student.class, StudentDTO.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        return metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
