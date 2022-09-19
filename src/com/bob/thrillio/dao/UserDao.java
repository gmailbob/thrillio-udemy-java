package com.bob.thrillio.dao;

import java.util.List;

import com.bob.thrillio.*;
import com.bob.thrillio.entities.*;

public class UserDao {
	public List<User> getUsers() {
		return DataStore.getUsers();
	}
}
