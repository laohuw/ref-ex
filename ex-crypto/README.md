## Reference page
* [Digital Signatures in Java](https://www.baeldung.com/java-digital-signature)
* [github code](https://github.com/eugenp/tutorials/blob/master/libraries-security/src/main/java/com/baeldung/digitalsignature/DigitalSignatureUtils.java)
## Steps to generate the keystore and certificate

### generate key pair
keytool -genkeypair -alias senderKeyPair -keyalg RSA -keysize 2048 --dname "CN=algo" -validity 365 -storetype JKS -keystore sender_keystore.jks -storepass changeit

### export public key certificate (self-signed)
keytool -exportcert -alias senderKeyPair -storetype JKS -keystore sender_keystore.jks -file sender_certificate.cer -rfc -storepass changeit

### create CSR(certificate signing request)
keytool -certreq -alias senderKeyPair -storetype JKS  -keystore sender_keystore.jks -file -rfc  -storepass changeit > sender_certificate.csr

### generate receiverKeyStore
keytool -genkeypair -alias receiverKeyPair -keyalg RSA -keysize 2048 -dname "CN=algo" -validity 365 -storetype JKS -keystore receiver_keystore.jks -storepass changeit

### List keypairs in keystore
keytool -list -keystore receiver_keystore.jks

### Clean the keystore
keytool -delete -alias receiverKeyPair -storepass changeit -keystore receiver_keystore.jks

### import the public certificate to keystore
keytool -importcert -alias receiverKeyPair -storetype JKS -keystore receiver_keystore.jks -file sender_certificate.cer -rfc -storepass changeit
