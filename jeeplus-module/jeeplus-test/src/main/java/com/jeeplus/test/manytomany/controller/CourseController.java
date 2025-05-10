/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.controller;

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
import com.jeeplus.test.manytomany.domain.Course;
import com.jeeplus.test.manytomany.service.dto.CourseDTO;
import com.jeeplus.test.manytomany.service.mapstruct.CourseWrapper;
import com.jeeplus.test.manytomany.service.CourseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 课程Controller
 * @author lgf
 * @version 2023-02-10
 */

@Api(tags ="课程")
@RestController
@RequestMapping(value = "/test/manytomany/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseWrapper courseWrapper;

	/**
	 * 课程列表数据
	 */
	@ApiLog("查询课程列表数据")
	@ApiOperation(value = "查询课程列表数据")
	@PreAuthorize("hasAuthority('test:manytomany:course:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<Course>> list(CourseDTO courseDTO, Page<Course> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (courseDTO, CourseDTO.class);
		IPage<Course> result = courseService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取课程数据
	 */
	@ApiLog("根据Id获取课程数据")
	@ApiOperation(value = "根据Id获取课程数据")
	@PreAuthorize("hasAnyAuthority('test:manytomany:course:view','test:manytomany:course:add','test:manytomany:course:edit')")
	@GetMapping("queryById")
	public ResponseEntity<CourseDTO> queryById(String id) {
		return ResponseEntity.ok ( courseWrapper.toDTO ( courseService.getById ( id ) ) );
	}

	/**
	 * 保存课程
	 */
	@ApiLog("保存课程")
	@ApiOperation(value = "保存课程")
	@PreAuthorize("hasAnyAuthority('test:manytomany:course:add','test:manytomany:course:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody CourseDTO courseDTO) {
		//新增或编辑表单保存
		courseService.saveOrUpdate (courseWrapper.toEntity (courseDTO));
        return ResponseEntity.ok ( "保存课程成功" );
	}


	/**
	 * 删除课程
	 */
	@ApiLog("删除课程")
	@ApiOperation(value = "删除课程")
	@PreAuthorize("hasAuthority('test:manytomany:course:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        courseService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除课程成功" );
	}
	
	/**
     * 导出课程数据
     *
     * @param courseDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出课程数据")
    @PreAuthorize("hasAnyAuthority('test:manytomany:course:export')")
    @GetMapping("export")
    public void exportFile(CourseDTO courseDTO, Page <Course> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (courseDTO, CourseDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List < Course> result = courseService.page ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( courseService, courseWrapper ).exportExcel ( result,  options.getSheetName ( ), CourseDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入课程数据
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('test:manytomany:course:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( courseService, courseWrapper ).importExcel ( file, CourseDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入课程数据模板
     *
     * @param response
     * @return
     */
    @PreAuthorize ("hasAnyAuthority('test:manytomany:course:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "课程数据导入模板.xlsx";
        List<CourseDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( courseService, courseWrapper ).exportExcel ( list,  "课程数据", CourseDTO.class, fileName, null, response );
    }

}
