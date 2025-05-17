package com.jeeplus.test.jiankong.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 摄像头数据传输对象
 * @author wjh
 * @version 2025-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCameraDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 摄像头名称
     */
    @Query(type = QueryType.LIKE)
    @ExcelProperty("摄像头名称")
    private String name;

    /**
     * 摄像头地址
     */
    @ExcelProperty("摄像头地址")
    private String address;

    /**
     * 备注信息
     */
    @ExcelProperty("备注信息")
    private String remarks;
}
