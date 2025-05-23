/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.one.controller;

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
import com.jeeplus.test.one.service.dto.TestFormLeaveDTO;
import com.jeeplus.test.one.service.mapstruct.TestFormLeaveWrapper;
import com.jeeplus.test.one.service.TestFormLeaveService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 请假表单Controller
 * @author 刘高峰
 * @version 2023-02-12
 */

@Api(tags ="请假表单")
@RestController
@RequestMapping(value = "/test/one/testFormLeave")
public class TestFormLeaveController {

	@Autowired
	private TestFormLeaveService testFormLeaveService;

	@Autowired
	private TestFormLeaveWrapper testFormLeaveWrapper;

	/**
	 * 请假表单列表数据
	 */
	@ApiLog("查询请假表单列表数据")
	@ApiOperation(value = "查询请假表单列表数据")
	@PreAuthorize("hasAuthority('test:one:testFormLeave:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestFormLeaveDTO>> list(TestFormLeaveDTO testFormLeaveDTO, Page<TestFormLeaveDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testFormLeaveDTO, TestFormLeaveDTO.class);
		IPage<TestFormLeaveDTO> result = testFormLeaveService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取请假表单数据
	 */
	@ApiLog("根据Id获取请假表单数据")
	@ApiOperation(value = "根据Id获取请假表单数据")
	@PreAuthorize("hasAnyAuthority('test:one:testFormLeave:view','test:one:testFormLeave:add','test:one:testFormLeave:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestFormLeaveDTO> queryById(String id) {
		return ResponseEntity.ok ( testFormLeaveService.findById ( id ) );
	}

	/**
	 * 保存请假表单
	 */
	@ApiLog("保存请假表单")
	@ApiOperation(value = "保存请假表单")
	@PreAuthorize("hasAnyAuthority('test:one:testFormLeave:add','test:one:testFormLeave:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestFormLeaveDTO testFormLeaveDTO) {
		//新增或编辑表单保存
		testFormLeaveService.saveOrUpdate (testFormLeaveWrapper.toEntity (testFormLeaveDTO));
        return ResponseEntity.ok ( "保存请假表单成功" );
	}


	/**
	 * 删除请假表单
	 */
	@ApiLog("删除请假表单")
	@ApiOperation(value = "删除请假表单")
	@PreAuthorize("hasAuthority('test:one:testFormLeave:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testFormLeaveService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除请假表单成功" );
	}
	
	/**
     * 导出请假表单数据
     *
     * @param testFormLeaveDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出请假表单数据")
    @PreAuthorize("hasAnyAuthority('test:one:testFormLeave:export')")
    @GetMapping("export")
    public void exportFile(TestFormLeaveDTO testFormLeaveDTO, Page <TestFormLeaveDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testFormLeaveDTO, TestFormLeaveDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "a.id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List<TestFormLeaveDTO> result = testFormLeaveService.findPage ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testFormLeaveService, testFormLeaveWrapper ).exportExcel ( result,  options.getSheetName ( ), TestFormLeaveDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入请假表单数据
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('test:one:testFormLeave:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testFormLeaveService, testFormLeaveWrapper ).importExcel ( file, TestFormLeaveDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入请假表单数据模板
     *
     * @param response
     * @return
     */
    @PreAuthorize ("hasAnyAuthority('test:one:testFormLeave:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "请假表单数据导入模板.xlsx";
        List<TestFormLeaveDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testFormLeaveService, testFormLeaveWrapper ).exportExcel ( list,  "请假表单数据", TestFormLeaveDTO.class, fileName, null, response );
    }

}
