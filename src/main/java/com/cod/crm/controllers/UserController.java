package com.cod.crm.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cod.crm.mapper.UsersMapper;
import com.cod.crm.model.Users;



@RestController
public class UserController {
	
	@Autowired
	private UsersMapper usersMapper;
	
	
	
	@RequestMapping(value = "/api/user/create", method = RequestMethod.GET)
	public HashMap<String,String> create()
    {
		

		
		HashMap<String,String> response = new HashMap<String,String>();
     
		
    Users user=new Users();
	user.setUsername("tiff");
	user.setPassword("tiff");
	usersMapper.insertSelective(user);
	
	response.put("id",user.getId().toString());
	
	

 
		
		return response ; 
	}
	
	

}

//
//@RequestMapping(value = "/api/custom/create", method = RequestMethod.POST)
//    public  Response<HashMap<String,String>>  create(
//         @RequestBody Custom custom
//         ) 
//    {
//
//          //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//          //String date= df.format(new Date());
//
//          custom.setCreatedAt(new Date());
//          custom.setUpdatedAt(new Date());
//
//         customMapper.insert(custom);
//
//         Response<HashMap<String,String>> response = new Response<HashMap<String,String>>();
//
//         response.data=new HashMap<String,String>();
//         response.data.put("id",custom.getId().toString());
//
//         return response;
//    }
