package name.soy.soundout.mixin;

import name.soy.soundout.client.SoundControlWei;
import name.soy.soundout.client.SoundOutClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundOptionsScreen.class)
public class SoundScreenMixin extends GameOptionsScreen {
	public SoundScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}

	@Inject(method = "init", at = @At("RETURN"))
	public void addOption(CallbackInfo ci) {
		TextFieldWidget outWei = new SoundControlWei(this.textRenderer, this.width / 2 + 5, this.height / 6 - 12 + 24 * (SoundCategory.values().length >> 1), 140, 20,
				new LiteralText("设置淡出函数(每1ms降低数量)"));
		outWei.setText(SoundOutClient.expressionText);
		this.addButton(outWei);
	}

}
