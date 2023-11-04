package com.noty.web.services;

import com.noty.web.NotyException;
import com.noty.web.entities.Entry;
import com.noty.web.entities.NotyList;
import com.noty.web.entities.User;
import com.noty.web.services.security.NotyImpersonation;

public interface ListProvider {

    NotyList createList(NotyImpersonation impersonation, String title) throws NotyException;

    NotyList findById(long id, boolean mandatory) throws NotyException;

    NotyList[] findAccessibleLists(long userId);

    void deleteList(NotyList list);

    Entry createEntry(long userId, NotyList list, String description);

    Entry findEntryById(long id, boolean mandatory) throws NotyException;

    void deleteEntry(Entry entry);

    void updateEntry(Entry entry);
}
