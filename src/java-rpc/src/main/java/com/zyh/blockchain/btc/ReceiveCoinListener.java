package com.zyh.blockchain.btc;

import com.zyh.blockchain.btc.bean.Transaction;

public interface ReceiveCoinListener {

    public void receiveCoins(Transaction transaction);

}
