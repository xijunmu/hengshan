package com.hengshan.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserPermsVo {

    private List<String> permissions;

    private List<String> roleNames;

    private UserInfoVo userInfoVo;
}
