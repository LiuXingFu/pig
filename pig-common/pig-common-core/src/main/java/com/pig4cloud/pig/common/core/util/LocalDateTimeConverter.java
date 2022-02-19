package com.pig4cloud.pig.common.core.util;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.converters.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.common.core.util
 * @ClassNAME: LocalDateTimeConverter
 * @Author: yd
 * @DATE: 2022/2/18
 * @TIME: 18:25
 * @DAY_NAME_SHORT: 周五
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

	/**
	 * 不使用{@code @DateTimeFormat}注解指定日期格式时,默认会使用该格式.
	 */
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	@Override
	public Class supportJavaTypeKey() {
		return LocalDateTime.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	/**
	 * 这里读的时候会调用
	 *
	 * @param cellData            excel数据 (NotNull)
	 * @param contentProperty     excel属性 (Nullable)
	 * @param globalConfiguration 全局配置 (NotNull)
	 * @return 读取到内存中的数据
	 */
	@Override
	public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
		DateTimeFormat annotation = contentProperty.getField().getAnnotation(DateTimeFormat.class);
		return LocalDateTime.parse(cellData.getStringValue(),
				DateTimeFormatter.ofPattern(Objects.nonNull(annotation) ? annotation.value() : DEFAULT_PATTERN));
	}


	/**
	 * 写的时候会调用
	 *
	 * @param value               java value (NotNull)
	 * @param contentProperty     excel属性 (Nullable)
	 * @param globalConfiguration 全局配置 (NotNull)
	 * @return 写出到excel文件的数据
	 */
	@Override
	public CellData convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
		DateTimeFormat annotation = contentProperty.getField().getAnnotation(DateTimeFormat.class);
		return new CellData(value.format(DateTimeFormatter.ofPattern(Objects.nonNull(annotation) ? annotation.value() : DEFAULT_PATTERN)));
	}

}
