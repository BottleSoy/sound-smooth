package name.soy.soundout.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.io.IOUtils;

import java.io.*;

@Environment(EnvType.CLIENT)
public class SoundOutClient implements ClientModInitializer {
	public static Expression expression;
	public static String expressionText = "0.003";
	public static File options = new File("soundOut.txt");

	@Override
	public void onInitializeClient() {
		if (!options.exists()) {
			try {
				options.createNewFile();
				saveExpress();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			expression = new ExpressionBuilder(expressionText = IOUtils.readLines(new FileReader(options)).get(0)).build();
		} catch (Exception e) {
			expressionText = "0.003";
			expression = new ExpressionBuilder(expressionText).build();
		}
	}

	public static void saveExpress() {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(options);
			IOUtils.write(expressionText, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
