package com.noty.web.entities;

import com.noty.web.services.security.EntityPermission;
import com.noty.web.services.security.SecuredEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lists")
public class NotyList implements SecuredEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    private User createdBy;

    private Date createdAt;

    @OneToMany(mappedBy = "list", fetch = FetchType.EAGER)
    private List<Entry> entries;

    @OneToMany(mappedBy = "list", fetch = FetchType.EAGER)
    private List<ListAccess> accessControlList;

    @Override
    public EntityPermission[] getAcl() {
        if (accessControlList == null)
            return new EntityPermission[0];

        return accessControlList.stream()
                .map(ListAccess::toPermission)
                .toArray(EntityPermission[]::new);
    }
}
