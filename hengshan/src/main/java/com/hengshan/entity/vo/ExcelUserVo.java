package com.hengshan.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelUserVo {

    @ExcelProperty("名称")
    private String username;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("手机号码")
    private String phone;
}
