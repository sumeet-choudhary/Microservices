package com.address.ADDRESS.repository;

import com.address.ADDRESS.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByEmpId(Long empId);
}
