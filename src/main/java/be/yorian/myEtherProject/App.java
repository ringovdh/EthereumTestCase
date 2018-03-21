package be.yorian.myEtherProject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Principal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import be.yorian.myEtherProject.contract.GreeterO;
import be.yorian.myEtherProject.helper.ServerHelper;
import rx.Subscription;

/**
 * Hello world!
 *
 */
public class App {
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		new App().run();
	}

	private void run() throws Exception {

		System.out.println("let's start this new project");
		
		//setup the server
		ServerHelper helper = new ServerHelper();
		Web3j web3Server = helper.startserver();
		System.out.println("Connected to Ethereum client version: "
				+ web3Server.web3ClientVersion().send().getWeb3ClientVersion());
		
		// load the ethereum keystore (wallet)
		Credentials creds = WalletUtils.loadCredentials("ringo!1234",
				"/Users/ringo/Library/Ethereum/testnet/keystore/UTC--2018-03-17T18-38-50.767000000Z--1a77bd30a485ea3903ac19a3e85ec59d6dca1c8c.json");
		System.out.println("Credentials loaded");
		
		//EthCoinbase coinBase = web3Server.ethCoinbase().send();
		//System.out.println("coinbase: " + coinBase.getAddress());

		
		
		System.out.println("Sending 1 Wei ("+ Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");

		TransactionReceipt transferReceipt = Transfer.sendFunds(
				web3Server, creds,
                "0x5Db2BC93c80b8C522034a80F433846Be75823bB1",  // you can put any address here
                BigDecimal.ONE, Convert.Unit.WEI);
		System.out.println("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
                + transferReceipt.getTransactionHash());
		
		
		BigInteger min = new BigInteger("20000000000");
		BigInteger max = new BigInteger("4300000");
		GreeterO contract = GreeterO.load("0x5e6d3bd77c23fe4fc552f6d3ef13a7e15b647585", web3Server, creds, min, max);
		System.out.println("contractaddress: " + contract.getContractAddress());
		Utf8String message = contract.greet().get();
		System.out.println("Message returned by Contract.greet(): " + message.toString());

		// Utf8String rewardReasonMsg = new Utf8String("dag ringo!");
		// Uint256 value = new Uint256(max);
		// Future<TransactionReceipt> setGreeting =
		// contract.setGreeting(rewardReasonMsg, value);
		// TransactionReceipt transactionReceipt = setGreeting.get();
		// System.out.println("transaction: " +
		// transactionReceipt.getTransactionHash());

		EthGetTransactionReceipt transactionReceipt = web3Server
				.ethGetTransactionReceipt("0x89e152c1f14074d63bcee8d3c1875aaa598b371292b40aba607d5114fab9d7f7")
				.sendAsync().get();

		if (transactionReceipt.getTransactionReceipt().isPresent()) {
			String contractAddress = transactionReceipt.getTransactionReceipt().get().getContractAddress();
			System.out.println("address: " + contractAddress);
		} else {
			System.out.println("shit happens");
		}
		System.out.println("ok");

	}

}
