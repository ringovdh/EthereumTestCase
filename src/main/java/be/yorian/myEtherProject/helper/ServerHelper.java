package be.yorian.myEtherProject.helper;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class ServerHelper {

	public Web3j startserver() {
		Web3j web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/hfoBoSuqEeGAOHxv0rUP"));  // defaults to http://localhost:8545/
		return web3;
	}

}
