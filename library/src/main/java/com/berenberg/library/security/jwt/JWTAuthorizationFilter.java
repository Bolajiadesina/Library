package com.berenberg.library.security.jwt;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import static com.berenberg.library.security.jwt.SecurityConstants.TOKEN_PREFIX;
import static com.berenberg.library.security.jwt.SecurityConstants.HEADER_STRING;
import static com.berenberg.library.security.jwt.SecurityConstants.SECRET;





import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bolaji
 * created on 27/08/2023
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private static Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HEADER_STRING);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req, res);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse res) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			logger.info("Verifying JWT");
			String user = null;
			try {
				user = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(token.replace(TOKEN_PREFIX, ""))
						.getSubject();
			} catch(SignatureVerificationException | AlgorithmMismatchException | JWTDecodeException e) {
				logger.error(e.toString());
				request.setAttribute("error-message", "Invalid Token");
			} catch (JWTVerificationException e) {
				logger.error(e.toString());
				request.setAttribute("error-message", e.getMessage());
			}
			if (user != null) {
				logger.info("JWT Verification successful. Client ID: {}", user);
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
