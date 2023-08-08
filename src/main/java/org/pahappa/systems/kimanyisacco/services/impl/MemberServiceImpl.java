package org.pahappa.systems.kimanyisacco.services.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.kimanyisacco.constants.MemberStatus;
import org.pahappa.systems.kimanyisacco.dao.MemberDAO;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.MemberService;

import java.util.List;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MemberServiceImpl implements MemberService {
    private List<Member> memberList;
    public MemberServiceImpl(){}
    @Override
    public void createMember(Member member){
        // Hash the password before storing it in the database
        String hashedPassword = hashPassword(member.getPassword());
        member.setPassword(hashedPassword);
        member.setMemberStatus(MemberStatus.PENDING);
        MemberDAO.save(member);
    }
    @Override
    public List<Member> getMembers(){
        memberList = MemberDAO.getAllMembers();
        return memberList;
    }
    @Override
    public void updateMember(Member member){
            MemberDAO.update(member);
    }

    @Override
    public Member getMemberById(long memberId){
        return MemberDAO.getMemberById(memberId);
    }

    @Override
    public void sendVerificationEmail(String recipientEmail ,String firstName) {
        // Configure the email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");


        // Set up the session with the authentication details
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("allanlubangtest@gmail.com", "ewubtiyfrscyslpl");
            }
        });

        try {
            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("allanlubangtest@gmail.com","Kimwanyi Sacco"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Membership Verification");
            message.setText("Subject: Account Verification Successful - Complete Your Registration!\n\n"
                    + "Dear " + firstName + ",\n\n"
                    + "Great news! Your account verification is complete, and you can now enjoy full access to our platform's features.\n\n"
                    + "Complete your registration by following these simple steps:\n\n"
                    + "1. Login:\n"
                    + "2. Register Bank Details:\n"
                    + "3. Start Transacting:\n\n"
                    + "Should you have any questions, our support team is ready to assist you 24/7. Reach us at kiwanya@help.org or call 0785106906.\n\n"
                    + "Welcome aboard, and thank you for choosing Kiwanyi Sacco. We're here to make banking effortless for you!\n\n"
                    + "Best regards,\n\n"
                    + "Kimwanyi Sacco\n"
                    + "Customer Support Team");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void sendRejectEmail(String recipientEmail ,String firstName) {
        // Configure the email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");


        // Set up the session with the authentication details
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("allanlubangtest@gmail.com", "ewubtiyfrscyslpl");
            }
        });

        try {
            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("allanlubangtest@gmail.com","Kimwanyi Sacco"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Membership Verification");
            message.setText("Subject: Verification Request Not Approved\n\n" +
                    "Dear " + firstName + ",\n\n" +
                    "We regret to inform you that your recent verification request has not been approved.\n\n" +
                    "Upon careful review of the information you've provided, we regret to inform you that your current qualifications do not meet the requirements for SACCO membership at this time.\n\n" +
                    "If you believe there has been an error or if you would like to provide additional information, please feel free to contact our support team. They are available 24/7 and can be reached at kiwanya@help.org or by calling 0785106906.\n\n" +
                    "We appreciate your understanding and patience in this matter.\n\n" +
                    "Best regards,\n\n" +
                    "Kiwanyi Sacco.\n" +
                    "Customer Support Team");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMember(Member member){
        MemberDAO.remove(member);
    }
    private String hashPassword(String password) {
        // Use a strong hashing algorithm
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
