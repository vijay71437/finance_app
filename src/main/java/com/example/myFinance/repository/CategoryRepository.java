package com.example.myFinance.repository;


import com.example.myFinance.entity.Category;
import com.example.myFinance.entity.User;
import com.example.myFinance.entity.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser(User user);

    List<Category> findByUserAndType(User user, CategoryType type);

    boolean existsByUserAndNameIgnoreCase(User user, String name);
}
