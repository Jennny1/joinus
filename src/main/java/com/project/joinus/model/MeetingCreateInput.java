package com.project.joinus.model;

import com.project.joinus.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingCreateInput {
    // 모임 아이디
    private long meetingId;

    // 멤버 아이디
    private MemberEntity member;
    
    // 글 제목
    private String title;

    // 글 내용
    private String content;

    // 장소
    private String place;

    // 모임 날짜
    private LocalDateTime meetingDate;

    // 참석인원
    private int attendees;

    // 취소여부
    private boolean isCalcled;

    // 모임장여부
    private boolean isLeader;

    // 완료여부
    private boolean isComplete;

    // 글 등록일
    private LocalDateTime regDate;

    // 글 수정일
    private LocalDateTime updateDate;



}
