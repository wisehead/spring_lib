#1.tokenTest

```
tokenTest
--//携带username，password信息，生成签名
--token = JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
                    .withClaim("password", password).withExpiresAt(date)
                    .sign(algorithm);

```