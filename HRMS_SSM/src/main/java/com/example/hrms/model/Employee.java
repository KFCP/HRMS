package com.example.hrms.model;

import java.io.Serializable;
import java.math.BigDecimal; // Import BigDecimal

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String phone;
    private String email;
    private BigDecimal salary; // New field
    
    private Long positionId; // Foreign key
    private Position position; // For holding the joined Position object

    // Constructors
    public Employee() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public BigDecimal getSalary() { // New getter
        return salary;
    }

    public void setSalary(BigDecimal salary) { // New setter
        this.salary = salary;
    }

    // toString (optional)
    @Override
    public String toString() {
        return "Employee{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", gender='" + gender + '\'' +
               ", age=" + age +
               ", phone='" + phone + '\'' +
               ", email='" + email + '\'' +
               ", salary=" + salary + // Add salary to toString
               ", positionId=" + positionId +
               ", position=" + (position != null ? position.getPositionName() : "null") +
               '}';
    }
}
