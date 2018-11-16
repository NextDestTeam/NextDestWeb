package com.happyweekend.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
	T get(Integer id) throws SQLException;
	List<T> getAll();
	void save(T t);
	void update(T t);
	void delete(T t);
}
