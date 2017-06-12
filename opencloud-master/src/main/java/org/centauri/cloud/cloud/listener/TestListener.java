package org.centauri.cloud.cloud.listener;

import org.centauri.cloud.cloud.event.Listener;
import org.centauri.cloud.cloud.event.events.ConsoleCommandEvent;

public class TestListener {
	//Only for tests

	@Listener
	public void onConsoleInput(ConsoleCommandEvent event) {
		System.out.println("Message: " + event.getInput());
	}
	
}
