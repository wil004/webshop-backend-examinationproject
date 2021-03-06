package com.novi.webshop.dto;
import java.util.List;

public class CustomerDto extends UserDto {
    private Long id;


    private String firstName;

    private String lastName;

    private String streetName;

    private int houseNumber;
    private String additionalToHouseNumber;

    private String city;

    private String zipcode;

    private ShoppingCartDto shoppingCartDto;

    private List<OrderDto> orderHistoryDto;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAdditionalToHouseNumber() {
        return additionalToHouseNumber;
    }
    public void setAdditionalToHouseNumber(String additionalToHouseNumber) {
        this.additionalToHouseNumber = additionalToHouseNumber;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public ShoppingCartDto getShoppingCartDto() {
        return shoppingCartDto;
    }

    public void setShoppingCartDto(ShoppingCartDto shoppingCartDto) {
        this.shoppingCartDto = shoppingCartDto;
    }

    public List<OrderDto> getOrderHistoryDto() {
        return orderHistoryDto;
    }

    public void setOrderHistoryDto(List<OrderDto> orderHistoryDto) {
        this.orderHistoryDto = orderHistoryDto;
    }
}
