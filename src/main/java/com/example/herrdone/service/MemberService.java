package com.example.herrdone.service;

import com.example.herrdone.DTO.Request.MemberFindReq;
import com.example.herrdone.DTO.Request.MemberSaveReq;
import com.example.herrdone.DTO.Response.MemberRes;
import com.example.herrdone.entity.Member;
import com.example.herrdone.exception.BusinessException;
import com.example.herrdone.exception.ErrorCode;
import com.example.herrdone.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberRes findMember (MemberFindReq memberFindReq, String email, String memberType){
        if(memberFindReq.type().equals("search") && memberType.equals(Member.MemberType.ADMIN.getType())){
            return memberRepository
                    .findByEmail(memberFindReq.email())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CANNOT_FIND_USER))
                    .toResDto();
        } else if(!memberFindReq.type().equals("search")){
            return memberRepository
                    .findByEmail(email)
                    .orElseThrow()
                    .toResDto();
        } else throw new BusinessException(ErrorCode.NO_PARAM);
    }

    @Transactional
    public MemberRes saveMember (MemberSaveReq memberSaveReq) {
        if(memberRepository.existsMemberByEmail(memberSaveReq.email()) && (memberSaveReq.member_type() == '0') ){
            throw new BusinessException(ErrorCode.DUPLICATED_DATA);
        }
        if (memberSaveReq.member_type() == '1') {
            throw new BusinessException(ErrorCode.NO_ACCESS);
        }
        return memberRepository.save(
                memberSaveReq.toEntity(
                        passwordEncoder.encode(
                                memberSaveReq.password()
                        )
                )
        ).toResDto();
    }

    @Transactional
    public MemberRes updateMember(MemberSaveReq memberSaveReq){
        Optional<Member> member = memberRepository.findByEmail(memberSaveReq.email());
        if(member == null){
            throw new BusinessException(ErrorCode.CANNOT_FIND_USER);
        }
        member.get().updateMember(
                memberSaveReq.membername(),
                memberSaveReq.password(),
                memberSaveReq.gender(),
                memberSaveReq.member_type()
        );
        return memberRepository.save(member.get()).toResDto();
    }

    @Transactional
    public Long deleteMember(MemberFindReq memberFindReq){
        System.out.println(memberFindReq.email());
        Optional<Member> member = memberRepository.findByEmail(memberFindReq.email());
        if (member == null){
            throw new BusinessException(ErrorCode.CANNOT_FIND_USER);
        }
        return memberRepository.deleteMemberByEmail(memberFindReq.email());
    }
}
