package com.github.shadow.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.shadow.enums.SexTypeEnum;

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

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("性别")
    private SexTypeEnum sexType;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机")
    private String phoneNo;

    @ApiModelProperty("是否删除")
    private Integer deleteStatus;

    @ApiModelProperty("权限列表")
    private String permissions;

    @ApiModelProperty("备注")
    private String extra;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

}
