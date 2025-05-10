/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.datav.domain.DataComponent;
import com.jeeplus.datav.service.DataComponentService;
import com.jeeplus.datav.service.dto.DataComponentDTO;
import com.jeeplus.datav.service.mapstruct.DataComponentWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 大屏组件Controller
 * @author 刘高峰
 * @version 2023-01-17
 */

@RestController
@RequestMapping(value = "/datav/dataComponent")
public class DataComponentController {

	@Autowired
	private DataComponentService dataComponentService;

	@Autowired
	private DataComponentWrapper dataComponentWrapper;

	/**
	 * 大屏组件列表数据
	 */
	@ApiLog("查询大屏组件列表数据")
	@PreAuthorize("hasAuthority('datav:dataComponent:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<DataComponentDTO>> list(DataComponentDTO dataComponentDTO, Page<DataComponent> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (dataComponentDTO, DataComponentDTO.class);
		IPage<DataComponentDTO> result = dataComponentWrapper.toDTO ( dataComponentService.page (page, queryWrapper) );
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取大屏组件数据
	 */
	@ApiLog("根据Id获取大屏组件数据")
	@PreAuthorize("hasAnyAuthority('datav:dataComponent:view','datav:dataComponent:add','datav:dataComponent:edit')")
	@GetMapping("queryById")
	public ResponseEntity<DataComponentDTO> queryById(String id) {
		return ResponseEntity.ok ( dataComponentWrapper.toDTO ( dataComponentService.getById ( id ) ) );
	}

	/**
	 * 保存大屏组件
	 */
	@ApiLog("保存大屏组件")
	@PreAuthorize("hasAnyAuthority('datav:dataComponent:add','datav:dataComponent:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody DataComponentDTO dataComponentDTO) {
		//新增或编辑表单保存
		dataComponentService.saveOrUpdate (dataComponentWrapper.toEntity (dataComponentDTO));
        return ResponseEntity.ok ( "保存大屏组件成功" );
	}


	/**
	 * 删除大屏组件
	 */
	@ApiLog("删除大屏组件")
	@PreAuthorize("hasAuthority('datav:dataComponent:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        dataComponentService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除大屏组件成功" );
	}


}
