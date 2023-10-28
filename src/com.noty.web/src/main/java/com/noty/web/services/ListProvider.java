package com.noty.web.services;

import com.noty.web.NotyException;
import com.noty.web.entities.NotyList;
import com.noty.web.services.security.NotyUserDetails;

public interface ListProvider {

    NotyList createList(NotyUserDetails owner, String title) throws NotyException;

    NotyList findById(long id);

}
