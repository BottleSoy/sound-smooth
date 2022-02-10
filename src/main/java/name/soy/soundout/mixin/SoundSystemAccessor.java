package name.soy.soundout.mixin;

import com.google.common.collect.Multimap;
import net.minecraft.client.sound.Channel;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(SoundSystem.class)
public interface SoundSystemAccessor {
	@Invoker("getAdjustedVolume")
	float realVolume(SoundInstance sound);

	@Accessor
	Multimap<SoundCategory, SoundInstance> getSounds();

	@Accessor
	boolean isStarted();

	@Accessor
	Map<SoundInstance, Channel.SourceManager> getSources();
}
