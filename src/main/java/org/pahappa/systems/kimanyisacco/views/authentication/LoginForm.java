package org.pahappa.systems.kimanyisacco.views.authentication;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.kimanyisacco.controllers.Hyperlinks;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.impl.MemberServiceImpl;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.List;

@ManagedBean(name = "loginForm")
@SessionScoped
public class LoginForm {
    private Member member;
    private List<Member> members ;
    private MemberService memberService;
    public LoginForm(){
        this.member = new Member();
        this.memberService = new MemberServiceImpl();
    }
    private Member acceptedMember;
    public void doLogin() throws Exception {
        members = memberService.getMembers();
        for (Member selectedMember : members) {
            if (BCrypt.checkpw(this.member.getPassword(), selectedMember.getPassword( )) && this.member.getEmail().trim().equals(selectedMember.getEmail())) {
                acceptedMember = selectedMember;
                break;
            }
        }
        if (acceptedMember != null) {
            String baseUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("member", acceptedMember.getMemberId());
            FacesContext.getCurrentInstance().getExternalContext().redirect(baseUrl + Hyperlinks.dashbord);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials.", "Please try again."));
        }
        System.out.println(acceptedMember);
    }
    public void doAdminLogin() throws Exception {
        String baseUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if(this.member.getEmail().equals("admin@gmail.com") && this.member.getPassword().equals("1234")){
            FacesContext.getCurrentInstance().getExternalContext().redirect(baseUrl + Hyperlinks.admin);
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials.", "Please try again."));
        }
    }
    public void logout() throws Exception {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        String baseUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(baseUrl + Hyperlinks.landing);
        System.out.println(member);
    }
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }
    public List<Member> getMembers() {
        return members;
    }
    public void setMembers(List<Member> members) {
        this.members = members;
    }
    public Member getAcceptedMember() {
        return acceptedMember;
    }
    public void setAcceptedMember(Member acceptedMember) {
        this.acceptedMember = acceptedMember;
    }
}
