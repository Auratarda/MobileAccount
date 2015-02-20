package com.tsystems.javaschool;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Option;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Test.
 */
public class AppTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MobileAccountPU");
        EntityManager em = emf.createEntityManager();
        try {
//            Option newOption = createOption();
            em.getTransaction().begin();
            Option newOption = em.find(Option.class, 2L);
            Option reqOption = em.find(Option.class, 3L);
            Set<Option> requiredOptions = newOption.getRequiredOptions();
            requiredOptions.add(reqOption);
            newOption.setRequiredOptions(requiredOptions);
            em.persist(newOption);
            em.getTransaction().commit();
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
        newOption.setName("Skryt' nomer");
        newOption.setConnectionCost(new BigDecimal("100"));
        newOption.setOptionPrice(new BigDecimal("10"));
        return newOption;
    }

    private static Option addRequiredOption(Option currentOption, EntityManager em, long id){
        Option requiredOption = em.find(Option.class, id);
        System.out.println("required option is " + requiredOption);
        System.out.println("current option is " + currentOption);
        Set<Option> requiredOptions = currentOption.getRequiredOptions();
        requiredOptions.add(requiredOption);
        currentOption.setRequiredOptions(requiredOptions);
        return currentOption;
    }

}
