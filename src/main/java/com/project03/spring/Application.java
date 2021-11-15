
package com.project03.spring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private JdbcTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/users")
	public String select(@RequestParam(value = "username", defaultValue = "Test") String username) {
		String sql = "SELECT username FROM users WHERE username = ?";
		String rows = template.queryForObject(sql, String.class, new Object[] { username });
		System.out.println(rows);
		return String.format("User %s exists", rows);
	}

	@PostMapping("/users")
	public String register(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {
		String sql = "SELECT username FROM users WHERE username ='" + username + "'";
		String rows;
		try {
			rows = template.queryForObject(sql, String.class);
		}
		catch (EmptyResultDataAccessException e) {
			rows = "";
		}


		if (rows.length() == 0) {
			sql = "INSERT INTO users (username, password) VALUES (?, ?)";
			template.update(sql, username, password);
			return "User " + username + " created successfully!";
		}
		return "User " + username + " already exists!";
	}
	
	@DeleteMapping("/users")
	public String deleteUser(@RequestParam(value = "userId") String userId) {
		// TODO: Ensure request has login attatched, delete user from database
		String sql = "DELETE FROM users WHERE User_id='" + userId + "'";
		try{
			String rows = template.queryForObject(sql, String.class);
		}
		catch (Exception e){
			return "Delete user failed";
		}
		return "Delete user success for user ID " + userId;
	}

	@GetMapping("/")
	public String landing(){
		return String.format("Landing page for Project 03");
	}

	@PostMapping("/login")
	public String login(@RequestParam(value="user") String username, @RequestParam(value="pass") String password){
		// Query database for username and password; If valid,
		//TODO: Authenticate username/password and instantiate session
		String sql = "SELECT password FROM users WHERE username='" + username +"'";
		String rows = "";
		try {
			rows = template.queryForObject(sql, String.class);
		}
		catch (Exception e){
			return "Login failed: User does not exist";
		}
		if(password.equals(rows)){
			return "Login successful";
		}
		return "Login failed: Invalid password";
	}

}
            