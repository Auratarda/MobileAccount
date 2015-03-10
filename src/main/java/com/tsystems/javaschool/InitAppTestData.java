package com.tsystems.javaschool;

import com.tsystems.javaschool.persistence.PersistenceUtil;
import com.tsystems.javaschool.services.Impl.OperatorServiceImpl;
import com.tsystems.javaschool.services.OperatorService;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Test.
 */
public class InitAppTestData {

    final static Logger logger = Logger.getLogger(InitAppTestData.class);

    public static void main(String[] args) {
        EntityManagerFactory emf = PersistenceUtil.getEntityManagerFactory();
        EntityManager em = PersistenceUtil.getEntityManager();
        try {
//            em.getTransaction().begin();
//
            OperatorService operatorService = new OperatorServiceImpl(em);

            /** Create client */
//            operatorService.createNewClient("Иван", "Иванов", new Date(),
//                    "Крыленко, 11", "4004000000", "ivan@ya.ru", "ivan");
//            operatorService.createNewClient("Петр", "Петров", new Date(),
//                    "Крыленко, 12", "4004000001", "petr@ya.ru", "petr");
//            operatorService.createNewClient("Сидор", "Сидоров", new Date(),
//                    "Крыленко, 13", "4004000002", "sidor@ya.ru", "sidor");
//            operatorService.createNewClient("Глеб", "Глебов", new Date(),
//                    "Крыленко, 14", "4004000003", "gleb@ya.ru", "gleb");
//            operatorService.createNewClient("Антон", "Антонов", new Date(),
//                    "Крыленко, 15", "4004000004", "anton@ya.ru", "anton");
            /** Create Admin */
//            operatorService.createNewAdmin("Станислав", "Васильевский", new Date(),
//                    "Шотмана, 5", "4004000010", "admin@ya.ru", "admin");
            /** Create contract */
            operatorService.createNewContract("9042222222");
            operatorService.createNewContract("9042222223");
            operatorService.createNewContract("9042222224");
            operatorService.createNewContract("9042222225");
            operatorService.createNewContract("9042222226");
            /** Create tariff */
//            operatorService.createNewTariff("Черный", 300L);
//            operatorService.createNewTariff("Очень Черный", 400L);
//            operatorService.createNewTariff("Оранжевый", 500L);
//            operatorService.createNewTariff("Желтый", 600L);
//            operatorService.createNewTariff("Зеленый", 700L);

//            /** Create options */
//            operatorService.createNewOption("Любимый номер", 10L, 30L);
//            operatorService.createNewOption("Маячок", 15L, 35L);
//            operatorService.createNewOption("Обещанный платеж", 20L, 40L);
//            operatorService.createNewOption("Гудок", 25L, 45L);
//            operatorService.createNewOption("Антиспам", 30L, 50L);
//            operatorService.createNewOption("Детализация", 35L, 55L);
//            operatorService.createNewOption("Роуминг", 40L, 60L);
//            operatorService.createNewOption("Смс-свобода", 45L, 65L);
//            operatorService.createNewOption("Погода", 50L, 70L);
//            operatorService.createNewOption("Черный список", 55L, 75L);
//
//            List<Client> clients = operatorService.findAllClients();
//            List<Contract> contracts = operatorService.findAllContracts();
//            List<Tariff> tariffs = operatorService.findAllTariffs();
//            List<Option> options = operatorService.findAllOptions();
            /** 1 & 2 - set required options */
//            options.get(0).getRequiredOptions().add(options.get(1));
            /** 3 & 5 - set incompatible options */
//            options.get(2).getIncompatibleOptions().add(options.get(4));

//            List<Tariff> allTariffs = operatorService.findAllTariffs();
//            List<Option> allOptions = operatorService.findAllOptions();
//            for (Tariff tariff : allTariffs) {
//                tariff.setOptions(allOptions);
//                operatorService.updateTariff(tariff);
//            }

//            for (int i = 0; i < 5; i++) {
//                /** Set Contract & Tariff */
//                contracts.get(i).setClient(clients.get(i));
//                contracts.get(i).setTariff(tariffs.get(i));
//                /** Add 1-4 options to tariffs */
//                tariffs.get(i).getOptions().add(options.get(i));
//            }

//            em.getTransaction().commit();
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
}
