package com.jctech.eshop.identity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.stream.Collectors;

import com.jctech.eshop.model.user.ERole;
import com.jctech.eshop.model.user.Role;
import com.jctech.eshop.model.user.User;

@Service
public class TokenUtil {
    //private static final long VALIDITY_TIME_MS =  2 * 60 * 60 * 1000; // 2 hours  validity
    private static final String AUTH_HEADER_NAME = "Authorization"; 
    
    @Value("${app.jwt.token.prefix}")
    public String TOKEN_PREFIX;
    
	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationMs}")
	private String jwtExpirationMs;

    
    public Optional<Authentication> verifyToken(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);

        if (token != null && !token.isEmpty()){
          final TokenUser user = parseUserFromToken(token.replace(TOKEN_PREFIX,"").trim());
          if (user != null) {
              return  Optional.of(new UserAuthentication(user));
          }
        }
        return Optional.empty();

      }

      //Get User Info from the Token
      public TokenUser parseUserFromToken(String token){

          Claims claims = Jwts.parser()
              .setSigningKey(jwtSecret)
              .parseClaimsJws(token)
              .getBody(); 
          
          User user = new User();
          user.setUserId( (String)claims.get("userId"));
          user.setRole(ERole.valueOf((String)claims.get("role")));
          user.setRoles(this.convertStringRoles((String)claims.get("roles")));
          if (user.getUserId() != null && user.getRole() != null) {
              return new TokenUser(user);
          } else {
              return null;
          }
          
          // claims.get("roles").stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet() );
      }

        

      public String createTokenForUser(TokenUser tokenUser) {
        return createTokenForUser(tokenUser.getUser());
      }

      public String createTokenForUser(User user) {
    	  //String strRoles = user.getRoles().stream().map(e -> e.toString()).reduce("", String::concat);
    	
        return Jwts.builder()
          //.setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
          .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpirationMs)))
          .setIssuedAt(new Date())
          .setSubject(user.getFullName())
          .claim("userId", user.getUserId())
          .claim("role", user.getRole().toString()) 
          .claim("roles", convertRolesToString(user.getRoles()))
          .signWith(SignatureAlgorithm.HS256, jwtSecret)
          .compact();
      }
      
      private String convertRolesToString(Set<Role> roles) {
    	  return roles.stream().map(e -> e.getName().name()).collect(Collectors.joining(",")); 
      }
      
      private Set<Role> convertStringRoles(String strRoles) {
    	  String[] rolesArr = (strRoles).split(",");  
          Set<Role> roles = new HashSet<>();
          for (int i = 0; i< rolesArr.length; i++){ 
        	  String r = rolesArr[i];
        	  roles.add(new Role(Role.fromString(r)));
          }
          return roles;
      }

}
