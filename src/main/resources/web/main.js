"use strict";

fetch ("/api/sessions", {
	method: "POST",
	
	body: JSON.stringify ({
		username: "username",
		password: "password"
	})
}).then (response => response.json ()).then (data => {
	console.log (data);
});