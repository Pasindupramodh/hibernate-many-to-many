package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Group;
import org.example.entity.User;
import org.example.persistence.PersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String,String> props = new HashMap<>();

        props.put("hibernate.show_sql","true");
        props.put("hibernate.hbm2ddl.auto","create");


//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new PersistenceUnitInfo(),props);
        EntityManager entityManager = emf.createEntityManager();//represent the context
        try {
            entityManager.getTransaction().begin();

            User user = new User();
            user.setName("Pasindu");

            User user1 = new User();
            user1.setName("Pramodh");

            Group group = new Group();
            group.setName("group1");
            group.setUsers(List.of(user1,user));

            Group group1 = new Group();
            group1.setName("group2");
            group1.setUsers(List.of(user1));

            user1.setGroups(List.of(group,group1));
            user.setGroups(List.of(group));

            entityManager.persist(user);
            entityManager.persist(user1);
            entityManager.persist(group);
            entityManager.persist(group1);
            entityManager.getTransaction().commit();
        }finally {
            entityManager.close();
        }
    }
}