package com.company.user.lib.core.v1.master.company;

import com.company.user.lib.core.v1.master.company.dto.CompanyCreateRequest;
import com.company.user.lib.core.v1.master.company.dto.CompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo companyRepo;

    @Transactional
    @Override
    public CompanyDto create(CompanyCreateRequest request) {
        if (companyRepo.existsByNama(request.getNama())) {
            throw new IllegalArgumentException("Nama perusahaan sudah digunakan");
        }

        Company company = Company.builder()
                .nama(request.getNama())
                .alamat(request.getAlamat())
                .build();

        Company saved = companyRepo.save(company);
        return CompanyDto.fromEntity(saved);
    }

    @Override
    public Company findById(String id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Perusahaan tidak ditemukan dengan ID: " + id));
    }
}