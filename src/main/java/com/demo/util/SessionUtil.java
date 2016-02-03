package com.demo.util;

import com.demo.model.EnfordSystemUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

	public static int getUserId(HttpServletRequest req) {
		int userId = -1;
		HttpSession session = req.getSession();
		if(session != null) {
			if(session.getAttribute("user") == null) {
				userId = -1;
			}
			else {
				userId =  ((EnfordSystemUser) session.getAttribute("user")).getId();
			}
		}
		return userId;
	}
}
