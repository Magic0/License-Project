package com.mitocode.clientqueryservice.util;

import com.mitocode.clientqueryservice.model.dto.ClientDTO;
import com.mitocode.clientqueryservice.model.entity.ClientEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UtilMapper {

    public ClientEntity convertDTOtoEntity(ClientDTO clientDTO) {
        ClientEntity clientEntity = ClientEntity.builder().build();
        BeanUtils.copyProperties(clientDTO, clientEntity);
        return clientEntity;
    }

    public ClientDTO convertEntityToDTO(ClientEntity clientEntity) {
        ClientDTO clientDTO = ClientDTO.builder().build();
        BeanUtils.copyProperties(clientEntity, clientDTO);
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
