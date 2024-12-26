package com.dangthuc.newword.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RoleInfoDto {
    private String name;
    private String description;
    private boolean active;
    private List<Map<Long, String>> permissions;
}
