package com.zyh.blockchain.btc.client;

import com.zyh.blockchain.btc.ClientRpcInterface;
import com.zyh.blockchain.btc.ReceiveCoinListener;
import com.zyh.blockchain.btc.bean.Transaction;
import com.zyh.blockchain.btc.connect.UsdtConnClient;
import com.zyh.blockchain.btc.utls.UsdtSannerBlockJob;

import java.util.List;

public class UsdtRpcClent implements ClientRpcInterface {


    private UsdtConnClient mUsdtConnClient;

    private static UsdtRpcClent mUsdtRpcClent;

    private UsdtSannerBlockJob mUsdtSannerBlockJob;

    private boolean isStop = true;

    private UsdtRpcClent(){
         mUsdtConnClient = new UsdtConnClient();
         mUsdtSannerBlockJob = new UsdtSannerBlockJob(mUsdtConnClient);

    }

    public static UsdtRpcClent getInstance(){
         if (mUsdtRpcClent == null) {
             synchronized (UsdtRpcClent.class) {
                 if (mUsdtRpcClent == null) {
                     mUsdtRpcClent = new UsdtRpcClent();
                 }
             }
         }
         return mUsdtRpcClent;
    }


    @Override
    public String getNewAddress(String account) throws Throwable {
        return mUsdtConnClient.getNewAddress(account);
    }

    @Override
    public String getAddressPriKey(String address) throws Throwable {
        return mUsdtConnClient.getAddressPriKey(address);
    }

    @Override
    public Boolean wallteLock() {
        return mUsdtConnClient.wallteLock();
    }

    @Override
    public Boolean unlockWallte(String passphrase) {
        return mUsdtConnClient.unlockWallte(passphrase);
    }

    @Override
    public String sendToAddress(String address, double count) throws Throwable {
        return mUsdtConnClient.sendToAddress(address,count);
    }

    @Override
    public List<String> getAddressTransactionInfo(String address) {
        return null;
    }

    @Override
    public double getBalance(String address) throws Throwable {
        return mUsdtConnClient.getBalance(address);
    }


    public int getBlockCount()
    {
        try {
            return mUsdtConnClient.getBlockCount();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
         return  0;
    }


    public boolean addReceiveListener(ReceiveCoinListener mReceiveCoinListener)
    {
        return  mUsdtSannerBlockJob.addReceiveListener(mReceiveCoinListener);
    }

    public boolean removeReceiveListener(ReceiveCoinListener mReceiveCoinListener)
    {
        return  mUsdtSannerBlockJob.removeReceiveListener(mReceiveCoinListener);
    }


    @Override
    public void startReceiveCoinListener(ReceiveCoinListener mReceiveCoinListener) {
            if (isStop){
                addReceiveListener(mReceiveCoinListener);
                mUsdtSannerBlockJob.setStop(false);
                new Thread(mUsdtSannerBlockJob).start();
                isStop =false;
            }
    }

    @Override
    public void stopReceiveCoinListener() {
        if (!isStop){
            mUsdtSannerBlockJob.setStop(true);
            isStop =true;
        }
    }

    public static void main(String[] args)
    {
             getInstance().startReceiveCoinListener(new ReceiveCoinListener() {
                 @Override
                 public void receiveCoins(Transaction transaction) {
                     String address = transaction.getReceiveAddress();
                     double amount = transaction.getAmount();
                     System.out.println( address + " recevice " + amount + " usdt");
                 }
             });
    }
}
