package com.jeeplus.core.excel;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeeplus.common.beanvalidator.BeanValidators;
import com.jeeplus.core.mapstruct.EntityWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;

// 有个很重要的点 ExcelListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
@Data
public class ExcelListener<T> implements ReadListener <T> {

    private String message;

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List <T> cachedDataList = ListUtils.newArrayListWithExpectedSize ( BATCH_COUNT );
    /**
     * 业务存储service
     */
    private IService service;

    private EntityWrapper wrapper;


    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param service
     */
    public ExcelListener(IService service, EntityWrapper wrapper) {
        this.service = service;
        this.wrapper = wrapper;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info ( "解析到一条数据:{}", data.toString ( ) );
        cachedDataList.add ( data );
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if ( cachedDataList.size ( ) >= BATCH_COUNT ) {
            saveData ( );
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize ( BATCH_COUNT );
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 保存数据到数据库
        saveData ( );
        log.info ( "所有数据解析完成！" );
    }

    /**
     * 加上存储数据库
     */
    public void saveData() {
        log.info ( "{}条数据，开始导入数据库！", cachedDataList.size ( ) );
        Validator validator = SpringUtil.getBean ( Validator.class );
        StringBuilder failureMsg = new StringBuilder ( );
        cachedDataList.forEach ( cachedData -> {
            try {
                BeanValidators.validateWithException ( validator, cachedData );
            } catch (ConstraintViolationException ex) {
                List <String> messageList = BeanValidators.extractPropertyAndMessageAsList ( ex, ": " );
                for (String message : messageList) {
                    failureMsg.append ( message + "; " );
                }
            }
        } );

        if ( StrUtil.isNotEmpty ( failureMsg.toString ( ) ) ) {
            this.message = failureMsg.toString ( );
            log.info ( this.message );
            return;
        }
        service.saveBatch ( wrapper.toEntity ( cachedDataList ) );
        this.message = "导入数据库成功！";
    }
}
