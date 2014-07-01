function Chat() {

	var self = this;
	self.users = ko.observableArray();
	self.rowMessages = ko.observableArray();
	self.name = ko.observable();
	
	self.loadData = function(obj){
		if (obj != null) {
			self.name = ko.observable(obj.name);
			_initRowMessages(obj.rowMessages);
			_initUsers(obj.users);
		}
	};
	
	self.addMessage = function(msg){
		self.rowMessages.push(new RowMessage(msg));
	};

	function _initUsers(list) {
		for ( var key in list) {
			user = list[key];
			self.users.push(new User(user));
		}
	}

	function _initRowMessages(list) {
		for ( var key in list) {
			row = list[key];
			self.rowMessages.push(new RowMessage(row));
		}
	}
	
	
	


}