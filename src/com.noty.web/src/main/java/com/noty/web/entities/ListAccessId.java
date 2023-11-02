package com.noty.web.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListAccessId implements Serializable {

    @Column(name = "list_id")
    private long listId;

    @Column(name = "user_id")
    private long userId;

}
