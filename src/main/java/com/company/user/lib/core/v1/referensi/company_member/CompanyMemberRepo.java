package com.company.user.lib.core.v1.referensi.company_member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyMemberRepo extends JpaRepository<CompanyMember, Long> {
    List<CompanyMember> findByCompanyId(String companyId);
    List<CompanyMember> findByUserId(String userId);
    boolean existsByUserIdAndCompanyId(String userId, String companyId);
    Optional<CompanyMember> findByUserIdAndCompanyId(String userId, String companyId);
}
