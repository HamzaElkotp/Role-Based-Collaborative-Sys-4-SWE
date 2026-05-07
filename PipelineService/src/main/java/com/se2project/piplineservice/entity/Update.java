package com.se2project.piplineservice.entity;

import com.se2project.piplineservice.Piplinedto.UpdateDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Update extends UpdateDTO {
    @Id
    int updateId;
    String name;
    String description;
    String creator;
    Date uploadeDateToUpdate;


    public Update(String hh, String hhhhh, String heidi, LocalDate localDate) {
    }

    public Update( ) {
    }

    public Update(int updateId, String name, String description, String creator, Date uploadeDateToUpdate) {
        this.updateId = updateId;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.uploadeDateToUpdate = uploadeDateToUpdate;
    }


    public void getName() {

    }
}
