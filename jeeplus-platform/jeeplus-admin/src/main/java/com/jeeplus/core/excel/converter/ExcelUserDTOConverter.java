/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.core.excel.converter;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.UserDTO;

/**
 * 字段类型转换
 *
 * @author jeeplus
 * @version 2016-03-10
 */

public class ExcelUserDTOConverter implements Converter <UserDTO> {

    @Override
    public Class <?> supportJavaTypeKey() {
        return UserDTO.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public UserDTO convertToJavaData(ReadCellData <?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String val = cellData.getStringValue ( );
        UserDTO userDTO = SpringUtil.getBean ( UserService.class ).getUserDTOByName ( val );
        return userDTO;
    }

    @Override
    public WriteCellData <?> convertToExcelData(UserDTO value, ExcelContentProperty contentProperty,
                                                GlobalConfiguration globalConfiguration) {

        if ( value != null && value.getName ( ) != null ) {
            return new WriteCellData <> ( value.getName ( ) );
        }
        return new WriteCellData <> ( "" );
    }


}

