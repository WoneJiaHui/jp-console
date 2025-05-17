package com.jeeplus.test.jiankong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.jiankong.service.dto.TestCameraDTO;
import com.jeeplus.test.jiankong.domain.TestCamera;

/**
 * 摄像头MAPPER接口
 * @author wjh
 * @version 2025-05-15
 */
public interface TestCameraMapper extends BaseMapper<TestCamera> {

    /**
     * 根据id获取摄像头信息
     * @param id
     * @return
     */
    TestCameraDTO findById(String id);

    /**
     * 获取摄像头列表
     *
     * @param queryWrapper
     * @return
     */
    IPage<TestCameraDTO> findList(Page<TestCameraDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}