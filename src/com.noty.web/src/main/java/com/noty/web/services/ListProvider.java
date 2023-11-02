package com.noty.web.services;

import com.noty.web.NotyException;
import com.noty.web.entities.NotyList;
import com.noty.web.services.security.NotyImpersonation;

public interface ListProvider {

    NotyList createList(NotyImpersonation impersonation, String title) throws NotyException;

    NotyList findById(long id, boolean mandatory) throws NotyException;

}
