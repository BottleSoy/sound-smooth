package name.soy.soundout.mixin;

import name.soy.soundout.SoundSmoother;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;

@Mixin(ClientPlayNetworkHandler.class)
public class NetWorkMixin {
	private void smoothout(SoundSystem soundSystem, SoundInstance ins) {
		float vol = ((SoundSystemAccessor) soundSystem).realVolume(ins);
		((AbstractSoundInstanceMixin)ins).setVolume(0f);
		if (((SoundSystemAccessor) soundSystem).isStarted()) {
			Channel.SourceManager sourceManager = ((SoundSystemAccessor) soundSystem).getSources().get(ins);
			if (sourceManager != null) {
				sourceManager.run(source -> SoundSmoother.smoothOut(source, vol));
			}
		}
	}

	private void smoothoutAll(SoundSystem soundSystem) {
		if (((SoundSystemAccessor) soundSystem).isStarted()) {
			((SoundSystemAccessor) soundSystem).getSources().forEach((soundInstance, sourceManager) -> {
				if (sourceManager != null) {
					sourceManager.run(source -> SoundSmoother.smoothOut(source, ((SoundSystemAccessor) soundSystem).realVolume(soundInstance)));
				}
			});
		}
	}

	@Redirect(method = "onStopSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundManager;stopSounds(Lnet/minecraft/util/Identifier;Lnet/minecraft/sound/SoundCategory;)V"))
	public void stop(SoundManager soundManager, Identifier id, SoundCategory category) {
		SoundSystem soundSystem = ((SoundManagerAccessor) soundManager).getSoundSystem();
		if (category != null) {
			SoundInstance ins;
			Iterator<SoundInstance> var3 = ((SoundSystemAccessor) soundSystem).getSounds().get(category).iterator();
			while (true) {
				do {
					if (!var3.hasNext()) {
						return;
					}
					ins = var3.next();
				} while (id != null && !ins.getId().equals(id));
				this.smoothout(soundSystem, ins);
			}
		} else if (id == null) {
			this.smoothoutAll(soundSystem);
		} else {
			for (SoundInstance soundInstance : ((SoundSystemAccessor) soundSystem).getSources().keySet()) {
				if (soundInstance.getId().equals(id)) {
					this.smoothout(soundSystem, soundInstance);
				}
			}
		}
	}

}
