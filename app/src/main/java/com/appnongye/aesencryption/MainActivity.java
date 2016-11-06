package com.appnongye.aesencryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    boolean mEncryptStatus = false; //加密状态：false-未加密; true-已经加密;
    String encryptKey = "xyz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView msgTextView = (TextView) findViewById(R.id.text_view);
        final Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEncryptStatus) {
                    String result;
                    result = aesEncrypt(msgTextView.getText().toString());
                    msgTextView.setText(result);
                    mEncryptStatus = true;
                    button.setText("decrypt");
                } else {
                    String result;
                    result = aesDecrypt(msgTextView.getText().toString());
                    msgTextView.setText(result);
                    mEncryptStatus = false;
                    button.setText("encrypt");
                }
            }
        });
    }

    private String aesDecrypt(String msg) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(encryptKey.getBytes()));
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
            byte[] decryptBytes = cipher.doFinal(msg.getBytes("UTF-8"));
            return new String(decryptBytes, "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return "sth";

}

    private String aesEncrypt(String origin) {
        KeyGenerator kgen = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(encryptKey.getBytes()));
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
            return String.valueOf(cipher.doFinal(origin.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "something wrong happed";
    }
}
