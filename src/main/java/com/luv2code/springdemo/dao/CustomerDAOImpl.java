package com.luv2code.springdemo.dao;

import com.luv2code.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	private final SessionFactory sessionFactory;

	public CustomerDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Customer> getCustomers() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Customer> query = currentSession.createQuery("from Customer order by lastName");

		return query.list();
	}

	@Override
	public void saveCustomer(Customer customer) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		return currentSession.get(Customer.class, id);
	}

	@Override
	public void deleteCustomer(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery("delete from Customer where id=:customerId");
		query.setParameter("customerId", id);
		query.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery = null;
		if (theSearchName != null && theSearchName.trim().length() > 0) {

			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName");
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

		}
		else {
			theQuery = currentSession.createQuery("from Customer");
		}

		return theQuery.getResultList();
	}
}











