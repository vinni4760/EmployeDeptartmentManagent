package com.nt.service;
import com.nt.exceptions.AlreadyExistsException;
import com.nt.exceptions.UserNotFoundException;
import com.nt.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

     private List<User> users = new ArrayList<User>();

     public String addUser(User newuser){
        boolean exists =  users.stream().anyMatch(user1->user1.getId()== newuser.getId());
        if(exists)
            throw new AlreadyExistsException("! OOps User Already Exists with Id  : "+newuser.getId());
        else
            users.add(newuser);
         return "user add success";
     }

     public List<User> getByName(String name){
        List<User> newuser = users.stream().filter(user -> user.getName().equals(name)).
                 collect(Collectors.toList());
        if(newuser.isEmpty())
            throw new UserNotFoundException("! No User Found with Name  : "+name);
         return newuser;
     }

     public User findById(Integer id){
         return users.stream().filter(user -> user.getId()==id)
                 .findFirst().orElseThrow(()->new UserNotFoundException("! User Not Found with id : "+id));
     }

     public List<User> getAllUsers(){
         return users;
     }

     public List<User> maxsalary(Double salary){
         List<User> newuser = users.stream().
                 filter(user -> user.getSalary()>=salary).
                 collect(Collectors.toList());
         if(newuser.isEmpty())
             throw new UserNotFoundException("NO Employee with Salary more than :"+salary);
         return newuser;
     }

     public  User userwithmaxsalary(){
         return users.stream().max(Comparator.comparing(User::getSalary)).get();
     }

     public List<User> getBySalary(){
         return users.stream().sorted(Comparator.comparing(User::getSalary))
                 .collect(Collectors.toList());
     }
}
