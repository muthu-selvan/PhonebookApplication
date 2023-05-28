package com.phonebook.dao;

import com.phonebook.modal.Phonebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhonebookRepository extends JpaRepository<Phonebook, Long> {

}
