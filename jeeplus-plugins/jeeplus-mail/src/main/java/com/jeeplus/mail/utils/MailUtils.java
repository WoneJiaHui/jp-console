package com.jeeplus.mail.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.jeeplus.sys.domain.SysConfig;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.service.SysConfigService;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.UserDTO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class MailUtils {


    public static void sendEmail(String to, String title, String content) {
        SysConfig config = SpringUtil.getBean ( SysConfigService.class ).getById ( "1" );
        String from = config.getMailName ( );
        String host = config.getSmtp ( );
        Properties properties = System.getProperties ( );
        properties.setProperty ( "mail.smtp.host", host );
        properties.put ( "mail.smtp.auth", "true" );


        Session session = Session.getDefaultInstance ( properties, new Authenticator ( ) {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication ( from, config.getMailPassword ( ) );
            }
        } );

        try {
            MimeMessage mimeMessage = new MimeMessage ( session );
            mimeMessage.setFrom ( new InternetAddress ( from ) );
            mimeMessage.addRecipient ( Message.RecipientType.TO,
                    new InternetAddress ( to ) );
            mimeMessage.setSubject ( title );
            mimeMessage.setContent ( content, "text/html" );
            Transport.send ( mimeMessage );
            System.out.println ( "发送邮件成功" );

        } catch (MessagingException e) {
            e.printStackTrace ( );
        }
    }

    /**
     * 获取收件人用户Name
     *
     * @return
     */
    public static String getReceiverNames(String receiverIds) {
        if ( StrUtil.isBlank ( receiverIds ) ) {
            return "";
        }
        List <UserDTO> receiverList = Lists.newArrayList ( );
        for (String id : StrUtil.split ( receiverIds, "," )) {

            receiverList.add ( SpringUtil.getBean ( UserService.class ).get ( id ) );
        }
        List <String> receiverNames = receiverList.stream ( ).filter ( userDTO -> {
            return userDTO != null && StrUtil.isNotBlank ( userDTO.getName ( ) );
        } ).map ( userDTO -> {
            return userDTO.getName ( );
        } ).collect ( Collectors.toList ( ) );
        return StrUtil.join ( ",", receiverNames );
    }

    /**
     * 获取收件人用户
     *
     * @return
     */

    public static List <User> getReceiverList(String receiverIds) {
        List receiverList = Lists.newArrayList ( );
        if ( StrUtil.isBlank ( receiverIds ) ) {
            return receiverList;
        }
        for (String id : StrUtil.split ( receiverIds, "," )) {

            receiverList.add ( new User ( id ) );
        }
        return receiverList;
    }
}
