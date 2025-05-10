/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.mail.domain.Mail;
import com.jeeplus.mail.domain.MailBox;
import com.jeeplus.mail.domain.MailCompose;
import com.jeeplus.mail.mapper.MailMapper;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 发件箱Service
 *
 * @author jeeplus
 * @version 2021-06-12
 */
@Service
@Transactional(readOnly = true)
public class MailService extends ServiceImpl <MailMapper, Mail> {

    @Autowired
    MailComposeService mailComposeService;
    @Autowired
    MailBoxService mailBoxService;

    public void sendEmail(List <UserDTO> receivers, String title, String content) {
        String assignIds = Collections3.extractToString ( receivers, "id", "," );
        //发送邮件
        Mail mail = new Mail ( );
        mail.setTitle ( title );
        mail.setContent ( content );

        //保存邮件内容
        super.saveOrUpdate ( mail );
        MailCompose mailCompose = new MailCompose ( );
        mailCompose.setMailId ( mail.getId ( ) );
        Date date = new Date ( System.currentTimeMillis ( ) );
        mailCompose.setSendTime ( date );
        mailCompose.setStatus ( CommonConstants.YES );
        mailCompose.setReceiverIds ( assignIds );
        mailCompose.setSenderId ( UserUtils.getCurrentUserDTO ( ).getId ( ) );
        mailComposeService.saveOrUpdate ( mailCompose );
        for (UserDTO receiver : receivers) {
            MailBox mailBox = new MailBox ( );
            mailBox.setReadStatus ( CommonConstants.NO );
            mailBox.setReceiverIds ( assignIds );
            mailBox.setReceiverId ( receiver.getId ( ) );
            mailBox.setSenderId ( UserUtils.getCurrentUserDTO ( ).getId ( ) );
            mailBox.setMailId ( mail.getId ( ) );
            mailBox.setSendTime ( date );
            mailBoxService.saveOrUpdate ( mailBox );
            //发送通知到客户端
        }
    }
}
