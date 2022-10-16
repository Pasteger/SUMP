package net.skaia.pasteger.sump.entity;

public class Provider {
    private Long providerRegistrationNumber;
    private String name;
    private String address;
    private String phoneNumber;

    public Long getProviderRegistrationNumber() {
        return providerRegistrationNumber;
    }

    public void setProviderRegistrationNumber(Long providerRegistrationNumber) {
        this.providerRegistrationNumber = providerRegistrationNumber;
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
