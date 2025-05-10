/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.grid.controller;

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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.grid.service.dto.TestCountryDTO;
import com.jeeplus.test.grid.service.mapstruct.TestCountryWrapper;
import com.jeeplus.test.grid.service.TestCountryService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 国家Controller
 * @author 刘高峰
 * @version 2023-02-10
 */

@Api(tags ="国家")
@RestController
@RequestMapping(value = "/test/grid/testCountry")
public class TestCountryController {

	@Autowired
	private TestCountryService testCountryService;

	@Autowired
	private TestCountryWrapper testCountryWrapper;

	/**
	 * 国家列表数据
	 */
	@ApiLog("查询国家列表数据")
	@ApiOperation(value = "查询国家列表数据")
	@PreAuthorize("hasAuthority('test:grid:testCountry:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestCountryDTO>> list(TestCountryDTO testCountryDTO, Page<TestCountryDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testCountryDTO, TestCountryDTO.class);
		IPage<TestCountryDTO> result = testCountryService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取国家数据
	 */
	@ApiLog("根据Id获取国家数据")
	@ApiOperation(value = "根据Id获取国家数据")
	@PreAuthorize("hasAnyAuthority('test:grid:testCountry:view','test:grid:testCountry:add','test:grid:testCountry:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestCountryDTO> queryById(String id) {
		return ResponseEntity.ok ( testCountryService.findById ( id ) );
	}

	/**
	 * 保存国家
	 */
	@ApiLog("保存国家")
	@ApiOperation(value = "保存国家")
	@PreAuthorize("hasAnyAuthority('test:grid:testCountry:add','test:grid:testCountry:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestCountryDTO testCountryDTO) {
		//新增或编辑表单保存
		testCountryService.saveOrUpdate (testCountryWrapper.toEntity (testCountryDTO));
        return ResponseEntity.ok ( "保存国家成功" );
	}


	/**
	 * 删除国家
	 */
	@ApiLog("删除国家")
	@ApiOperation(value = "删除国家")
	@PreAuthorize("hasAuthority('test:grid:testCountry:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testCountryService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除国家成功" );
	}
	
	/**
     * 导出国家数据
     *
     * @param testCountryDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出国家数据")
    @PreAuthorize("hasAnyAuthority('test:grid:testCountry:export')")
    @GetMapping("export")
    public void exportFile(TestCountryDTO testCountryDTO, Page <TestCountryDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testCountryDTO, TestCountryDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "a.id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List<TestCountryDTO> result = testCountryService.findPage ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testCountryService, testCountryWrapper ).exportExcel ( result,  options.getSheetName ( ), TestCountryDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入国家数据
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('test:grid:testCountry:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testCountryService, testCountryWrapper ).importExcel ( file, TestCountryDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入国家数据模板
     *
     * @param response
     * @return
     */
    @PreAuthorize ("hasAnyAuthority('test:grid:testCountry:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "国家数据导入模板.xlsx";
        List<TestCountryDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testCountryService, testCountryWrapper ).exportExcel ( list,  "国家数据", TestCountryDTO.class, fileName, null, response );
    }

}
