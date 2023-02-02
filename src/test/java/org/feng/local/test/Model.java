package org.feng.local.test;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 11/09/2022 11:17
 * @Description:
 */
@Data
@Builder
public class Model {
    public int age;
    public String userId;
    public List<String> userIds;
    public String teamName;
    public String teamId;
    public boolean flag;

}
