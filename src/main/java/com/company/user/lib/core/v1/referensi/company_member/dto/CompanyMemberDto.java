package com.company.user.lib.core.v1.referensi.company_member.dto;

import com.company.user.lib.core.v1.referensi.company_member.CompanyMember;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyMemberDto {
    private String id;
    private String userId;
    private String companyId;

    public static CompanyMemberDto fromEntity(CompanyMember member) {
        return CompanyMemberDto.builder()
                .id(member.getId())
                .userId(member.getUser().getId())
                .companyId(member.getCompany().getId())
                .build();
    }
}
