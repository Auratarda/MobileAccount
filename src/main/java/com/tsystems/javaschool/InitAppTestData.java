package com.tsystems.javaschool;

import com.tsystems.javaschool.entities.Client;
import com.tsystems.javaschool.entities.Contract;
import com.tsystems.javaschool.entities.Option;
import com.tsystems.javaschool.entities.Tariff;
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
            /** Client dump */
            List<String> firstNames = createFirstNames();
            List<String> lastNames = createLastNames();
            List<String> addresses = createAddresses();
            List<String> passports = createPassports();
            List<String> emails = createEmails();
            List<String> passwords = createPasswords();
            /** Contract dump */
            List<String> contracts = createContracts();
            /** Tariff dump */
            List<String> tariffs = createTariffs();
            /** Option dump */
            List<String> options = createOptions();

            for (int i = 0; i < 5; i++) {
                /** Create client */
                operatorService.createNewClient(firstNames.get(i),
                        lastNames.get(i), new Date(), addresses.get(i),
                        passports.get(i), emails.get(i), passwords.get(i));
                /** Create contract */
                operatorService.createNewContract(contracts.get(i));
                /** Create tariff */
                operatorService.createNewTariff(tariffs.get(i), (long) ((i + 1) * 100));
                /** Create options */
                operatorService.createNewOption(options.get(i * 2),
                        (long) ((i + 1) * 10), (long) ((i + 1) * 20));
                operatorService.createNewOption(options.get(i * 2 + 1),
                        (long) ((i + 1) * 10), (long) ((i + 1) * 20));
            }
            Option option_1 = operatorService.findOptionByID((long) (1));
            Option option_2 = operatorService.findOptionByID((long) (2));
            Option option_3 = operatorService.findOptionByID((long) (3));
            Option option_4 = operatorService.findOptionByID((long) (4));
            Option option_5 = operatorService.findOptionByID((long) (5));
            /** 1 & 2 - set required options */
            option_1.getRequiredOptions().add(option_2);
            /** 1 & 5 - set incompatible options */
            option_1.getIncompatibleOptions().add(option_5);
            for (int i = 0; i < 5; i++) {
                /** Find entities */
                Client client = operatorService.findClientByID((long) (i + 1));
                Contract contract = operatorService.findContractByID((long) (i + 1));
                Tariff tariff = operatorService.findTariffByID((long) (i + 1));

                /** Set Client & Tariff */
                contract.setClient(client);
                contract.setTariff(tariff);
                /** Add 2 options to contract */
                contract.getOptions().add(option_1);
                contract.getOptions().add(option_2);
                /** Add 4 options to tariff */
                tariff.getOptions().add(option_1);
                tariff.getOptions().add(option_2);
                tariff.getOptions().add(option_3);
                tariff.getOptions().add(option_4);
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

    private static List<String> createOptions() {
        List<String> options = new ArrayList<String>();
        options.add("Любимый номер");
        options.add("Скрыть номер");
        options.add("Маячок");
        options.add("Обещанный платеж");
        options.add("Гудок");
        options.add("Антиспам");
        options.add("Детализация");
        options.add("Роуминг");
        options.add("Смс-свобода");
        options.add("Черный список");
        return options;
    }

    private static List<String> createTariffs() {
        List<String> tariffs = new ArrayList<String>();
        tariffs.add("Черный");
        tariffs.add("Очень черный");
        tariffs.add("Оранжевый");
        tariffs.add("Желтый");
        tariffs.add("Зеленый");
        return tariffs;
    }

    private static List<String> createContracts() {
        List<String> contracts = new ArrayList<String>();
        contracts.add("9040000001");
        contracts.add("9040000002");
        contracts.add("9040000003");
        contracts.add("9040000004");
        contracts.add("9040000005");
        return contracts;
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
