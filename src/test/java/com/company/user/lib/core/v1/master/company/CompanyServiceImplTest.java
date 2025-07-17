package com.company.user.lib.core.v1.master.company;

import com.company.user.lib.core.v1.master.company.dto.CompanyCreateRequest;
import com.company.user.lib.core.v1.master.company.dto.CompanyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyServiceImplTest {

    private CompanyRepo companyRepo;
    private CompanyServiceImpl companyService;

    @BeforeEach
    void setUp() {
        companyRepo = mock(CompanyRepo.class);
        companyService = new CompanyServiceImpl(companyRepo);
    }

    @Test
    void create_ShouldSaveCompany_WhenNameNotExists() {
        // Arrange
        CompanyCreateRequest request = new CompanyCreateRequest();
        request.setNama("PT. Testing");
        request.setAlamat("Jl. Unit Test No. 1");

        when(companyRepo.existsByNama("PT. Testing")).thenReturn(false);

        Company company = Company.builder()
                .id("uuid-123")
                .nama("PT. Testing")
                .alamat("Jl. Unit Test No. 1")
                .build();

        when(companyRepo.save(any(Company.class))).thenReturn(company);

        // Act
        CompanyDto result = companyService.create(request);

        // Assert
        assertNotNull(result);
        assertEquals("PT. Testing", result.getNama());
        assertEquals("uuid-123", result.getId());
    }

    @Test
    void create_ShouldThrowException_WhenNameExists() {
        // Arrange
        CompanyCreateRequest request = new CompanyCreateRequest();
        request.setNama("PT. SudahAda");

        when(companyRepo.existsByNama("PT. SudahAda")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            companyService.create(request);
        });
        assertEquals("Nama perusahaan sudah digunakan", ex.getMessage());
    }

    @Test
    void findById_ShouldReturnCompany_WhenExists() {
        // Arrange
        Company company = Company.builder()
                .id("uuid-456")
                .nama("PT. Ditemukan")
                .alamat("Jl. Ditemukan No. 2")
                .build();

        when(companyRepo.findById("uuid-456")).thenReturn(Optional.of(company));

        // Act
        Company result = companyService.findById("uuid-456");

        // Assert
        assertNotNull(result);
        assertEquals("PT. Ditemukan", result.getNama());
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(companyRepo.findById("missing-id")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            companyService.findById("missing-id");
        });
        assertEquals("Perusahaan tidak ditemukan dengan ID: missing-id", ex.getMessage());
    }
}