/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.AssignType;
import com.jeeplus.extension.service.dto.AssignTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * AssignTypeWrapper
 *
 * @author 刘高峰
 * @version 2021-12-26
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface AssignTypeWrapper extends EntityWrapper <AssignTypeDTO, AssignType> {

    AssignTypeWrapper INSTANCE = Mappers.getMapper ( AssignTypeWrapper.class );
}

