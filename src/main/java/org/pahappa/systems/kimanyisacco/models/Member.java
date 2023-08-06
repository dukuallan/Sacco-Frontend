package org.pahappa.systems.kimanyisacco.models;

import org.pahappa.systems.kimanyisacco.constants.Gender;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "members")
public class Member {
    private long memberId;
    private String firstName;
    private String lastName;
    private String password;
    private Date dateOfBirth;
    private String email;
    private String location;
    private Gender gender;
    private boolean status;
    private String nextName;
    private String contact;
    private Account account;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", nullable = false)
    public long getMemberId() {
        return memberId;
    }
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    @Column(name = "first_name", nullable = false, length = 50)
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false, length = 50)
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "password", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "date_of_birth", nullable = false, length = 50)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "email", nullable = false, length = 50)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "location", nullable = false, length = 50)
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender", nullable = false, length = 50)
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Column(name = "status" ,columnDefinition = "boolean default false")
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column(name = "kin_name", nullable = false, length = 50)
    public String getNextName() {
        return nextName;
    }
    public void setNextName(String nextName) {
        this.nextName = nextName;
    }

    @Column(name = "kin_contact", nullable = false, length = 50)
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", gender='" + gender + '\'' +
                ", status=" + status +
                ", account=" + account +
                '}';
    }
}