function RowMessage(row) {
	var self = this;

	self.user = ko.observable();
	self.content = ko.observable();
	
	loadData(row);
	
	function loadData(row){
		self.user(new User(row.user));
		self.content(row.content);
	}

}