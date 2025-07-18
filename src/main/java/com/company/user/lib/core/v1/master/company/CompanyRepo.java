package com.company.user.lib.core.v1.master.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company, String> {
    boolean existsByNama(String nama);
}
