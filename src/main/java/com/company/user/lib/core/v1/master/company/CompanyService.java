package com.company.user.lib.core.v1.master.company;

import com.company.user.lib.core.v1.master.company.dto.CompanyCreateRequest;
import com.company.user.lib.core.v1.master.company.dto.CompanyDto;

public interface CompanyService {
    CompanyDto create(CompanyCreateRequest request);
    Company findById(String id);
}
