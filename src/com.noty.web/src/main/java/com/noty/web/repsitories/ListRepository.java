package com.noty.web.repsitories;

import com.noty.web.entities.NotyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<NotyList, Long> {
}
