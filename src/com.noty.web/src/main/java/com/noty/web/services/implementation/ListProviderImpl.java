package com.noty.web.services.implementation;

import com.noty.web.components.DateTime;
import com.noty.web.entities.EntryList;
import com.noty.web.entities.ListAccess;
import com.noty.web.entities.User;
import com.noty.web.model.NotyUser;
import com.noty.web.repsitories.ListAccessRepository;
import com.noty.web.repsitories.ListRepository;
import com.noty.web.services.ListProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class ListProviderImpl implements ListProvider {

    private final DateTime dateTime;

    private final ListRepository listRepository;

    private final ListAccessRepository listAccessRepository;

    @Override
    @Transactional
    public EntryList createList(User owner, String title) {
        Date now = dateTime.now();

        EntryList list = EntryList.builder()
                .title(title)
                .createdBy(owner)
                .createdAt(now)
                .build();

        listRepository.save(list);

        ListAccess access = new ListAccess(list, owner, now);
        listAccessRepository.save(access);

        return list;
    }
}
