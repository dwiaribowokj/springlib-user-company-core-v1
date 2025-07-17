package com.company.user.lib.core.v1.referensi.company_member;

import com.company.user.lib.core.v1.master.company.Company;
import com.company.user.lib.core.v1.master.company.CompanyService;
import com.company.user.lib.core.v1.master.company.dto.CompanyDto;
import com.company.user.lib.core.v1.master.user.User;
import com.company.user.lib.core.v1.master.user.UserService;
import com.company.user.lib.core.v1.referensi.company_member.dto.CompanyMemberAddRequest;
import com.company.user.lib.core.v1.referensi.company_member.dto.CompanyMemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompanyMemberServiceImplTest {

    private CompanyMemberRepo companyMemberRepo;
    private UserService userService;
    private CompanyService companyService;
    private CompanyMemberServiceImpl companyMemberService;

    @BeforeEach
    void setUp() {
        companyMemberRepo = mock(CompanyMemberRepo.class);
        userService = mock(UserService.class);
        companyService = mock(CompanyService.class);
        companyMemberService = new CompanyMemberServiceImpl(companyMemberRepo, userService, companyService);
    }

    @Test
    void addMember_ShouldSave_WhenNotAlreadyMember() {
        // Arrange
        CompanyMemberAddRequest request = new CompanyMemberAddRequest();
        request.setUserId("user-1");
        request.setCompanyId("company-1");

        User user = User.builder().id("user-1").email("u@test.com").build();
        Company company = Company.builder().id("company-1").nama("Test Company").build();

        when(companyMemberRepo.existsByUserIdAndCompanyId("user-1", "company-1")).thenReturn(false);
        when(userService.findById("user-1")).thenReturn(user);
        when(companyService.findById("company-1")).thenReturn(company);

        CompanyMember saved = CompanyMember.builder()
                .id("member-1")
                .user(user)
                .company(company)
                .build();

        when(companyMemberRepo.save(any())).thenReturn(saved);

        // Act
        CompanyMemberDto result = companyMemberService.addMember(request);

        // Assert
        assertNotNull(result);
        assertEquals("company-1", result.getCompanyId());
        assertEquals("user-1", result.getUserId());
    }

    @Test
    void addMember_ShouldThrow_WhenAlreadyMember() {
        when(companyMemberRepo.existsByUserIdAndCompanyId("u", "c")).thenReturn(true);

        CompanyMemberAddRequest req = new CompanyMemberAddRequest();
        req.setUserId("u");
        req.setCompanyId("c");

        assertThrows(IllegalArgumentException.class, () -> {
            companyMemberService.addMember(req);
        });
    }

    @Test
    void getMembersByCompanyId_ShouldReturnDtos() {
        Company company = Company.builder().id("c1").nama("X").build();
        User user = User.builder().id("u1").email("e").build();

        CompanyMember member = CompanyMember.builder()
                .id("m1").user(user).company(company).build();

        when(companyMemberRepo.findByCompanyId("c1")).thenReturn(List.of(member));

        List<CompanyMemberDto> result = companyMemberService.getMembersByCompanyId("c1");

        assertEquals(1, result.size());
        assertEquals("u1", result.get(0).getUserId());
    }

    @Test
    void getCompaniesByUserId_ShouldReturnCompanyDtos() {
        Company company = Company.builder().id("c99").nama("TestCorp").alamat("Jl. X").build();
        CompanyMember member = CompanyMember.builder().id("m99").company(company).build();

        when(companyMemberRepo.findByUserId("u99")).thenReturn(List.of(member));

        List<CompanyDto> result = companyMemberService.getCompaniesByUserId("u99");

        assertEquals(1, result.size());
        assertEquals("c99", result.get(0).getId());
    }

    @Test
    void removeMember_ShouldDelete_WhenFound() {
        Company company = Company.builder().id("c").build();
        User user = User.builder().id("u").build();
        CompanyMember member = CompanyMember.builder().id("m").user(user).company(company).build();

        when(companyMemberRepo.findByUserIdAndCompanyId("u", "c")).thenReturn(Optional.of(member));

        companyMemberService.removeMember("u", "c");

        verify(companyMemberRepo).delete(member);
    }

    @Test
    void removeMember_ShouldThrow_WhenNotFound() {
        when(companyMemberRepo.findByUserIdAndCompanyId("u", "x")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            companyMemberService.removeMember("u", "x");
        });
    }
}