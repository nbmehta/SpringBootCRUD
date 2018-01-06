package com.developer.SpringMySQL.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.developer.SpringMySQL.models.AppUsers;
import com.developer.SpringMySQL.models.PersonJsonObject;
import com.developer.SpringMySQL.models.User;
import com.developer.SpringMySQL.repo.AppUsersRepo;
import com.developer.SpringMySQL.repo.UserRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




@Controller
public class UserController {
	
	@Autowired
	UserRepo userRepo;
	
	 @RequestMapping("/user")
	    public ModelAndView doHome(Model model){
	        ModelAndView mv = new ModelAndView("user");
	        model.addAttribute("adduser", new User());
	        return mv;
	    }
	 
	 @RequestMapping( value = "/adduser", method = RequestMethod.POST)
	    public ModelAndView doSave(@ModelAttribute("adduser") User user,Model model){
	              
	        userRepo.save(user);
	        
	        ModelAndView mv = new ModelAndView("user");
	        return mv;
	    }
	 
	 
	 @RequestMapping(value = "/userList", method = RequestMethod.GET, produces = "application/json")
	    public @ResponseBody String springPaginationDataTables(HttpServletRequest  request) throws IOException {
			
	    	//Fetch the page number from client
	    	Integer pageNumber = 0;
	    	if (null != request.getParameter("iDisplayStart"))
	    		pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/5);		
	    	
	    	//Fetch search parameter
	    	String searchParameter = request.getParameter("sSearch");
	    	
	    	//Fetch Page display length
	    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
	    	
	    	//Create page list data
	    	//List<User> personsList = createPaginationData(pageDisplayLength);
	    	Page<User> personsList=userRepo.findAll(new PageRequest(pageNumber, pageDisplayLength));
			
			//Here is server side pagination logic. Based on the page number you could make call 
			//to the data base create new list and send back to the client. For demo I am shuffling 
			//the same list to show data randomly
			
			
			//Search functionality: Returns filtered list based on search parameter
	    	List<User> usrlist=(List<User>) userRepo.findAll();
	    	
	    	getListBasedOnSearchParameter(searchParameter,usrlist);
			
			
			PersonJsonObject personJsonObject = new PersonJsonObject();
			//Set Total display record
			personJsonObject.setiTotalDisplayRecords(500);
			//Set Total record
			personJsonObject.setiTotalRecords(personsList.getSize());
			personJsonObject.setAaData(personsList.getContent());
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(personJsonObject);
		
			return json2;
	    }
	 
		private List<User> getListBasedOnSearchParameter(String searchParameter,List<User> personsList) {
			
			if (null != searchParameter && !searchParameter.equals("")) {
				List<User> personsListForSearch = new ArrayList<User>();
				searchParameter = searchParameter.toUpperCase();
				for (User person : personsList) {
					if (person.getFirstName().toUpperCase().indexOf(searchParameter)!= -1 || person.getLastName().toUpperCase().indexOf(searchParameter)!= -1) {
						personsListForSearch.add(person);					
					}
					
				}
				personsList = personsListForSearch;
				personsListForSearch = null;
			}
			return personsList;
		}
	 
		
		

}
