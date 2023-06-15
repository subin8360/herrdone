package com.example.herrdone.controller;

import com.example.herrdone.DTO.Request.MemberSaveReq;
import com.example.herrdone.entity.Member;
import com.example.herrdone.exception.BusinessException;
import com.example.herrdone.exception.ErrorCode;
import com.example.herrdone.repository.MemberRepository;
import com.example.herrdone.service.MemberService;
import com.example.herrdone.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

//    private final MemberService memberService;

    public final MemberRepository memberRepository;

    @GetMapping("/all")
    public CommonResponse getAllMembers(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        CommonResponse result = null;

        try {
            result = new CommonResponse<>("200" , "OK" , memberRepository.findAll(pageable).map(member -> member.toResDto()));
        }
        catch (Exception e) {
            result = new CommonResponse<>("503", "Database Connection Error" , null);
        }

        return result;
    }

    @PostMapping
    public void postNewMember(MemberSaveReq memberSaveReq) {
    }



}
