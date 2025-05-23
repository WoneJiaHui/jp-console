/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.grid.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 洲Entity
 * @author lgf
 * @version 2023-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_continent")
public class TestContinent extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 洲名
     */
	private String name;
	/**
     * 备注信息
     */
	private String remarks;

}
