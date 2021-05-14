<%@ page import="com.paymentserviceclient.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Form</title>
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>
<link rel="stylesheet" href="views/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">


				<div class="container mt-5">
					<center><h4 class="mb-3">Payment Form</h4> </center>
					<form class="needs-validation" novalidate id = "paymentForm">
					<input type='hidden' id='hiddenPaymentIDSave' name='hiddenPaymentIDSave' value=''>
						<div class="row">
							<div class="col-md-12 mb-4">
								<label for="custID">Customer Id </label> <input type="text"
									class="form-control" id="custID" name="custID" placeholder="Customer ID"
									value="" required>
								<div class="invalid-feedback">Valid Customer ID is
									required.</div>
							</div>

						</div>

						<div class="row">
							<div class="col-md-4 mb-3">
								<label for="date">Date</label>
								<div class="input-group">

									<input type="date" class="form-control" id="date" name="date"
										placeholder="DATE" required>
									<div class="invalid-feedback" style="width: 100%;">Your
										payment date is required.</div>
								</div>
							</div>

							<div class="col-md-4 mb-3">
								<label for="pAmount">Payment Amount <span
									class="text-muted"></span></label> <input type="number"
									class="form-control" id="pAmount" name="pAmount" placeholder="000000.00">
								<div class="invalid-feedback">Please enter a Valid Amount.</div>
							</div>

							<div class="col-md-4 mb-3">
								<label for="taxRate">Tax Rate</label> <input type="number"
									class="form-control" id="taxRate" name="taxRate" placeholder="000.00" required>
								<div class="invalid-feedback">Please enter a Valid Tax
									Rate.</div>
							</div>
						</div>
						<div class="mb-3">
							<label for="description">Description </label>
							<textarea class="form-control" id="description" name="description"
								placeholder="Description on the payment !!!">
					</textarea>
						</div>

						<div class="row">
							<div class="col-md-5 mb-3">
								<label for="cardType">Card Type</label> <select
									class="custom-select d-block w-100" id="cardType" name="cardType" required>
									<option>Choose</option>
									<option>Credit Card</option>
									<option>Debit Card</option>
									<option>Payoneer Card</option>
								</select>
								<div class="invalid-feedback">Please select a valid Card.
								</div>
							</div>
							<div class="mb-3 col-md-4">
								<label for="cardNumber">Card Number </label> <input type="text"
									class="form-control" id="cardNumber" name="cardNumber" placeholder="Card Number"
									value="" required>
								<div class="invalid-feedback">Please enter a valid Card
									Number</div>
							</div>

							<div class="col-md-3 mb-3">
								<label for="cvn">Card CVN</label> <input type="text"
									class="form-control" id="cvn" name="cvn" placeholder="" required>
								<div class="invalid-feedback">CVN required.</div>
							</div>
						</div>

						<button class="btn btn-primary btn-lg btn-block" type="button" id="saveBtn">Save Payment</button>
					</form>
				</div><br><br>



				<div id='alertSuccess' name='alertSuccess'
					class='alert alert-success'></div>
				<div id='alertError' name='alertError' class='alert alert-danger'></div>

				<br><hr>
				<div id="divPaymentsGrid"  style='overflow-x:auto'>
					<%
						Payment paymentObjRead = new Payment();
					out.print(paymentObjRead.getPayment());
					%>
				</div>
<br><hr>
			</div>
		</div>
	</div>
</body>
</html>