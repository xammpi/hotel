package org.calisto.mvc.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable {

    private Integer id;
    private Integer roomNumber;
    private int capacity;
}
