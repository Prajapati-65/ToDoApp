package com.bridgeit.springToDoApp.Utility.Filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bridgeit.springToDoApp.Utility.token.VerifiedJWT;

/**
 * @author Om Prajapati
 * 
 * this class is use to check and varify the JWT token  
 * and set the userId to the request  using setAttribute
 *
 */
public class TokenInterceptor implements HandlerInterceptor {

	private Logger logger = (Logger) LogManager.getLogger(TokenInterceptor.class);

	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		int userId = VerifiedJWT.verify(request.getHeader("token"));
		if (userId == 0) {
			response.setStatus(511);
			logger.error("Token is not varified");
			return false;
		}
		request.setAttribute("userId", userId);
		logger.info("Token is varified");
		return true;
	}

}
