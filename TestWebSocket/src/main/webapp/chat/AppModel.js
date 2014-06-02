function AppModel(stompClient) {
	var self = this;
	self.chat = ko.observable(new Chat());
	self.userMessage = ko.observable("plz input message");

	self.connect = function() {

		stompClient.connect({}, self._successHandler, function(error) {
			console.log("STOMP protocol error " + error);
		});


	};

	self._successHandler = function(frame) {
		console.log('Connected ' + frame);
		stompClient.subscribe("/app/hi", function(message) {
			loadChat(JSON.parse(message.body));
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

	function loadChat(obj) {
		c = self.chat();
		c.loadData(obj);
	}

}