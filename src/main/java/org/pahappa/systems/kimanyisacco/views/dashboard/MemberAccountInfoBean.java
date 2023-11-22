package org.pahappa.systems.kimanyisacco.views.dashboard;

import org.pahappa.systems.kimanyisacco.constants.BankName;
import org.pahappa.systems.kimanyisacco.constants.MemberStatus;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.AccountService;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.impl.AccountServiceImpl;
import org.pahappa.systems.kimanyisacco.services.impl.MemberServiceImpl;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "memberAccountInfo")
@SessionScoped
public class MemberAccountInfoBean {
    private Member member;
    private MemberService memberService;
    private BankName bankName;
    private Account account;
    private AccountService accountService;
    public MemberAccountInfoBean() {
        this.member = new Member();
        this.account = new Account();
        this.memberService = new MemberServiceImpl();
        this.accountService = new AccountServiceImpl();
    }

    public void registerAccount() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        if (session != null) {
            Long currentMemberId = (Long) session.getAttribute("member");

            if (currentMemberId != null) {
                Member currentMember = memberService.getMemberById(currentMemberId);

                if (currentMember != null && currentMember.getMemberStatus().equals(MemberStatus.VERIFIED)) {
                    Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
                    if(currentAccount == null){
                        account.setMember(currentMember);
                        accountService.createAccount(account);

                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account Created", "You can now deposit and withdraw"));
                    }else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Allowed", "You already have an account."));
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not Verified", "Please wait for Verification"));
                }
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Session Error", "Session is not initialized."));
        }
    }

    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public BankName[] getBankName() {
        return BankName.values();
    }
    public void setBankName(BankName bankName) {
        this.bankName = bankName;
    }
}
