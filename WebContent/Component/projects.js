$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "ProjectsAPI",
		type : type,
		data : $("#formItem").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});
});

function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	14
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidItemIDSave").val($(this).data("projectid"));
	$("#projectCode").val($(this).closest("tr").find('td:eq(0)').text());
	$("#projectName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#projectFund").val($(this).closest("tr").find('td:eq(2)').text());
	$("#projectDesc").val($(this).closest("tr").find('td:eq(3)').text());
});

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "ProjectsAPI",
		type : "DELETE",
		data : "projectID=" + $(this).data("projectid"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});

function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENT-MODEL================================================================
function validateItemForm() {
	// CODE
	if ($("#projectCode").val().trim() == "") {
		return "Insert Project Code.";
	}

	// NAME
	if ($("#projectName").val().trim() == "") {
		return "Insert Project Name.";
	}

	// PRICE-------------------------------
	if ($("#projectFund").val().trim() == "") {
		return "Insert Project Price.";
	}

	// is numerical value
	var tmpPrice = $("#projectFund").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Project Price.";
	}

	// convert to decimal price
	$("#projectFund").val(parseFloat(tmpPrice).toFixed(2));

	// DESCRIPTION------------------------
	if ($("#projectDesc").val().trim() == "") {
		return "Insert Project Description.";
	}

	return true;
}