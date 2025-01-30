package com.mitocode.clientqueryservice.util;

import com.mitocode.clientqueryservice.model.dto.ClientDTO;
import com.mitocode.clientqueryservice.model.entity.ClientPostgresEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UtilMapper {
    public ClientPostgresEntity convertDTOtoEntity(ClientDTO clientDTO) {
        ClientPostgresEntity clientPostgresEntity = ClientPostgresEntity.builder().build();
        BeanUtils.copyProperties(clientDTO, clientPostgresEntity);
        return clientPostgresEntity;
    }


    public ClientDTO convertEntityToDTO(ClientPostgresEntity clientPostgresEntity) {
        ClientDTO clientDTO = ClientDTO.builder().build();
        BeanUtils.copyProperties(clientPostgresEntity, clientDTO);
        return clientDTO;
    }

    public void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> nullProperties = new HashSet<>();
        for (var pd : src.getPropertyDescriptors()) {
            if (src.getPropertyValue(pd.getName()) == null) {
                nullProperties.add(pd.getName());
            }
        }
        return nullProperties.toArray(new String[0]);
    }
}
