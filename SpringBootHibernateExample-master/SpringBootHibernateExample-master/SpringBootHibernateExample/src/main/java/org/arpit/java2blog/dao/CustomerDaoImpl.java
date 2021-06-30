package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Customer;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl implements CustomerDao{

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Customer> getAllCustomers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Customer>  customerList = session.createQuery("from Customer").list();
		return customerList;
	}

	public Customer getCustomer(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Customer customer = (Customer) session.get(Customer.class, id);
		return customer;
	}

	public Customer addCustomer(Customer customer) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(customer);
		session.flush();
		return customer;
	}

	public void updateCustomer(Customer customer) {
		Session session = this.sessionFactory.getCurrentSession();
		Hibernate.initialize(customer);
		session.update(customer);
		session.flush();
	}

	public void deleteCustomer(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Customer p = (Customer) session.load(Customer.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	} 
	
	public List<Customer> getCustomerName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Customer E WHERE E.customerName = :customer_name";
		Query query = session.createQuery(hql);
		query.setParameter("customer_name",name);
		List<Customer> customerList = query.list();
		
		return customerList;
		}
}
