/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.core.excel.EasyExcelUtils;
import com.jeeplus.core.excel.ExcelOptions;
import com.jeeplus.core.excel.annotation.ExportMode;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataMainFormWrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataMainFormDTO;
import com.jeeplus.test.onetomany.service.TestDataMainFormService;

/**
 * 票务代理Controller
 * @author liugf
 * @version 2023-02-10
 */
@Api(tags ="票务代理")
@RestController
@RequestMapping(value = "/test/onetomany/testDataMainForm")
public class TestDataMainFormController {

	@Autowired
	private TestDataMainFormService testDataMainFormService;
	
	@Autowired
	private TestDataMainFormWrapper testDataMainFormWrapper;

	/**
	 * 票务代理列表数据
	 */
	@ApiLog("查询票务代理列表数据")
	@ApiOperation(value = "查询票务代理列表数据")
	@PreAuthorize("hasAuthority('test:onetomany:testDataMainForm:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestDataMainFormDTO>> list(TestDataMainFormDTO testDataMainFormDTO, Page<TestDataMainFormDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testDataMainFormDTO, TestDataMainFormDTO.class);
		IPage<TestDataMainFormDTO> result = testDataMainFormService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取票务代理数据
	 */
	@ApiLog("根据Id获取票务代理数据")
	@ApiOperation(value = "根据Id获取票务代理数据")
	@PreAuthorize("hasAnyAuthority('test:onetomany:testDataMainForm:view','test:onetomany:testDataMainForm:add','test:onetomany:testDataMainForm:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestDataMainFormDTO> queryById(String id) {
		return ResponseEntity.ok ( testDataMainFormService.findById ( id ) );
	}

	/**
	 * 保存票务代理
	 */
	@ApiLog("保存票务代理")
	@ApiOperation(value = "保存票务代理")
	@PreAuthorize("hasAnyAuthority('test:onetomany:testDataMainForm:add','test:onetomany:testDataMainForm:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestDataMainFormDTO testDataMainFormDTO) {
		//新增或编辑表单保存
		testDataMainFormService.saveOrUpdate (testDataMainFormDTO);
        return ResponseEntity.ok ( "保存票务代理成功" );
	}


	/**
	 * 删除票务代理
	 */
	@ApiLog("删除票务代理")
	@ApiOperation(value = "删除票务代理")
	@PreAuthorize("hasAuthority('test:onetomany:testDataMainForm:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			testDataMainFormService.removeById ( id );
		}
		return ResponseEntity.ok( "删除票务代理成功" );
	}
	
	/**
     * 导出票务代理数据
     *
     * @param testDataMainFormDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出票务代理数据")
    @PreAuthorize("hasAnyAuthority('test:onetomany:testDataMainForm:export')")
    @GetMapping("export")
    public void exportFile(TestDataMainFormDTO testDataMainFormDTO, Page <TestDataMainFormDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testDataMainFormDTO, TestDataMainFormDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "a.id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List<TestDataMainFormDTO> result = testDataMainFormService.findPage ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testDataMainFormService, testDataMainFormWrapper ).exportExcel ( result,  options.getSheetName ( ), TestDataMainFormDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入票务代理数据
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('test:onetomany:testDataMainForm:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testDataMainFormService, testDataMainFormWrapper ).importExcel ( file, TestDataMainFormDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入票务代理数据模板
     *
     * @param response
     * @return
     */
    @PreAuthorize ("hasAnyAuthority('test:onetomany:testDataMainForm:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "票务代理数据导入模板.xlsx";
        List<TestDataMainFormDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testDataMainFormService, testDataMainFormWrapper ).exportExcel ( list,  "票务代理数据", TestDataMainFormDTO.class, fileName, null, response );
    }	

}
