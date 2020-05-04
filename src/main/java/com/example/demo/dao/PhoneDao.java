package com.example.demo.dao;

import com.example.demo.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhoneDao extends JpaRepository<Phone, UUID> {

    Phone findByPhoneNumber(String phoneNumber);

}
