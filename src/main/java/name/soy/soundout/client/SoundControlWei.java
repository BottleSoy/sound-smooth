package name.soy.soundout.client;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SoundControlWei extends TextFieldWidget {
	public SoundControlWei(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
		super(textRenderer, x, y, width, height, text);
		setChangedListener(s -> {
			try {
				SoundOutClient.expression = new ExpressionBuilder(this.getText()).variables("t", "d").build();
				setEditableColor(16777215);
			} catch (Exception e) {
				setEditableColor(65536 * 255);
			}
			SoundOutClient.expressionText = this.getText();
			SoundOutClient.saveExpress();
		});
	}

}
