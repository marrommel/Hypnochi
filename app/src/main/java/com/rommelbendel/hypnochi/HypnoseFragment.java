package com.example.hypnochi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;
import java.util.zip.Inflater;

public class HypnoseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_1, container, false);
        final TinyDB tb = new TinyDB(getContext());

        ImageButton[] starts = {v.findViewById(R.id.s1),  v.findViewById(R.id.s2),  v.findViewById(R.id.s3)};

        for(int z=0; z<3; z++ ) {
            final int finalZ = z;
            starts[z].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("Willst du wirklich starten?");
                    dialog.setMessage("Du kannst die Abstimmung nur 1 Mal starten!");

                    dialog.setPositiveButton("ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (finalZ == 1) {
                                tb.putBoolean("played1", true);

                                Intent intent = new Intent(getActivity(), RedOrBlue.class);
                                startActivity(intent);
                            } else if (finalZ == 2) {
                                tb.putBoolean("played2", true);

                                Intent intent = new Intent(getActivity(), WhoDidIt.class);
                                startActivity(intent);
                            }
                        }
                    });

                    dialog.setNegativeButton("abbrechen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.setCancelable(true);
                        }
                    });

                    if(finalZ == 0) {
                        Intent intent = new Intent(getActivity(), HypnoImages.class);
                        startActivity(intent);
                    }
                    else if (finalZ == 1){
                        if(tb.getBoolean("played1")) {
                            Toast.makeText(getContext(), "Du hast bereits teilgenommen!", Toast.LENGTH_LONG).show();
                        } else {
                            dialog.create().show();
                        }
                    }
                    else if (finalZ == 2) {
                        if(tb.getBoolean("played2")) {
                            Toast.makeText(getContext(), "Du hast bereits teilgenommen!", Toast.LENGTH_LONG).show();
                        } else {
                            dialog.create().show();
                        }
                    }
                }
            });
        }

        return v;
    }
}