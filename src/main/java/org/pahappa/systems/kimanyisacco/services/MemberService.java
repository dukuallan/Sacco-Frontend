package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;

import java.util.List;

public interface MemberService {

    void createMember(Member member);
    List<Member> getMembers();

    void updateMember(Member member);
    Member getMemberById(long memberId);

    void deleteMember(Member member);
    void sendVerificationEmail(String recipientEmail, String firstName);
    void sendRejectEmail(String recipientEmail ,String firstName);


}
