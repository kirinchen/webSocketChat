function User(obj) {
	var self = this;
	self.name = ko.observable();
	
	loadData(obj);

	function loadData(obj) {
		self.name(obj.name);
	}
}