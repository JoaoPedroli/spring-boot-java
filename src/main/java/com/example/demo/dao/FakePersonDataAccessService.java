package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public Person insertPerson(UUID id, Person person) {
        Person data = new Person(id, person.getName());
        DB.add(data);
        return new Person(
            id, person.getName()
        );
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if(personMaybe.isEmpty()) return;
        DB.remove(personMaybe.get());
    }

    @Override
    public void updatePersonById(UUID id, Person person) {
        Person originalData = selectPersonById(id).orElse(null);
        if(originalData == null) return;
        Person updatedData = new Person(id, person.getName());
        int indexOfOriginalData = DB.indexOf(originalData);
        if(indexOfOriginalData >= 0) {
            DB.set(indexOfOriginalData, updatedData);
        }
    }
}
