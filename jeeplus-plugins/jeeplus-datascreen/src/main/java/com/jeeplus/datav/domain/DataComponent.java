/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 大屏组件Entity
 * @author 刘高峰
 * @version 2023-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_datascreen_component")
public class DataComponent extends BaseEntity {

	private static final long serialVersionUID = 1L;

			
	/**
     * 组件名称
     */
	private String name;
			
	/**
     * 组件内容
     */
	private String content;

}
