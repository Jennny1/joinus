package com.project.joinus.service;


import com.project.joinus.dto.MemberDto;
import com.project.joinus.entity.MemberEntity;
import com.project.joinus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    /*
    회원등록
     */
    public void creatMember(){
        MemberEntity memberEntity = new MemberEntity();
        memberRepository.save(memberEntity);
        System.out.println("멤버 등록");
    }



}
