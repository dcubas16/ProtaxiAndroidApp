package layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;

/**
 * Created by DIEGO on 10/11/2016.
 */
public class General {

    public static ProgressDialog progressDialog;

    public static void showAlert(View v, String message, String header){
        AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
        alertDialog.setTitle(header);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void showLoading(Activity activity, String header, String message){
        progressDialog = new ProgressDialog(activity, AlertDialog.THEME_HOLO_DARK);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
        progressDialog.show();

    }

    public static void dismissLoading(){
        progressDialog.dismiss();
    }

}
