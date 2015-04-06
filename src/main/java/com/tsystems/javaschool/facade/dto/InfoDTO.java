package com.tsystems.javaschool.facade.dto;

/**
 * InfoDTO.
 */
public class InfoDTO {
    private String clientFirstName;
    private String clientLastName;
    private String clientEmail;
    private String clientNumber;

    public InfoDTO(String clientFirstName, String clientLastName, String clientEmail, String clientNumber) {
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.clientEmail = clientEmail;
        this.clientNumber = clientNumber;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    @Override
    public String toString() {
        return "InfoDTO{" +
                "clientFirstName='" + clientFirstName + '\'' +
                ", clientLastName='" + clientLastName + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", clientNumber='" + clientNumber + '\'' +
                '}';
    }
}
