package fr.mi.wewok.utils.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import fr.mi.wewok.R
import fr.mi.wewok.ui.home.HomeActivity
import fr.mi.wewok.utils.CONNECTED
import fr.mi.wewok.utils.IID
import fr.mi.wewok.utils.TOKEN
import io.paperdb.Paper

class MyFirebaseInstanceIDService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        var isLoggedIn: Boolean = false
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.i("TOKEN_TT", "Refreshed token: $refreshedToken")
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.

        if (Paper.book().contains(CONNECTED)) {
            isLoggedIn = Paper.book().read(CONNECTED)
            if (isLoggedIn) {
                if (refreshedToken != null)
                    saveToFirebase(refreshedToken!!)
            }
        }
    }

    fun saveToFirebase(token: String){
        Log.i("TOKEN_TT", " SAVE Refreshed token: $token")
        var id =  Paper.book().read<Int>(IID)
        Log.i("TOKEN_TT", " UID: $id")
        Paper.book().write<String>(TOKEN,token)

    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("msg", "onMessageReceived: " + remoteMessage.data["title"] + remoteMessage.data["body"] +remoteMessage.data.toString() )
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = "Default"
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(remoteMessage.data["title"])
            .setContentText(remoteMessage.data["body"]).setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(0, builder.build())
    }
    /*private fun showNotification(
        notification: RemoteMessage.Notification,
        data: Map<String, String>
    ) {
        val icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete)
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(
            this,
            "channel_id"
        )
            .setContentTitle(notification.title + " by service")
            .setContentText(notification.body + " by service")
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            .setContentInfo(notification.title)
            .setColor(Color.RED_FIELD_NUMBER)
            .setLights(Color.RED_FIELD_NUMBER, 1000, 300)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setLargeIcon(icon)
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "channel description"
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.RED_FIELD_NUMBER
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }*/
}
