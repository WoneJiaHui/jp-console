package com.jeeplus.test.jiankong.service.dto;

import com.jeeplus.core.service.dto.BaseDTO;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 摄像头DTO
 * @author wjh
 * @version 2025-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCameraDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("摄像头名称")
    private String name;

    @ExcelProperty("摄像头地址")
    private String address;

    @ExcelProperty("备注信息")
    private String remarks;

    // 以下字段由BaseDTO继承而来，但为了清晰可见，这里列出
    // private String createBy;
    // private Date createDate;
    // private String updateBy;
    // private Date updateDate;
    // private String delFlag;
}