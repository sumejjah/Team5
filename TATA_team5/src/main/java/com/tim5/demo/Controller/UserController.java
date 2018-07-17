package com.tim5.demo.Controller;

import com.tim5.demo.entity.Users;
import com.tim5.demo.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    //all, add, delete, update
    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<Users>> findAll(){

        Collection<Users> users = this.usersRepository.findAll();

        if(users.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }


        return new ResponseEntity<Collection<Users>>(users, HttpStatus.OK);
    }

    // RETRIEVE ONE USER
    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    ResponseEntity<?> getUser(@PathVariable Long userId) {

        Optional<Users> user = this.usersRepository.findById(userId);
        if (!user.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Users>>(user, HttpStatus.OK);
    }

    //CREATE NEW USER
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody Users user, UriComponentsBuilder ucBuilder) {

        Collection<Users>  users = this.usersRepository.findAll();
        boolean exists = false;
        for (Iterator<Users> i = users.iterator(); i.hasNext();) {
            if(i.next().getName().equals(user.getName()))
                exists = true;
        }

        if (exists) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        usersRepository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //UPDATE
    /*@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody Users user, @PathVariable long id) {

        Optional<Users> userOptional = usersRepository.findById(id);

        if (!userOptional.isPresent())
            return ResponseEntity.notFound().build();

        user.setId(id);

        usersRepository.save(user);

        return new ResponseEntity().noContent().build();
    }*/

    //DELETE ONE USER
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {

        Optional<Users> user = usersRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        usersRepository.deleteById(id);

        return new ResponseEntity<Users>(HttpStatus.NO_CONTENT);
    }





}
