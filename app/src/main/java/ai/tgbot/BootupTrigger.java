package ai.tgbot;
import android.content.BroadcastReceiver;
import android.content.*;
/*
 TGBot for Android
 Agus Ibrahim
 http://fb.me/mynameisagoes

 License: GPLv2
 http://www.gnu.org/licenses/gpl-2.0.html
 */
public class BootupTrigger extends BroadcastReceiver
{
	@Override
	public void onReceive(Context p1, Intent p2) {
		android.util.Log.d(Constant.TAG, "Starting Robot...");
		p1.startService(new Intent(p1, TGPoll.class));
	}
}
