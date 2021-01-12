package com.jakeprim.dao;

import com.jakeprim.domain.User;

import java.io.IOException;
import java.util.List;

public interface UserDao {
    List<User> findAll() throws IOException;
}
