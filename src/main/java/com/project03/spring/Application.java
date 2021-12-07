
package com.project03.spring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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

	/*
	List Claimed Furnishings (GET: [url]/items?list={username})
	Display Furnishing (GET [url]/items?search={type, age})
	 */
	@GetMapping("/items")
	public List<Map<String, Object>> listings(@RequestParam(value="search", defaultValue="all") String search, @RequestParam(value="list", defaultValue = "none") String list){
		String sql;
		if(search.equals("all") && list.equals("none")){
			sql = "SELECT * FROM listings";
		}
		else if(list.equals("none")){
			sql = "SELECT * FROM listings WHERE listings.name LIKE '%" + search + "%'";
		}
		else{
			int userId = getUserId(list);
			sql = "SELECT * FROM listings WHERE User_id = '" + userId + "'";
		}
		List<Map<String, Object>> rows;
		try {
			rows = template.queryForList(sql);
		}
		catch (Exception e) {
			rows = null;
		}
		return rows;
	}

	@PostMapping("/items")
	public String postItem(@RequestParam(value="name") String name, @RequestParam(value="description") String description,
						   @RequestParam(value="url") String url, @RequestParam(value="price") double price,
						   @RequestParam(value="item_id") int item_id, @RequestParam(value="User_id") int User_id){
		String sql = "INSERT INTO listings (name, description, url, price, item_id, User_id)" +
				"VALUES ('"+ name + "','" + description  + "','"+ url +"','" + price + "','" + item_id + "','" + User_id +"')";
		try {
			template.execute(sql);
		}
		catch (Exception e){
			return "Insertion failed";
		}
		return "Insertion success";
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
	public String deleteUser(@CookieValue(name = "User_id", defaultValue = "000") String cookie_id, @RequestParam(value = "userId") String userId) {
		// TODO: Ensure request has login attatched, delete user from database
		String sql = "DELETE FROM users WHERE User_id='" + userId + "'";

		if(cookie_id.equals(userId) || checkAdmin(Integer.parseInt(userId))) {
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
	public String logout(HttpServletResponse response){
		Cookie cookie = new Cookie("User_id", "0");
		response.addCookie(cookie);
		return "Logout successful";
	}

	public boolean checkAdmin(int userId){
		String sql = "SELECT is_admin FROM users WHERE user_id='" + userId + "'";
		boolean rows = template.queryForObject(sql, Boolean.class);
		return rows;
	}

	public int getUserId(String username){
		String sql = "SELECT User_id FROM users WHERE username = '" + username + "'";
		int User_id = template.queryForObject(sql, Integer.class);
		return User_id;
	}

	@GetMapping("/items")
	public List<Map<String, Object>> getListings() {
		String sql = "SELECT item_id, name, price, description FROM listings";
		List<Map<String, Object>> rows = template.queryForList(sql);
		return rows;
	}
	
	@PostMapping("/items")
	public String postListing(@CookieValue(value = "User_id", defaultValue = "1") String cookie_id, @RequestParam(value="item_name") String itemName, @RequestParam(value="price") double price, @RequestParam(value="description", defaultValue = "") String description, @RequestParam(value="url", defaultValue="") String url, HttpServletResponse response) {
		String sql = "INSERT INTO listings (user_id, name, price, description, url) VALUES (?, ?, ?, ?, ?)";
		template.update(sql, Integer.valueOf(cookie_id), itemName, price, description, url);
		return "Listing successfully posted!";
	}

}
            
