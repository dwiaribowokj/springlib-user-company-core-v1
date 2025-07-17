package com.company.user.lib.core.v1.referensi.company_member;

import com.company.user.lib.core.v1.master.company.dto.CompanyDto;
import com.company.user.lib.core.v1.referensi.company_member.dto.CompanyMemberAddRequest;
import com.company.user.lib.core.v1.referensi.company_member.dto.CompanyMemberDto;

import java.util.List;

public interface CompanyMemberService {
    CompanyMemberDto addMember(CompanyMemberAddRequest request);
    List<CompanyMemberDto> getMembersByCompanyId(String companyId);
    void removeMember(String userId, String companyId);
    List<CompanyDto> getCompaniesByUserId(String userId);

}