/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.one.service.dto;

import com.jeeplus.sys.service.dto.OfficeDTO;
import javax.validation.constraints.NotNull;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.service.dto.AreaDTO;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.excel.converter.ExcelOfficeDTOConverter;
import com.jeeplus.core.excel.converter.ExcelUserDTOConverter;
import com.jeeplus.core.excel.converter.ExcelAreaDTOConverter;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 请假表单DTO
 * @author 刘高峰
 * @version 2023-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestFormLeaveDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 所属公司
     */
	@NotNull(message="所属公司不能为空")
    @ExcelProperty(value = "所属公司", converter = ExcelOfficeDTOConverter.class) 
	private OfficeDTO company;
	        
	/**
     * 归属部门
     */
	@NotNull(message="归属部门不能为空")
    @Query(tableColumn = "a.office_id", javaField = "office.id", type = QueryType.EQ)
    @ExcelProperty(value = "归属部门", converter = ExcelOfficeDTOConverter.class) 
	private OfficeDTO office;
	        
	/**
     * 员工
     */
    @Query(tableColumn = "a.user_id", javaField = "user.id", type = QueryType.EQ)
    @ExcelProperty(value = "员工", converter = ExcelUserDTOConverter.class) 
	private UserDTO user;
	        
	/**
     * 归属区域
     */
    @Query(tableColumn = "a.area_id", javaField = "area.id", type = QueryType.EQ)
    @ExcelProperty(value = "归属区域", converter = ExcelAreaDTOConverter.class) 
	private AreaDTO area;
	        
	/**
     * 请假开始日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Query(tableColumn = "a.begin_date", javaField = "beginDate", type = QueryType.BETWEEN)
	@ExcelProperty("请假开始日期") 
	private Date beginDate;
	        
	/**
     * 请假结束日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Query(tableColumn = "a.end_date", javaField = "endDate", type = QueryType.NOTBETWEEN)
	@ExcelProperty("请假结束日期") 
	private Date endDate;

}
