package com.ammerzon.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ammerzon.helper.EntityManagerStore;
import com.ammerzon.helper.EntityManagerStoreImpl;
import com.ammerzon.helper.TransactionHelper;
import com.ammerzon.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerDaoTests {
  private EntityManagerStore entityManagerStore;
  private TransactionHelper helper;
  private EntityManager entityManager;
  private CustomerDao customerDao;

  @BeforeEach
  public void beforeEach() {
    entityManagerStore = new EntityManagerStoreImpl();
    entityManager = entityManagerStore.createAndRegister();
    helper = new TransactionHelper(entityManager);
    customerDao = new CustomerDaoImpl(entityManager);
  }

  @AfterEach
  public void afterEach() {
    entityManager = entityManagerStore.get();
    if (entityManager != null) {
      entityManagerStore.unregister(entityManager);
    }
  }

  @Test
  public void whenNoCustomers_ThenCountIsNull() {
    assertEquals(0, customerDao.count());
  }

  @Test
  public void whenInsertingCustomer_ThenCountReturnsOne() {
    var customer = new Customer("Apple");
    helper.transactional(() -> customerDao.save(customer));
    assertEquals(1, customerDao.count());
  }

  @Test
  public void whenInsertingMultipleCustomers_ThenCountReturnsTheAmount() {
    var customer1 = new Customer("Apple");
    var customer2 = new Customer("Tesla");
    var customer3 = new Customer("Google");
    List<Customer> customers = new ArrayList<>();
    customers.add(customer1);
    customers.add(customer2);
    customers.add(customer3);

    helper.transactional(() -> customerDao.saveAll(customers));

    assertEquals(3, customerDao.count());
  }

  @Test
  public void whenInsertingCustomer_ThenFindReturnsIt() {
    var name = "Apple";
    var customerId = new AtomicLong();

    helper.transactional(
        () -> {
          var customer = new Customer(name);
          customerId.set(customerDao.save(customer).getId());
        });

    helper.transactional(
        () -> {
          var customer = customerDao.findById(customerId.get());
          assertTrue(customer.isPresent());
          assertEquals(customerId.get(), customer.get().getId());
          assertEquals(name, customer.get().getName());
        });
  }

  @Test
  public void whenInsertingMultipleCustomers_ThenAllCanBeFound() {
    var customer1 = new Customer("Apple");
    var customer2 = new Customer("Tesla");
    var customer3 = new Customer("Google");
    List<Customer> customers = new ArrayList<>();
    customers.add(customer1);
    customers.add(customer2);
    customers.add(customer3);

    helper.transactional(() -> customerDao.saveAll(customers));

    helper.transactional(
        () -> {
          var dbCustomers = customerDao.findAll();
          assertEquals(customers.size(), dbCustomers.size());
          assertEquals(customers.get(0).getName(), dbCustomers.get(0).getName());
          assertEquals(customers.get(1).getName(), dbCustomers.get(1).getName());
          assertEquals(customers.get(2).getName(), dbCustomers.get(2).getName());
        });
  }

  @Test
  public void whenSavingOneCustomer_ThenItShouldUpdateTheValues() {
    var customer = new Customer("Apple");
    var dbCustomer = new AtomicReference<Customer>();

    helper.transactional(
        () -> {
          dbCustomer.set(customerDao.save(customer));
          assertEquals(customer.getName(), dbCustomer.get().getName());
        });

    helper.transactional(
        () -> {
          dbCustomer.get().setName("Google");
          customerDao.save(dbCustomer.get());
        });

    helper.transactional(
        () -> {
          assertEquals(1, customerDao.count());
          var updatedCustomer = customerDao.findById(dbCustomer.get().getId());
          assertTrue(updatedCustomer.isPresent());
          assertEquals(updatedCustomer.get().getName(), dbCustomer.get().getName());
        });
  }

  @Test
  public void whenInsertingCustomerAndDeletingIt_ThenCountIsZero() {
    var customer = new Customer("Apple");
    var customerId = new AtomicLong();

    helper.transactional(
        () -> {
          customerId.set(customerDao.save(customer).getId());
        });

    helper.transactional(
        () -> {
          customerDao.deleteById(customerId.get());
          assertEquals(0, customerDao.count());
        });
  }
}
