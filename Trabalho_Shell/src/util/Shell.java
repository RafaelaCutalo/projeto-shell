package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Shell {

	public static void main(String[] args) throws Exception {

		ComandosHelper helper = new ComandosHelper();
		
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

		ProcessBuilder pb = new ProcessBuilder();
		List<String> history = new ArrayList<String>();
		int index = 0;
		
		while (true) {
			System.out.print("jsh>");
			helper.setCommandLine(console.readLine());
			
			if(helper.getCommandLine().equals("")) continue;		
			
			List<String> listaDeComandos = helper.recebeListaDeComandos(helper.getCommandLine().split(" "));

			history.addAll(listaDeComandos);
			try {
				
				if (listaDeComandos.get(listaDeComandos.size() - 1).equals("history")) {
					for (String s : history)
						System.out.println((index++) + " " + s);
					continue;
				}

				if (listaDeComandos.contains("cd")) {
					if (listaDeComandos.get(listaDeComandos.size() - 1).equals("cd")) {
						File home = new File(System.getProperty("user.home"));
						pb.directory(home);
						continue;

					} else {
						String dir = listaDeComandos.get(1);
						File newPath = new File(dir);
						boolean exists = newPath.exists();

						if (exists) {
							System.out.println("/" + dir);
							pb.directory(newPath);
							continue;
						} else {
							System.out.print("Caminho ");
						}
					}
				}

				
				if (listaDeComandos.get(listaDeComandos.size() - 1).equals("!!")) {
					pb.command(history.get(history.size() - 2));
				}
				else if (listaDeComandos.get(listaDeComandos.size() - 1).charAt(0) == '!') {
					int b = Character.getNumericValue(listaDeComandos.get(listaDeComandos.size() - 1).charAt(1));
					if (b <= history.size())
						pb.command(history.get(b));
				} else {
					pb.command(listaDeComandos);
				}

				Process process = pb.start();
				
				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				
				String line;
				while ((line = br.readLine()) != null)
					System.out.println(line);
				br.close();

				if (helper.getCommandLine().equals(" "))
					continue;
			} catch (Exception e) {
				System.out.println("Comando inválido");
			}

		}

	}

}