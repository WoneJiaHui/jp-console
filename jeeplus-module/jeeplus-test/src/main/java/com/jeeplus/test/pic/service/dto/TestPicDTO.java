/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.pic.service.dto;

import javax.validation.constraints.NotNull;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 图片管理DTO
 * @author lgf
 * @version 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestPicDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 标题
     */
	@NotNull(message="标题不能为空")
	@ExcelProperty("标题") 
	private String title;
	        
	/**
     * 图片路径
     */
	@NotNull(message="图片路径不能为空")
	@ExcelProperty("图片路径") 
	private String pic;
	        
	/**
     * 备注信息
     */
	@NotNull(message="备注信息不能为空")
	@ExcelProperty("备注信息") 
	private String remarks;

}
