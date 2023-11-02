package com.noty.web.repsitories;

import com.noty.web.entities.NotyList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListRepository extends JpaRepository<NotyList, Long> {

    @Query("SELECT l FROM NotyList l INNER JOIN ListAccess la ON l.id = la.list.id WHERE la.user.id = ?1")
    List<NotyList> findAccessibleLists(long userId);

}
