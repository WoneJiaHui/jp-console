/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.tools.controller;

import com.jeeplus.mail.utils.MailUtils;
import com.jeeplus.sys.service.SysConfigService;
import com.jeeplus.tools.model.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送外部邮件
 *
 * @author lgf
 * @version 2021-01-07
 */
@RestController
@RequestMapping("/tools/email")
public class EmailController {

    @Autowired
    private SysConfigService systemConfigService;

    /**
     * 发送邮件
     */
    @RequestMapping("send")
    public ResponseEntity <String> send(@RequestBody EmailRequest emailRequest) throws Exception {
        String[] addresses = emailRequest.getEmailAddress ( ).split ( ";" );
        String result = "";
        for (String address : addresses) {
            try {
                MailUtils.sendEmail ( address, emailRequest.getTitle ( ), emailRequest.getContent ( ) );
                result += address + ":<font color='green'>发送成功!</font><br/>";
            } catch (Exception e) {
                result += address + ":<font color='red'>发送失败!</font><br/>";
            }
        }
        return ResponseEntity.ok ( result );
    }

}
