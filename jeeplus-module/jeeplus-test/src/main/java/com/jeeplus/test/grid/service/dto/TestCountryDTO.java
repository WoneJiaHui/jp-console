/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.grid.service.dto;

import com.jeeplus.test.grid.service.dto.TestContinentDTO;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.excel.ExcelFieldDTOConverter;
import com.jeeplus.core.excel.annotation.ExcelFieldProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 国家DTO
 * @author 刘高峰
 * @version 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCountryDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 国名
     */
    @Query(tableColumn = "a.name", javaField = "name", type = QueryType.LIKE)
	@ExcelProperty("国名") 
	private String name;
	        
	/**
     * 人口
     */
	@ExcelProperty("人口") 
	private String sum;
	        
	/**
     * 所属洲
     */
	@ExcelProperty(value = "所属洲", converter = ExcelFieldDTOConverter.class)
	@ExcelFieldProperty(value = "continent.name", service =  "com.jeeplus.test.grid.service.TestContinentService", wrapper= "com.jeeplus.test.grid.service.mapstruct.TestContinentWrapper")
	private TestContinentDTO continent;
	        
	/**
     * 备注信息
     */
	@ExcelProperty("备注信息") 
	private String remarks;

}
