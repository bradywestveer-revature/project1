"use strict";

const handleResponse = async (response, callback) => {
	const data = await response.json ();
	
	if (data.success) {
		if (callback !== undefined) {
			await callback (data);
		}
	}
	
	else {
		alert (data.message);
	}
	
	if (data.redirect !== null) {
		location.href = data.redirect;
	}
};