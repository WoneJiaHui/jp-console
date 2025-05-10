/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.domain.Condition;
import com.jeeplus.extension.mapper.ConditionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程表达式Service
 *
 * @author liugf
 * @version 2021-09-29
 */
@Service
@Transactional
public class ConditionService extends ServiceImpl <ConditionMapper, Condition> {

}
