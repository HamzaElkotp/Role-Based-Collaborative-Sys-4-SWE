package com.se2project.piplineservice.Piplinedto;

import com.se2project.piplineservice.entity.Update;
import jakarta.persistence.Id;

import java.util.Date;

public class UpdateDTO  {
    String name;
    String description;
    String creator;
    Date uploadeDateToUpdate;

}
