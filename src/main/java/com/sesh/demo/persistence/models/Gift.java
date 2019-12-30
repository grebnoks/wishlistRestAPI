package com.sesh.demo.persistence.models;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String location;

}
