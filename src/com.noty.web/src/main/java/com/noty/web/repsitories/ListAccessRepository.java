package com.noty.web.repsitories;

import com.noty.web.entities.ListAccess;
import com.noty.web.entities.ListAccessId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListAccessRepository extends JpaRepository<ListAccess, ListAccessId> {
}
