package com.surfm.testws.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.surfm.testws.chat.Chat;
import com.surfm.testws.chat.ChatService;
import com.surfm.testws.chat.RowMessage;
import com.surfm.testws.chat.User;

@Controller
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	
	@SubscribeMapping("/hi")
	public Chat hi(Principal principal){
		Chat chat =  chatService.getChat(ChatService.DEFAULT_CHAT_NAME);
		RowMessage row = new RowMessage();
		row.setUser(ChatService.ADMIN_USER);
		row.setContent("hi " + principal.getName());
		chat.getRowMessages().add(row);
		chatService.createUser(chat.getName(), principal.getName());
		return chat;
	}
	
	@MessageMapping("{chatName}/sendMessage")
	public void receiveSendMessage(@DestinationVariable String chatName,RowMessage row,Principal principal){
		System.out.println(principal.getName() + " send msg "+row+" in " + chatName);
		row.setUser(chatService.getUser(chatName, principal.getName()));
		chatService.addMessage(chatName, row);
	}
}
