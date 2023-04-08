package com.elinxer.cloud.library.server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String pid;
    private String name;
    private String nameEn;
    private int weight;
    private String type;
    private String isDeleted;
    private String version;
    private String createdAt;
    private String updatedAt;
}
