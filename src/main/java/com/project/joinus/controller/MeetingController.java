package com.project.joinus.controller;

import com.project.joinus.entity.MeetingEntity;
import com.project.joinus.entity.MemberEntity;
import com.project.joinus.error.ResponseError;
import com.project.joinus.exception.IdNoExistException;
import com.project.joinus.exception.MeetingNoExistException;
import com.project.joinus.exception.MemberQuitException;
import com.project.joinus.exception.pointlessException;
import com.project.joinus.model.MeetingCreateInput;
import com.project.joinus.model.MeetingListDetail;
import com.project.joinus.model.MeetingListInput;
import com.project.joinus.repository.MeetingRepository;
import com.project.joinus.repository.MemberRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

  private final MeetingRepository meetingRepository;
  private final MemberRepository memberRepository;
  private final long CREATE_POINT = 100;
  private final long CREATE_POINT_LIMIT = 500;



    /*
    모임 글 생성
    모임 글 생성은 포인트 500이상인 회원만 가능하다.
    모임 글을 생성한 회원은 벙주로 지칭한다.
    모임 글을 생성하면 회원의 벙주 flag를 true로 변경한다.
    모임글 생성할 때 포인트 100을 차감한다.
    모임글을 생성할 때 제목, 내용, 장소, 모집 인원, 모임 날짜, 모임 시간을 입력받는다.
    장소는 위치(지도)API를 사용한다.
    모집 인원은 3명~10명 중 지정할 수 있다.
    모임 참석인원을 지정하지 않을 경우, 3명으로 자동 지정된다.
     */


  @ExceptionHandler(value = {pointlessException.class, IdNoExistException.class,
      MemberQuitException.class})
  public ResponseEntity<?> ExceptionHandler(RuntimeException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @PostMapping("/{id}/create")
  public ResponseEntity<?> createNewMeeting(@PathVariable long id,
      @RequestBody @Valid MeetingCreateInput meetingCreateInput, Errors errors)
      throws MemberQuitException {

    List<ResponseError> responseErrorList = new ArrayList<>();
    if (errors.hasErrors()) {

      errors.getAllErrors().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });

      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);

    }

    // 탈퇴한 아이디 확인
    MemberEntity member = memberRepository.findById(id)
        .orElseThrow(() -> new IdNoExistException("존재하지 않는 아이디입니다."));

    if (member.isQuit()) {
      throw new MemberQuitException("탈퇴한 아이디입니다.");
    }

    // 모임생성 포인트 확인
    if (member.getPoint() < CREATE_POINT_LIMIT) {
      throw new pointlessException("500점 이상인 회원만 모임을 생성할 수 있습니다.");
    }

    System.out.println(member.getUserName() + "님, 모임을 생성할 수 있는 회원입니다.");
    System.out.println("보유 포인트 : " + member.getPoint() + "점");

    // 모집인원 체크 : 모임 참석인원을 지정하지 않을 경우, 3명으로 자동 지정된다.
    if (meetingCreateInput.getAttendees() == 0) {
      meetingCreateInput.setAttendees(3);
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime dateTime = LocalDateTime.parse(meetingCreateInput.getMeetingDate(), formatter);

    // 새로운 모임글 등록
    MeetingEntity meeting = MeetingEntity.builder()
        .member(MemberEntity.builder().memberId(id).build())
        .title(meetingCreateInput.getTitle())
        .content(meetingCreateInput.getContent())
        .place(meetingCreateInput.getPlace())
        .meetingDate(dateTime)
        .classification(meetingCreateInput.getClassification())
        .attendees(meetingCreateInput.getAttendees())
        .regDate(LocalDateTime.now())
        .build();

    meetingRepository.save(meeting);

    System.out.println("모임 생성 완료");
    System.out.println("모임생성 포인트 100점 차감, 남은 포인트 : " + member.getPoint() + "점");

    // 포인트 차감
    long newPoint = minusPoint(id, member.getPoint());

    member.setPoint(newPoint);
    member.setLeader(true);
    memberRepository.save(member);

    return ResponseEntity.ok().build();
  }

  /*
  모임 포인트 차감
  모임글 생성할 때 포인트 100을 차감한다.
   */

  public long minusPoint(long id, long point) {
    MemberEntity member = memberRepository.findById(id)
        .orElseThrow(() -> new IdNoExistException("존재하지 않는 아이디입니다."));

    return point - CREATE_POINT;

  }


  /*
  모임 전체 글 보기
  최근글 노출
   */
  @GetMapping("/list/latest/{size}")
  public Page<MeetingEntity> meetingListLatest(@PathVariable int size) {
    Page<MeetingEntity> meetingList = meetingRepository.findAll(PageRequest.of(0, size,
        Direction.DESC, "regDate"));

    return meetingList;

  }

  /*
  관심사별 글 보기
   */

  @GetMapping("/list/favorit/{size}")
  public Page<MeetingEntity> meetingListFavorit(@PathVariable int size,
      @RequestBody MeetingListInput meetingListInput) {
    Page<MeetingEntity> meetingList = meetingRepository.findAllByClassification(
        meetingListInput.getClassification(), PageRequest.of(0, size,
            Direction.DESC, "regDate"));

    return meetingList;

  }

  /*
  모임 글 상세보기
   */
  @GetMapping("/detail/{id}")
  public void meetingListDetail(@PathVariable long id,
      @RequestBody MeetingListDetail meetingListDetail) {

    Optional<MeetingEntity> meeting = Optional.ofNullable(meetingRepository.findById(id)
        .orElseThrow(() -> new MeetingNoExistException("모임 글이 없습니다.")));



  }


}
