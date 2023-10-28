package com.noty.web.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    private boolean isDone;

    @ManyToOne
    private User CreatedBy;

    @ManyToOne
    private NotyList list;

}
