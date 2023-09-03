package com.project.joinus.controller;

import com.project.joinus.dto.MemberDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

  @PostMapping("/create")
  void createMember(@RequestBody MemberDto memberDto) {
    System.out.println("등록");
  }
}
