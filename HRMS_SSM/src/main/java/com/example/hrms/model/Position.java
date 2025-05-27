package com.example.hrms.model;

import java.io.Serializable;

public class Position implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String positionName;
    private Integer level;

    // Constructors
    public Position() {
    }

    public Position(String positionName, Integer level) {
        this.positionName = positionName;
        this.level = level;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    // toString (optional)
    @Override
    public String toString() {
        return "Position{" +
               "id=" + id +
               ", positionName='" + positionName + '\'' +
               ", level=" + level +
               '}';
    }
}
