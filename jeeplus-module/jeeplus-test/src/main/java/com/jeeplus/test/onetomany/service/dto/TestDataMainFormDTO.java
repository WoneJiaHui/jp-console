/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.dto;

import com.jeeplus.sys.service.dto.UserDTO;
import javax.validation.constraints.NotNull;
import com.jeeplus.sys.service.dto.OfficeDTO;
import com.jeeplus.sys.service.dto.AreaDTO;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.excel.converter.ExcelUserDTOConverter;
import com.jeeplus.core.excel.converter.ExcelOfficeDTOConverter;
import com.jeeplus.core.excel.converter.ExcelAreaDTOConverter;
import com.jeeplus.core.excel.converter.ExcelDictDTOConverter;
import com.jeeplus.core.excel.annotation.ExcelDictProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 票务代理DTO
 * @author liugf
 * @version 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestDataMainFormDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 用户
     */
	@NotNull(message="用户不能为空")
    @Query(tableColumn = "a.user_id", javaField = "tuser.id", type = QueryType.EQ)
    @ExcelProperty(value = "用户", converter = ExcelUserDTOConverter.class) 
	private UserDTO tuser;
	        
	/**
     * 所属部门
     */
	@NotNull(message="所属部门不能为空")
    @ExcelProperty(value = "所属部门", converter = ExcelOfficeDTOConverter.class) 
	private OfficeDTO office;
	        
	/**
     * 所属区域
     */
	@NotNull(message="所属区域不能为空")
    @ExcelProperty(value = "所属区域", converter = ExcelAreaDTOConverter.class) 
	private AreaDTO area;
	        
	/**
     * 名称
     */
	@NotNull(message="名称不能为空")
    @Query(tableColumn = "a.name", javaField = "name", type = QueryType.LIKE)
	@ExcelProperty("名称") 
	private String name;
	        
	/**
     * 性别
     */
	@NotNull(message="性别不能为空")
    @Query(tableColumn = "a.sex", javaField = "sex", type = QueryType.EQ)
	@ExcelProperty(value = "性别", converter = ExcelDictDTOConverter.class)
	@ExcelDictProperty("sex")
	private String sex;
	        
	/**
     * 身份证照片
     */
	@ExcelProperty("身份证照片") 
	private String file;
	        
	/**
     * 加入日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Query(tableColumn = "a.in_date", javaField = "inDate", type = QueryType.BETWEEN)
	@ExcelProperty("加入日期") 
	private Date inDate;
	        
	/**
     * 备注信息
     */
	@ExcelProperty("备注信息") 
	private String remarks;
    /**
     *子表列表
     */
	private List<TestDataChild23DTO> testDataChild23DTOList = Lists.newArrayList();
    /**
     *子表列表
     */
	private List<TestDataChild21DTO> testDataChild21DTOList = Lists.newArrayList();
    /**
     *子表列表
     */
	private List<TestDataChild22DTO> testDataChild22DTOList = Lists.newArrayList();

}
