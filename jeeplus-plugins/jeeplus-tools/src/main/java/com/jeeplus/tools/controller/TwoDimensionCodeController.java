/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.tools.controller;

import cn.hutool.core.codec.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jeeplus.common.utils.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

/**
 * 二维码Controller
 *
 * @author jeeplus
 * @version 2021-06-30
 */
@RestController
@RequestMapping("/tools/TwoDimensionCodeController")
public class TwoDimensionCodeController {

    /**
     * 生成二维码
     *
     * @throws Exception
     */
    @GetMapping("createTwoDimensionCode")
    public ResponseEntity createTwoDimensionCode(String encoderContent) throws Exception {

        QRCodeWriter qrCodeWriter = new QRCodeWriter ( );
        BitMatrix bitMatrix = qrCodeWriter.encode ( encoderContent, BarcodeFormat.QR_CODE, 300, 300 );
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream ( );
        MatrixToImageWriter.writeToStream ( bitMatrix, "PNG", pngOutputStream );
        byte[] pngData = pngOutputStream.toByteArray ( );
        return ResponseUtil.newInstance ( ).add ( "codeImg", Base64.encode ( pngData ) ).ok ( "二维码生成成功" );
    }

}
