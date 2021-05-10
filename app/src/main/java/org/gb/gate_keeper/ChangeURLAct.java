/**
 * On the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * (c) 2020 grzegorz.bylica@gmail.com
 */


package org.gb.gate_keeper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.gb.gate_keeper.R;

/**
 * @author grzegorz.bylica@gmail.com
 */
public class ChangeURLAct extends AppCompatDialogFragment {

    private TextView oldUrlInfo;
    private EditText newUrl;

    private MyActionStr act;
    private Config conf;

    public ChangeURLAct(MyActionStr aAct, Config aConf) {
        this.act = aAct;
        this.conf = aConf;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.layout_url_change_dlg, null);
        b.setView(view)
                .setTitle("URL change")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newUrlStr = newUrl.getText().toString();
                        act.make(newUrlStr);
                        Toast.makeText(getActivity(), "The URL is changed.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        oldUrlInfo = view.findViewById(R.id.old_url_info);
        oldUrlInfo.setText(conf.url);
        newUrl = view.findViewById(R.id.new_url);
        newUrl.setText(conf.url);

        return b.create();

    }


}
