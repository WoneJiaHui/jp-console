/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.extension.domain.ActCategory;
import com.jeeplus.extension.mapper.ActCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程分类Service
 *
 * @author 刘高峰
 * @version 2021-09-09
 */
@Service
@Transactional
public class ActCategoryService extends TreeService <ActCategoryMapper, ActCategory> {

}
