package com.example.herrdone.DTO.Request;

import com.example.herrdone.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

public record MemberSaveReq(
        String membername,
        String password,
        String email,
        int member_type, // 0 - USER, 1 - ADMIN
        int gender // 0 - MALE, 1 - FEMALE, 2 - UNKNOWN
){
    public Member toEntity(String encodedPassword){
        return Member.builder()
                .membername(membername)
                .password(encodedPassword)
                .email(email)
                .member_type(member_type)
                .gender(gender)
                .build();
    }
}

