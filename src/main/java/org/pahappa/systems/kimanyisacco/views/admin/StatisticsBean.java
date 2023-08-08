package org.pahappa.systems.kimanyisacco.views.admin;

import org.pahappa.systems.kimanyisacco.constants.MemberStatus;
import org.pahappa.systems.kimanyisacco.constants.TransactionStatus;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.AccountService;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.TransactionService;
import org.pahappa.systems.kimanyisacco.services.impl.AccountServiceImpl;
import org.pahappa.systems.kimanyisacco.services.impl.MemberServiceImpl;
import org.pahappa.systems.kimanyisacco.services.impl.TransactionServiceImpl;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "statisticsBean")
@SessionScoped
public class StatisticsBean {
    private Member member;
    private MemberService memberService;
    private TransactionService transactionService;
    private AccountService accountService;
    private List<Member> members;
    private Transaction transaction;
    private List<Transaction> transactionList;
    private LineChartModel lineModel;
    private DonutChartModel donutModel;
    private Account account;

    public StatisticsBean(){
        this.member = new Member();
        this.memberService = new MemberServiceImpl();
        this.transactionService = new TransactionServiceImpl();
        this.accountService = new AccountServiceImpl();
        this.transaction = new Transaction();

    }

    @PostConstruct
    public void init() {
        createDonutModel();
//        createLineModel();
    }

    public int getTotalVerifiedMembers(){
        members = memberService.getMembers();
        int totalMembers = 0;
        for(Member member1 : members){
            if(member1.getMemberStatus().equals(MemberStatus.VERIFIED)){
                totalMembers++;
            }
        }
        return  totalMembers;
    }

    public int getTotalUnverifiedMembers(){
        members = memberService.getMembers();
        int totalMembers = 0;
        for(Member member1 : members){
            if(member1.getMemberStatus().equals(MemberStatus.PENDING)){
                totalMembers++;
            }
        }
        return  totalMembers;
    }
    public double getTotalDeposit(){
        transactionList = transactionService.getTransaction();
        double totalDeposit = 0.0;
        for(Transaction trans : transactionList){
            if(trans.getTransactionType().contains("Deposit")){
                totalDeposit += trans.getTransactionAmount();
            }
        }
        return totalDeposit;
    }

    public double getTotalWithdraw(){
        transactionList = transactionService.getTransaction();
        double totalWithdraw = 0.0;
        for(Transaction trans : transactionList){
            if(trans.getTransactionType().contains("Withdraw")){
                totalWithdraw += trans.getTransactionAmount();
            }
        }
        return totalWithdraw;
    }

    public int getTotalDepositCount() {
        transactionList = transactionService.getTransaction();
        int depositCount = 0;
        for (Transaction trans : transactionList) {
            if (trans.getTransactionType().contains("Deposit")) {
                depositCount++;
            }
        }
        return depositCount;
    }
    public int getTotalWithdrawCount() {
        transactionList = transactionService.getTransaction();
        int withdrawCount = 0;
        for (Transaction trans : transactionList) {
            if (trans.getTransactionType().contains("Withdraw")) {
                withdrawCount++;
            }
        }
        return withdrawCount;

    }
    public void createDonutModel() {
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();
        DonutChartOptions options = new DonutChartOptions();
        options.setAnimateRotate(false);
        donutModel.setOptions(options);

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Transaction> transactions = transactionService.getTransaction();
        List<Number> values = new ArrayList<>();
        double depositTransactions = 0.0;
        double withdrawTransactions = 0.0;

        for(Transaction transactionSelected : transactions){
            if(transactionSelected.getTransactionType().contains("Deposit")){
                depositTransactions += transactionSelected.getTransactionAmount();
            } else if (transactionSelected.getTransactionType().contains("Withdraw")) {
                withdrawTransactions += transactionSelected.getTransactionAmount();
            }
        }
        values.add(depositTransactions);
        values.add(withdrawTransactions);
        dataSet.setData(values);


        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(33, 150, 243)");
        bgColors.add("rgb(97, 84, 177)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Deposit");
        labels.add("Withdraw");
        data.setLabels(labels);

        donutModel.setData(data);
    }
//    public void createLineModel() {
//        lineModel = new LineChartModel();
//        ChartData data = new ChartData();
//
//        // Get the HttpSession object from the current FacesContext
//        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//
//        // Check if the session exists
//        if (session != null) {
//            // Retrieve the currentAccountId from the session attributes
//            Long currentMemberId = (Long) session.getAttribute("member");
//            Account currentAccount = accountService.getAccountByMemberId(currentMemberId);
//            Long currentAccountId = currentAccount.getAccountId();
//            // Check if the currentAccountId exists in the session
//            if (currentAccountId != null) {
//                List<Transaction> transactions = transactionService.getTransaction();
//                List<Transaction> depositTransactions = new ArrayList<>();
//                List<Transaction> withdrawTransactions = new ArrayList<>();
//
//                // Separate transactions based on type (Deposit or Withdraw)
//                for (Transaction selectedTransaction : transactions) {
//                    if ("Deposit".equals(selectedTransaction.getTransactionType()) && selectedTransaction.getAccount().getAccountId() == currentAccountId) {
//                        depositTransactions.add(selectedTransaction);
//                    } else if ("Withdraw".equals(selectedTransaction.getTransactionType()) && selectedTransaction.getAccount().getAccountId() == currentAccountId && selectedTransaction.getTransactionStatus().equals(TransactionStatus.APPROVED)) {
//                        withdrawTransactions.add(selectedTransaction);
//                    }
//                }
//
//                // Labels for Withdrawal transactions
//                List<String> withdrawLabels = new ArrayList<>();
//                int withdrawTransactionCount = 1;
//                for (Transaction trans : withdrawTransactions) {
//                    withdrawLabels.add("Transaction " + withdrawTransactionCount++); // Displaying initial labels like "Transaction 1," "Transaction 2," etc.
//                }
//
//                // Combined labels for both Deposit and Withdraw transactions
//                List<String> combinedLabels = new ArrayList<>();
//                combinedLabels.addAll(withdrawLabels);
//
//                // Dataset for Deposits
//                LineChartDataSet depositDataSet = createDataSetForType(depositTransactions, "Deposit", "rgb(75, 192, 192)");
//                data.addChartDataSet(depositDataSet);
//
//                // Dataset for Withdrawals
//                LineChartDataSet withdrawDataSet = createDataSetForType(withdrawTransactions, "Withdraw", "rgb(192, 75, 75)");
//                data.addChartDataSet(withdrawDataSet);
//
//                data.setLabels(combinedLabels);
//
//                // Options
//                LineChartOptions options = new LineChartOptions();
//                Title title = new Title();
//                title.setDisplay(true);
//                title.setText("Line Chart - Deposit and Withdraw Transactions");
//                options.setTitle(title);
//                lineModel.setOptions(options);
//                lineModel.setData(data);
//            }
//        }
//    }
//    private LineChartDataSet createDataSetForType(List<Transaction> transactions, String type, String borderColor) {
//        LineChartDataSet dataSet = new LineChartDataSet();
//        List<Object> values = new ArrayList<>();
//
//        for (Transaction selectedTransaction : transactions) {
//            values.add(selectedTransaction.getTransactionAmount());
//        }
//
//        dataSet.setData(values);
//        dataSet.setFill(false);
//        dataSet.setLabel(type + " Amounts");
//        dataSet.setBorderColor(borderColor);
//        dataSet.setTension(0.1);
//        return dataSet;
//    }

    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    public Transaction getTransaction() {
        return transaction;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public List<Member> getMembers() {
        return members;
    }
    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }
    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }
    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
