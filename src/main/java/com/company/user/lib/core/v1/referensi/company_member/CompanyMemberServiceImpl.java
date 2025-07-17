package com.company.user.lib.core.v1.referensi.company_member;

import com.company.user.lib.core.v1.master.company.Company;
import com.company.user.lib.core.v1.master.company.CompanyService;
import com.company.user.lib.core.v1.master.company.dto.CompanyDto;
import com.company.user.lib.core.v1.master.user.User;
import com.company.user.lib.core.v1.master.user.UserService;
import com.company.user.lib.core.v1.referensi.company_member.dto.CompanyMemberAddRequest;
import com.company.user.lib.core.v1.referensi.company_member.dto.CompanyMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyMemberServiceImpl implements CompanyMemberService {

    private final CompanyMemberRepo companyMemberRepo;
    private final UserService userService;
    private final CompanyService companyService;

    @Transactional
    @Override
    public CompanyMemberDto addMember(CompanyMemberAddRequest request) {
        if (companyMemberRepo.existsByUserIdAndCompanyId(request.getUserId(), request.getCompanyId())) {
            throw new IllegalArgumentException("User sudah menjadi anggota perusahaan ini");
        }

        User user = userService.findById(request.getUserId());

        Company company = companyService.findById(request.getCompanyId());

        CompanyMember member = CompanyMember.builder()
                .user(user)
                .company(company)
                .build();

        return CompanyMemberDto.fromEntity(companyMemberRepo.save(member));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompanyMemberDto> getMembersByCompanyId(String companyId) {
        return companyMemberRepo.findByCompanyId(companyId).stream()
                .map(CompanyMemberDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompanyDto> getCompaniesByUserId(String userId) {
        return companyMemberRepo.findByUserId(userId).stream()
                .map(CompanyMember::getCompany)
                .map(CompanyDto::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public void removeMember(String userId, String companyId) {
        CompanyMember member = companyMemberRepo.findByUserIdAndCompanyId(userId, companyId)
                .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan"));

        companyMemberRepo.delete(member);
    }
}
