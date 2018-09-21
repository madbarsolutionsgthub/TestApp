package madbarsoft.com.testapp;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

public class UpdateAsync extends AsyncTask<String, String, JSONObject> {
    Dialog dialog;
    private String latestVersion;
    private String currentVersion;
    private Context context;
    public UpdateAsync(String currentVersion, Context context){
        this.currentVersion = currentVersion;
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        try {
            latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "madbarsoft.com.testapp" + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
                    .first()
                    .ownText();
//            latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName()+ "&hl=en")
//                    .timeout(30000)
//                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                    .referrer("http://www.google.com")
//                    .get()
//                    .select("div.hAyfc:nth-child(3) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
//                    .first()
//                    .ownText();
            Log.e("latestversion","---"+latestVersion);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if(latestVersion!=null){
            if(!currentVersion.equalsIgnoreCase(latestVersion)){
                Toast.makeText(context,"update is available.!\nCrV:"+currentVersion+"+LaV:"+latestVersion,Toast.LENGTH_LONG).show();
                showForceUpdateDialog();
                //                if(!(context instanceof MainActivity)) {
//                    if(!((Activity)context).isFinishing()){
//                        showForceUpdateDialog();
//                    }
//                }
            }else{
                Toast.makeText(context,"No need Update..!\nCrV:"+currentVersion+"+LaV:"+latestVersion,Toast.LENGTH_LONG).show();
            }
        }
        super.onPostExecute(jsonObject);
    }

    public void showForceUpdateDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("More content are added..!\nPlease update...");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Click button action
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=madbarsoft.com.testapp")));
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}


