package com.mitocode.licenseservice.service;

import com.mitocode.common_models.model.dto.ClientDTO;
import com.mitocode.common_models.model.dto.GenericModel;
import com.mitocode.licenseservice.model.dto.CategoryReportDTO;
import com.mitocode.licenseservice.model.dto.LicenseDTO;
import com.mitocode.licenseservice.model.entity.LicenseEntity;
import com.mitocode.licenseservice.proxy.ClientFeign;
import com.mitocode.licenseservice.service.repository.LicenseRepository;
import com.mitocode.licenseservice.util.KafkaUtil;
import com.mitocode.licenseservice.util.UtilMapper;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LicenseService {

    private final ClientFeign clientFeign;
    private final LicenseRepository licenseRepository;
    private final UtilMapper utilMapper;
    private final KafkaUtil  kafkaUtil;

    @Value("${server.port}")
    private Integer port;

    public LicenseDTO saveLicense(LicenseDTO licenseDTO) {
        if(licenseDTO.getIssueDate() != null && licenseDTO.getExpirationDate() != null
                && licenseDTO.getIssueDate().isAfter(licenseDTO.getExpirationDate())){
            throw new IllegalArgumentException("La fecha de expiración no puede ser menor a la fecha de emisión");
        }
        if(licenseDTO.getClient()==null || licenseDTO.getClient().getDNI() == null){
            throw new IllegalArgumentException("El DNI del cliente es obligatorio.");
        }
        licenseDTO.setIssueDate(Optional.ofNullable(licenseDTO.getIssueDate()).orElse(LocalDate.now()));
        licenseDTO.setExpirationDate(Optional.ofNullable(licenseDTO.getExpirationDate()).orElse(LocalDate.now().plusMonths(1)));
        licenseDTO.setIsActive(validateDate(licenseDTO.getExpirationDate()));
        ClientDTO clientDTO = clientFeign.getClientById(licenseDTO.getClient().getDNI());
        log.info("Client: {}", clientDTO);
        licenseDTO.setClient(clientDTO);
        LicenseEntity licenseEntity = licenseRepository.save(utilMapper.convertDTOtoEntity(licenseDTO));
        sendKafkaMessage(licenseEntity);
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
            throw new IllegalArgumentException("La data del cliente no puede ser modificada en este servicio. Utilizar client-service para ello.");
        }
        if(licenseDTO.getIssueDate() != null && licenseDTO.getExpirationDate() != null
                && licenseDTO.getIssueDate().isAfter(licenseDTO.getExpirationDate())){
            throw new IllegalArgumentException("La fecha de expiración no puede ser menor a la fecha de emisión");
        }
        if (licenseDTO.getExpirationDate() != null) {
            licenseDTO.setIsActive(validateDate(licenseDTO.getExpirationDate()));
        }
        LicenseEntity licenseVerify = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Licencia no encontrado"));
        utilMapper.copyNonNullProperties(licenseDTO, licenseVerify);
        LicenseEntity licenseVerify2 = licenseRepository.save(licenseVerify);
        sendKafkaMessage(licenseVerify2);
        LicenseDTO licenseResponse = utilMapper.convertEntityToDTO(licenseVerify2);
        licenseResponse.setPort(port);
        return licenseResponse;
    }

    public LicenseDTO updateLicense(String licenseId) {
        LicenseEntity licenseVerify = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Licencia no encontrado"));
        licenseVerify.setExpirationDate(LocalDate.now().plusMonths(1));
        LicenseEntity licenseVerify2 = licenseRepository.save(licenseVerify);
        sendKafkaMessage(licenseVerify2);
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

    private void sendKafkaMessage(LicenseEntity licenseEntity) {
        GenericModel<LicenseEntity> genericModel = new GenericModel<>(LicenseEntity.builder()
                .idLicense(licenseEntity.getIdLicense())
                .issueDate(licenseEntity.getIssueDate())
                .expirationDate(licenseEntity.getExpirationDate())
                .isActive(licenseEntity.getIsActive())
                .licenseCategory(licenseEntity.getLicenseCategory())
                .licenseType(licenseEntity.getLicenseType())
                .client(licenseEntity.getClient())
                .build(), LicenseEntity.class.getSimpleName());
        kafkaUtil.sendMessage(genericModel);
    }

    private Boolean validateDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    public List<CategoryReportDTO> getLicenseReportByCategory() {
        return licenseRepository.getLicenseCountByCategory();
    }

    public byte[] generateCsvReport() {
        List<CategoryReportDTO> report = getLicenseReportByCategory();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        byte[] csvBytes;
        try ( CSVWriter csvWriter = new CSVWriter(writer)) {
            String[] header = {
                    "LicenseCategory", "TotalLicenses"
            };

            for (CategoryReportDTO data : report) {
                String[] row = {
                        data.getLicenseCategory(), String.valueOf(data.getTotalLicenses())
                };
                csvWriter.writeNext(row);
            }
            csvWriter.close();
            writer.close();
            csvBytes = outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV report", e);
        }
        return csvBytes;
    }
}
