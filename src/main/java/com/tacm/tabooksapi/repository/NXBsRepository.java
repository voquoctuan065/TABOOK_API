package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.NXBs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NXBsRepository extends JpaRepository<NXBs, Long> {
    @Query("SELECT nxb FROM NXBs nxb WHERE LOWER(nxb.nxbName) LIKE LOWER(CONCAT(:keyword, '%')) OR " +
            "LOWER(nxb.nxbName) LIKE LOWER(:keyword) OR " +
            "LOWER(nxb.nxbName) LIKE LOWER(CONCAT('%', :keyword)) OR " +
            "LOWER(nxb.nxbName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<NXBs> findByNxbName(@Param("keyword") String keyword);
}
