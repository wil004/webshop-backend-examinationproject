package com.novi.webshop.dto;
import java.util.List;

public class AdminDto extends UserDto{
    private Long id;
    private String emailAddress;
    private String username;
    private String password;
    private String bankAccount;


    private List<UserEmployeeDto> employees;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public List<UserEmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<UserEmployeeDto> employees) {
        this.employees = employees;
    }
}
