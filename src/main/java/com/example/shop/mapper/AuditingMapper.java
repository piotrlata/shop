package com.example.shop.mapper;

import com.example.shop.domain.dao.Auditable;
import com.example.shop.domain.dto.AuditableDto;
import com.example.shop.security.SecurityUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

public interface AuditingMapper<DAO extends Auditable, DTO extends AuditableDto> {
    @AfterMapping
    default void mapAuditingForAdmin(DAO dao, @MappingTarget DTO.AuditableDtoBuilder<?, ?> dto) {
        if (!SecurityUtils.hasRole("ROLE_ADMIN")) {
            dto.createdBy(null);
            dto.createdDate(null);
            dto.lastModifiedBy(null);
            dto.lastModifiedDate(null);
        }
    }
}
