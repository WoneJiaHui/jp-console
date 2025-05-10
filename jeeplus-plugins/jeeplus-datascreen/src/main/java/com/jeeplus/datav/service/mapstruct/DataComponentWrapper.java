/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.datav.service.dto.DataComponentDTO;
import com.jeeplus.datav.domain.DataComponent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  DataComponentWrapper
 * @author 刘高峰
 * @version 2023-01-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface DataComponentWrapper extends EntityWrapper<DataComponentDTO, DataComponent> {

    DataComponentWrapper INSTANCE = Mappers.getMapper(DataComponentWrapper.class);
}

