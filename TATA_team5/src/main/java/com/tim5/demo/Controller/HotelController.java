package com.tim5.demo.Controller;

import com.tim5.demo.entity.Hotel;
import com.tim5.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    //all,add,delete,update
    @Autowired
    private HotelRepository hotelRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/all")

    public ResponseEntity<Collection<Hotel>> findAll(){
        Collection<Hotel> hotel=this.hotelRepository.findAll();
        if(hotel.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Hotel>>(hotel,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{hotelId}")
    ResponseEntity<?> getHotel (@PathVariable Long hotelId) {

        Optional<Hotel> hotel = this.hotelRepository.findById(hotelId);
        if (!hotel.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Hotel>>(hotel, HttpStatus.OK);
    }
    //CREATE NEW HOTEL
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createHotel( @RequestBody Hotel hotel, UriComponentsBuilder ucBuilder) {

        Collection<Hotel> hotels = this.hotelRepository.findAll();
        boolean exists = false;
        for (Iterator<Hotel> i = hotels.iterator(); i.hasNext();) {
            if(i.next().getName().equals(hotel.getName()))
                exists = true;
        }

        if (exists) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        hotelRepository.save(hotel);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/hotels/{id}").buildAndExpand(hotel.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //UPDATE HOTEL
    /*@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateHotel(@PathVariable long id,@RequestBody Hotel hotel) {

        Optional<Hotel> hotelOptional = hotelRepository.findById(id);

        if (!hotelOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        hotel.setId(id);

        hotelRepository.save(hotel);
        return new ResponseEntity().NoContent().build();
    }*/

    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteHotel(@PathVariable("id") long id) {

        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (!hotel.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        hotelRepository.deleteById(id);
        return new ResponseEntity<Hotel>(HttpStatus.NO_CONTENT);
    }
}
