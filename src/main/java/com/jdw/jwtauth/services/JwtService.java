package com.jdw.jwtauth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    protected static final String SECRET_KEY = "e94ab471c24ccd39cd1cff7e655bbf47b7e661063488e15ad65e11401d4f31577086ecca8acf0f5eb30e33505eb8010bb9176919055977dbb12eb8cf3467c99c28e04b385d87b935b91512b306d910094e60cdd5a2e7ca6064f58ef4c36112258f5239308bef924d32aa661d309bdb3579b00d0e89f09615fb168e7b807dee182b687ee8ee56f90e1c87c059e5a4c4a72eaaa92ebb5ada3688b370f5121cbab921147b92d8b0610d3f81621c33a8410c1e839f85104b753069b2e2996864c6e7b5c734140ee76ae9f266e83746ef83f5edc55c54fe59779a88decb64fb0137329ce4a95f7d7ba3ff13c43db1a3d161bc2a2c5ec0513d677801ef901db37ae0e4f115db8ce2968061b5351df56e1cc5e11afdb8ad5d9a00dba2dece211c943adb2de02ef018713927020462ca2ac9ac266ccc13ffc19251830b3705dec5e863c46dcf5f09545d773e46fbd6927d630dcf694ce4dc0f36e95003374f2d8a1d5a21898995ef63e6d87562b4379abca741b7ad97458a1c45e5be18f30374912808a6e9f3c8e5fd1667ce407d5082b74c9828b3b956b69ea852ba182346a402299745daeb1f1a48cd4dd667ebafcac238a93fd072986a9f1e9ac801b2ebbd74c5b2c0a2e7222dda718642faca4b25e4de86f3fa5ccb98cfda6a7b18fbc673198494473739179f3cc6115ea689136df80fb6d3878e17821b5331f793787b460bea1afac8d19f94025fc4fa6567a6e6fbb694d2f1acfda7ca45936849723498fc9c45beac860034e7f6cd90893a6b09929a0c5933198d850c1447b4d4a786ef1954d844cadca721eee0eef3c1b2c631f3a47d03e725d046a50c585278547c87884f7b28018cdf1dbe702a6130bc77d8e9e34a8912c745f127ecf2a4473d9a5e278f4778b6d8bfc2239083c47f08783486cbdaaa1d7f021b118c5a98b39e6e64b90b0f4f4486b848b509bac62f52251160ff1d6310ec6213786c60608a98e245ee500ba813cfc8ade7aad2ff59ffbbac5b0c8143c5ab46750b6ed4a73db15edf21962c9fa1ed0a87a986efc571961073bbf5049b8c1c8f3412a8eb0fdab69ebfaceb7031fb40804309045a7616fa9a16959c609effa87e5ac7d692ca0c094b42d0c2dc8dcb28eebe6e6c943921c2e1227438c87042f674264fdf76de7b2dcd059890fe8e66c1eee4f288adaddc33f71d6daa3afc9a7fd4358a855a00c9f71cc4ef988bc99f679a4cd5c9aa4500de1182fe1ed9de769284fe3007ab32e785882c6fcde2d574ee8db006ca8a25b9123d65bd012a1767878f95d90a4522913e32c8df48d4dd485ec8fd0303017df68b0e90ca44937353dcbcc93661eefc07c879c8d16a2f5ac2cf637b348b64a3d03ded51ec789e671b8435a4addb473035931b39aedc3947eb83d56ab98d689aa9f72887d47a6ef03eca89dac2c68ac94dbaaa93c163b069ecf6d9d291491758ff7096b4f9e56de48fe3772a62c1501c17fcafe8f4c4147168a2c894e3d0efd9fcfb3d2c236884893c34226c7bfd107e5c44a03e2f4704fa5ed6264e803e7292f0da5fa9b00476a39b65b9aa1bafb3f152defcbcf9ecfebd1683e2555966f44fb65c1f59ef5dfd2f1617af6112e1f3eab6d8313e541ce1bd003edf9438eb842b10877875f58850b83612dc8c44fb2c80a40ae227b1606bef8f5a8fb6927e3beb8fc7a8d11ddafacf02ee70c5a3424d524d28293493d604fbcf5e8cca068f5b2cb8b9a4119f68589f9ffd35adb7985ef66688a26e8014f1bb0e8136eb5965c033a84c06465c3e61ffe845f569e6e5a494c4738439288341968f288c33d0ff45eed95b99d7e98f8412a37acdc28f6bfea35b1fad80e7c41d41b3ae51bab52723777f77657363bab85f6d4e02cd16c99f056c89790e7d6f5746acfd56c354bdbfa6ebc2c3fb09a86328b72af55929a7f49e034c895c924d4c0cb1512f53c5e05a182350e2ac0c8b6c1042b270f7b307b3c0a8b9af1098b010230285b476ff17589f30c757c761d6449c6a188607badc8b0db56309560fc5201d6c5b28384dd8e5b7256f8a4c1faff848b7385b169a379b7ff517779a12dca27232b00b63f7669a59cc1c4e04ae40fda2328dd9a2e8c58d152b49d9f3cab200b527802cecb2d4266fc894c427ad55f46197829710cfa1d4d6129a983d1c543b049f255414a93eb5e64becd8380fea100009bb1dbc0307c3b3c84496c8f3d6dd6104f55ca8f2f3e1260fd078141ee38f3d4d07a32d9e5cce9bfdc7b1c687503e8ff01d55bbae46b23e637a11b104313be1bcf61909f69adc04c5f2deeb79ae73143e1784d02b60d0c74bcff8307939893d73dc0c9591783a561ab7f0710b4947dd402b252c661ba4a0d339d906504ab70f885d6154d055bfbc4dd6af8782244994ccaafbe0f916a3b267698f83699d241403ffb31feef76cf94c5bc8188c6ac6a459c1fdd9e772aad2402b4d03135cdc4414e8b71172b629c93c903d230d7dc8a8476e14309dc7a8418a67242a69c3a34ce0f7b5ebbca4cf93b0faf2dac8288d116399c7d7af41202081cf458d46ee00bd9227489470babf50492c29f6633667d5ba8dda48869fb8e8104bfff1445bbf204111dfb23e9a92c1a88184d7646e140b22f44bcd9d86c7f2a813c6b4935b3bf742c1d025483382c3f50a9fec17bcde309f3511098be45d0a331e0edb837ba2ed5de6ae8b720d64b3bf53b67f5328470494c8517c83306ac9e8b79363bfb939b71703e7ae430366dad6d7e4b0e9d91067709fe59919b4592c072d436c3d3161273fcc0213246a09831c4b0a23a9c0f573241ce3d81a139f7d98d14b4c9ccb1b576e0e19ae35ee0930a309e86ab581448f47a46f2c368cc381634484253e506573ab207413b187109e97ed4b193f347355";
    protected static final long JWT_EXPIRATION_TIME_MS = Duration.of(2, ChronoUnit.HOURS).toMillis();
    public static String getEmailAddress(String authorizationHeader) {
        log.info("Retrieving email address with: authorizationHeader={}", authorizationHeader);
        return extractEmailAddress(getJwtToken(authorizationHeader));
    }

    public static String getJwtToken(String authorizationHeader) {
        log.info("Retrieving JWT token with: authorizationHeader={}", authorizationHeader);
        return authorizationHeader.substring(7);
    }

    public static String extractEmailAddress(String jwtToken) {
        log.info("Extracting email address with: jwtToken={}", jwtToken);
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public static <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        log.info("Extracting claim with: jwtToken={}", jwtToken);
        return claimsResolver.apply(extractAllClaims(jwtToken));
    }

    public static String generateToken(UserDetails userDetails) {
        log.info("Generating empty extra claims token with: emailAddress={}", userDetails.getUsername());
        return generateToken(new HashMap<>(), userDetails);
    }

    public static String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        log.info("Generating token with: emailAddress={}", userDetails.getUsername());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME_MS))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        log.info("Checking token validation with: jwtToken={}", jwtToken);
        return extractEmailAddress(jwtToken).equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    public static boolean isTokenExpired(String jwtToken) {
        log.info("Checking token expiration with: jwtToken={}", jwtToken);
        return extractExpiration(jwtToken).before(new Date());
    }

    protected static Date extractExpiration(String jwtToken) {
        log.info("Extracting expiration with: jwtToken={}", jwtToken);
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    protected static Claims extractAllClaims(String jwtToken) {
        log.info("Extracting all claims with: jwtToken={}", jwtToken);
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    protected static Key getSignInKey() {
        log.info("Retrieving sign in key");
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
