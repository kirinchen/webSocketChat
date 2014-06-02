package com.surfm.testws.chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
	
	public static final User ADMIN_USER = createAdminUser();
	public static final String DEFAULT_CHAT_NAME = "default";
	
	@Autowired
	private MessageSendingOperations<String> messagingTemplate;
	
	private final List<Chat> chats = new ArrayList<Chat>();
	
	
	ChatService(){
		chats.add(createDefaultChat());
	}
	
	public List<Chat> getChats() {
		return chats;
	}
	
	public Chat getChat(String chatName){
		for(Chat c : chats){
			if(c.getName().equals(chatName))
				return c;
		}
		return null;
	}
	
	public void addMessage(String chatName,RowMessage row){
		System.out.println("addMessage");
		Chat chat = getChat(chatName);
		chat.getRowMessages().add(row);
		this.messagingTemplate.convertAndSend("/topic/chat." +chatName , row);
	}


	private Chat createDefaultChat() {
		Chat ans = new Chat();
		ans.setName(DEFAULT_CHAT_NAME);
		ans.setRowMessages(createHiMessages());
		ArrayList<User> us = new ArrayList<User>();
		us.add(ADMIN_USER);
		ans.setUsers(us);
		return ans;
	}
	
	private List<RowMessage> createHiMessages(){
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
		ArrayList<RowMessage> ans = new ArrayList<RowMessage>();

		RowMessage a = new RowMessage();
		a.setUser(ADMIN_USER);
		a.setContent("WilCOme~~");
		ans.add(a);
		RowMessage b = new RowMessage();
		b.setUser(ADMIN_USER);
		b.setContent(sf.format(new Date()));
		ans.add(b);
		return ans;
		
	}
	
	public User createUser(String chatName,String userName){
		Chat chat = getChat(chatName);
		User u = new User();
		u.setName(userName);
		chat.getUsers().add(u);
		return u;
	}
	
	public User getUser(String chatName,String userName){
		Chat chat = getChat(chatName);
		for(User u : chat.getUsers()){
			if(u.getName().equals(userName))
				return u;
		}
		return null;
	}
	
	private static User createAdminUser() {
		User u = new User();
		u.setName("Admin");
		return u;
	}

}
