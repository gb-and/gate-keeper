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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.gb.gate_keeper.R;

/**
 * @author grzegorz.bylica@gmail.com
 */
public class ChangePINAct extends AppCompatDialogFragment {

    private EditText pin;
    public EditText pinCorrect;

    private MyActionStr act;

    public ChangePINAct(MyActionStr aAction) {
        this.act = aAction;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.layout_pin_change_dlg, null);
        b.setView(view)
                .setTitle("PIN change")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String pinStr = pin.getText().toString();
                        String pinStrC = pinCorrect.getText().toString();
                        if (pinStr.equals(pinStrC)) {
                            Toast.makeText(getActivity(), "The PINs are identical", Toast.LENGTH_SHORT).show();
                            act.make(pinCorrect.getText().toString());
                            Toast.makeText(getActivity(), "The PIN is changed.", Toast.LENGTH_SHORT).show();
                        } else {
                            AlertAct al = new AlertAct("PINs is not correct");
                            al.show(getActivity().getSupportFragmentManager(), "Msg");
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        pin = view.findViewById(R.id.pin);
        pinCorrect = view.findViewById(R.id.pinc);

        return b.create();

    }
}
