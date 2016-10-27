/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */

package org.geoserver.geofence.core.model.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * 
 * @author ETj <etj at geo-solutions.it>
 */
public class PwEncoder {

    private static byte[] getKey() {
        String strKey = System.getProperty("GEOFENCE_PWENCODER_KEY");
        if (strKey == null || strKey.length() < 16) {
          strKey = "installation dependant key needed";
        }
        return strKey.substring(0, 16).getBytes();
    }

    public static String encode(String msg) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(getKey(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] input = msg.getBytes();
            byte[] encrypted = cipher.doFinal(input);
            return DatatypeConverter.printBase64Binary(encrypted);

        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error while encoding", ex);
        } catch (NoSuchPaddingException ex) {
            throw new RuntimeException("Error while encoding", ex);
        } catch (IllegalBlockSizeException ex) {
            throw new RuntimeException("Error while encoding", ex);
        } catch (BadPaddingException ex) {
            throw new RuntimeException("Error while encoding", ex);
        } catch (InvalidKeyException ex) {
            throw new RuntimeException("Error while encoding", ex);
        }
    }

    public static String decode(String msg) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(getKey(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            byte[] de64 = DatatypeConverter.parseBase64Binary(msg);
            byte[] decrypted = cipher.doFinal(de64);

            return new String(decrypted);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error while encoding", ex);
        } catch (NoSuchPaddingException ex) {
            throw new RuntimeException("Error while encoding", ex);
        } catch (IllegalBlockSizeException ex) {
            throw new RuntimeException("Error while encoding", ex);
        } catch (BadPaddingException ex) {
            throw new RuntimeException("Error while encoding", ex);
        } catch (InvalidKeyException ex) {
            throw new RuntimeException("Error while encoding", ex);
        }
    }
}
