package com.tsystems.javaschool;

import com.tsystems.javaschool.service.services.Impl.OperatorServiceImpl;
import com.tsystems.javaschool.service.services.OperatorService;
import org.apache.log4j.Logger;

/**
* Test.
*/
public class InitAppTestData {

    final static Logger logger = Logger.getLogger(InitAppTestData.class);

    public static void main(String[] args) {
        try {
            OperatorService operatorService = new OperatorServiceImpl();

            /** Create client */
//            operatorService.createNewClient("Иван", "Иванов", "1991-01-01",
//                    "Крыленко, 11", "4004000000", "ivan@ya.ru", "ivan");
//            operatorService.createNewClient("Петр", "Петров", "1991-01-01",
//                    "Крыленко, 12", "4004000001", "petr@ya.ru", "petr");
//            operatorService.createNewClient("Сидор", "Сидоров", "1991-01-01",
//                    "Крыленко, 13", "4004000002", "sidor@ya.ru", "sidor");
//            operatorService.createNewClient("Глеб", "Глебов", "1991-01-01",
//                    "Крыленко, 14", "4004000003", "gleb@ya.ru", "gleb");
//            operatorService.createNewClient("Антон", "Антонов", "1991-01-01",
//                    "Крыленко, 15", "4004000004", "anton@ya.ru", "anton");
            /** Create Admin */
//            operatorService.createNewAdmin("Станислав", "Васильевский", "1988-02-22",
//                    "Шотмана, 5", "4004000010", "admin@ya.ru", "admin");
            /** Create contract */
//            operatorService.createNewContract("9042222222");
//            operatorService.createNewContract("9042222223");
//            operatorService.createNewContract("9042222224");
//            operatorService.createNewContract("9042222225");
//            operatorService.createNewContract("9042222226");

            /** Create more free numbers */
//            operatorService.createNewContract("9040000001");
//            operatorService.createNewContract("9040000002");
//            operatorService.createNewContract("9040000003");
//            operatorService.createNewContract("9040000004");
//            operatorService.createNewContract("9040000005");
//            operatorService.createNewContract("9040000006");
//            operatorService.createNewContract("9040000007");
//            operatorService.createNewContract("9040000008");
//            operatorService.createNewContract("9040000009");
//            operatorService.createNewContract("9040000010");
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
//            List<ClientDTO> clientDTOs = operatorService.findAllClients();
//            logger.info("Client 1: " + clientDTOs.get(0));
//            List<ContractDTO> freeContractDTOs = operatorService.findFreeContracts();
//            logger.info("Contract 1: " + freeContractDTOs.get(0));
            /**Mapping clients to free numbers. */
//            for (int i = 0; i < freeContractDTOs.size(); i++) {
//                operatorService.setNumber(clientDTOs.get(i), freeContractDTOs.get(i).getNumber());
//            }
//            List<ContractDTO> contractsDTOs = operatorService.findAllContracts();
//            logger.info("Contract 1: " + contractsDTOs.get(0));
//            List<TariffDTO> tariffsDTOs = operatorService.findAllTariffs();
//            logger.info("Tariff 1: " + tariffsDTOs.get(0));
//            List<OptionDTO> optionDTOs = operatorService.findAllOptions();
//
//            for (int i = 0; i < contractsDTOs.size(); i++) {
//                operatorService.setTariff(contractsDTOs.get(i), tariffsDTOs.get(i));
//                operatorService.addOptions(contractsDTOs.get(i), optionDTOs.subList(0, i));
//            }

            logger.info("Commit success!");
        } catch (Exception e) {
            logger.error("Sorry, something wrong!", e);
            e.printStackTrace();
        }
    }
}
