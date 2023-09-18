package com.project.joinus.controller;

import com.project.joinus.entity.MeetingEntity;
import com.project.joinus.entity.MemberEntity;
import com.project.joinus.entity.ReserveEntity;
import com.project.joinus.exception.*;
import com.project.joinus.repository.MeetingRepository;
import com.project.joinus.repository.MemberRepository;
import com.project.joinus.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reserve")
public class ReserveController {

    private final ReserveRepository reserveRepository;
    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;
    private final long POINTLIMIT = 50;

    /*
    모임 신청
    모임신청할 때 포인트 50점을 차감한다.
    포인트가 없을 때 모임을 신청할 수 없다.
    벙주 flag가 true일 때 즉, 벙주 본인이 생성한 글은 신청할 수 없다.
     */

    @PostMapping("/meeting/{meetingId}/{memberId}")
    public ResponseEntity<?> reserveMeeting(@PathVariable long meetingId, long memberId) {
        MemberEntity member = memberRepository.findById(memberId).orElseThrow(() -> new MemberIdNoSearchException("존재하지 않는 회원입니다."));
        MeetingEntity meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new MeetingIdNoSearchException("존재하지 않는 모임입니다."));

        // 회원 탈퇴여부 확인
        if (member.isQuit()) {
            throw new MemberIdNoSearchException("없는 회원입니다.");
        }

        // 벙주여부 확인
        if (meeting.getMember().getMemberId() == member.getMemberId()) {
            throw new FailMeetingJoinException("모임의 벙주는 자신의 모임을 신청할 수 없습니다.");
        }

        // 포인트 확인
        if (member.getPoint() <= POINTLIMIT) {
            throw new PointlessException("모임 신청 포인트가 부족합니다");
        }

        // 모임 신청
        ReserveEntity reserve = ReserveEntity.builder()
                .member(member)
                .meeting(meeting)
                .build();

        reserveRepository.save(reserve);


        // 포인트 차감
        member.setPoint(member.getPoint() - 50);
        memberRepository.save(member);

        return ResponseEntity.ok().build();

    }

    /*
    모임 완료
    모임 펑 flag가 false 대상 모임 중, 등록시 지정한 날짜가 지나면 모임완료 flag를 true 변경한다.
     */

    @PatchMapping("/complete/{id}")
    public ResponseEntity<?> meetingComplete(@PathVariable long id) {
        MeetingEntity meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingIdNoSearchException("존재하지 않는 모임입니다."));

        // 모임종료 확인(날짜)
        int dateCompare = meeting.getMeetingDate().compareTo(LocalDateTime.now());

        if (dateCompare > 0) {
            throw new MeetingNotCompleteException("모임 종료가 되지 않았습니다.");
        }

        meeting.setComplete(true);
        meetingRepository.save(meeting);

        return ResponseEntity.ok().build();

    }

    @ExceptionHandler(value = {MemberIdNoSearchException.class, MeetingIdNoSearchException.class, FailMeetingJoinException.class, PointlessException.class})
    public ResponseEntity<?> ExceptionHandler(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
