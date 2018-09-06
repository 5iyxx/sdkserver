package com.sdkserver.controller;



import com.sdkserver.sso.model.User;
import com.sdkserver.sso.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

	@Autowired
	private UserClientService service = null;


	@RequestMapping(value = "/consumer/user/get/{id}")
	public User add(@PathVariable Integer id) {
		return this.service.get(id);
	}

	@RequestMapping(value = "/consumer/user/add")
	public Object add(User user) {
		return this.service.add(user);
	}
}