package ai.tgbot;

import android.app.*;
import android.os.*;
import android.view.*;
import android.support.v4.app.NotificationCompat;
import android.content.Intent;
import android.widget.Toast;
import android.animation.ObjectAnimator;
import android.view.animation.Animation;
/*
TGBot for Android
Agus Ibrahim
http://fb.me/mynameisagoes

License: GPLv2
http://www.gnu.org/licenses/gpl-2.0.html
*/
public class MainActivity extends Activity 
{
	View txtlabel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		txtlabel=findViewById(R.id.mainTextView1);
		ObjectAnimator anim=ObjectAnimator.ofFloat(txtlabel, View.ALPHA, 0.1f,1.0f);
		anim.setDuration(2000);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		anim.start();
		startService(new Intent(MainActivity.this, TGPoll.class));
    }
}
