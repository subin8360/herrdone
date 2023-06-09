package com.example.herrdone.controller;

import com.example.herrdone.DTO.Request.MemberFindReq;
import com.example.herrdone.DTO.Request.MemberSaveReq;
import com.example.herrdone.config.security.annotation.NeedAdmin;
import com.example.herrdone.config.security.annotation.NeedLogin;
import com.example.herrdone.exception.BusinessException;
import com.example.herrdone.exception.ErrorCode;
import com.example.herrdone.repository.MemberRepository;
import com.example.herrdone.service.MemberService;
import com.example.herrdone.util.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public final MemberRepository memberRepository;

    @GetMapping("/all")
    @NeedAdmin
    public Object getAllMembers(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        try {
            return new CommonResponse<>(HttpStatus.OK, "멤버 목록을 불러왔습니다.",
                    memberRepository.findAll(pageable).map(member -> member.toResDto()));
        } catch (Exception e) {
            return ErrorCode.DB_CONNECTION_REFUSED;
        }
    }

    @GetMapping
    @NeedLogin
    public Object getOneMember(MemberFindReq memberFindReq, HttpServletRequest req){ // aop에서 할당해준 req.setAttribute("email") 값을 받아오기 위해 파라미터 추가, 커스텀 어노테이션으로 숨길 예정
        String email = (String) req.getAttribute("email");
        String memberType = (String) req.getAttribute("memberType");
        try {
            return new CommonResponse<>(HttpStatus.OK, "해당 멤버 정보를 불러왔습니다.",
                    memberService.findMember(memberFindReq, email, memberType));
        } catch (BusinessException e){
            return e.getErrorCode();
        }
    }

    @PostMapping
    public Object postNewMember(@RequestBody MemberSaveReq memberSaveReq) {
        try {
            return new CommonResponse<>(HttpStatus.CREATED, "멤버 생성이 완료되었습니다.",
                    memberService.saveMember(memberSaveReq));
        } catch (BusinessException e){
            return e.getErrorCode();
        }
    }

    @PutMapping
    @NeedLogin
    public Object updateMember(@RequestBody MemberSaveReq memberSaveReq) {
        try {
            return new CommonResponse<>(HttpStatus.OK, "회원 정보 수정이 완료되었습니다.",
                    memberService.updateMember(memberSaveReq));
        } catch (BusinessException e){
            return e.getErrorCode();
        }
    }

    @DeleteMapping("/delete")
    @NeedLogin
    public Object deleteMember(@RequestBody MemberFindReq memberFindReq) {
        try {
            return new CommonResponse<>(HttpStatus.NO_CONTENT, "회원 삭제가 완료되었습니다.",
                    memberService.deleteMember(memberFindReq));
        } catch (Exception e){
            return ErrorCode.CANNOT_FIND_USER;
        }
    }


}
