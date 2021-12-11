"use strict";

const usernameInput = document.getElementById ("usernameInput");
const passwordInput = document.getElementById ("passwordInput");
const loginButton = document.getElementById ("loginButton");

const login = async () => {
	const response = await fetch ("/api/sessions", {
		method: "POST",
		
		body: JSON.stringify ({
			username: usernameInput.value,
			password: passwordInput.value
		})
	});
	
	const data = await response.json ();
	
	if (data.success) {
		sessionStorage.username = data.data.username;
		sessionStorage.userRole = data.data.role;
		
		location.href = "/";
	}
	
	else {
		alert (data.message);
	}
};

const loginKeyDown = event => {
	if (event.key === "Enter") {
		login ();
	}
};

usernameInput.addEventListener ("keydown", loginKeyDown);
passwordInput.addEventListener ("keydown", loginKeyDown);

loginButton.addEventListener ("click", login);