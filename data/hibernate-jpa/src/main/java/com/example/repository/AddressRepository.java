package com.example.repository;

import com.example.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 李磊
 * @datetime 2020/2/10 16:34
 * @description
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}