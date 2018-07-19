package com.tim5.demo.Controller;

import com.tim5.demo.entity.Hotel;
import com.tim5.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    //all,add,delete,update
    @Autowired
    private HotelRepository hotelRepository;
    //ALL HOTELS
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public String findAll(Model model){
        List<Hotel> hotels = (List<Hotel>) this.hotelRepository.findAll();

        model.addAttribute("hotelsList", hotels);

        return "showHotels";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/{hotelId}")
    ResponseEntity<?> getHotel (@PathVariable Long hotelId) {

        Optional<Hotel> hotel = this.hotelRepository.findById(hotelId);
        if (!hotel.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Hotel>>(hotel, HttpStatus.OK);
    }

    //CREATE NEW USER
    @RequestMapping(path = "/addHotel", method = RequestMethod.GET)
    public String createProduct(Model model) {
        model.addAttribute("hotelDetail", new Hotel());
        return "addHotel";
    }
    @RequestMapping(path = "/addHotelNew", method = RequestMethod.POST)
    public String addNewHotel(Model model, Hotel hotel) {
        model.addAttribute("hotelDetail", new Hotel());
        hotelRepository.save(new Hotel(hotel.getName(), hotel.getLongitude(), hotel.getLatitude()));
        model.addAttribute("hotelsList",(List<Hotel>) hotelRepository.findAll());
        return "showHotels";
    }

    //EDIT HOTEL
    @RequestMapping(value={"/hotelEdit","/hotelEdit/{id}"}, method = RequestMethod.GET)
    public String notesEditForm(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("hotelDetail",(Optional<Hotel>) hotelRepository.findById(id));
        } else {
            model.addAttribute("hotelDetail", new Hotel());
        }
        return "editHotel";
    }
    @RequestMapping(value="/hotelEdit", method = RequestMethod.POST)
    public String hotelEdit(Model model, Hotel hotel) {
        hotelRepository.save(hotel);
        model.addAttribute("hotelList",(List<Hotel>) hotelRepository.findAll());
        return "redirect:all";
    }

    //DELETE ONE HOTEL
    @RequestMapping(value="/hotelDelete/{id}", method = RequestMethod.GET)
    public String hotelDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        hotelRepository.deleteById(id);
        model.addAttribute("hotelsList", hotelRepository.findAll());
        return "redirect:/hotel/all";
    }
}
