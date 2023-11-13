package com.berenberg.library.security.jwt;

/**
 * @author Bolaji
 * created on 27/08/2023
 */

public class SecurityConstants {
	    public static final String SECRET = "SecretKeyToGenJWTsForBerenberLibrary";
	    public static final String ISSUER = "Berenberg";
	    public static final long EXPIRATION_TIME = 3_600_000; // 1hr
		public static final long INVALID_EXPIRATION_TIME = 0_0; // 1hr
	    public static final String TOKEN_PREFIX = "Bearer ";
	    public static final String HEADER_STRING = "Authorization";
	    public static final String GET_AUTH_TOKEN = "/api/v1/library/getToken";
	   
	   
	

}
