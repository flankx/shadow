package com.github.shadow.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xuwen
 * @since 2023-07-12
 */
@Data
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("名称")
    private String userName;

    @ApiModelProperty("数据源类型")
    private String password;

    @ApiModelProperty("连接地址")
    private String nickName;

    @ApiModelProperty("备注")
    private Integer deleteStatus;

    @ApiModelProperty("创建人")
    private String permissions;

    @ApiModelProperty("创建部门")
    private String extra;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
