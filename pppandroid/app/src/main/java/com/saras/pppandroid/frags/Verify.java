package com.saras.pppandroid.frags;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.saras.pppandroid.R;
import com.saras.pppandroid.activity.ScanResult;


public class Verify extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 1001;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_verify, container, false);

        checkCameraPermission();
        scannerView = rootView.findViewById(R.id.scanner_view);

        mCodeScanner = new CodeScanner(requireActivity(), scannerView);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setFormats(CodeScanner.TWO_DIMENSIONAL_FORMATS);

        mCodeScanner.setDecodeCallback(result ->
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), result.getText(), Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getActivity(), ScanResult.class);
                    i.putExtra("place", result.getText());

                    startActivity(i);
                }));

        scannerView.setOnClickListener(view1 -> {
            if (!mCodeScanner.isPreviewActive()) {
                mCodeScanner.startPreview();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                return;
            } else {
                Toast.makeText(requireContext(), "Camera permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
