package net.vokhmin.testtask.bss.dao;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface for defining a custom behaviour to all repositories.
 * http://static.springsource.org/spring-data/data-jpa/docs/current/reference/html/#repositories.custom-behaviour-for-all-repositories
 * 
 * @author a.vokhmin
 *
 * @param <T> entity type.
 * @param <ID> type of identifier of entity (primary key).
 */
@NoRepositoryBean
public interface BaseRepository <T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

}
