package com.zhu.facepay.utils;

public class Ip {


    public static String getPublicIpv4() {
        String ip = "";
        try {
            String[] urls = {
                "http://checkip.amazonaws.com",
                "https://api.ipify.org",
                "http://icanhazip.com"
            };
            
            for (String url : urls) {
                try {
                    java.net.URL myIP = new java.net.URL(url);
                    java.io.BufferedReader in = new java.io.BufferedReader(
                        new java.io.InputStreamReader(myIP.openStream())
                    );
                    ip = in.readLine().trim();
                    if (ip != null && !ip.isEmpty()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://"+ip;
    }

    public static String getLocalIpv4() {
        String ip = "";
        try {
            java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
            ip = inetAddress.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://"+ip;
    }
    
}
