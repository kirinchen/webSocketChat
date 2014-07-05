function AppModel(stompClient) {
	var self = this;
	self.chat = ko.observable("lobby");
	self.rowMessages = ko.observableArray();
	self.userMessage = ko.observable("plz input message");

	self.connect = function() {

		stompClient.connect({}, self._successHandler, function(error) {
			console.log("STOMP protocol error " + error);
		});

	};

	self._successHandler = function(frame) {
		console.log('Connected ' + frame);
		stompClient.subscribe("/app/hi", function(message) {
			addMessage(JSON.parse(message.body));
		});
		stompClient.subscribe("/message/chat.*", function(message) {
			addMessage(JSON.parse(message.body));
		});
		stompClient.subscribe("/message/test", function(message) {
			alert(message.body);
		});
		stompClient.subscribe("/user/message/testSendToUser",
				function(message) {
					alert(message.body);
				});
		stompClient.subscribe("/user/message/errors", function(message) {
			alert("Error " + message.body);
		});
	};

	self.test = function() {
		stompClient.send("/app/test", {}, JSON.stringify({}));
	};
	
	self.testError = function() {
		stompClient.send("/app/testError", {}, JSON.stringify({}));
	};

	self.testSendToUser = function() {
		stompClient.send("/app/testSendToUser", {}, JSON.stringify({}));
	};

	self.sendMessage = function() {
		message = {
			content : self.userMessage()
		};
		stompClient.send("/app/" + self.chat() + "/sendMessage", {}, JSON
				.stringify(message));
		self.userMessage("");
	};

	function addMessage(msg) {
		self.rowMessages.push(new RowMessage(msg));
	}

}