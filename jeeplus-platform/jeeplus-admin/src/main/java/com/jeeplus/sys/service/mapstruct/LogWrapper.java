package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.sys.domain.Log;
import com.jeeplus.sys.service.dto.LogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface LogWrapper extends EntityWrapper <LogDTO, Log> {

    LogWrapper INSTANCE = Mappers.getMapper ( LogWrapper.class );

}
