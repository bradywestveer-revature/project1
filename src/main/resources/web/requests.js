"use strict";

const usernameText = document.getElementById ("usernameText");
const logoutButton = document.getElementById ("logoutButton");

const requestFilter = document.getElementById ("requestFilter");

const requestsElement = document.getElementById ("requests");

const requestTemplate = document.getElementById ("requestTemplate");
const requestControlsTemplate = document.getElementById ("requestControlsTemplate");
const requestApprovedMessageTemplate = document.getElementById ("requestApprovedMessageTemplate");
const requestDeniedMessageTemplate = document.getElementById ("requestDeniedMessageTemplate");

let requests = [];

const formatAmount = amount => {
	return amount.toLocaleString ("en-US", { style: "currency", currency: "USD" });
};

const capitalCase = string => {
	return string.charAt (0) + string.slice (1).toLowerCase ();
};

const formatDate = date => {
	return new Date (date).toLocaleDateString ();
};

const updateRequest = async (id, approved) => {
	await handleResponse (await fetch ("/api/requests", {
		method: "PUT",
		
		body: JSON.stringify ({
			id: id,
			approved: approved
		})
	}), async data => {
		clearRequests ();
		
		await getRequests ();
		
		createRequests ();
	});
};

const createRequest = request => {
	const requestElement = requestTemplate.content.cloneNode (true).children [0];
	
	//id
	requestElement.id = request.id;
	
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
			//approve/deny buttonss
			const requestControls = requestControlsTemplate.content.cloneNode (true).children [0];
			
			//approve button
			requestControls.children [0].addEventListener ("click", () => {
				updateRequest (parseInt (requestElement.id), true);
			});
			
			//deny button
			requestControls.children [1].addEventListener ("click", () => {
				updateRequest (parseInt (requestElement.id), false);
			});
			
			requestElement.children [1].appendChild (requestControls);
		}
	}
	
	else {
		//approved/denied status message
		const requestStatusMessage = (request.status === "APPROVED" ? requestApprovedMessageTemplate : requestDeniedMessageTemplate).content.cloneNode (true).children [0];
		
		requestStatusMessage.children [0].childNodes [1].textContent = " on " + formatDate (request.resolved);
		
		requestStatusMessage.children [1].textContent = "by " + request.resolver;
		
		requestElement.children [1].appendChild (requestStatusMessage);
	}
	
	return requestElement;
};

const clearRequests = () => {
	requestsElement.replaceChildren ();
};

const createRequests = () => {
	//sort requests
	requests.sort ((a, b) => {
		//sort pending above other statuses
		if (a.status === "PENDING" && b.status !== "PENDING") {
			return -1;
		}
		
		if (b.status === "PENDING" && a.status !== "PENDING") {
			return 1;
		}
		
		//sort by activity (most recent on top)
		if (new Date (a.resolved) > new Date (b.resolved) || new Date (a.submitted) > new Date (b.submitted)) {
			return -1;
		}
		
		else {
			return 1;
		}
	});
	
	for (let i = 0; i < requests.length; i++) {
		if (requestFilter.value === "NONE" || requests [i].status === requestFilter.value) {
			requestsElement.appendChild (createRequest (requests [i]));
		}
	}
};

const getRequests = async () => {
	await handleResponse (await fetch ("/api/requests"), data => {
		requests = data.data;
	});
};

(async () => {
	await getRequests ();
	
	createRequests ();
}) ();

usernameText.textContent = sessionStorage.username;

logoutButton.addEventListener ("click", async () => {
	//remove user data from sessionStorage
	sessionStorage.clear ();
	
	handleResponse (await fetch ("/api/sessions", {
		method: "DELETE"
	}));
});

requestFilter.addEventListener ("change", () => {
	clearRequests ();
	
	createRequests ();
});