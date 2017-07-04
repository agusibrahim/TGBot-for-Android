package ai.tgbot;

import android.content.*;
import android.net.*;
import java.util.*;
/*
 TGBot for Android
 Agus Ibrahim
 http://fb.me/mynameisagoes

 License: GPLv2
 http://www.gnu.org/licenses/gpl-2.0.html
 */
public class NetTrigger extends BroadcastReceiver{
	@Override
	public void onReceive(final Context p1, Intent intent) {
		if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			boolean isNetAvailable=isNetworkAvailable(p1);
			android.util.Log.d(Constant.TAG, "NetTrigger - Net Status: "+(isNetAvailable?"ONLINE":"OFFLINE"));
			if(isNetAvailable){
				p1.startService(new Intent(p1, TGPoll.class));
			}
		}
	}
	private boolean isNetworkAvailable(Context c) {
		ConnectivityManager cm =
			(ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}
}

