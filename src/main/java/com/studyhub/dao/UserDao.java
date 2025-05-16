package com.studyhub.dao;

import com.studyhub.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User findByEmail(String email);
    boolean existsByBusinessNumber(String businessNumber);
    void save(User user);
}