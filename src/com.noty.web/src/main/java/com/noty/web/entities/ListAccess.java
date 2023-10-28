package com.noty.web.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListAccess {

    @EmbeddedId
    private ListAccessId id;

    @ManyToOne
    @MapsId("listId")
    @JoinColumn(name = "list_id")
    private NotyList list;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    private Date createdAt;

    public ListAccess(NotyList list, User user, Date createdAt) {
        this.id = ListAccessId.builder()
                .listId(list.getId())
                .userId(user.getId())
                .build();

        this.user = user;
        this.list = list;
        this.createdAt = createdAt;
    }

}
