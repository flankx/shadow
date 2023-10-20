package com.github.shadow.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 内容
 * </p>
 *
 * @author xuwen
 * @since 2023-09-20
 */
@Data
@TableName("zhihu_content")
@ApiModel(value = "ZhihuContent对象", description = "内容")
public class ZhihuContent implements Serializable {

    private static final long serialVersionUID = 1546410641623528693L;

    @ExcelProperty("主键")
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ExcelProperty("标题")
    @ApiModelProperty("标题")
    private String title;

    @ExcelProperty("问题")
    @ApiModelProperty("问题")
    private String question;

    @ExcelProperty("内容")
    @ApiModelProperty("内容")
    private String content;

    @ExcelProperty("创建时间")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("修改时间")
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
