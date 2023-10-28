package com.noty.web.controllers.api.model.response;

import com.noty.web.entities.Entry;
import com.noty.web.entities.NotyList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotyListResponse {

    private long id;

    private String title;

    private Date createdAt;

    private Set<Entry> entries;

    public static NotyListResponse fromList(NotyList list) {
        return NotyListResponse.builder()
                .id(list.getId())
                .title(list.getTitle())
                .createdAt(list.getCreatedAt())
                .build();

    }

}
