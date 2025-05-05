package com.example.library.service.interfaces;

import java.util.List;
import com.example.library.model.Member;
public interface MemberService {
    
    List<Member> getAllMembers();
Member getMemberById(Long id);
Member createMember(Member member);
Member updateMember(Long id, Member member);
void deleteMember(Long id);
}
