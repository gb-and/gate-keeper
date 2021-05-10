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
public class ChangeNameAct extends AppCompatDialogFragment {

    private EditText name;

    private MyActionStr act;
    private Config conf;

    public ChangeNameAct(MyActionStr aAct, Config aConf) {
        this.act = aAct;
        this.conf = aConf;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.layout_name_change_dlg, null);
        b.setView(view)
                .setTitle("Name change")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newName = name.getText().toString();
                        act.make(newName);
                        Toast.makeText(getActivity(), "The Name is changed.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        name = view.findViewById(R.id.name);
        name.setText(conf.name);

        return b.create();

    }

}
