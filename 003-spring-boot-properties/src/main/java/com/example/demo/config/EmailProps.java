package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailProps {

    private String address;
    private boolean isEnable;
    private int postIndex;
    private String anotherAddress;

    private final Security security = new Security();

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public Security getSecurity() {
        return security;
    }

    public String getAnotherAddress() {
        return anotherAddress;
    }

    public void setAnotherAddress(String anotherAddress) {
        this.anotherAddress = anotherAddress;
    }

    public static class Security {
        private String password;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Security{" +
                    "password='" + password + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "EmailProps{" +
                "address='" + address + '\'' +
                ", isEnable=" + isEnable +
                ", anotherAddress='" + anotherAddress + '\'' +
                ", post index='" + postIndex + '\'' +
                ", security=" + security +
                '}' + '\n' +
                "   (address = null, because there are not getters/setters)";
    }
}
