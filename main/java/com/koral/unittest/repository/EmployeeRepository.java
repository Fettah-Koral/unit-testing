package com.koral.unittest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.koral.unittest.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findByEmail(String email);
	
	@Query("SELECT e FROM Employee e WHERE e.firstName=?1 AND e.lastName=?2")
	Employee findByJPQL(String firstName, String lastName);
	
	@Query("SELECT e FROM Employee e WHERE e.firstName= :fname AND e.lastName= :lname")
	Employee findByJPQLNamedParams(@Param("fname") String firstName, @Param("lname") String lastName); 

	@Query(value="SELECT * FROM employees e where e.first_name = ?1 AND e.last_name= ?2"
															,nativeQuery = true)
	Employee findByNativeSQL(String firstName, String lastName);
	
	@Query(value="SELECT * FROM employees e where e.first_name = :fname AND e.last_name= :lname"
																	,nativeQuery = true)
	Employee findByNativeSQLNamed(@Param("fname") String firstName, @Param("lname") String lastName);
	
}
