package com.noty.web.repsitories;

import com.noty.web.entities.EntryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<EntryList, Long> {
}
