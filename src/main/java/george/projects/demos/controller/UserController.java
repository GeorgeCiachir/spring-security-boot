package george.projects.demos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/userPage")
@Controller
public class UserController {

	@RequestMapping("/premiumUsers")
	public String premiumUsers() {
		return "premiumUsersPage";
	}

	@RequestMapping("/normalUsers")
	public String normalUsers() {
		return "normalUsersPage";
	}
}
