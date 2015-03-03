package com.tsystems.javaschool;

import com.tsystems.javaschool.persistence.PersistenceUtil;
import com.tsystems.javaschool.services.OperatorService;
import com.tsystems.javaschool.services.OperatorServiceImpl;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Test.
 */
public class InitAppTestData {

    final static Logger logger = Logger.getLogger(InitAppTestData.class);
    public static void main(String[] args) {
        EntityManagerFactory emf = PersistenceUtil.getEntityManagerFactory();
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            OperatorService operatorService = new OperatorServiceImpl(em);
            List<String> firstNames = createFirstNames();
            List<String> lastNames = createLastNames();
            List<String> addresses = createAddresses();
            List<String> emails = createEmails();
            List<String> passports = createPassports();
            List<String> passwords = createPasswords();

            for (int i = 0; i < 5; i++) {
                operatorService.createNewClient(firstNames.get(i),
                        lastNames.get(i), new Date(), addresses.get(i), emails.get(i),
                        passports.get(i), passwords.get(i));
            }

            em.getTransaction().commit();
            logger.info("Commit success!");
        } catch (Exception e) {
            logger.error("Sorry, something wrong!", e);
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static List<String> createFirstNames() {
        List<String> firstNames = new ArrayList<String>();
        firstNames.add("Иван");
        firstNames.add("Петр");
        firstNames.add("Сидор");
        firstNames.add("Николай");
        firstNames.add("Станислав");
        return firstNames;
    }

    private static List<String> createLastNames() {
        List<String> lastNames = new ArrayList<String>();
        lastNames.add("Иванов");
        lastNames.add("Петров");
        lastNames.add("Сидоров");
        lastNames.add("Николаев");
        lastNames.add("Васильевский");
        return lastNames;
    }

    private static List<String> createAddresses() {
        List<String> addresses = new ArrayList<String>();
        addresses.add("пр. Ленина, 1");
        addresses.add("ул. Правды, 2");
        addresses.add("ул. Шотмана, 5");
        addresses.add("бул. Трудящихся, 11");
        addresses.add("2-я линия ВО, 9");
        return addresses;
    }

    private static List<String> createEmails() {
        List<String> emails = new ArrayList<String>();
        emails.add("ivan@ya.ru");
        emails.add("petr@ya.ru");
        emails.add("sidor@ya.ru");
        emails.add("nikolai@ya.ru");
        emails.add("auaraacra@yandex.ru");
        return emails;
    }

    private static List<String> createPassports() {
        List<String> passports = new ArrayList<String>();
        passports.add("4004 000001");
        passports.add("4004 000002");
        passports.add("4004 000003");
        passports.add("4004 000004");
        passports.add("4004 000005");
        return passports;
    }

    private static List<String> createPasswords() {
        List<String> passwords = new ArrayList<String>();
        passwords.add("123");
        passwords.add("password");
        passwords.add("admin");
        passwords.add("love");
        passwords.add("lJ5dU56Yr2");
        return passwords;
    }
}
