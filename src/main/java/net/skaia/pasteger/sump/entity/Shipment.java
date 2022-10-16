package net.skaia.pasteger.sump.entity;

public class Shipment {
    private Long number;
    private String status;
    private Long productKey;
    private Integer productQuantity;
    private Long providerRegistrationNumber;
    private Long clientRegistrationNumber;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProductKey() {
        return productKey;
    }

    public void setProductKey(Long productKey) {
        this.productKey = productKey;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Long getProviderRegistrationNumber() {
        return providerRegistrationNumber;
    }

    public void setProviderRegistrationNumber(Long providerRegistrationNumber) {
        this.providerRegistrationNumber = providerRegistrationNumber;
    }

    public Long getClientRegistrationNumber() {
        return clientRegistrationNumber;
    }

    public void setClientRegistrationNumber(Long clientRegistrationNumber) {
        this.clientRegistrationNumber = clientRegistrationNumber;
    }
}
