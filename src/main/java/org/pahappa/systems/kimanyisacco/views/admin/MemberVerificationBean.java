package org.pahappa.systems.kimanyisacco.views.admin;

import org.pahappa.systems.kimanyisacco.constants.MemberStatus;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.impl.MemberServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "memberVerification")
@SessionScoped
public class MemberVerificationBean {
    private Member member;
    private MemberService memberService;
    private List<Member> memberList;

    public MemberVerificationBean(){
        this.member = new Member();
        this.memberService = new MemberServiceImpl();
    }

    public List<Member> displayUnverifiedMembers(){
        memberList = memberService.getMembers();
        List<Member> filteredMembers = new ArrayList<>();
        for(Member member: memberList){
            if(member.getMemberStatus().equals(MemberStatus.PENDING)){
                filteredMembers.add(member);
            }
        }
        return filteredMembers;
    }
    public void acceptMember(Member member) {
        member.setMemberStatus(MemberStatus.VERIFIED);
        memberService.updateMember(member);
        memberService.sendVerificationEmail(member.getEmail(), member.getFirstName());
    }

    public void rejectMember(Member member){
        memberService.sendRejectEmail(member.getEmail(), member.getFirstName());
        memberService.deleteMember(member);
    }

    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    public List<Member> getMemberList() {
        return memberList;
    }
    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }
}
