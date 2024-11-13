package com.nt.controller;

import com.nt.exceptions.AlreadyExistsException;
import com.nt.model.User;
import com.nt.response.ApiResponse;
import com.nt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService  userService;

    @GetMapping("/home")
    public String showhome(){
        return "Welcome to Home Page";
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUser(@RequestBody User user){
        try {
            String msg =   userService.addUser(user);
            return new ResponseEntity<ApiResponse>(new ApiResponse(msg,null), HttpStatus.CREATED);

        }
        catch (AlreadyExistsException e){
            return  new ResponseEntity<>(new ApiResponse("Failed !",e.getMessage()),HttpStatus.CONFLICT);
        }
     }

     
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllByName(@RequestParam String name){
        try {
            List<User> users  =   userService.getByName(name);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Users :",users), HttpStatus.OK);

        }
        catch (Exception e){
            return  new ResponseEntity<>(new ApiResponse(e.getMessage(),null),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/by-id")
    public ResponseEntity<ApiResponse> getAllByName(@RequestParam Integer id){
        try {
            User user = userService.findById(id);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Users :",user), HttpStatus.OK);

        }
        catch (Exception e){
            return  new ResponseEntity<>(new ApiResponse(e.getMessage(),null),HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAll(){
        try {
            List<User> users  =   userService.getAllUsers();
//            System.out.println(10/0);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Users :",users), HttpStatus.OK);

        }
        catch (Exception e){
            return  new ResponseEntity<>(new ApiResponse(e.getMessage(),null),HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/salgreater")
    public ResponseEntity<ApiResponse> getByMaxSal(@RequestParam Double salary){
        try{
//            List<User> users =  userService.maxsalary(salary);
            List<User> users = userService.getBySalary();
              return new ResponseEntity<ApiResponse>(new ApiResponse("Employees with Salary ",users),
                      HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("! Failed",e.getMessage()),HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/maxsal")
    public ResponseEntity<ApiResponse> getByMaxSal(){
        try{
            User users =  userService.userwithmaxsalary();
            return new ResponseEntity<ApiResponse>(new ApiResponse("Employees with Max Salary ",users),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("! Failed",e.getMessage()),HttpStatus.CONFLICT);
        }
    }



}
