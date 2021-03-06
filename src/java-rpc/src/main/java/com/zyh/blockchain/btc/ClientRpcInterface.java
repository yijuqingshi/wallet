package com.zyh.blockchain.btc;


import java.util.List;

public interface ClientRpcInterface {

    /**
     * 获取某账号的钱包地址
     * @param account
     * @return
     */
    public String getNewAddress(String account) throws Throwable;

    /**
     * 获取某地址的私钥
     * @param address
     * @return
     */
    public String getAddressPriKey(String address) throws Throwable;


    /**
     * 锁定钱包
     * @return
     */
    public Boolean wallteLock();

    /**
     * 解锁钱包
     * @return
     */
    public Boolean unlockWallte(String passphrase);


    /**
     * 转账到某地址金额
     * @param address
     * @param count
     * @return
     */
    public String  sendToAddress(String address,double count) throws Throwable;


    /**
     * 获取某地址的转账记录
     * @param address
     * @return
     */
    public List<String> getAddressTransactionInfo(String address);

    /**
     * 获取某地址的余额
     * @param address
     * @return
     */
    public double getBalance(String address) throws Throwable;



    /**
     * 开始监听接收币
     */
    public void  startReceiveCoinListener(ReceiveCoinListener mCoinListener);


    /**
     * 停止监听接收币
     */

    public void  stopReceiveCoinListener();

}
