package com.project.joinus.controller;

import com.project.joinus.entity.MeetingEntity;
import com.project.joinus.entity.MemberEntity;
import com.project.joinus.error.ResponseError;
import com.project.joinus.exception.FailDeleteMeetingException;
import com.project.joinus.exception.FailEditAttendeesException;
import com.project.joinus.exception.FailEditDateException;
import com.project.joinus.exception.IdNoExistException;
import com.project.joinus.exception.LeaderExistException;
import com.project.joinus.exception.MeetingCompleteException;
import com.project.joinus.exception.MeetingNoExistException;
import com.project.joinus.exception.MeetingRecruitmentCompleteException;
import com.project.joinus.exception.MemberQuitException;
import com.project.joinus.exception.pointlessException;
import com.project.joinus.model.MeetingCreateInput;
import com.project.joinus.model.MeetingListDetail;
import com.project.joinus.model.MeetingListInput;
import com.project.joinus.model.MeetingUpdateInput;
import com.project.joinus.model.Member;
import com.project.joinus.repository.MeetingRepository;
import com.project.joinus.repository.MemberRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    한명의 벙주는 한번에 1개의 모임만 생성할 수 있다.
    모임 글을 생성하면 회원의 벙주 flag를 true로 변경한다.
    모임글 생성할 때 포인트 100을 차감한다.
    모임글을 생성할 때 제목, 내용, 장소, 모집 인원, 모임 날짜, 모임 시간을 입력받는다.
    장소는 위치(지도)API를 사용한다.
    모집 인원은 3명~10명 중 지정할 수 있다.
    모임 참석인원을 지정하지 않을 경우, 3명으로 자동 지정된다.
     */


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

    // 벙주여부 확인
    if (member.getMemberId() == 1) {
      throw new LeaderExistException("한번에 1개의 모임만 생성할 수 있습니다.");
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
        .recruitment(1)
        .remain(meetingCreateInput.getAttendees() - 1)
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

  회원은 회원가입시 지정한 관심사로 항목으로 참여 가능한 모임을 검색한다.
  전체 글에서는 글 제목, 관심사, 모임 날짜, 모집인원을 확인할 수 있다.
  글 정렬은 모임 날짜를 조건으로 정렬한다.
   */
  @GetMapping("/list/latest/{size}")
  public Page<MeetingEntity> meetingListLatest(@PathVariable int size) {
    Page<MeetingEntity> meetingList = meetingRepository.findAll(PageRequest.of(0, size,
        Direction.DESC, "regDate"));

    return meetingList;

  }

  /*
  관심사별 글 보기
  관심사별로 모임 목록을 호출
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
  모임 글 상세 보기
  상세보기 기능에서 모임 글 제목, 내용, 모임 장소, 신청 가능 인원, 모집 대상 인원, 모집 완료 여부를 확인할 수 있다.

   */
  @GetMapping("/detail/{id}")
  public MeetingListDetail meetingListDetail(@PathVariable long id) {

    MeetingEntity meeting = meetingRepository.findById(id)
        .orElseThrow(() -> new MeetingNoExistException("모임 글이 없습니다."));

    MeetingListDetail detail = new MeetingListDetail();

    // 완료여부 확인
    detail.setTitle(meeting.getTitle());
    detail.setContent(meeting.getContent());
    detail.setPlace(meeting.getPlace());
    detail.setMeetingDate(meeting.getMeetingDate());
    detail.setClassification(meeting.getClassification());
    detail.setAttendees(meeting.getAttendees());
    detail.setRecruitment(meeting.getRecruitment());
    detail.setRemain(meeting.getRemain());
    return detail;

  }

  /*
  모임 글 수정
  모임 글은 모임 글을 생성한 회원(벙주)만 해당 글을 수정할 수 있다.
  글 제목, 내용, 모집인원을 변경할 수 있다.
  모집 완료가 된 경우에는 글을 수정할 수 없다.
  모임 장소는 수정할 수 없다.
  모임 장소 변경은 글을 삭제 후 다시 생성해야 한다.
   */

  @PatchMapping("/detail/edit/{id}")
  public ResponseEntity<?> MeetingUpdate(@PathVariable long id,
      @RequestBody MeetingUpdateInput meetingUpdateInput) {

    MeetingEntity meeting = meetingRepository.findById(id)
        .orElseThrow(() -> new MeetingNoExistException("모임 글이 없습니다."));

    // 모집완료여부 확인
    if (meeting.getRemain() == 0) {
      throw new MeetingRecruitmentCompleteException("모집이 완료된 모임은 수정할 수 없습니다.");
    }

    // 종료 여부 확인
    if (meeting.isComplete()) {
      throw new MeetingCompleteException("모임이 종료된 이후 수정할 수 없습니다.");
    }

    // 모임 펑 여부 확인
    if (meeting.isCalcled()) {
      throw new MeetingCompleteException("취소(펑)된 모임은 수정할 수 없습니다.");
    }

    // 모임 제목
    if (meetingUpdateInput.getTitle() != null) {
      meeting.setTitle(compareUpdate(meeting.getTitle(), meetingUpdateInput.getTitle()));
    }

    // 모임 내용
    if (meetingUpdateInput.getContent() != null) {
      meeting.setContent(compareUpdate(meeting.getContent(), meetingUpdateInput.getContent()));
    }
    meeting.setContent(meeting.getContent());

    // 장소
    if (meetingUpdateInput.getPlace() != null) {
      meeting.setPlace(compareUpdate(meeting.getPlace(), meetingUpdateInput.getPlace()));
    }
    meeting.setPlace(meeting.getPlace());

    // 모임 날짜
    if (meetingUpdateInput.getMeetingDate() != null) {
      int dateCompare = meetingUpdateInput.getMeetingDate().compareTo(LocalDateTime.now());

      if (dateCompare < 0) {
        throw new FailEditDateException("모임 날짜는 오늘 날짜 이후여야 합니다.");

      }
      meeting.setMeetingDate(meetingUpdateInput.getMeetingDate());

    }

    // 모임 인원
    int beforeAttendees = meeting.getAttendees();
    int afterAttendees = meetingUpdateInput.getAttendees();
    int recruitment = meeting.getRecruitment();
    int remain = meeting.getRecruitment();

    if (afterAttendees < 3) {
      throw new FailEditAttendeesException("참여 인원은 3명 이상이어야 합니다.");
    }

    if (afterAttendees != 0 && beforeAttendees != afterAttendees) {
      if (recruitment > afterAttendees) {
        throw new FailEditAttendeesException("참여 인원은 남은 인원보다 커야합니다.");

      } else {
        beforeAttendees = afterAttendees;
        remain = recruitment - beforeAttendees;

      }
    }
    // 인원 변경 반영
    meeting.setAttendees(beforeAttendees);
    meeting.setRecruitment(recruitment);
    meeting.setRemain(remain);

    // 완료여부 체크
    if (remain == 0) {
      meeting.setComplete(true);
    }

    meetingRepository.save(meeting);
    return ResponseEntity.ok().build();

  }


  /*
  모임 글 삭제
  글 삭제는 모집인원 0명일 때만 가능하다.
  모집 완료가 된 경우에는 글을 삭제할 수 없다. 모임 펑 만 가능하다.
  글 삭제시 delete 한다.
 */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> meetingDelete(@PathVariable long id) {
    MeetingEntity meeting = meetingRepository.findById(id)
        .orElseThrow(() -> new MeetingNoExistException("모임 글이 없습니다."));

    // 모집인원 검사
    if (meeting.getRecruitment() >= 2) {
      throw new FailDeleteMeetingException("글 삭제는 모집인원 0명일 때만 가능합니다.");
    }

    // 모집완료 검사
    if (meeting.isComplete()) {
      throw new FailDeleteMeetingException("모집 완료가 된 경우에는 글 삭제가 불가합니다.");
    }

    meetingRepository.delete(meeting);

    return ResponseEntity.ok().build();
  }

  /*
  모임 펑 기능
  모임 펑 기능은 벙주 flag가 true일 때만 가능하다.
  모임 펑을 신청하면 모임펑 flag 가 true로 변경된다.
  모임 펑이 된 경우 벙주에게 모임 생성시 사용한 포인트 100점을 돌려준다.
  모임 펑이 된 경우 모임을 신청한 회원에 포인트 50점을 돌려준다.
   */
  @PatchMapping("pung/{id}")
  public ResponseEntity<?> MeetingPung(@PathVariable long id) {
    MeetingEntity meeting = meetingRepository.findById(id)
        .orElseThrow(() -> new MeetingNoExistException("모임 글이 없습니다."));

    // 벙주 여부 확인
    if (meeting.getMember().getMemberId() == 0) {
      throw new LeaderExistException("벙주만 모임을 펑할 수 있습니다.");
    }

    //
    meeting.setCalcled(true);
    meetingRepository.save(meeting);

    return ResponseEntity.ok().build();

  }


  public String compareUpdate(String before, String after) {
    if (!after.equals("") && !before.equals(after)) {
      return after;
    }

    return before;
  }


  @ExceptionHandler(value = {MemberQuitException.class, pointlessException.class,
      IdNoExistException.class, MeetingNoExistException.class, MemberQuitException.class,
      MeetingCompleteException.class, MeetingCompleteException.class,
      FailEditAttendeesException.class, MeetingRecruitmentCompleteException.class,
      FailEditAttendeesException.class, FailDeleteMeetingException.class})
  public ResponseEntity<?> ExceptionHandler(RuntimeException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }


}
