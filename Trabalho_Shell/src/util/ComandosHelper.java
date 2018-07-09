package util;

import java.util.ArrayList;
import java.util.List;

public class ComandosHelper {

	private String commandLine;
	private String[] commandos;

	public String getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}
	
	public List<String> recebeListaDeComandos(String[] cmds) {
		List<String> comands = new ArrayList<>();		
		this.commandos = cmds;
		
		for (int i = 0; i < commandos.length; i++) {			
			comands.add(commandos[i]);
		}
		
		return comands;
	}
	
}
