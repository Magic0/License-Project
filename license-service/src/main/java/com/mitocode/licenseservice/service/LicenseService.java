package com.mitocode.licenseservice.service;

import com.mitocode.licenseservice.model.dto.ClientDTO;
import com.mitocode.licenseservice.model.dto.LicenseDTO;
import com.mitocode.licenseservice.model.entity.LicenseEntity;
import com.mitocode.licenseservice.proxy.ClientFeign;
import com.mitocode.licenseservice.service.repository.LicenseRepository;
import com.mitocode.licenseservice.util.UtilMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LicenseService {

    private final ClientFeign clientFeign;
    private final LicenseRepository licenseRepository;
    private final UtilMapper utilMapper;

    @Value("${server.port}")
    private Integer port;

    public LicenseDTO saveLicense(LicenseDTO licenseDTO) {
        licenseDTO.setPort(port);
        licenseDTO.setIssueDate(LocalDate.now());
        licenseDTO.setExpirationDate(LocalDate.now().plusMonths(1));
        licenseDTO.setIsActive(true);
        ClientDTO clientDTO = clientFeign.getClientById(licenseDTO.getClient().getDNI());
        log.info("Client: {}", clientDTO);
        licenseDTO.setClient(clientDTO);
        LicenseEntity licenseEntity = licenseRepository.save(utilMapper.convertDTOtoEntity(licenseDTO));
        LicenseDTO response = utilMapper.convertEntityToDTO(licenseEntity);
        response.setPort(port);
        return response;
    }

    public List<LicenseDTO> getAllLicense() {
        List<LicenseEntity> itLicense = licenseRepository.findAll();
        return itLicense.stream().map(licenseEntities -> {
            LicenseDTO licenseDTO = utilMapper.convertEntityToDTO(licenseEntities);
            licenseDTO.setPort(port);
            return licenseDTO;
        }).collect(Collectors.toList());

    }

    public LicenseDTO getLicenseById(String id) {
        LicenseEntity licenseEntity = licenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Licencia no encontrado"));
        return utilMapper.convertEntityToDTO(licenseEntity);
    }

    public void deleteLicense(String id) {
        licenseRepository.deleteById(id);
    }

    public LicenseDTO updateLicense(String licenseId, LicenseDTO licenseDTO) {
        if (licenseDTO.getClient() != null) {
            throw new IllegalArgumentException("Client data cannot be modified here. Use client-service instead.");
        }
        LicenseEntity licenseVerify = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Licencia no encontrado"));
        utilMapper.copyNonNullProperties(licenseDTO, licenseVerify);
        LicenseEntity licenseVerify2 = licenseRepository.save(licenseVerify);
        LicenseDTO licenseResponse = utilMapper.convertEntityToDTO(licenseVerify2);
        licenseResponse.setPort(port);
        return licenseResponse;
    }

    public LicenseDTO updateLicense(String licenseId) {
        LicenseEntity licenseVerify = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Licencia no encontrado"));
        licenseVerify.setExpirationDate(LocalDate.now().plusMonths(1));
        LicenseEntity licenseVerify2 = licenseRepository.save(licenseVerify);
        LicenseDTO licenseResponse = utilMapper.convertEntityToDTO(licenseVerify2);
        licenseResponse.setPort(port);
        return licenseResponse;
    }

    public List<LicenseDTO> getAllByLicenseCategory(String licenseCategory) {
        List<LicenseEntity> itLicense = licenseRepository.getAllByLicenseCategory(licenseCategory);
        return itLicense.stream().map(licenseEntities -> {
            LicenseDTO licenseDTO = utilMapper.convertEntityToDTO(licenseEntities);
            licenseDTO.setPort(port);
            return licenseDTO;
        }).collect(Collectors.toList());

    }

    public List<LicenseDTO> getAllByLicenseType(String licenseType) {
        List<LicenseEntity> itLicense = licenseRepository.getAllByLicenseType(licenseType);
        return itLicense.stream().map(licenseEntities -> {
            LicenseDTO licenseDTO = utilMapper.convertEntityToDTO(licenseEntities);
            licenseDTO.setPort(port);
            return licenseDTO;
        }).collect(Collectors.toList());

    }

    public List<LicenseDTO> getAllByActive(boolean active) {
        List<LicenseEntity> itLicense = licenseRepository.getAllByIsActive(active);
        return itLicense.stream().map(licenseEntities -> {
            LicenseDTO licenseDTO = utilMapper.convertEntityToDTO(licenseEntities);
            licenseDTO.setPort(port);
            return licenseDTO;
        }).collect(Collectors.toList());

    }
}
