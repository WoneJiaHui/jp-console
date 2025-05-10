/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.grid.service.dto;

import javax.validation.constraints.NotNull;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 洲DTO
 * @author lgf
 * @version 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestContinentDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 洲名
     */
	@NotNull(message="洲名不能为空")
    @Query(type = QueryType.LIKE)
	@ExcelProperty("洲名") 
	private String name;
	        
	/**
     * 备注信息
     */
	@ExcelProperty("备注信息") 
	private String remarks;

}
