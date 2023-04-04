package com.stocktrading.platform.Service;

import com.stocktrading.platform.entity.Person;
import com.stocktrading.platform.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public float getBalance(String emailId) {
        return personRepository.findById(emailId).get().getBalance();
    }

    public void addBalance(String emailId, float value) {
        Person person = personRepository.findById(emailId).get();
        float balance = person.getBalance();
        balance += value;
        person.setBalance(balance);
        personRepository.save(person);
    }

    public void withdrawBalance(String emailId, float value) {
        Person person = personRepository.findById(emailId).get();
        float balance = person.getBalance();
        if(balance < value)
        {
            return;
        }
        balance -= value;
        person.setBalance(balance);
        personRepository.save(person);
    }
}
