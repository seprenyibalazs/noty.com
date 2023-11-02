package com.noty.web.controllers.api.model.response;

import com.noty.web.entities.NotyList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotyListResponse {

    private long id;

    private String title;

    private Date createdAt;

    private List<EntryResponse> entries;

    public static NotyListResponse fromList(NotyList list) {
        List<EntryResponse> entries = list.getEntries().stream().map(EntryResponse::fromEntry).toList();

        return NotyListResponse.builder()
                .id(list.getId())
                .title(list.getTitle())
                .createdAt(list.getCreatedAt())
                .entries(entries)
                .build();

    }

}
