package com.jeeplus.test.jiankong.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.jiankong.service.dto.TestCameraDTO;
import com.jeeplus.test.jiankong.domain.TestCamera;
import com.jeeplus.test.jiankong.mapper.TestCameraMapper;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.IOException;
import java.util.List;

/**
 * 摄像头服务类
 * @author wjh
 * @version 2025-05-15
 */
@Service
@Transactional
public class TestCameraService extends ServiceImpl<TestCameraMapper, TestCamera> {

    /**
     * 根据id查询摄像头信息
     * @param id
     * @return
     */
    public TestCameraDTO findById(String id) {
        return baseMapper.findById(id);
    }

    /**
     * 自定义分页检索摄像头列表
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage<TestCameraDTO> findPage(Page<TestCameraDTO> page, QueryWrapper queryWrapper) {
        return baseMapper.findList(page, queryWrapper);
    }

    /**
     * 获取摄像头视频流
     * @param cameraId
     * @return
     * @throws IOException
     */
    public byte[] getVideoStream(String cameraId) throws IOException {
        // 加载 OpenCV 库（如果还未加载）
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);

        // 创建 VideoCapture 对象，尝试打开摄像头
        VideoCapture capture = new VideoCapture(cameraId);
        if (!capture.isOpened()) {
            System.err.println("无法打开摄像头: " + cameraId);
            return new byte[0];
        }

        try {
            // 创建一个 Mat 对象用于存储视频帧
            Mat frame = new Mat();
            // 读取一帧视频
            if (capture.read(frame) && !frame.empty()) {
                // 创建一个 MatOfByte 对象用于存储编码后的字节数组
                MatOfByte buffer = new MatOfByte();
                // 将视频帧编码为 JPEG 格式
                Imgcodecs.imencode(".jpg", frame, buffer);
                // 返回编码后的字节数组
                return buffer.toArray();
            } else {
                System.err.println("无法读取摄像头帧");
                return new byte[0];
            }
        } finally {
            // 释放摄像头资源
            capture.release();
        }
    }
}