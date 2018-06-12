package myspringboot.user;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/users")
public class UserController {

	private List<User> users = new ArrayList<>();	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	UserController() {
		buildUsers();
	}

	void buildUsers() {
		User user1 = new User(1L, "John", "Doe", "john@email.com");
		User user2 = new User(2L, "Jon", "Smith", "smith@email.com");
		User user3 = new User(3L, "Will", "Craig", "will@email.com");
		User user4 = new User(4L, "Sam", "Lernorad", "sam@email.com");
		User user5 = new User(5L, "Ross", "Doe", "ross@email.com");

		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<User> getUsers() {
		return this.users;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable("id") Long id) {
		return this.users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
	}

	@RequestMapping(method = RequestMethod.POST)
	public User saveUser(@RequestBody User user) {
		logger.debug("saveUser " + user);
		Long nextId = 0L;
		if (this.users.size() != 0) {
			User lastUser = this.users.stream().skip(this.users.size() - 1).findFirst().orElse(null);
			nextId = lastUser.getId() + 1;
		}
		user.setId(nextId);
		this.users.add(user);
		return user;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public User updateUser(@RequestBody User user) {		
		User modifiedUser = this.users.stream().filter(u -> u.getId() == user.getId()).findFirst().orElse(null);
		modifiedUser.setFirstName(user.getFirstName());
		modifiedUser.setLastName(user.getLastName());
		modifiedUser.setEmail(user.getEmail());
		return modifiedUser;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean deleteUser(@PathVariable Long id) {
		User deleteUser = this.users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
		if (deleteUser != null) {
			this.users.remove(deleteUser);
			return true;
		} else  {
			return false;
		}
	}
}