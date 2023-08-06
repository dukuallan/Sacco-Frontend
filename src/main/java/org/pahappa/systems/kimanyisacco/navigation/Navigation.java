package org.pahappa.systems.kimanyisacco.navigation;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Contains the links to the different pages with in the application.
 * It is to help us navigate between the pages in the application easily.
 */
@ManagedBean(name = "navigation")
@ApplicationScoped //There should be only one instance of the class created for the entire application
public class Navigation {

    private final String dashboard = "/pages/dashboard/MemberDashboard.xhtml";
    private final String login = "/pages/login/Login.xhtml";

    private final String statistics = "/pages/admin/Statistics.xhtml";

    private final String admin = "/pages/admin/MemberVerification.xhtml";

    private final String register = "/pages/login/register.xhtml";

    private final String landing = "/pages/landing/Landing.xhtml";

    private final String accountInfo = "/pages/dashboard/MemberAccountInfo.xhtml";

    private final String transaction = "/pages/dashboard/MemberTransaction.xhtml";
    private final String recentStat= "/pages/dashboard/MemberTransactionRecords.xhtml";
    private final String approval= "/pages/admin/WithdrawApproval.xhtml";
    private final String approvalList= "/pages/admin/WithdrawApprovalRecords.xhtml";
    private final String adminLogin = "/pages/login/AdminLogin.xhtml";

    public String getDashboard() {
        return dashboard;
    }

    public String getLanding() {
        return landing;
    }

    public String getLogin() {
        return login;
    }

    public String getRegister() {
        return register;
    }

    public String getStatistics() {
        return statistics;
    }

    public String getAdmin() {
        return admin;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getRecentStat() {
        return recentStat;
    }

    public String getApproval() {
        return approval;
    }

    public String getApprovalList() {
        return approvalList;
    }

    public String getAdminLogin() {
        return adminLogin;
    }
}
