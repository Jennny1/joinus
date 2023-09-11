package com.project.joinus.controller;

import com.project.joinus.entity.MeetingEntity;
import com.project.joinus.entity.MemberEntity;
import com.project.joinus.error.ResponseError;
import com.project.joinus.model.MeetingCreateInput;
import com.project.joinus.repository.MeetingRepository;
import com.project.joinus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meet")
public class MeetingController2 {
    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;

    /*
    모임 글 생성
    모임 글 생성은 포인트 500이상인 회원만 가능하다.
    모임 글을 생성한 회원은 벙주로 지칭한다.
    모임 글을 생성하면 회원의 벙주 flag를 true로 변경한다.
    모임글 생성할 때 포인트 100을 차감한다.
    모임글을 생성할 때 제목, 내용, 장소, 모집 인원, 모임 날짜, 모임 시간을 입력받는다.
    장소는 위치(지도)API를 사용한다.
    최초 모집 인원은3명~10명 중 지정할 수 있다.
     */

    @RequestMapping("create")
    public ResponseEntity<?> createNewMeeting(@RequestBody MeetingCreateInput meetingCreateInput, Errors errors) {

        List<ResponseError> responseErrorList = new ArrayList<>();
        if (errors.hasErrors()) {

            errors.getAllErrors().forEach((e) -> {
                responseErrorList.add(ResponseError.of((FieldError) e));
            });

            return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);

        }


        MeetingEntity meeting = MeetingEntity.builder()
                .meetingId(meetingCreateInput.getMeetingId())
                .title(meetingCreateInput.getTitle())
                .content(meetingCreateInput.getContent())
                .place(meetingCreateInput.getPlace())
                .meetingDate(meetingCreateInput.getMeetingDate())
                .attendees(meetingCreateInput.getAttendees())
                .isCalcled(false)
                .isLeader(true)
                .isComplete(false)
                .regDate(LocalDateTime.now())
                .build();

        meetingRepository.save(meeting);

        return ResponseEntity.ok().build();
    }

}
