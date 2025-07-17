package com.company.user.lib.core.v1.referensi.company_member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyMemberAddRequest {

    @NotNull(message = "ID user wajib diisi")
    private String userId;

    @NotNull(message = "ID perusahaan wajib diisi")
    private String companyId;
}