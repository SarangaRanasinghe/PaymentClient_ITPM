package com.paymentserviceclient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class PaymentServiceClientAPI
 */
@WebServlet("/PaymentServiceClientAPI")
public class PaymentServiceClientAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Payment payment = new Payment();

    /**
     * Default constructor. 
     */
    public PaymentServiceClientAPI() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		JsonObject output = payment.insertPayment(
				Integer.parseInt(request.getParameter("custID")),
				request.getParameter("date"),
				Double.parseDouble(request.getParameter("pAmount")),
				Double.parseDouble(request.getParameter("taxRate")),
				request.getParameter("description"),
				request.getParameter("cardType"),
				request.getParameter("cardNumber"),
				request.getParameter("cvn"));
		response.getWriter().write(output.toString());
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String> paras = getParasMap(request);
		JsonObject output = payment.updatePayment(
				Integer.parseInt(paras.get("hiddenPaymentIDSave").toString()),
				Integer.parseInt(paras.get("custID")),
				paras.get("date"),
				Double.parseDouble(paras.get("pAmount")),
				Double.parseDouble(paras.get("taxRate")),
				paras.get("description"),
				paras.get("cardType"),
				paras.get("cardNumber"),
				paras.get("cvn"));
		
		response.getWriter().write(output.toString());
		
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paras = getParasMap(request);
		System.out.println("ID: "+paras.get("paymentID").toString());
		JsonObject output = payment.deletePayment(Integer.parseInt(paras.get("paymentID").toString()));
		response.getWriter().write(output.toString());
	}
	
	private static Map<String,String> getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
					scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params)
			{
				String[] p = param.split("=");
				map.put(p[0], java.net.URLDecoder.decode(p[1], StandardCharsets.UTF_8.name()));
			}
		}
		catch (Exception e)
		{
		}
		return map;
	}

}
