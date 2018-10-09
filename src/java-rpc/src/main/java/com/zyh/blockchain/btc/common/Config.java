package com.zyh.blockchain.btc.common;

public class Config {
    //公司局域网服务器地址
//    public static final String BTC_RPC_ADDRESS = "192.168.0.156";
    //本机虚拟机调试地址
    public static final String BTC_RPC_ADDRESS = "192.168.0.114";

    public static final String BTC_RPC_PORT = "8835";

    public static final String BTC_RPC_USER = "test";

    public static final String BTC_RPC_PASSWORD = "test";

    public static final String USDT_RPC_ADDRESS = "192.168.0.114";

    public static final String USDT_RPC_PORT = "8334";

    public static final String USDT_RPC_USER = "test";

    public static final String USDT_RPC_PASSWORD = "test";

    public static final Config mUsd_Config = new Config(Config.USDT_RPC_ADDRESS,Config.USDT_RPC_USER,Config.USDT_RPC_PASSWORD,Config.USDT_RPC_PORT) ;
    public static final Config mBtc_Config = new Config(Config.BTC_RPC_ADDRESS,Config.BTC_RPC_USER,Config.BTC_RPC_PASSWORD,Config.BTC_RPC_PORT) ;

    private  String rpc_Address;
    private  String rpc_user;
    private  String rpc_password;
    private  String rpc_port;

    public Config(String rpc_Address, String rpc_user, String rpc_password, String rpc_port) {
        this.rpc_Address = rpc_Address;
        this.rpc_user = rpc_user;
        this.rpc_password = rpc_password;
        this.rpc_port = rpc_port;
    }

    public static String getBtcRpcAddress() {
        return BTC_RPC_ADDRESS;
    }

    public String getRpc_Address() {
        return rpc_Address;
    }

    public void setRpc_Address(String rpc_Address) {
        this.rpc_Address = rpc_Address;
    }

    public String getRpc_user() {
        return rpc_user;
    }

    public void setRpc_user(String rpc_user) {
        this.rpc_user = rpc_user;
    }

    public String getRpc_password() {
        return rpc_password;
    }

    public void setRpc_password(String rpc_password) {
        this.rpc_password = rpc_password;
    }

    public String getRpc_port() {
        return rpc_port;
    }

    public void setRpc_port(String rpc_port) {
        this.rpc_port = rpc_port;
    }
}
