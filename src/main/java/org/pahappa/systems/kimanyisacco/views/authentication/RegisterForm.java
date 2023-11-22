package org.pahappa.systems.kimanyisacco.views.authentication;

import org.pahappa.systems.kimanyisacco.constants.Gender;
import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.impl.MemberServiceImpl;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "registerForm")
@SessionScoped
public class RegisterForm {
    private Member member;
    private Gender gender;
    private MemberService memberService;
    private List<Member> memberList;
    private Member rejectedMember;

    public RegisterForm(){
        this.member =new Member();
        this.memberService = new MemberServiceImpl();
    }
    public void doRegister() throws Exception {
        memberList = memberService.getMembers();
        for(Member selectedMember : memberList){
            if(selectedMember.getEmail().equals(this.member.getEmail())){
                rejectedMember = selectedMember;
                break;
            }
        }
        if(rejectedMember == null){
            String baseUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(baseUrl + Hyperlinks.login);
            System.out.println(member);
            memberService.createMember(member);
        }else{
            rejectedMember = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existing Email.", "Please use another email."));

        }
    }

    public Date getMaxSelectableDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate maxDate = currentDate.minusYears(18);
        return Date.from(maxDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Gender[] getGender() {
        return Gender.values();
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public Member getRejectedMember() {
        return rejectedMember;
    }

    public void setRejectedMember(Member rejectedMember) {
        this.rejectedMember = rejectedMember;
    }
}
