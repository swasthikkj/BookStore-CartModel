package com.bridgelabz.bookstorecartmodel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstorecartmodel.model.CartModel;
@Repository
public interface CartRepository extends JpaRepository<CartModel, Long> {

	List<CartModel> findByUserId(Long userId);

}
