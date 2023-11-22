package org.pahappa.systems.kimanyisacco.models;

import org.pahappa.systems.kimanyisacco.constants.Gender;
import org.pahappa.systems.kimanyisacco.constants.MemberStatus;

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
    private String memberContact;
    private String location;
    private Gender gender;
    private MemberStatus memberStatus;
    private String nextOfKinName;
    private String nextOfKinContact;

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

    @Column(name = "member_contact", nullable = false, length = 50)
    public String getMemberContact() {
        return memberContact;
    }
    public void setMemberContact(String memberContact) {
        this.memberContact = memberContact;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 50)
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "member_status", nullable = false, length = 50)
    public MemberStatus getMemberStatus() {
        return memberStatus;
    }
    public void setMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    @Column(name = "next_of_kin_name", nullable = false, length = 50)
    public String getNextOfKinName() {
        return nextOfKinName;
    }
    public void setNextOfKinName(String nextOfKinName) {
        this.nextOfKinName = nextOfKinName;
    }

    @Column(name = "next_of_kin_contact", nullable = false, length = 50)
    public String getNextOfKinContact() {
        return nextOfKinContact;
    }
    public void setNextOfKinContact(String nextOfKinContact) {
        this.nextOfKinContact = nextOfKinContact;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", memberContact='" + memberContact + '\'' +
                ", location='" + location + '\'' +
                ", gender=" + gender +
                ", memberStatus=" + memberStatus +
                ", nextOfKinName='" + nextOfKinName + '\'' +
                ", nextOfKinContact='" + nextOfKinContact + '\'' +
                '}';
    }
}