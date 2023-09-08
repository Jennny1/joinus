package com.project.joinus.controller;

import com.project.joinus.exception.EmailNotFountException;
import com.project.joinus.model.MemberInput;
import com.project.joinus.entity.MemberEntity;
import com.project.joinus.error.ResponseError;
import com.project.joinus.model.MemberPasswordInput;
import com.project.joinus.model.MemberUpdateInput;
import com.project.joinus.repository.MemberRepository;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
  private final MemberRepository memberRepository;

  /*
  회원등록
  * 회원 아이디, 패스워드, 관심사1, 관심사2를 입력 받는다.
  * 첫 가입 축하 포인트 100점을 부여한다.
  */

  @PostMapping("/add")
  public ResponseEntity<List<ResponseError>> addMember(@RequestBody @Valid MemberInput memberInput, Errors errors){
    List<ResponseError> responseErrorList = new ArrayList<>();

    if (errors.hasErrors()) {
      errors.getAllErrors().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });

      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);

    }

    MemberEntity member = MemberEntity.builder()
        .userName(memberInput.getUserName())
        .password(memberInput.getPassword())
        .favorit1(memberInput.getFavorit1())
        .favorit2(memberInput.getFavorit2())
        .point(100)
        .regDate(LocalDateTime.now())
        .build();

    memberRepository.save(member);

    return ResponseEntity.ok().build();

  }


  /*
  회원수정
  * 정보 수정은 email이 동일할때
  유저이름, 관심사1, 관심사2 수정이 가능하다.
   */

  @PatchMapping("/update/info/{email}")
  public ResponseEntity<?> updateMember(@PathVariable String email, @RequestBody @Valid MemberUpdateInput memberUpdateInput, Errors errors) {

    List<ResponseError> responseErrorList = new ArrayList<>();
    if (errors.hasErrors()) {
      errors.getAllErrors().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });

      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);

    }
    
    // 이메일 검색
    MemberInput memberInput = memberRepository.findByEmail(email).orElseThrow(() -> new EmailNotFountException("검색한 이메일 정보가 없습니다."));

    String userName, favorit1, favorit2;


    if (memberUpdateInput.getUserName().equals("")) {
      userName = memberInput.getUserName();
    }
    userName = memberUpdateInput.getUserName();


    if (memberUpdateInput.getFavorit1().equals("")) {
      favorit1 = memberInput.getFavorit1();
    }
    favorit1 = memberUpdateInput.getFavorit1();


    if (memberUpdateInput.getFavorit2().equals("")) {
      favorit2 = memberInput.getFavorit2();
    }
    favorit2 = memberUpdateInput.getFavorit2();


    MemberEntity member = MemberEntity.builder()
            .userName(userName)
            .favorit1(favorit1)
            .favorit2(favorit2)
            .updateDate(LocalDateTime.now())
            .build();

    memberRepository.save(member);

    return ResponseEntity.ok().build();

  }

  // EmailNotFountException Handler
  @ExceptionHandler
  public ResponseEntity<?> EmailNotFountExceptionHandler(RuntimeException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }


  /*
  비밀번호 변경
  기존에 입력한 비밀번호와 동일할 때 수정이 가능하다.
   */

  @PatchMapping("/update/password")
  public ResponseEntity<?> updatePassword (@RequestBody @Valid MemberPasswordInput memberPasswordInput, Errors errors) {

    List<ResponseError> responseErrorList = new ArrayList<>();
    if (errors.hasErrors()) {
      errors.getAllErrors().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });

      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);

    }

    // 기존 비밀번호 검색
    MemberInput memberInput = MemberRepository.findByPassword(memberPasswordInput.getPassword())
            .orElseThrow("입력한 비밀번호가 일치하지 않습니다.");









  }

}
