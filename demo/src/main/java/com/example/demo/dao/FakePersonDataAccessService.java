package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {
	
	private static List<Person> DB = new ArrayList<>();
	
	@Override
	public int insertPerson(UUID id, Person person) {
		DB.add(new Person(id, person.getName()));
		return 1;
	}

	@Override
	public List<Person> selectAllPeople() {
		return DB;
	}

	@Override
	public int deletePersonById(UUID id) {
		Optional<Person> person = this.selectPersonById(id);
		if(person.isPresent()) {
			DB.remove(person.get());
			return 1;
		}
		return 0;
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		return selectPersonById(id).map(x -> {
			int index = DB.indexOf(x);
			if(index >= 0) {
				person.setId(id);
				DB.set(index, person);
				return 1;
			}
			else {
				return 0;
			}
		}).orElse(0);
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		return DB.stream().filter(x -> x.getId().equals(id)).findFirst();
	}
	
	
}
