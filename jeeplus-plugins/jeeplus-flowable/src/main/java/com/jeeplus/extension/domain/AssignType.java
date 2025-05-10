/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代办人类型Entity
 *
 * @author 刘高峰
 * @version 2021-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_assign_type")
public class AssignType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 待办人编码
     */
    private String code;
    /**
     * 待办人名称
     */
    private String name;
    /**
     * 待办人排序
     */
    private int sort;
    /**
     * 说明
     */
    private String remarks;

}
