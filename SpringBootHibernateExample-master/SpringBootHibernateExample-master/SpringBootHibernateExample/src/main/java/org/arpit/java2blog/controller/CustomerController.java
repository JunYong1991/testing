package org.arpit.java2blog.controller;

import java.util.List;

import org.arpit.java2blog.model.Customer;
import org.arpit.java2blog.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@RequestMapping(value = "/getAllCustomers", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getAllCustomers(Model model) {

		List<Customer> listOfCustomers = customerService.getAllCustomers();
		model.addAttribute("customer", new Customer());
		model.addAttribute("listOfCustomers", listOfCustomers);
		return "customerDetails";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getLogin(Model model) {

		List<Customer> listOfCustomers = customerService.getAllCustomers();
		model.addAttribute("customer", new Customer());
		//model.addAttribute("listOfCustomers", listOfCustomers);
		return "login";
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getError(Model model) {

		//List<Customer> listOfCustomers = customerService.getAllCustomers();
		model.addAttribute("customer", new Customer());
		//model.addAttribute("listOfCustomers", listOfCustomers);
		return "login";
	}

//	@RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/json")
//	public String goToHomePage() {
//		return "redirect:/getAllCustomers";
//	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/json")
	public String goToHomePage() {
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/getCustomer/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Customer getCustomerById(@PathVariable int id) {
		return customerService.getCustomer(id);
	}

	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST, headers = "Accept=application/json")
	public String addCustomer(@ModelAttribute("customer") Customer customer) {	
		
		if(customer.getId()==0)
		{
			customerService.addCustomer(customer);
		}
		else
		{	
			customerService.updateCustomer(customer);
		}

		return "redirect:/getAllCustomers";
	}
	
	@RequestMapping(value = "/validate", method = RequestMethod.POST, headers = "Accept=application/json")
	public String validate(HttpSession session,@ModelAttribute("customer") Customer customer) {	
		
		try{
			List<Customer> listOfCustomers = customerService.getCustomerName(customer.getCustomerName());
			
			System.out.println("customer.getCustomerName() "+customer.getCustomerName());
			System.out.println("listOfCustomers.get(0).getCustomerName() "+listOfCustomers.get(0).getCustomerName());
			System.out.println("customer.getEmail() "+customer.getEmail());
			System.out.println("listOfCustomers.get(0).getEmail() "+listOfCustomers.get(0).getEmail());
			
			 if ((customer.getCustomerName().equals(listOfCustomers.get(0).getCustomerName())) && 
				 (customer.getEmail().equals(listOfCustomers.get(0).getEmail()))) {
				 session.setAttribute("username", customer.getCustomerName());
				 return "redirect:/getAllCustomers";
			 }
			 else {
				 return "redirect:/login";
				 //return "redirect:/error";
			 }
		}
		catch (Exception e) {
			session.removeAttribute("username");
			return "redirect:/login";
		}
		
	}
	
	

	@RequestMapping(value = "/updateCustomer/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String updateCustomer(@PathVariable("id") int id,Model model) {
		model.addAttribute("customer", this.customerService.getCustomer(id));
		model.addAttribute("listOfCustomers", this.customerService.getAllCustomers());
		return "customerDetails";
	}

	@RequestMapping(value = "/deleteCustomer/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String deleteCustomer(@PathVariable("id") int id) {
		customerService.deleteCustomer(id);
		return "redirect:/getAllCustomers";

	}
	@RequestMapping(value = "/exit", method = RequestMethod.GET, headers = "Accept=application/json")
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		return "redirect:/login";
	}
}
