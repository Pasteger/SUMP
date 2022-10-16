package net.skaia.pasteger.sump.entity;

public class Client {
    private Long clientRegistrationNumber;
    private String name;
    private String address;
    private String phoneNumber;

    public Long getClientRegistrationNumber() {
        return clientRegistrationNumber;
    }

    public void setClientRegistrationNumber(Long clientRegistrationNumber) {
        this.clientRegistrationNumber = clientRegistrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
