package org.springbootdev.modules.mockserver.config;

import org.mockserver.integration.ClientAndServer;

public enum GlobalMockServerClient {
	INSTANCE;
	private ClientAndServer clientAndServer;
	GlobalMockServerClient(){
		this.clientAndServer=new  ClientAndServer(1080);
	}

	public ClientAndServer getInstance(){
	 	return this.clientAndServer;
	}

}
