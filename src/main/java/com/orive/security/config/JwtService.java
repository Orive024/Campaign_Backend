package com.orive.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.orive.security.user.User;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(User user) {
	    Map<String, Object> claims = new HashMap<>();
	    claims.put("firstname", user.getFirstname());
	    claims.put("lastname", user.getLastname());
	    claims.put("email", user.getEmail());
	    claims.put("mobilenumber", user.getMobilenumber());
	    claims.put("username", user.getUsername()); // Use the registered username
	    claims.put("role", user.getRole().name());
	    
	    return buildToken(claims, user.getUsername(), jwtExpiration); // Pass the registered username as subject
	}


  public String generateRefreshToken(UserDetails userDetails) {
    return buildToken(new HashMap<>(), userDetails.getUsername(), refreshExpiration);
  }

  private String buildToken(
      Map<String, Object> claims,
      String subject,
      long expiration
  ) {
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
//package com.orive.security.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import com.orive.security.user.User;
//
//@Service
//public class JwtService {
//
//  @Value("${application.security.jwt.secret-key}")
//  private String secretKey;
//  @Value("${application.security.jwt.expiration}")
//  private long jwtExpiration;
//  @Value("${application.security.jwt.refresh-token.expiration}")
//  private long refreshExpiration;
//
//  public String extractUsername(String token) {
//    return extractClaim(token, Claims::getSubject);
//  }
//
//  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//    final Claims claims = extractAllClaims(token);
//    return claimsResolver.apply(claims);
//  }
//
////  public String generateToken(UserDetails userDetails) {
////    return generateToken(new HashMap<>(), userDetails);
////  }
//  public String generateToken(User user) {
//        Map<String, Object> extraClaims = new HashMap<>();
//        // Add additional claims to the extraClaims map
//        extraClaims.put("firstname", user.getFirstname());
//        extraClaims.put("lastname", user.getLastname());
//        extraClaims.put("email", user.getEmail());
//        // Add mobile number if available in User entity
//        extraClaims.put("mobilenumber", user.getMobilenumber()); // Assuming a getMobile() method exists in User entity
//        extraClaims.put("role", user.getRole().name());
//        
//        return buildToken(extraClaims, user, jwtExpiration);
//      }
//
//  public String generateToken(
//      Map<String, Object> extraClaims,
//      UserDetails userDetails
//  ) {
//    return buildToken(extraClaims, userDetails, jwtExpiration);
//  }
//
//  public String generateRefreshToken(
//      UserDetails userDetails
//  ) {
//    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
//  }
//
//  private String buildToken(
//          Map<String, Object> extraClaims,
//          UserDetails userDetails,
//          long expiration
//  ) {
//    return Jwts
//            .builder()
//            .setClaims(extraClaims)
//            .setSubject(userDetails.getUsername())
//            .setIssuedAt(new Date(System.currentTimeMillis()))
//            .setExpiration(new Date(System.currentTimeMillis() + expiration))
//            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//            .compact();
//  }
//
//  public boolean isTokenValid(String token, UserDetails userDetails) {
//    final String username = extractUsername(token);
//    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//  }
//
//  private boolean isTokenExpired(String token) {
//    return extractExpiration(token).before(new Date());
//  }
//
//  private Date extractExpiration(String token) {
//    return extractClaim(token, Claims::getExpiration);
//  }
//
//  private Claims extractAllClaims(String token) {
//    return Jwts
//        .parserBuilder()
//        .setSigningKey(getSignInKey())
//        .build()
//        .parseClaimsJws(token)
//        .getBody();
//  }
//
//  private Key getSignInKey() {
//    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//    return Keys.hmacShaKeyFor(keyBytes);
//  }
//}
