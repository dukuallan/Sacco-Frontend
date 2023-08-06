package org.pahappa.systems.kimanyisacco.views.admin;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.MemberService;
import org.pahappa.systems.kimanyisacco.services.impl.MemberServiceImpl;
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
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "admin")
@RequestScoped
public class Admin implements Serializable {
    private LineChartModel lineModel;
    private DonutChartModel donutModel;
    private MemberService memberService;
    private Account account;
    private Transaction transaction;

    public Admin(){
        this.account = new Account();
        this.memberService = new MemberServiceImpl();
        this.transaction = new Transaction();
    }
    @PostConstruct
    public void init() {
        createDonutModel();
        createLineModel();
    }
    public void createDonutModel() {
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();
        DonutChartOptions options = new DonutChartOptions();
        options.setAnimateRotate(false);
        donutModel.setOptions(options);

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Transaction> transactions = memberService.getTransaction();
        List<Number> values = new ArrayList<>();
        double depositTransactions = 0.0;
        double withdrawTransactions = 0.0;

        for(Transaction transactionSelected : transactions){
            if(transactionSelected.getType().contains("Deposit")){
                depositTransactions += transactionSelected.getAmount();
            } else if (transactionSelected.getType().contains("Withdraw")) {
                withdrawTransactions += transactionSelected.getAmount();
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
    public void createLineModel() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();

        // Get the HttpSession object from the current FacesContext
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        // Check if the session exists
        if (session != null) {
            // Retrieve the currentAccountId from the session attributes
            Long currentAccountId = (Long) session.getAttribute("account");

            // Check if the currentAccountId exists in the session
            if (currentAccountId != null) {
                List<Transaction> transactions = memberService.getTransaction();
                List<Transaction> depositTransactions = new ArrayList<>();
                List<Transaction> withdrawTransactions = new ArrayList<>();

                // Separate transactions based on type (Deposit or Withdraw)
                for (Transaction selectedTransaction : transactions) {
                    if ("Deposit".equals(selectedTransaction.getType()) && selectedTransaction.getAccount().getAccountId() == currentAccountId) {
                        depositTransactions.add(selectedTransaction);
                    } else if ("Withdraw".equals(selectedTransaction.getType()) && selectedTransaction.getAccount().getAccountId() == currentAccountId && selectedTransaction.getWithdrawStatus().contains("APPROVED")) {
                        withdrawTransactions.add(selectedTransaction);
                    }
                }

                // Labels for Withdrawal transactions
                List<String> withdrawLabels = new ArrayList<>();
                int withdrawTransactionCount = 1;
                for (Transaction trans : withdrawTransactions) {
                    withdrawLabels.add("Transaction " + withdrawTransactionCount++); // Displaying initial labels like "Transaction 1," "Transaction 2," etc.
                }

                // Combined labels for both Deposit and Withdraw transactions
                List<String> combinedLabels = new ArrayList<>();
                combinedLabels.addAll(withdrawLabels);

                // Dataset for Deposits
                LineChartDataSet depositDataSet = createDataSetForType(depositTransactions, "Deposit", "rgb(75, 192, 192)");
                data.addChartDataSet(depositDataSet);

                // Dataset for Withdrawals
                LineChartDataSet withdrawDataSet = createDataSetForType(withdrawTransactions, "Withdraw", "rgb(192, 75, 75)");
                data.addChartDataSet(withdrawDataSet);

                data.setLabels(combinedLabels);

                // Options
                LineChartOptions options = new LineChartOptions();
                Title title = new Title();
                title.setDisplay(true);
                title.setText("Line Chart - Deposit and Withdraw Transactions");
                options.setTitle(title);
                lineModel.setOptions(options);
                lineModel.setData(data);
            }
        }
    }
    private LineChartDataSet createDataSetForType(List<Transaction> transactions, String type, String borderColor) {
        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();

        for (Transaction selectedTransaction : transactions) {
            values.add(selectedTransaction.getAmount());
        }

        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel(type + " Amounts");
        dataSet.setBorderColor(borderColor);
        dataSet.setTension(0.1);
        return dataSet;
    }
    public LineChartModel getLineModel() {
        return lineModel;
    }
    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }
    public MemberService getMemberService() {
        return memberService;
    }
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public Transaction getTransactions() {
        return transaction;
    }
    public void setTransactions(Transaction transaction) {
        this.transaction = transaction;
    }
    public DonutChartModel getDonutModel() {
        return donutModel;
    }
    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }
}
