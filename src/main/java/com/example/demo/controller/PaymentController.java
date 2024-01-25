package com.example.demo.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entities.Users;
import com.example.demo.services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PaymentController {
	@Autowired
	UsersService service;

	@GetMapping("/pay")
	public String pay() {
		return "pay";
	}
	
	@GetMapping("/payment-success")
	public String paymentSuccess(HttpSession session) {
		String mail=(String) session.getAttribute("email");
		Users u=service.getUser(mail);
		u.setPremium(true);
		service.updateUser(u);
		return "customerHome";
	}
	
	//https://github.com/deep473/tuneHub

	@SuppressWarnings("finally")
	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder() {

		int  amount  = 5000;
		Order order=null;
		try {
			RazorpayClient razorpay=new RazorpayClient("rrzp_test_VlhOQYr2j1VHBZ", "Oxn7dV7V4oBALst7urLj8IIv");

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount*100); // amount in the smallest currency unit
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "order_rcptid_11");

			order = razorpay.orders.create(orderRequest);

		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		finally {
			return order.toString();
		}
	}
	
	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam String orderId, @RequestParam String  paymentId,@RequestParam String signature) {
	
		try {
			RazorpayClient razorpayClient=new RazorpayClient("rrzp_test_VlhOQYr2j1VHBZ", "Oxn7dV7V4oBALst7urLj8IIv");

	            String verificationData = orderId + "|" + paymentId;

              boolean isValidSignature =  Utils.verifySignature(verificationData, signature,"Oxn7dV7V4oBALst7urLj8IIv");
	
	    return isValidSignature;
		}
		 catch (RazorpayException e) {
				e.printStackTrace();
				return false;
	
}
}
}




