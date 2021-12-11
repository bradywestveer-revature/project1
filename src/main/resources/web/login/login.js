"use strict";

const usernameInput = document.getElementById ("usernameInput");
const passwordInput = document.getElementById ("passwordInput");
const loginButton = document.getElementById ("loginButton");

const login = () => {
	fetch ("/api/sessions", {
		method: "POST",
		
		body: JSON.stringify ({
			username: usernameInput.value,
			password: passwordInput.value
		})
	}).then (response => response.json ()).then (data => {
		sessionStorage.username = data.data.username;
		sessionStorage.userRole = data.data.role;
		
		location.href = "/";
	});
};

const loginKeyDown = event => {
	if (event.key === "Enter") {
		login ();
	}
};

usernameInput.addEventListener ("keydown", loginKeyDown);
passwordInput.addEventListener ("keydown", loginKeyDown);

loginButton.addEventListener ("click", login);