package org.springclouddev.iot.driver.key;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.stack.core.util.SelfSignedCertificateBuilder;
import org.eclipse.milo.opcua.stack.core.util.SelfSignedCertificateGenerator;
import org.springclouddev.iot.common.utils.Dc3Util;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.regex.Pattern;


@Slf4j
public class KeyLoader {
    @Getter
    private X509Certificate clientCertificate;
    @Getter
    private KeyPair clientKeyPair;

    private static final Pattern IP_ADDR_PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final char[] PASSWORD = "password".toCharArray();
    private static final String CLIENT_ALIAS = "client-ai";


    public KeyLoader load(Path baseDir) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        Path serverKeyStore = baseDir.resolve("dc3-opc-ua-client.pfx");

        if (!Files.exists(serverKeyStore)) {
            keyStore.load(null, PASSWORD);
            KeyPair keyPair = SelfSignedCertificateGenerator.generateRsaKeyPair(2048);
            SelfSignedCertificateBuilder builder = new SelfSignedCertificateBuilder(keyPair)
                    .setCommonName("DC3 Opc Ua Client")
                    .setOrganization("dc3")
                    .setOrganizationalUnit("iot")
                    .setLocalityName("BeiJing")
                    .setStateName("BJ")
                    .setCountryCode("ZN")
                    .setApplicationUri("urn:dc3:opc:ua:client")
                    .addDnsName("localhost")
                    .addIpAddress("127.0.0.1");

            // Get as many hostnames and IP addresses as we can listed in the certificate.
            for (String hostname : Dc3Util.getHostNames("0.0.0.0")) {
                if (IP_ADDR_PATTERN.matcher(hostname).matches()) {
                    builder.addIpAddress(hostname);
                } else {
                    builder.addDnsName(hostname);
                }
            }

            X509Certificate certificate = builder.build();
            keyStore.setKeyEntry(CLIENT_ALIAS, keyPair.getPrivate(), PASSWORD, new X509Certificate[]{certificate});
            try (OutputStream out = Files.newOutputStream(serverKeyStore)) {
                keyStore.store(out, PASSWORD);
            }
        } else {
            try (InputStream in = Files.newInputStream(serverKeyStore)) {
                keyStore.load(in, PASSWORD);
            }
        }

        Key serverPrivateKey = keyStore.getKey(CLIENT_ALIAS, PASSWORD);
        if (serverPrivateKey instanceof PrivateKey) {
            clientCertificate = (X509Certificate) keyStore.getCertificate(CLIENT_ALIAS);
            PublicKey serverPublicKey = clientCertificate.getPublicKey();
            clientKeyPair = new KeyPair(serverPublicKey, (PrivateKey) serverPrivateKey);
        }

        return this;
    }

}
