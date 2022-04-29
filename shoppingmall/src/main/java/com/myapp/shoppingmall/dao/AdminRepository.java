package com.myapp.shoppingmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.shoppingmall.entities.admin;

public interface AdminRepository extends JpaRepository<admin, Integer> {

	admin findByUsername(String username);

}
