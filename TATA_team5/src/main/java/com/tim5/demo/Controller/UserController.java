package com.tim5.demo.Controller;

import com.tim5.demo.entity.Hotel;
import com.tim5.demo.entity.Users;
import com.tim5.demo.repository.HotelRepository;
import com.tim5.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    //all, add, delete, update
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/showUsers")
    public String findAllNew(Model model){

        List<Users> users = (List<Users>) this.usersRepository.findAll();

        model.addAttribute("usrList", users);

        return "showUsers";
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

    // RETRIEVE ONE USER
    @RequestMapping(method = RequestMethod.GET, value = "/findUser")
    String findUser(@RequestParam String usrName, Model model) {

        List<Users> users = this.usersRepository.findAll();
        List<Users> result = new ArrayList<Users>();
        for(int i = 0; i< users.size(); i++){
            if (users.get(i).getName().contains(usrName) || users.get(i).getSurname().contains(usrName)){
                result.add(users.get(i));
            }
        }

        model.addAttribute("usrList", result);
        return "showUsers";
    }

    //CREATE NEW USER
    @RequestMapping(path = "/userAdd", method = RequestMethod.GET)
    public String createProduct(Model model) {
        model.addAttribute("usrDetails", new Users());

        return "userAdd";
    }

    @RequestMapping(path = "/{userId}/reserve", method = RequestMethod.GET)
    public String userPanel(@PathVariable Long userId, Model model) {
        model.addAttribute("currentUsrId", userId);
        model.addAttribute("currentUsrLong", usersRepository.findById(userId).get().getLongitude());
        model.addAttribute("currentUsrLat", usersRepository.findById(userId).get().getLatitude());
        model.addAttribute("hotel_id", 1);
        model.addAttribute("hotelsList", hotelRepository.findAll());
        return "userProfile";
    }

    //LOGIN
    @RequestMapping(method = RequestMethod.GET, value = "/openPanel")
    public String findUserbyName(@RequestParam String username, @RequestParam String password, Model model){

        List<Users> users = this.usersRepository.findAll();

        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUserName().equals(username) && users.get(i).getPassword().equals(password)){
                if (users.get(i).getRole().equals("korisnik")){
                    return "redirect:/users/"+users.get(i).getId().toString()+"/reserve";
                }
                else if (users.get(i).getRole().equals("supervisor")){
                    return "redirect:/supervisorProfile";
                }
                else{
                    return "redirect:/admin";
                }

            }
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/userAddNew", method = RequestMethod.POST)
    public String addNewUser(Model model, Users user) {
        model.addAttribute("usrDetails", new Users());

        if(user.getName() != "") {
            usersRepository.save(new Users(user.getName(), user.getSurname(), user.getUserName(), user.getPassword(), user.getEmail(), user.getRole(), user.getLongitude(), user.getLatitude()));
        }
        model.addAttribute("usrList",(List<Users>) usersRepository.findAll());
        return "showUsers";
    }

    //UPDATE
    @RequestMapping(value={"/userEdit","/userEdit/{id}"}, method = RequestMethod.GET)
    public String notesEditForm(Model model, @PathVariable(required = false, name = "id") Long id) {
        if (null != id) {
            model.addAttribute("usrDetails",(Optional<Users>) usersRepository.findById(id));
        } else {
            model.addAttribute("usrDetails", new Users());
        }
        return "userEdit";
    }

    @RequestMapping(value="/userEdit", method = RequestMethod.POST)
    public String notesEdit(Model model, Users user) {
        usersRepository.save(user);
        model.addAttribute("usrList",(List<Users>) usersRepository.findAll());
        return "redirect:showUsers";
    }

    //DELETE ONE USER
    @RequestMapping(value="/userDelete/{id}", method = RequestMethod.GET)
    public String userDelete(Model model, @PathVariable(required = true, name = "id") Long id) {
        usersRepository.deleteById(id);
        model.addAttribute("usrList",(List<Users>) usersRepository.findAll());
        return "redirect:/users/showUsers";
    }
}