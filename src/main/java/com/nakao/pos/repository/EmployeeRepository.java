package com.nakao.pos.repository;

import com.nakao.pos.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Naoki Nakao on 7/23/2023
 * @project POS
 */

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String >,
        PagingAndSortingRepository<Employee, String> {
}
