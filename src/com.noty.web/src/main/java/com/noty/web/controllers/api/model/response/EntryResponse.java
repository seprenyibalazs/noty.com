package com.noty.web.controllers.api.model.response;

import com.noty.web.entities.Entry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryResponse {

    private long id;
    private String description;
    private boolean isCompleted;
    private int order;

    public static EntryResponse fromEntry(Entry entry) {
        return EntryResponse.builder()
                .id(entry.getId())
                .description(entry.getDescription())
                .isCompleted(entry.isDone())
                .order((entry.getOrder()))
                .build();
    }

}
