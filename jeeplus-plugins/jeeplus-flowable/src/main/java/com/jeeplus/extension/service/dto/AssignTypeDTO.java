/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;

import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 代办人类型DTO
 *
 * @author 刘高峰
 * @version 2021-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AssignTypeDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 待办人编码
     */
    @NotNull(message = "待办人编码不能为空")
    private String code;
    /**
     * 待办人名称
     */
    @NotNull(message = "待办人名称不能为空")
    private String name;

    /**
     * 排序
     */
    private int sort;
    /**
     * 说明
     */
    private String remarks;

}
