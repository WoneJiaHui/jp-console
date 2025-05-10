/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.controller;

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
import com.jeeplus.test.treetable.service.dto.CarDTO;
import com.jeeplus.test.treetable.service.mapstruct.CarWrapper;
import com.jeeplus.test.treetable.service.CarService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 车辆Controller
 * @author lgf
 * @version 2023-02-10
 */

@Api(tags ="车辆")
@RestController
@RequestMapping(value = "/test/treetable/car")
public class CarController {

	@Autowired
	private CarService carService;

	@Autowired
	private CarWrapper carWrapper;

	/**
	 * 车辆列表数据
	 */
	@ApiLog("查询车辆列表数据")
	@ApiOperation(value = "查询车辆列表数据")
	@PreAuthorize("hasAuthority('test:treetable:car:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<CarDTO>> list(CarDTO carDTO, Page<CarDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (carDTO, CarDTO.class);
		IPage<CarDTO> result = carService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取车辆数据
	 */
	@ApiLog("根据Id获取车辆数据")
	@ApiOperation(value = "根据Id获取车辆数据")
	@PreAuthorize("hasAnyAuthority('test:treetable:car:view','test:treetable:car:add','test:treetable:car:edit')")
	@GetMapping("queryById")
	public ResponseEntity<CarDTO> queryById(String id) {
		return ResponseEntity.ok ( carService.findById ( id ) );
	}

	/**
	 * 保存车辆
	 */
	@ApiLog("保存车辆")
	@ApiOperation(value = "保存车辆")
	@PreAuthorize("hasAnyAuthority('test:treetable:car:add','test:treetable:car:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody CarDTO carDTO) {
		//新增或编辑表单保存
		carService.saveOrUpdate (carWrapper.toEntity (carDTO));
        return ResponseEntity.ok ( "保存车辆成功" );
	}


	/**
	 * 删除车辆
	 */
	@ApiLog("删除车辆")
	@ApiOperation(value = "删除车辆")
	@PreAuthorize("hasAuthority('test:treetable:car:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        carService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除车辆成功" );
	}
	
	/**
     * 导出车辆数据
     *
     * @param carDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出车辆数据")
    @PreAuthorize("hasAnyAuthority('test:treetable:car:export')")
    @GetMapping("export")
    public void exportFile(CarDTO carDTO, Page <CarDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (carDTO, CarDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "a.id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List<CarDTO> result = carService.findPage ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( carService, carWrapper ).exportExcel ( result,  options.getSheetName ( ), CarDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入车辆数据
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('test:treetable:car:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( carService, carWrapper ).importExcel ( file, CarDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入车辆数据模板
     *
     * @param response
     * @return
     */
    @PreAuthorize ("hasAnyAuthority('test:treetable:car:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "车辆数据导入模板.xlsx";
        List<CarDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( carService, carWrapper ).exportExcel ( list,  "车辆数据", CarDTO.class, fileName, null, response );
    }

}
