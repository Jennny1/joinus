package com.project.joinus.controller;

import com.project.joinus.entity.MeetingEntity;
import com.project.joinus.exception.MeetingIdNoSearchException;
import com.project.joinus.exception.MeetingNotCompleteException;
import com.project.joinus.repository.EvaluationRepository;
import com.project.joinus.repository.MeetingRepository;
import com.project.joinus.repository.MemberRepository;
import com.project.joinus.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
    private final ReserveRepository reserveRepository;
    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;
    private final EvaluationRepository evalupationRepository;


    /*
    모임 평가
    모임완료 여부가 true이고 펑 여부가 false, 포인트 적립 대상이 true일 때 평가 할 수 있다. (벙주는 자신의 모임을 평가할 수 없다.)
    모임 평가(1~5점)를 완료할 경우 모임 참가 회원의 모임 평가 flag가 true로 변경된다.
    모임 평가 flag가 true인 회원 의 경우 보상 포인트를 100점 적립한다.
    모임참가자 중 한명이라도 평가를 한 경우, 모임 평가 완료여부 flag 를 true로 변경한다.
    모임 평가 완료여부 flag가 true일 때, 회원의 평가 점수를 취합하여 벙주에게 적립한다.
     */

    @PostMapping("/complete/{id}")
    public void meetingEvaluation(@PathVariable long id) {
        MeetingEntity meeting = meetingRepository.findById(id).orElseThrow(() -> new MeetingIdNoSearchException("존재하지 않는 모임입니다."));

    }






}
