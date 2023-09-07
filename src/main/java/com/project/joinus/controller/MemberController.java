package com.project.joinus.controller;

import com.project.joinus.domain.MemberInputDto;
import com.project.joinus.entity.MemberEntity;
import com.project.joinus.error.ResponseError;
import com.project.joinus.repository.MemberRepository;
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
  public ResponseEntity<List<ResponseError>> addMember(@RequestBody @Valid MemberInputDto memberInputDto, Errors errors){
    List<ResponseError> responseErrorList = new ArrayList<>();

    if (errors.hasErrors()) {
      errors.getAllErrors().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });

      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);

    }

    MemberEntity member = MemberEntity.builder()
        .userName(memberInputDto.getUserName())
        .password(memberInputDto.getPassword())
        .favorit1(memberInputDto.getFavorit1())
        .favorit2(memberInputDto.getFavorit2())
        .point(100)
        .regDate(LocalDateTime.now())
        .build();

    memberRepository.save(member);

    return ResponseEntity.ok().build();

  }


  /*
  회원수정
  * 정보 수정은 패스워드, 관심사1, 관심사2 수정이 가능하다.
   */

  @PatchMapping("/edit/{id}")
  public void editMember(@PathVariable long id, @RequestBody @Valid MemberInputDto memberInputDto, Errors errors) {

    

  }
}
