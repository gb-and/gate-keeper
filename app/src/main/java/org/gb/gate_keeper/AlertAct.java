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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.gb.gate_keeper.R;

/**
 * @author grzegorz.bylica@gmail.com
 */
public class AlertAct extends AppCompatDialogFragment {

    private TextView textView;
    private String msg;

    public AlertAct(String aMsg) {
        this.msg = aMsg;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.layout_alert_dlg, null);
        b.setView(view)
                .setPositiveButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        textView = view.findViewById(R.id.msg);
        textView.setText(msg);

        return b.create();
    }
}
