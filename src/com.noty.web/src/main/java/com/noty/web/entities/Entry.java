package com.noty.web.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    private boolean isDone;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private NotyList list;

    @Column(name = "display_order", columnDefinition = "INT DEFAULT 0")
    private int order;

    public Entry(
            NotyList list,
            String description,
            User owner
    ) {
        this.list = list;
        this.description = description;
        this.createdBy = owner;
    }

}
