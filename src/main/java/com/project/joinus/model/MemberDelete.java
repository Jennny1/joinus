package com.project.joinus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDelete {
    private String email;
    private boolean isQuit;
    private long point;
    private Boolean updateDate;
}
