package com.noty.web.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "lists")
public class EntryList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    private User CreatedBy;

    @OneToMany(mappedBy = "list")
    private Set<Entry> entries;

}
