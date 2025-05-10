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
import com.jeeplus.test.grid.domain.TestContinent;
import com.jeeplus.test.grid.service.dto.TestContinentDTO;
import com.jeeplus.test.grid.service.mapstruct.TestContinentWrapper;
import com.jeeplus.test.grid.service.TestContinentService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 洲Controller
 * @author lgf
 * @version 2023-02-10
 */

@Api(tags ="洲")
@RestController
@RequestMapping(value = "/test/grid/testContinent")
public class TestContinentController {

	@Autowired
	private TestContinentService testContinentService;

	@Autowired
	private TestContinentWrapper testContinentWrapper;

	/**
	 * 洲列表数据
	 */
	@ApiLog("查询洲列表数据")
	@ApiOperation(value = "查询洲列表数据")
	@PreAuthorize("hasAuthority('test:grid:testContinent:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestContinent>> list(TestContinentDTO testContinentDTO, Page<TestContinent> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testContinentDTO, TestContinentDTO.class);
		IPage<TestContinent> result = testContinentService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取洲数据
	 */
	@ApiLog("根据Id获取洲数据")
	@ApiOperation(value = "根据Id获取洲数据")
	@PreAuthorize("hasAnyAuthority('test:grid:testContinent:view','test:grid:testContinent:add','test:grid:testContinent:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestContinentDTO> queryById(String id) {
		return ResponseEntity.ok ( testContinentWrapper.toDTO ( testContinentService.getById ( id ) ) );
	}

	/**
	 * 保存洲
	 */
	@ApiLog("保存洲")
	@ApiOperation(value = "保存洲")
	@PreAuthorize("hasAnyAuthority('test:grid:testContinent:add','test:grid:testContinent:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestContinentDTO testContinentDTO) {
		//新增或编辑表单保存
		testContinentService.saveOrUpdate (testContinentWrapper.toEntity (testContinentDTO));
        return ResponseEntity.ok ( "保存洲成功" );
	}


	/**
	 * 删除洲
	 */
	@ApiLog("删除洲")
	@ApiOperation(value = "删除洲")
	@PreAuthorize("hasAuthority('test:grid:testContinent:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testContinentService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除洲成功" );
	}
	
	/**
     * 导出洲数据
     *
     * @param testContinentDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出洲数据")
    @PreAuthorize("hasAnyAuthority('test:grid:testContinent:export')")
    @GetMapping("export")
    public void exportFile(TestContinentDTO testContinentDTO, Page <TestContinent> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testContinentDTO, TestContinentDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List < TestContinent> result = testContinentService.page ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testContinentService, testContinentWrapper ).exportExcel ( result,  options.getSheetName ( ), TestContinentDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入洲数据
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('test:grid:testContinent:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testContinentService, testContinentWrapper ).importExcel ( file, TestContinentDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入洲数据模板
     *
     * @param response
     * @return
     */
    @PreAuthorize ("hasAnyAuthority('test:grid:testContinent:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "洲数据导入模板.xlsx";
        List<TestContinentDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testContinentService, testContinentWrapper ).exportExcel ( list,  "洲数据", TestContinentDTO.class, fileName, null, response );
    }

}
