/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.note.controller;

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
import com.jeeplus.test.note.domain.TestNote;
import com.jeeplus.test.note.service.dto.TestNoteDTO;
import com.jeeplus.test.note.service.mapstruct.TestNoteWrapper;
import com.jeeplus.test.note.service.TestNoteService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 富文本测试Controller
 * @author liugf
 * @version 2023-02-10
 */

@Api(tags ="富文本测试")
@RestController
@RequestMapping(value = "/test/note/testNote")
public class TestNoteController {

	@Autowired
	private TestNoteService testNoteService;

	@Autowired
	private TestNoteWrapper testNoteWrapper;

	/**
	 * 富文本测试列表数据
	 */
	@ApiLog("查询富文本测试列表数据")
	@ApiOperation(value = "查询富文本测试列表数据")
	@PreAuthorize("hasAuthority('test:note:testNote:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestNote>> list(TestNoteDTO testNoteDTO, Page<TestNote> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testNoteDTO, TestNoteDTO.class);
		IPage<TestNote> result = testNoteService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取富文本测试数据
	 */
	@ApiLog("根据Id获取富文本测试数据")
	@ApiOperation(value = "根据Id获取富文本测试数据")
	@PreAuthorize("hasAnyAuthority('test:note:testNote:view','test:note:testNote:add','test:note:testNote:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestNoteDTO> queryById(String id) {
		return ResponseEntity.ok ( testNoteWrapper.toDTO ( testNoteService.getById ( id ) ) );
	}

	/**
	 * 保存富文本测试
	 */
	@ApiLog("保存富文本测试")
	@ApiOperation(value = "保存富文本测试")
	@PreAuthorize("hasAnyAuthority('test:note:testNote:add','test:note:testNote:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestNoteDTO testNoteDTO) {
		//新增或编辑表单保存
		testNoteService.saveOrUpdate (testNoteWrapper.toEntity (testNoteDTO));
        return ResponseEntity.ok ( "保存富文本测试成功" );
	}


	/**
	 * 删除富文本测试
	 */
	@ApiLog("删除富文本测试")
	@ApiOperation(value = "删除富文本测试")
	@PreAuthorize("hasAuthority('test:note:testNote:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testNoteService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除富文本测试成功" );
	}
	
	/**
     * 导出富文本测试数据
     *
     * @param testNoteDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出富文本测试数据")
    @PreAuthorize("hasAnyAuthority('test:note:testNote:export')")
    @GetMapping("export")
    public void exportFile(TestNoteDTO testNoteDTO, Page <TestNote> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testNoteDTO, TestNoteDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List < TestNote> result = testNoteService.page ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testNoteService, testNoteWrapper ).exportExcel ( result,  options.getSheetName ( ), TestNoteDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入富文本测试数据
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('test:note:testNote:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testNoteService, testNoteWrapper ).importExcel ( file, TestNoteDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入富文本测试数据模板
     *
     * @param response
     * @return
     */
    @PreAuthorize ("hasAnyAuthority('test:note:testNote:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "富文本测试数据导入模板.xlsx";
        List<TestNoteDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testNoteService, testNoteWrapper ).exportExcel ( list,  "富文本测试数据", TestNoteDTO.class, fileName, null, response );
    }

}
