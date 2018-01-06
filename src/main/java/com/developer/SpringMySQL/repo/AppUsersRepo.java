package com.developer.SpringMySQL.repo;

import org.springframework.data.repository.CrudRepository;

import com.developer.SpringMySQL.models.AppUsers;

/**
 * Created by Pukar on 6/25/2017.
 */
public interface AppUsersRepo extends CrudRepository<AppUsers,Integer>{
}
