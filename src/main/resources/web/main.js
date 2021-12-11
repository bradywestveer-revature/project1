"use strict";

const requests = document.getElementById ("requests");

const requestTemplate = document.getElementById ("requestTemplate");
const requestControlsTemplate = document.getElementById ("requestControlsTemplate");
const requestApprovedMessageTemplate = document.getElementById ("requestApprovedMessageTemplate");
const requestDeniedMessageTemplate = document.getElementById ("requestDeniedMessageTemplate");

let userRole;

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
		if (userRole === "MANAGER") {
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

fetch ("/api/requests").then (response => {
	if (response.redirected) {
		location.href = response.url;
		
		return;
	}
	
	return response.json ();
}).then (data => {
	userRole = data.data.userRole;
	
	for (let i = 0; i < data.data.requests.length; i++) {
		requests.appendChild (createRequest (data.data.requests [i]));
	}
	
	//todo remove
	console.log (data);
});