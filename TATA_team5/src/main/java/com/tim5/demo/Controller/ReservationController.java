package com.tim5.demo.Controller;

import com.tim5.demo.entity.Reservation;
import com.tim5.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<Reservation>> findAll(){

        Collection<Reservation> reservations = this.reservationRepository.findAll();

        if(reservations.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }


        return new ResponseEntity<Collection<Reservation>>(reservations, HttpStatus.OK);
    }

    // RETRIEVE ONE CAKE SHOP
    @RequestMapping(method = RequestMethod.GET, value = "/{reservationId}")
    ResponseEntity<?> getReservation(@PathVariable Long reservationId) {

        Optional<Reservation> reservation = this.reservationRepository.findById(reservationId);
        if (!reservation.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Reservation>>(reservation, HttpStatus.OK);
    }

    //CREATE NEW USER
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation, UriComponentsBuilder ucBuilder) {

        Collection<Reservation>  reservations = this.reservationRepository.findAll();
        boolean exists = false;

        reservationRepository.save(reservation);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/reservation/{id}").buildAndExpand(reservation.getId()).toUri());
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

    //DELETE ONE)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteReservation(@PathVariable("id") long id) {

        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (!reservation.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        reservationRepository.deleteById(id);

        return new ResponseEntity<Reservation>(HttpStatus.NO_CONTENT);
    }

}
