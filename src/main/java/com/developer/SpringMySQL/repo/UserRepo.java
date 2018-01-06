package com.developer.SpringMySQL.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.developer.SpringMySQL.models.User;

public interface UserRepo extends PagingAndSortingRepository<User, Long>{

}
