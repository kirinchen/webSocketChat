function AppModel(stompClient) {
	var self = this;
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

	};

	self.sendMessage = function() {
		chatName = self.chat().name();
		message = {
			content : self.userMessage()
		};
		stompClient.send("/app/" + chatName + "/sendMessage", {}, JSON
				.stringify(message));
		// self.userMessage("");
	};

	
	function addMessage(msg){
		self.rowMessages.push(new RowMessage(msg));
	}

}