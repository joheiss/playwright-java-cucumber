package com.jovisco.context;

import com.jovisco.domain.Person;

public class PersonContext {
    private final ThreadLocal<Person> randomPerson = ThreadLocal.withInitial(() -> null);

    public Person getRandomPerson() {
        return randomPerson.get();
    }
    public void setRandomPerson(Person person) {
        randomPerson.set(person);
    }
}
