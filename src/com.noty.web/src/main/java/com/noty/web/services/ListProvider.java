package com.noty.web.services;

import com.noty.web.entities.EntryList;
import com.noty.web.entities.User;
import com.noty.web.model.NotyUser;

public interface ListProvider {

    EntryList createList(User owner, String title);

}
