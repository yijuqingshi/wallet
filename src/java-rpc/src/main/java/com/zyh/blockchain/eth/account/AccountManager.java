package com.zyh.blockchain.eth.account;

import com.zyh.blockchain.eth.common.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 *账号管理
 */
public class AccountManager {
    private final Logger log = LogManager.getLogger(getClass());
    private static Admin admin  = Admin.build(new HttpService(Config.RPC_URL));

    /**
     * 创建账号
     */
    public void createNewAccount(String password) {
        try {
            NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
            String address = newAccountIdentifier.getAccountId();
            //todo 保存keystore
            log.info("new account address " + address);
        } catch (IOException e) {
            log.error("createNewAccount exception",e);
        }
    }

    /**
     * 获取账号列表
     */
    public void getAccountList() {
        try {
            PersonalListAccounts personalListAccounts = admin.personalListAccounts().send();
            List<String> addressList = personalListAccounts.getAccountIds();
            log.info("account size " + addressList.size());

        } catch (IOException e) {
            log.error("getAccountList exception",e);
        }
    }

    /**
     * 账号解锁
     */
    public boolean unlockAccount(String address,String password ) {
        //账号解锁持续时间 单位秒 缺省值300秒
        BigInteger unlockDuration = BigInteger.valueOf(60L);
        Boolean isUnlocked = false;
        try {
            PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(address, password, unlockDuration).send();
            isUnlocked = personalUnlockAccount.accountUnlocked();
        } catch (IOException e) {
            log.error("unlockAccount exception",e);
        }
        log.info("account unlock " + isUnlocked);
        return isUnlocked;
    }
}
