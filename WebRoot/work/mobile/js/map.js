//自定义Map对象
function JSMap(){
	this.keys = new Array();
	this.data = new Array();
 
	this.put = function(key,value){
		if(this.data[key] == null){
			this.keys.push(value);
		}
		this.data[key] = value;
	};

	this.get = function(key){
		return this.data[key];
	};

	this.remove = function(key){
		this.keys.remove(key);
		this.data[key] = null;
	};

	this.isEmpty = function(){
		return this.keys.length == 0;
	};

	this.size = function(){
		return this.keys.length;
	};
}


