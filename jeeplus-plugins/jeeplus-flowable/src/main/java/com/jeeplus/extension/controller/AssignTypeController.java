/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.AssignType;
import com.jeeplus.extension.service.AssignTypeService;
import com.jeeplus.extension.service.dto.AssignTypeDTO;
import com.jeeplus.extension.service.mapstruct.AssignTypeWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 代办人类型Controller
 *
 * @author 刘高峰
 * @version 2021-12-26
 */

@Api(tags = "代办人类型")
@RestController
@RequestMapping(value = "/extension/assignType")
public class AssignTypeController {

    @Autowired
    private AssignTypeService assignTypeService;

    @Autowired
    private AssignTypeWrapper assignTypeWrapper;

    /**
     * 代办人类型列表数据
     */
    @ApiLog("查询代办人类型列表数据")
    @ApiOperation(value = "查询代办人类型列表数据")
    @PreAuthorize("hasAuthority('extension:assignType:list')")
    @GetMapping("list")
    public ResponseEntity <IPage <AssignType>> list(AssignTypeDTO assignTypeDTO, Page <AssignType> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition ( assignTypeDTO, AssignTypeDTO.class );
        IPage <AssignType> result = assignTypeService.page ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }


    /**
     * 根据Id获取代办人类型数据
     */
    @ApiLog("根据Id获取代办人类型数据")
    @ApiOperation(value = "根据Id获取代办人类型数据")
    @PreAuthorize("hasAnyAuthority('extension:assignType:view','extension:assignType:add','extension:assignType:edit')")
    @GetMapping("queryById")
    public ResponseEntity <AssignTypeDTO> queryById(String id) {
        return ResponseEntity.ok ( assignTypeWrapper.toDTO ( assignTypeService.getById ( id ) ) );
    }

    /**
     * 保存代办人类型
     */
    @DemoMode
    @ApiLog("保存代办人类型")
    @PreAuthorize("hasAnyAuthority('extension:assignType:add','extension:assignType:edit')")
    @PostMapping("save")
    public ResponseEntity <String> save(@Valid @RequestBody AssignTypeDTO assignTypeDTO) {
        //新增或编辑表单保存
        assignTypeService.saveOrUpdate ( assignTypeWrapper.toEntity ( assignTypeDTO ) );
        return ResponseEntity.ok ( "保存代办人类型成功" );
    }


    /**
     * 删除代办人类型
     */
    @DemoMode
    @ApiLog("删除代办人类型")
    @PreAuthorize("hasAuthority('extension:assignType:del')")
    @DeleteMapping("delete")
    public ResponseEntity <String> delete(String ids) {
        String idArray[] = ids.split ( "," );
        assignTypeService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok ( "删除代办人类型成功" );
    }

}
