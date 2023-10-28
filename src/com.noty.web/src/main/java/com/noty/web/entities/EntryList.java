package com.noty.web.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lists")
public class EntryList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    private User createdBy;

    private Date createdAt;

    @OneToMany(mappedBy = "list")
    private Set<Entry> entries;

}
