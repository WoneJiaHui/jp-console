package com.jeeplus.test.jiankong.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.jiankong.domain.TestCamera;
import com.jeeplus.test.jiankong.service.dto.TestCameraDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  CameraWrapper
 * @author wjh
 * @version 2025-05-15
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestCameraWrapper extends EntityWrapper<TestCameraDTO, TestCamera> {

    TestCameraWrapper INSTANCE = Mappers.getMapper(TestCameraWrapper.class);
}