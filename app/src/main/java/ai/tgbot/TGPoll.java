package ai.tgbot;
import android.app.Service;
import android.content.*;
import android.os.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
import org.json.*;
import android.content.SharedPreferences.*;
import android.support.v4.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationManager;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import android.app.PendingIntent;
/*
 TGBot for Android
 Agus Ibrahim
 http://fb.me/mynameisagoes

 License: GPLv2
 http://www.gnu.org/licenses/gpl-2.0.html
 */
public class TGPoll extends Service
{
	private AsyncHttpClient client;
	private JsonHttpResponseHandler resp;
	private String UPDATES_URL="https://api.telegram.org/bot"+Constant.TOKEN+"/getUpdates?limit=1&offset=-1";
	private SharedPreferences pref;
	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		pref=getSharedPreferences("update_data", MODE_PRIVATE);
		client=new AsyncHttpClient();
		client.setTimeout(3000);
		client.setConnectTimeout(3000);
		client.setResponseTimeout(3000);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		registerReceiver(onClickReceiver, new IntentFilter(Constant.NOTIF_ON_CLICK_));
		registerReceiver(onDismissReceiver, new IntentFilter(Constant.NOTIF_ON_DISMISS_));
		
		resp=new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
				String lastUpdate=pref.getString("update_id","");
				try{
					JSONArray reslst=res.getJSONArray("result");
					if(reslst.length()>0){
						String upid=reslst.getJSONObject(0).getString("update_id");
						JSONObject msgfrom=reslst.getJSONObject(0).getJSONObject("message").getJSONObject("from");
						//String from_username=msgfrom.getString("username");
						String from_id=msgfrom.getString("id");
						String from_name=msgfrom.getString("first_name")+" "+msgfrom.getString("last_name");
						String msg=reslst.getJSONObject(0).getJSONObject("message").getString("text");
						if(upid.equals(lastUpdate)){
							android.util.Log.d(Constant.TAG, "No Update "+System.currentTimeMillis());
						}else{
							android.util.Log.d(Constant.TAG, "Found Update!! "+System.currentTimeMillis());
							SharedPreferences.Editor ed=pref.edit();
							ed.putString("update_id", upid);ed.commit();
							notifyme("Message From "+from_name, msg, from_id);
							sendMsg("Message Delivered to "+getDevmodel(), from_id);
						}
					}
				} catch (Exception e) {
				}
				new android.os.Handler().postDelayed(new Runnable(){
						@Override
						public void run() {
							client.get(UPDATES_URL, null,resp);
							android.util.Log.d(Constant.TAG, "Check for Update "+System.currentTimeMillis());
						}
					}, Constant.POLLING_INTERVAL);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
				android.util.Log.d(Constant.TAG, "Check Error: "+t.getMessage());
			}	
		};
		client.get(UPDATES_URL, null,resp);
		return super.onStartCommand(intent, flags, startId);
	}
	private void notifyme(String title, String str, String fromid){
		Intent sendmsgClickintent=new Intent(Constant.NOTIF_ON_CLICK_);
		Intent sendmsgDismissintent=new Intent(Constant.NOTIF_ON_DISMISS_);
		sendmsgClickintent.putExtra("fromid", fromid);
		sendmsgDismissintent.putExtra("fromid", fromid);
		PendingIntent piClick=PendingIntent.getBroadcast(this,0, sendmsgClickintent,0);
		PendingIntent piDismiss=PendingIntent.getBroadcast(this,0, sendmsgDismissintent,0);
		NotificationCompat.Builder nf = new NotificationCompat.Builder(this);
		nf.setSmallIcon(R.drawable.ic_comment_alert_outline);
		nf.setContentTitle(title);
		nf.setContentText(str);
		//nf.setAutoCancel(true);
		nf.setDeleteIntent(piDismiss);
		nf.setContentIntent(piClick);
		nf.setDefaults(Notification.DEFAULT_ALL);
		nf.setPriority(Notification.PRIORITY_HIGH);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.notify(Constant.NOTIFY_ID, nf.build());
	}
	public void sendMsg(String msg, String target){
		RequestParams params = new RequestParams();
		params.put("chat_id", target);
		params.put("text", msg);
		client.post("https://api.telegram.org/bot"+Constant.TOKEN+"/sendMessage", params, new TextHttpResponseHandler(){
				@Override
				public void onFailure(int p1, Header[] p2, String p3, Throwable p4) {
					android.util.Log.d(Constant.TAG, "send msg FAIL, "+p4.getMessage());
				}

				@Override
				public void onSuccess(int p1, Header[] p2, String p3) {
					android.util.Log.d(Constant.TAG, "send msg OK "+p3);
				}
			});
	}
	public static String getDevmodel(){
		String manufacturer = android.os.Build.MANUFACTURER;
		String model = android.os.Build.MODEL;
		return (manufacturer+" ("+model+")").toUpperCase();
	}
	private final BroadcastReceiver onClickReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			sendMsg("Notification Clicked 😂👍\n"+getDevmodel(), intent.getStringExtra("fromid"));
		}
	};
	private final BroadcastReceiver onDismissReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			sendMsg("Notification Dismissed 😞\n"+getDevmodel(), intent.getStringExtra("fromid"));
		}
	};
}
