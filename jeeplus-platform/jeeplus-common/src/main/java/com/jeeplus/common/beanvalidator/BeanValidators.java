/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.common.beanvalidator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JSR303 Validator(Hibernate Validator)工具类.
 * <p>
 * ConstraintViolation中包含propertyPath, message 和invalidValue等信息.
 * 提供了各种convert方法，适合不同的i18n需求:
 * 1. List<String>, String内容为message
 * 2. List<String>, String内容为propertyPath + separator + message
 * 3. Map<propertyPath, message>
 * <p>
 * 详情见wiki: https://github.com/springside/springside4/wiki/HibernateValidator
 *
 * @author calvin
 * @version 2013-01-15
 */
public class BeanValidators {

    /**
     * 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void validateWithException(Validator validator, Object object, Class <?>... groups)
            throws ConstraintViolationException {
        Set constraintViolations = validator.validate ( object, groups );
        if ( !constraintViolations.isEmpty ( ) ) {
            throw new ConstraintViolationException ( constraintViolations );
        }
    }

    /**
     * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>中为List<message>.
     */
    public static List <String> extractMessage(ConstraintViolationException e) {
        return extractMessage ( e.getConstraintViolations ( ) );
    }

    /**
     * 辅助方法, 转换Set<ConstraintViolation>为List<message>
     */
    @SuppressWarnings("rawtypes")
    public static List <String> extractMessage(Set <? extends ConstraintViolation> constraintViolations) {
        List <String> errorMessages = Lists.newArrayList ( );
        for (ConstraintViolation violation : constraintViolations) {
            errorMessages.add ( violation.getMessage ( ) );
        }
        return errorMessages;
    }

    /**
     * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>为Map<property, message>.
     */
    public static Map <String, String> extractPropertyAndMessage(ConstraintViolationException e) {
        return extractPropertyAndMessage ( e.getConstraintViolations ( ) );
    }

    /**
     * 辅助方法, 转换Set<ConstraintViolation>为Map<property, message>.
     */
    @SuppressWarnings("rawtypes")
    public static Map <String, String> extractPropertyAndMessage(Set <? extends ConstraintViolation> constraintViolations) {
        Map <String, String> errorMessages = Maps.newHashMap ( );
        for (ConstraintViolation violation : constraintViolations) {
            errorMessages.put ( violation.getPropertyPath ( ).toString ( ), violation.getMessage ( ) );
        }
        return errorMessages;
    }

    /**
     * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>为List<propertyPath message>.
     */
    public static List <String> extractPropertyAndMessageAsList(ConstraintViolationException e) {
        return extractPropertyAndMessageAsList ( e.getConstraintViolations ( ), " " );
    }

    /**
     * 辅助方法, 转换Set<ConstraintViolations>为List<propertyPath message>.
     */
    @SuppressWarnings("rawtypes")
    public static List <String> extractPropertyAndMessageAsList(Set <? extends ConstraintViolation> constraintViolations) {
        return extractPropertyAndMessageAsList ( constraintViolations, " " );
    }

    /**
     * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>为List<propertyPath +separator+ message>.
     */
    public static List <String> extractPropertyAndMessageAsList(ConstraintViolationException e, String separator) {
        return extractPropertyAndMessageAsList ( e.getConstraintViolations ( ), separator );
    }

    /**
     * 辅助方法, 转换Set<ConstraintViolation>为List<propertyPath +separator+ message>.
     */
    @SuppressWarnings("rawtypes")
    public static List <String> extractPropertyAndMessageAsList(Set <? extends ConstraintViolation> constraintViolations,
                                                                String separator) {
        List <String> errorMessages = Lists.newArrayList ( );
        for (ConstraintViolation violation : constraintViolations) {
            errorMessages.add ( violation.getPropertyPath ( ) + separator + violation.getMessage ( ) );
        }
        return errorMessages;
    }

    public static Object extractPropertyAndMessage(MethodArgumentNotValidException methodArgumentNotValidException) {
        return methodArgumentNotValidException;
    }

    public static List <String> extractPropertyAndMessageAsList(MethodArgumentNotValidException methodArgumentNotValidException, String s) {
        return extractPropertyAndMessageAsList ( methodArgumentNotValidException, " " );
    }
}
