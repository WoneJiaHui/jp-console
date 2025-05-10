/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.domain.AssignType;
import com.jeeplus.extension.mapper.AssignTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 代办人类型Service
 *
 * @author 刘高峰
 * @version 2021-12-26
 */
@Service
@Transactional
public class AssignTypeService extends ServiceImpl <AssignTypeMapper, AssignType> {

}
