package com.company.user.lib.core.v1.master.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyCreateRequest {

    @NotBlank(message = "Nama perusahaan wajib diisi")
    private String nama;

    @NotBlank(message = "Alamat perusahaan wajib diisi")
    private String alamat;
}