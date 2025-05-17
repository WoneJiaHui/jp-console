package com.jeeplus.test.jiankong.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 摄像头Domain
 * @author wjh
 * @version 2025-05-15
 */

// 摄像头实体类，映射数据库camera表
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_camera")
public class TestCamera extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 摄像头名称
     */
    private String name;

    /**
     * 摄像头地址
     */
    private String address;

    /**
     * 备注信息
     */
    private String remarks;
}