package com.gs_data_collector.rest.webservices.restfulwebservices.dao;

import com.gs_data_collector.rest.webservices.restfulwebservices.entities.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RegisterDao extends JpaRepository<Register, Integer> {
    Register save(Register register);
    boolean existsByEmail(String email);

    List<Register> findAllByAcceptedIsFalse();

    Register findById(Long id);

    @Modifying
    @Transactional
    @Query( "update Register r set r.accepted = true where r.id = :id")
    void accept(@Param("id") Long id);

    @Modifying
    @Transactional
    void removeRegisterById(Long id);
}
