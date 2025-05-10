/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.activiti.controller.app;

import com.jeeplus.test.activiti.controller.TestActivitiLeaveController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请假申请Controller
 * @author liugf
 * @version 2023-02-10
 */
@Api(tags ="请假申请")
@RestController
@RequestMapping(value = "/app/test/activiti/testActivitiLeave")
public class AppTestActivitiLeaveController extends TestActivitiLeaveController {


}
