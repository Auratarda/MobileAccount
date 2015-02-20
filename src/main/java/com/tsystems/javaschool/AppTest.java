package com.tsystems.javaschool;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Option;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Test.
 */
public class AppTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MobileAccountPU");
        EntityManager em = emf.createEntityManager();
        try {
            Option newOption = createOption();
            em.getTransaction().begin();
            em.persist(newOption);
            em.getTransaction().commit();
            System.out.printf("New option is: %s\n", em.find(Option.class, 1L));
        } catch (Exception e) {
            System.out.println("Something wrong!");
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static Client createClient(){
        Client newClient = new Client();
        newClient.setFirstName("Vasya");
        newClient.setLastName("Pupkin");
        newClient.setDateOfBirth(new Date());
        newClient.setAddress("Shotmana");
        newClient.setPassport("RUS");
        newClient.setEmail("vasya@ya.ru");
        newClient.setPassword("admin");
        return newClient;
    }

    private static Option createOption(){
        Option newOption = new Option();
        newOption.setName("Mayachok");
        newOption.setConnectionCost(new BigDecimal("30"));
        newOption.setOptionPrice(new BigDecimal("2"));
        return newOption;
    }

}
