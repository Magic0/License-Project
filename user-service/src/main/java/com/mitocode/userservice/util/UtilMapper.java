package com.mitocode.userservice.util;


import com.mitocode.userservice.model.dto.UserDTO;
import com.mitocode.userservice.model.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UtilMapper {

    public UserEntity convertDTOtoEntity(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.builder().build();
        BeanUtils.copyProperties(userDTO, userEntity);
        return userEntity;
    }

    public UserDTO convertEntityToDTO(UserEntity userEntity) {
        UserDTO userDTO = UserDTO.builder().build();
        BeanUtils.copyProperties(userEntity, userDTO);
        return userDTO;
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
