package com.epoint.szjs.zhrq.ngas.comment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在表字段上的中文别名
 * 用处:1.袋鼠云详情页展示
 * 2.导出excel的表头
 *
 * @author zxj
 * @Date 2022/6/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChineseField
{
    //字段中文 @ChineseField(name = "公司名称")
    String name();

    //序号 @ChineseField(order = "1")
    int order() default 0;

    //代码项  @ChineseField(codeItem = "企业类型")
    String codeItem() default "";

    //时间格式化 @ChineseField(format = "yyyy-MM-dd HH:mm:ss")
    String format() default "yyyy-MM-dd";
}
