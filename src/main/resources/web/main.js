"use strict";

const usernameText = document.getElementById ("usernameText");
const logoutButton = document.getElementById ("logoutButton");

const requestsElement = document.getElementById ("requests");

const requestTemplate = document.getElementById ("requestTemplate");
const requestControlsTemplate = document.getElementById ("requestControlsTemplate");
const requestApprovedMessageTemplate = document.getElementById ("requestApprovedMessageTemplate");
const requestDeniedMessageTemplate = document.getElementById ("requestDeniedMessageTemplate");

let requests = [];

let filterStatus = null;

const formatAmount = amount => {
	return amount.toLocaleString ("en-US", { style: "currency", currency: "USD" });
};

const capitalCase = string => {
	return string.charAt (0) + string.slice (1).toLowerCase ();
};

const formatDate = date => {
	return new Date (date).toLocaleDateString ();
};

const createRequest = request => {
	const requestElement = requestTemplate.content.cloneNode (true).children [0];
	
	//amount
	requestElement.children [0].children [0].textContent = formatAmount (request.amount);
	
	//type
	requestElement.children [0].children [1].textContent = capitalCase (request.type);
	
	//description
	requestElement.children [0].children [2].textContent = request.description;
	
	//submitted
	requestElement.children [1].children [0].children [0].childNodes [1].textContent = " on " + formatDate (request.submitted);
	
	//author
	requestElement.children [1].children [0].children [1].textContent = "by " + request.author;
	
	if (request.status === "PENDING") {
		if (sessionStorage.userRole === "MANAGER") {
			//approve/deny buttons
			requestElement.children [1].appendChild (requestControlsTemplate.content.cloneNode (true).children [0]);
		}
	}
	
	else {
		//approved/denied status message
		const requestStatusMessageElement = (request.status === "APPROVED" ? requestApprovedMessageTemplate : requestDeniedMessageTemplate).content.cloneNode (true).children [0];
		
		requestStatusMessageElement.children [0].childNodes [1].textContent = " on " + formatDate (request.resolved);
		
		requestStatusMessageElement.children [1].textContent = "by " + request.resolver;
		
		requestElement.children [1].appendChild (requestStatusMessageElement);
	}
	
	return requestElement;
};

const clearRequests = () => {
	requestsElement.replaceChildren ();
};

const createRequests = () => {
	for (let i = 0; i < requests.length; i++) {
		if (filterStatus === null || requests [i].status === filterStatus) {
			requestsElement.appendChild (createRequest (requests [i]));
		}
	}
};

logoutButton.addEventListener ("click", () => {
	//remove user data from sessionStorage
	sessionStorage.clear ();
	
	fetch ("/api/sessions", {
		method: "DELETE"
	}).then (response => {
		if (response.redirected) {
			location.href = response.url;
			
			return;
		}
	});
});

usernameText.textContent = sessionStorage.username;

fetch ("/api/requests").then (response => {
	if (response.redirected) {
		location.href = response.url;
		
		return;
	}
	
	return response.json ();
}).then (data => {
	requests = data.data;
	
	createRequests ();
});