package com.jeeplus.test.jiankong.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.core.excel.EasyExcelUtils;
import com.jeeplus.core.excel.ExcelOptions;
import com.jeeplus.core.excel.annotation.ExportMode;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.jiankong.service.dto.TestCameraDTO;
import com.jeeplus.test.jiankong.service.mapstruct.TestCameraWrapper;
import com.jeeplus.test.jiankong.service.TestCameraService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 摄像头Controller
 * @author wjh
 * @version 2025-05-15
 */
@Api(tags = "摄像头")
@RestController
@RequestMapping(value = "/test/jiankong/testCamera")
public class TestCameraController {

    @Autowired
    private TestCameraService testCameraService;

    @Autowired
    private TestCameraWrapper testCameraWrapper;

    /**
     * 摄像头列表数据
     */
    @ApiLog("查询摄像头列表数据")
    @ApiOperation(value = "查询摄像头列表数据")
    //@PreAuthorize("hasAuthority('test:jiankong:testCamera:list')")
    @GetMapping("list")
    public ResponseEntity<IPage<TestCameraDTO>> list(TestCameraDTO cameraDTO, Page<TestCameraDTO> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition(cameraDTO, TestCameraDTO.class);
        IPage<TestCameraDTO> result = testCameraService.findPage(page, queryWrapper);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据Id获取摄像头数据
     */
    @ApiLog("根据Id获取摄像头数据")
    @ApiOperation(value = "根据Id获取摄像头数据")
    //@PreAuthorize("hasAnyAuthority('test:jiankong:testCamera:view','test:jiankong:testCamera:add','test:jiankong:testCamera:edit')")
    @GetMapping("queryById")
    public ResponseEntity<TestCameraDTO> queryById(String id) {
        return ResponseEntity.ok(testCameraService.findById(id));
    }

    /**
     * 保存摄像头信息
     */
    @ApiLog("保存摄像头信息")
    @ApiOperation(value = "保存摄像头信息")
    //@PreAuthorize("hasAnyAuthority('test:jiankong:testCamera:add','test:jiankong:testCamera:edit')")
    @PostMapping("save")
    public ResponseEntity<String> save(@Valid @RequestBody TestCameraDTO cameraDTO) {
        // 新增或编辑表单保存
        testCameraService.saveOrUpdate(testCameraWrapper.toEntity(cameraDTO));
        return ResponseEntity.ok("保存摄像头信息成功");
    }

    /**
     * 删除摄像头信息
     */
    @ApiLog("删除摄像头信息")
    @ApiOperation(value = "删除摄像头信息")
    //@PreAuthorize("hasAuthority('test:jiankong:testCamera:del')")
    @DeleteMapping("delete")
    public ResponseEntity<String> delete(String ids) {
        String idArray[] = ids.split(",");
        testCameraService.removeByIds(Lists.newArrayList(idArray));
        return ResponseEntity.ok("删除摄像头信息成功");
    }

    /**
     * 导出摄像头数据
     *
     * @param cameraDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出摄像头数据")
    //@PreAuthorize("hasAnyAuthority('test:jiankong:testCamera:export')")
    @GetMapping("export")
    public void exportFile(TestCameraDTO cameraDTO, Page<TestCameraDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename();
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition(cameraDTO, TestCameraDTO.class);
        if (ExportMode.current.equals(options.getMode())) { // 导出当前页数据

        } else if (ExportMode.selected.equals(options.getMode())) { // 导出选中数据
            queryWrapper.in("a.id", options.getSelectIds());
        } else { // 导出全部数据
            page.setSize(-1);
            page.setCurrent(0);
        }
        List<TestCameraDTO> result = testCameraService.findPage(page, queryWrapper).getRecords();
        EasyExcelUtils.newInstance(testCameraService, testCameraWrapper).exportExcel(result, options.getSheetName(), TestCameraDTO.class, fileName, options.getExportFields(), response);
    }

    /**
     * 导入摄像头数据
     *
     * @return
     */
    //@PreAuthorize("hasAnyAuthority('test:jiankong:testCamera:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance(testCameraService, testCameraWrapper).importExcel(file, TestCameraDTO.class);
        return ResponseEntity.ok(result);
    }

    /**
     * 下载导入摄像头数据模板
     *
     * @param response
     * @return
     */
    //@PreAuthorize("hasAnyAuthority('test:jiankong:testCamera:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "摄像头数据导入模板.xlsx";
        List<TestCameraDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance(testCameraService, testCameraWrapper).exportExcel(list, "摄像头数据", TestCameraDTO.class, fileName, null, response);
    }

    /**
     * 获取摄像头视频流
     */
    @ApiLog("获取摄像头视频流")
    @ApiOperation(value = "获取摄像头视频流")
    @GetMapping(value = "/video-stream/{cameraId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getVideoStream(@PathVariable String cameraId) throws IOException {
        byte[] videoStream = testCameraService.getVideoStream(cameraId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(videoStream);
    }
}
