package com.surfm.report;

import java.security.Principal;

import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
	
	@SubscribeMapping("/hi")
	public RowMessage hi(Principal principal){
		String content =  "Hi " + principal.getName() ;
		String user  = "admin";
		RowMessage rw = new RowMessage();
		rw.setContent(content);
		rw.setUser(user);
		return rw;
	}
}
