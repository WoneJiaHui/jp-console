/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.controller;

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
import com.jeeplus.test.shop.service.dto.GoodsDTO;
import com.jeeplus.test.shop.service.mapstruct.GoodsWrapper;
import com.jeeplus.test.shop.service.GoodsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 商品Controller
 * @author liugf
 * @version 2023-02-10
 */

@Api(tags ="商品")
@RestController
@RequestMapping(value = "/test/shop/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsWrapper goodsWrapper;

	/**
	 * 商品列表数据
	 */
	@ApiLog("查询商品列表数据")
	@ApiOperation(value = "查询商品列表数据")
	@PreAuthorize("hasAuthority('test:shop:goods:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<GoodsDTO>> list(GoodsDTO goodsDTO, Page<GoodsDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (goodsDTO, GoodsDTO.class);
		IPage<GoodsDTO> result = goodsService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取商品数据
	 */
	@ApiLog("根据Id获取商品数据")
	@ApiOperation(value = "根据Id获取商品数据")
	@PreAuthorize("hasAnyAuthority('test:shop:goods:view','test:shop:goods:add','test:shop:goods:edit')")
	@GetMapping("queryById")
	public ResponseEntity<GoodsDTO> queryById(String id) {
		return ResponseEntity.ok ( goodsService.findById ( id ) );
	}

	/**
	 * 保存商品
	 */
	@ApiLog("保存商品")
	@ApiOperation(value = "保存商品")
	@PreAuthorize("hasAnyAuthority('test:shop:goods:add','test:shop:goods:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody GoodsDTO goodsDTO) {
		//新增或编辑表单保存
		goodsService.saveOrUpdate (goodsWrapper.toEntity (goodsDTO));
        return ResponseEntity.ok ( "保存商品成功" );
	}


	/**
	 * 删除商品
	 */
	@ApiLog("删除商品")
	@ApiOperation(value = "删除商品")
	@PreAuthorize("hasAuthority('test:shop:goods:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        goodsService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除商品成功" );
	}
	
	/**
     * 导出商品数据
     *
     * @param goodsDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出商品数据")
    @PreAuthorize("hasAnyAuthority('test:shop:goods:export')")
    @GetMapping("export")
    public void exportFile(GoodsDTO goodsDTO, Page <GoodsDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (goodsDTO, GoodsDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "a.id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List<GoodsDTO> result = goodsService.findPage ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( goodsService, goodsWrapper ).exportExcel ( result,  options.getSheetName ( ), GoodsDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入商品数据
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('test:shop:goods:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( goodsService, goodsWrapper ).importExcel ( file, GoodsDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入商品数据模板
     *
     * @param response
     * @return
     */
    @PreAuthorize ("hasAnyAuthority('test:shop:goods:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "商品数据导入模板.xlsx";
        List<GoodsDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( goodsService, goodsWrapper ).exportExcel ( list,  "商品数据", GoodsDTO.class, fileName, null, response );
    }

}
