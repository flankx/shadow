package com.github.shadow.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author xuwen
 * @since 2023-07-12
 */
@Data
@TableName("user_data")
@ApiModel(value = "UserData对象", description = "用户信息表")
public class UserData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("名称")
    private String userName;

    @ApiModelProperty("性别")
    private String sexType;

    @ApiModelProperty("生日")
    private LocalDate birthDay;

    @ApiModelProperty("是否冻结")
    private Integer isFreeze;

    @ApiModelProperty("用户状态")
    private Integer userStatus;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机")
    private String phoneNo;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
