package com.example.herrdone.config;

import com.example.herrdone.DTO.Request.MemberSaveReq;
import com.example.herrdone.Entity.Member;
import com.example.herrdone.repository.MemberRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CreateDummyData {

    @Autowired
    private MemberRepository memberRepository;


    // 더미데이터가 필요한 경우에만
//    @EventListener(ApplicationReadyEvent.class)
//    private void createDummyData(){
//        List<Member> memberList = new ArrayList<>();
//        int count = 0;
//        while (count < 100){
//            memberList.add(generateMember());
//            count++;
//        }
//        System.out.println(memberList.get(0));
//        memberRepository.saveAll(memberList);
//    }
    // 더미데이터가 필요한 경우에만

    private Member generateMember () {
        Faker faker = new Faker();
        MemberSaveReq memberSaveReq = new MemberSaveReq(faker.artist().name(), faker.business().creditCardNumber(), faker.internet().safeEmailAddress(), 1, 1);
        return memberSaveReq.toEntity();
    }
}
