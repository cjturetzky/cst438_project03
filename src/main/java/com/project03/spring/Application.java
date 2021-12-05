
package com.project03.spring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@SpringBootApplication
@RestController
public class Application extends SpringBootServletInitializer{

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
	public String deleteUser(@CookieValue("User_id") String cookie_id, @RequestParam(value = "userId") String userId) {
		// TODO: Ensure request has login attatched, delete user from database
		String sql = "DELETE FROM users WHERE User_id='" + userId + "'";
		if(cookie_id.equals(userId)) {
			try {
				String rows = template.queryForObject(sql, String.class);
			} catch (Exception e) {
				return "Delete user failed";
			}
			return "Delete user success for user ID " + userId;
		}
		return "Cannot delete user if not logged in";
	}

	@GetMapping("/")
	public String landing(){
		return String.format("Landing page for Project 03");
	}

	@PostMapping("/login")
	public String login(@RequestParam(value="user") String username, @RequestParam(value="pass") String password, HttpServletResponse response){
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
			sql = "SELECT User_id FROM users WHERE username='" + username + "'";
			rows = template.queryForObject(sql, String.class);
			Cookie cookie = new Cookie("User_id", rows);
			response.addCookie(cookie);
			return "Login successful";
		}
		return "Login failed: Invalid password";
	}

	@PostMapping("/logout")
	public String logout(@CookieValue(value = "User_id", defaultValue = "0") String cookie_id, @RequestParam(value="urlId", defaultValue = "None") String url_id, HttpServletResponse response){
		if(cookie_id.equals(url_id)){
			Cookie cookie = new Cookie("User_id", "0");
			response.addCookie(cookie);
			return "Logout successful";
		}
		return "Error logging out: User not logged in";
	}

	@PostMapping("/items")
	public String postListing(@CookieValue(value = "User_id") String cookie_id, @RequestParam(value="item_name") String itemName, @RequestParam(value="price") double price, @RequestParam(value="description") String description, @RequestParam(value="url") String url) {
		String sql = "INSERT INTO listings (user_id, name, price, description, url) VALUES (?, ?, ?, ?, ?)";
		template.update(sql, Integer.valueOf(cookie_id), itemName, price, description, url);
		return "Listing successfully posted!";
	}

}
            