package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
	T get(Integer id);
	List<T> getAll();
	void save(T t);
	void update(T t, String[] args);
	void delete(T t);
}
