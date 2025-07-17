package com.company.user.lib.core.v1.master.company.dto;

import com.company.user.lib.core.v1.master.company.Company;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDto {

    private String id;
    private String nama;
    private String alamat;

    public static CompanyDto fromEntity(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .nama(company.getNama())
                .alamat(company.getAlamat())
                .build();
    }
}