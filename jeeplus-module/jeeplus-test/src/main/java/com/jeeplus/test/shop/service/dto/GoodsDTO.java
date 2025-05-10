/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service.dto;

import javax.validation.constraints.NotNull;
import com.jeeplus.test.shop.service.dto.CategoryDTO;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.excel.ExcelFieldDTOConverter;
import com.jeeplus.core.excel.annotation.ExcelFieldProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 商品DTO
 * @author liugf
 * @version 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 商品名称
     */
	@NotNull(message="商品名称不能为空")
    @Query(tableColumn = "a.name", javaField = "name", type = QueryType.LIKE)
	@ExcelProperty("商品名称") 
	private String name;
	        
	/**
     * 所属类型
     */
	@NotNull(message="所属类型不能为空")
    @Query(tableColumn = "a.category_id", javaField = "category.id", type = QueryType.EQ)
	@ExcelProperty(value = "所属类型", converter = ExcelFieldDTOConverter.class)
	@ExcelFieldProperty(value = "category.name", service =  "com.jeeplus.test.shop.service.CategoryService", wrapper= "com.jeeplus.test.shop.service.mapstruct.CategoryWrapper")
	private CategoryDTO category;
	        
	/**
     * 价格
     */
	@NotNull(message="价格不能为空")
	@ExcelProperty("价格") 
	private String price;
	        
	/**
     * 备注信息
     */
	@ExcelProperty("备注信息") 
	private String remarks;

}
