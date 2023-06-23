package com.koral.unittest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.koral.unittest.model.Employee;

// it will only load the repository layer components
@DataJpaTest // provide in-memory embedded database for tests
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	// BDD style naming convention for Unit test case
	// given..._when..._then...()
	
	private Employee employee;
	
	
	// Her bir test için Test'ten önce çalıştırılacak ortak kod. 
	@BeforeEach
	public void setup() {
		
		employee = Employee.builder()
					.firstName("Ali")
					.lastName("karaca")
					.email("alik@gmail.com")
					.build();
	}
	
	
	
	@DisplayName("JUnit test for save employee operation")
	@Test
	public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
		
		// given - precondition or setup
//		Employee employee = Employee.builder()
//				.firstName("Ali")
//				.lastName("karaca")
//				.email("alik@gmail.com")
//				.build();
		
		// when - action or the behaviour that we are going to test
		Employee savedEmployee = employeeRepository.save(employee);
		
		
		// then - verify the output
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0);
	}
	
	@DisplayName("JUnit test for get all employees operation")
	@Test
	public void givenEmployeesList_whenFindAll_thenEmployeesList() {
		
		// given - precondition or setup
		
//		Employee employee = Employee.builder()
//				.firstName("Ali")
//				.lastName("karaca")
//				.email("alik@gmail.com")
//				.build();
		
		Employee employee1 = Employee.builder()
				.firstName("John")
				.lastName("Cena")
				.email("cena@gmail.com")
				.build();
		
		employeeRepository.saveAll(List.of(employee, employee1));
		
		
		// when - action or the behaviour that we are going to test
		
		List<Employee> employeeList = employeeRepository.findAll();
		
		
		// then - verify the output
		
		assertThat(employeeList).isNotNull();
		assertThat(employeeList.size()).isEqualTo(2);
		
	}
	
	
	@DisplayName("JUnit test for get employee by id  operation")
	@Test
	public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

		// given - precondition or setup

//		Employee employee = Employee.builder()
//				.firstName("Ali")
//				.lastName("karaca")
//				.email("alik@gmail.com")
//				.build();
//		
		employeeRepository.save(employee);
		
		// when - action or the behaviour that we are going to test
		
		Employee employeeDB = employeeRepository.findById(employee.getId()).get();
		
		
		// then - verify the output
		
		assertThat(employeeDB).isNotNull();

	}
	
	
	@DisplayName("JUnit test for get employee by email operation")
	@Test
	public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

		// given - precondition or setup
//		Employee employee = Employee.builder()
//				.firstName("yavuz")
//				.lastName("uzun")
//				.email("yvzu@gmail.com")
//				.build();
		
		employeeRepository.save(employee);
		
		// when - action or the behaviour that we are going to test
		Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

		// then - verify the output
		assertThat(employeeDB).isNotNull();
	}
	
	
	@DisplayName("JUnit test for update employee operation")
	@Test
	public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

		// given - precondition or setup
		
//		Employee employee = Employee.builder()
//				.firstName("Ramesh")
//				.lastName("Fardash")
//				.email("rf@gmail.com")
//				.build();
		
		employeeRepository.save(employee);

		// when - action or the behaviour that we are going to test
		
		Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
		savedEmployee.setEmail("ram@gmail.com");
		savedEmployee.setFirstName("ramedsh");
		Employee updatedEmployee = employeeRepository.save(savedEmployee);
		
		// then - verify the output
		assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
		assertThat(updatedEmployee.getFirstName()).isEqualTo("ramedsh");

	}
	
	
	@DisplayName("JUnit for delete employee operation")
	@Test
	public void givenEmplyeeObject_whenDelete_thenRemoveEmployee() {

		// given - precondition or setup
//		Employee employee = Employee.builder()
//				.firstName("Ramesh")
//				.lastName("Fardash")
//				.email("rf@gmail.com")
//				.build();
		
		employeeRepository.save(employee);

		// when - action or the behaviour that we are going to test
		employeeRepository.delete(employee);
		Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
		
		
		// then - verify the output
		assertThat(employeeOptional).isEmpty(); 
	}
	
	
	@DisplayName("JUnit test for custom query using JPQL with index params")
	@Test
	public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {

		// given - precondition or setup
//		Employee employee = Employee.builder()
//				.firstName("Ramesh")
//				.lastName("Fardash")
//				.email("rf@gmail.com")
//				.build();
		
		employeeRepository.save(employee);
		
		// when - action or the behaviour that we are going to test
		Employee savedEmployee = employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());
		
		// then - verify the output
		assertThat(savedEmployee).isNotNull();

	}
	
	@DisplayName("JUnit test for custom query using JPQL with named params")
	@Test
	public void  givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
		// given - precondition or setup
//		Employee employee = Employee.builder()
//				.firstName("Ramesh")
//				.lastName("Fardash")
//				.email("rf@gmail.com")
//				.build();
//		
		employeeRepository.save(employee);
		
		// when - action or the behaviour that we are going to test
		Employee savedEmployee = employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());
		
		// then - verify the output
		assertThat(savedEmployee).isNotNull();

	}
	
	
	@DisplayName("JUnit test for custom query using native SQL with index")
	@Test
	public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {

		// given - precondition or setup
//		Employee employee = Employee.builder()
//							.firstName("fettah")
//							.lastName("koral")
//							.email("random@gmail.com")
//							.build();
		
		employeeRepository.save(employee);
		
		// when - action or the behaviour that we are going to test
		Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());
		
		// then - verify the output
		assertThat(savedEmployee).isNotNull();
	}
	
	@DisplayName("JUnit test for custom query using native SQL with named params")
	@Test
	public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {

		// given - precondition or setup
//		Employee employee = Employee.builder()
//							.firstName("fettah")
//							.lastName("koral")
//							.email("random@gmail.com")
//							.build();
		
		employeeRepository.save(employee);
		
		// when - action or the behaviour that we are going to test
		Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());
		
		// then - verify the output
		assertThat(savedEmployee).isNotNull();
	}
}











