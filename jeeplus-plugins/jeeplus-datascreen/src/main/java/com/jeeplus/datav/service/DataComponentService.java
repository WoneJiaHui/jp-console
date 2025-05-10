/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.datav.domain.DataComponent;
import com.jeeplus.datav.mapper.DataComponentMapper;

/**
 * 大屏组件Service
 * @author 刘高峰
 * @version 2023-01-17
 */
@Service
@Transactional
public class DataComponentService extends ServiceImpl<DataComponentMapper, DataComponent> {

}
