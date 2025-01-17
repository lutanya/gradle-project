package com.epam.learn.microcervices.resourceservice.dao;

import org.springframework.data.repository.ListCrudRepository;

import com.epam.learn.microcervices.resourceservice.dao.entity.Resource;

public interface ResourceRepository extends ListCrudRepository<Resource, Long> {

}
