package com.surfm.report;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

	@Autowired
	private MessageSendingOperations<String> messagingTemplate;

	@SubscribeMapping("/hi")
	public RowMessage hi(Principal principal) {
		String content = "Hi " + principal.getName();
		String user = "admin";
		RowMessage rw = new RowMessage();
		rw.setContent(content);
		rw.setUser(user);
		return rw;
	}

	@MessageMapping("{chatName}/sendMessage")
	public void receiveSendMessage(@DestinationVariable String chatName,
			RowMessage row, Principal principal) {
		System.out.println(principal.getName() + " send msg " + row + " in "
				+ chatName);
		row.setUser(principal.getName());
		this.messagingTemplate.convertAndSend("/message/chat." + chatName, row);
	}

	@MessageMapping("/test")
	@SendTo("/message/test")
	public RowMessage test(Principal principal) {
		System.out.println(principal.getName());
		return new RowMessage();

	}

	@MessageMapping("/testSendToUser")
	@SendToUser("/message/testSendToUser")
	public RowMessage testSendToUser(Principal principal) {
		System.out.println(principal.getName());
		return new RowMessage();
	}

	@MessageMapping("/testError")
	public void testError(Principal principal) {
		throw new RuntimeException("just test");
	}

	@MessageExceptionHandler
	@SendToUser("/message/errors")
	public String handleException(Throwable exception) {
		return exception.getMessage();
	}

}
