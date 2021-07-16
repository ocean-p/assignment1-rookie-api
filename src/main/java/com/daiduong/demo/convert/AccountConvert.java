package com.daiduong.demo.convert;

import java.util.ArrayList;
import java.util.List;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.entity.AccountEntity;

import org.springframework.stereotype.Component;

@Component
public class AccountConvert {
    
    public AccountEntity toEntity(AccountDTO dto){
        AccountEntity entity = new AccountEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setFullName(dto.getFullName());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setRole(dto.getRole());
        return entity;
    }

    public AccountDTO toDTO(AccountEntity entity) {
        AccountDTO dto = new AccountDTO();
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setFullName(entity.getFullName());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setCreateDate(entity.getCreateDate());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setRole(entity.getRole());
        dto.setDeleted(entity.isDeleted());
        return dto;
    }

    public List<AccountDTO> toDTOList(List<AccountEntity> entityList){
        List<AccountDTO> dtoList = new ArrayList<>();
        for (AccountEntity accountEntity : entityList) {
            AccountDTO dto = toDTO(accountEntity);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
