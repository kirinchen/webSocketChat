package com.surfm.testws.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Chat {

	private String name;
	private List<User> users = new ArrayList<User>();
	private List<RowMessage> rowMessages = new CopyOnWriteArrayList<>();

	public List<RowMessage> getRowMessages() {
		return rowMessages;
	}

	public void setRowMessages(List<RowMessage> rowMessages) {
		this.rowMessages = rowMessages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
