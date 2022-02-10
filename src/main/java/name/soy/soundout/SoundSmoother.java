package name.soy.soundout;

import name.soy.soundout.client.SoundOutClient;
import net.minecraft.client.sound.Source;

public class SoundSmoother {

	public static void smoothOut(Source source, float default_vol) {
		new Thread(() -> {
			float vol = default_vol;
			int tick = 0;//淡出的毫秒数
			if (default_vol > 0) {//如果原本发声响度就是0,忽略
				while (!source.isStopped()) {//当发声还未停止时，循环
					try {
						Thread.sleep(1);//等待1ms
						vol -= SoundOutClient.expression //我们存储的表达式
								.setVariable("t", tick)//赋值
								.setVariable("d", default_vol)//赋值*2
								.evaluate();
						if (vol <= 0) { //小于0我们就可以停止了
							vol = 0;
							break;
						}
						tick++;
						source.setVolume(vol); //设置响度
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
