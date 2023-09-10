package org.calisto.mvc.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
