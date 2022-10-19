package ru.job4j.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Person;
import ru.job4j.domain.dto.PersonDTO;
import ru.job4j.service.PersonService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PersonService personService, BCryptPasswordEncoder encoder, ModelMapper modelMapper) {
        this.personService = personService;
        this.encoder = encoder;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonDTO>> findAll() {
        return new ResponseEntity<>(
                personService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                this.personService.findById(id).orElseThrow(
                        () -> new NoSuchElementException("Username and Password cannot be empty!")), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> create(@RequestBody PersonDTO personDTO) {
        Person person = convertToPerson(personDTO);
        if (person.getPassword().isEmpty() || person.getUsername().isEmpty()) {
            throw new NullPointerException("Username and Password cannot be empty!");
        }
        if (person.getUsername().length() < 3 || person.getUsername().length() > 16) {
            throw new IllegalArgumentException("The name must be no shorter than three and no longer than sixteen characters!");
        }
        if (person.getPassword().length() < 8) {
            throw new IllegalArgumentException("The password cannot be less than eight characters!");
        }
        if (personService.findByUsername(person.getUsername()).isPresent()) {
            throw new IllegalArgumentException("This name already exists!");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<>(personService.save(person), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody PersonDTO personDTO) {
        this.personService.save(convertToPerson(personDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        this.personService.delete(person);
        return ResponseEntity.ok().build();
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    public PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }
}
