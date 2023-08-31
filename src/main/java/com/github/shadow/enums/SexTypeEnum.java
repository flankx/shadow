package com.github.shadow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexTypeEnum {

    // 性别
    UNKNOWN("unknown"), MALE("male"), FEMALE("female"),;

    private final String type;

}
