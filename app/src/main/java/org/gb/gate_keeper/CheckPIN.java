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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.gb.gate_keeper.R;

/**
 * @author grzegorz.bylica@gmail.com
 */
public class CheckPIN extends AppCompatDialogFragment {

    private EditText pin;
    private Config conf;
    private final MyAction act;
    private String confirmMsg;

    public CheckPIN(Config aConf, MyAction aAct, String aConfirmMsg) {
        this.conf = aConf;
        this.act = aAct;
        this.confirmMsg = aConfirmMsg;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.layout_pin_ver, null);
        b.setView(view)
                .setTitle("Option available after giving the PIN")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String pinStr = pin.getText().toString();
                        if (pinStr.equals(conf.pin)) {
                            Toast.makeText(getActivity(), "PIN is correct", Toast.LENGTH_SHORT).show();
                            act.make();
                            Toast.makeText(getActivity(), confirmMsg, Toast.LENGTH_SHORT).show();
                        } else {
                            AlertAct al = new AlertAct("PIN is not correct");
                            al.show(getActivity().getSupportFragmentManager(),"Msg");
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });


        pin = view.findViewById(R.id.pin);

        return b.create();
    }
}

