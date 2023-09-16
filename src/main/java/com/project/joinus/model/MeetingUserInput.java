package com.project.joinus.model;

import com.project.joinus.entity.MemberEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingUserInput {

    // 멤버 아이디
    private MemberEntity member;

}
