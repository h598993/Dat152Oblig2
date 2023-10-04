/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.repository.UserRepository;

/**
 * @author tdoy
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderService orderService;
	
	public User saveUser(User user) {
		
		user = userRepository.save(user);
		
		return user;
	}
	
	public void deleteUser(Long id) throws UserNotFoundException {
		
		// TODO
		User user = findUser(id);
		userRepository.delete(user);
	}
	
	public User updateUser(User user, Long id) throws UserNotFoundException {
		
		// TODO
		
		User usertoUpdate = findUser(id);
		long id1 = usertoUpdate.getUserid();
		user.setUserid(id1);
		userRepository.save(user);
		
		return user;
		
	}
	
	public List<User> findAllUsers(){
		
		List<User> allUsers = (List<User>) userRepository.findAll();
		
		return allUsers;
	}
	
	public User findUser(Long id) throws UserNotFoundException {
		
		User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User with id: "+id+" not found"));
		
		return user;
	}
	
	public Set<Order> findOrdersForUser(Long id) throws UserNotFoundException{
		
		User user = findUser(id);
		
		return user.getOrders();
	}
	
	public User createOrdersForUser(Long id, Order order) throws UserNotFoundException{
		
		// TODO
		
		Order o1 = orderService.saveOrder(order);
		User user = findUser(id);
		
		Set<Order> orders = user.getOrders();
		
		orders.add(o1);
		user.setOrders(orders);
		
		updateUser(user, id);
		
		return user;
	}
}
