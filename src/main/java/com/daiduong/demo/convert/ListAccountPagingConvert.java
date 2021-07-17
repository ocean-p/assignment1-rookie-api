package com.daiduong.demo.convert;

import java.util.List;

import com.daiduong.demo.dto.AccountDTO;
import com.daiduong.demo.dto.ListAccountPagingDTO;
import com.daiduong.demo.entity.AccountEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ListAccountPagingConvert {
    
    @Autowired
    private AccountConvert accountConvert;

    public ListAccountPagingDTO convert(int pageNo, Page<AccountEntity> page){
        ListAccountPagingDTO result = new ListAccountPagingDTO();
        result.setCurrentPage(pageNo);
        result.setTotalPages(page.getTotalPages());
        result.setTotalItems(page.getTotalElements());
        
        List<AccountDTO> dtolist = accountConvert.toDTOList(page.getContent());
        result.setAccountDTOList(dtolist);
        return result;
    }
}
