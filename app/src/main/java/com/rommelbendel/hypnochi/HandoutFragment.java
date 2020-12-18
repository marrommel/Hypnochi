package com.example.hypnochi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.ArrayList;
import java.util.List;

public class HandoutFragment extends Fragment implements OnPageChangeListener,OnLoadCompleteListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String Handout_File = "handout.pdf";

    PDFView pdfView;
    int pageNumber;
    String pdfFileName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_2, container, false);
        pdfView = v.findViewById(R.id.pdfView);

        displayFromAsset(Handout_File);

        return v;
    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(Handout_File)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(false)
                .onLoad(this)
                .enableDoubletap(false)
                .load();
    }

    @Override
    public void loadComplete(int nbPages) { }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }
}