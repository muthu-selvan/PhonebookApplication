package com.phonebook.modal;

import com.phonebook.parser.CSVCell;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "PHONE_BOOK")
public class Phonebook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @CSVCell(label = "First Name")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @CSVCell(label = "Last Name")
    @Column(name = "LAST_NAME")
    private String lastName;

    @CSVCell(label = "Phone Number")
    @Column(name = "PHONE_NUMBER")
    private String phoneNo;

    public String getFirstName() {
        return firstName.strip();
    }

    public String getLastName() {
        return lastName.strip();
    }

    public String getPhoneNo() {
        return phoneNo.strip().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
    }

    public boolean isEmpty() {
        return Objects.isNull(firstName) || Objects.isNull(lastName) || Objects.isNull(phoneNo);
    }
}
