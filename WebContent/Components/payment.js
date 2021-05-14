$(document).ready(function () {
    if ($("#alertSuccess").text().trim() == "") {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
});



$(document).on("click", "#saveBtn", function (event) {
    // Clear alerts---------------------
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
    // Form validation-------------------
    var status = validatePaymentForm();
    if (status != true) {
        $("#alertError").text(status);
        $("#alertError").show();
        return;
    }
    // If valid------------------------
   var type = ($("#hiddenPaymentIDSave").val() == "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "PaymentServiceClientAPI",
            type: type,
            data: $("#paymentForm").serialize(),
            dataType: "text",
            complete: function (response, status) {
                onPaymentSaveComplete(response.responseText, status);
            }
        }); 
});

function onPaymentSaveComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "successfull") {
            $("#alertSuccess").text("Successfully Saved.");
            $("#alertSuccess").show();
            $("#divPaymentsGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while Saving!!!");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while Saving!!!");
        $("#alertError").show();
    }
    $("#hiddenPaymentIDSave").val("");
    $("#paymentForm")[0].reset();
}

$(document).on("click", ".btnUpdate", function (event) {
    $("#hiddenPaymentIDSave").val($(this).data("paymentid"));
    $("#custID").val($(this).closest("tr").find('td:eq(1)').text());
    $("#date").val($(this).closest("tr").find('td:eq(2)').text());
    $("#pAmount").val($(this).closest("tr").find('td:eq(3)').text());
    $("#taxRate").val($(this).closest("tr").find('td:eq(4)').text());
    $("#description").val($(this).closest("tr").find('td:eq(5)').text());
    $("#cardType").val($(this).closest("tr").find('td:eq(6)').text());
    $("#cardNumber").val($(this).closest("tr").find('td:eq(7)').text());
    $("#cvn").val($(this).closest("tr").find('td:eq(8)').text());
});

$(document).on("click", ".btnRemove", function (event) {
    $.ajax(
        {
            url: "PaymentServiceClientAPI",
            type: "DELETE",
            data: "paymentID=" + $(this).data("paymentid"),
            dataType: "text",
            complete: function (response, status) {
                onPaymentDeleteComplete(response.responseText, status);
            }
        });
});

function onPaymentDeleteComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "successfull") {
            $("#alertSuccess").text("Successfully deleted.");
            $("#alertSuccess").show();
            $("#divPaymentsGrid").html(resultSet.data);
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



//CLIENT-MODEL================================================================
function validatePaymentForm() {
 // CODE
 if ($("#custID").val().trim() == "") {
     return "Insert Customer ID !!!";
 }

 // is numerical value
  var tmpCust = $("#custID").val().trim();
 if (!$.isNumeric(tmpCust)) {
     return "Insert a valid Numeric Customer ID !!!";
 }
 
 if ($("#date").val().trim() == "") {
     return "Insert Correct Date !!!";
 }
 
 if ($("#pAmount").val().trim() == "") {
     return "Insert Payment Amount !!!";
 }
 // is numerical value
 var tmpAmount = $("#pAmount").val().trim();
 if (!$.isNumeric(tmpAmount)) {
     return "Insert a numerical value for Payment Amount";
 }
 
 $("#pAmount").val(parseFloat(tmpAmount).toFixed(2));
 

 if ($("#taxRate").val().trim() == "") {
     return "Insert Tax Rate !!!";
 }
 // is numerical value
 var tmpTaxRate = $("#taxRate").val().trim();
 if (!$.isNumeric(tmpTaxRate)) {
     return "Insert a numerical value for Tax Rate";
 }
 $("#taxRate").val(parseFloat(tmpTaxRate).toFixed(2));
 
 if ($("#description").val().trim() == "") {
     return "Insert Description !!!";
 }
 
 
 if ($("#cardType").val().trim() == "Choose") {
     return "Choose Valid Card Type !!!";
 }
 
 if ($("#cardNumber").val().trim() == "") {
     return "Insert Card Number !!!";
 }
 var tmpCardN = $("#cardNumber").val().trim();
 if (!$.isNumeric(tmpCardN)) {
     return "Insert a valid Card Number";
 }
 
 if ($("#cvn").val().trim() == "") {
     return "Insert CVN Number !!!";
 }
 var tmpCVN = $("#cvn").val().trim();
 if (!$.isNumeric(tmpCVN)) {
     return "Insert a valid CVN Number";
 }
 
 return true;
}