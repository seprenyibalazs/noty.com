package com.noty.web.services.implementation;

import com.noty.web.NotyAuthorizationException;
import com.noty.web.NotyEntityNotFoundException;
import com.noty.web.NotyException;
import com.noty.web.components.DateTime;
import com.noty.web.entities.Entry;
import com.noty.web.entities.ListAccess;
import com.noty.web.entities.NotyList;
import com.noty.web.entities.User;
import com.noty.web.repsitories.EntryRepository;
import com.noty.web.repsitories.ListAccessRepository;
import com.noty.web.repsitories.ListRepository;
import com.noty.web.services.ListProvider;
import com.noty.web.services.UserProvider;
import com.noty.web.services.security.NotyImpersonation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ListProviderImpl implements ListProvider {

    private final DateTime dateTime;

    private final ListRepository listRepository;

    private final ListAccessRepository listAccessRepository;

    private final UserProvider userProvider;

    private final EntryRepository entryRepository;

    @Transactional
    public NotyList createList(NotyImpersonation impersonation, String title) throws NotyException {
        User user = userProvider.fromDetails(impersonation);
        if (user == null)
            throw new NotyAuthorizationException("You are not permitted to create a list.");

        Date now = dateTime.now();

        NotyList list = NotyList.builder()
                .title(title)
                .createdBy(user)
                .createdAt(now)
                .build();

        listRepository.save(list);

        ListAccess access = new ListAccess(list, user, now);
        listAccessRepository.save(access);

        return list;
    }

    @Override
    public NotyList[] findAccessibleLists(long userId) {
        return listRepository.findAccessibleLists(userId)
                .toArray(NotyList[]::new);
    }

    @Override
    public void deleteList(NotyList list) {
        listRepository.delete(list);
    }

    @Override
    public Entry createEntry(long ownerId, NotyList list, String description) {
        User owner = userProvider.findById(ownerId);

        Entry newEntry = new Entry(
                list,
                description,
                owner
        );
        entryRepository.save(newEntry);

        return newEntry;
    }

    @Override
    public Entry findEntryById(long id, boolean mandatory) throws NotyException {
        Entry entry = entryRepository.findById(id).orElse(null);
        if (entry == null && mandatory)
            throw new NotyEntityNotFoundException("The entry was not found.");

        return entry;
    }

    @Override
    public void deleteEntry(Entry entry) {
        entryRepository.delete(entry);
    }

    @Override
    public NotyList findById(long id, boolean mandatory) throws NotyException {
        NotyList list = listRepository.findById(id).orElse(null);
        if (list == null && mandatory)
            throw new NotyEntityNotFoundException("The list was not found.");

        return list;

    }
}
