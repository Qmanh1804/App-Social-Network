package com.example.frontend.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frontend.R;
import com.example.frontend.activities.FragmentReplacerActivity;


public class VerificationCodeFragment extends Fragment {

        Button verifyBtn,resendBtn;
    public VerificationCodeFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_verification_code, container, false);

        // Tìm và gán trình nghe sự kiện cho nút "Verify"
        verifyBtn = rootView.findViewById(R.id.verifyBtn);
        resendOTP = rootView.findViewById(R.id.resendOTP);
        Email= rootView.findViewById(R.id.usernameTitle);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        otp = rootView.findViewById(R.id.otp);

        // Nhận dữ liệu email từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String email = bundle.getString("email", "");
            // Đặt giá trị email lên TextView
            Email.setText(email);
        }

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                if(!email.contains("@gmail.com")){
                    Email.setError("Please input valid email");
                    return;
                }
                System.out.println(email);
                userViewModel.sendOtp_forgotpassword(email).observe(getViewLifecycleOwner(), new Observer<ApiResponse<String>>() {
                    @Override
                    public void onChanged(ApiResponse<String> stringApiResponse) {
                        checkOTP  = stringApiResponse.getData();
                        Toast.makeText(getActivity(), stringApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(checkOTP);
                    }
                });

            }
        });
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String OTP = otp.getText().toString();
                if(OTP.isEmpty()){
                    otp.setError("Please input otp");
                    return;
                }

                if(!OTP.equals(checkOTP) || checkOTP.isEmpty()){
                    Toast.makeText(getActivity(), "OTP is invalid"+checkOTP, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Chuyển sang fragment quên mật khẩu
                ((FragmentReplacerActivity) requireActivity()).setFragment(new ChangePasswordFragment());
            }
        });
        // Tìm và gán trình nghe sự kiện cho nút "Back" và nút "Verify"
        ImageButton backButton = rootView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại trang đăng nhập khi nút "Back" được nhấn
                ((FragmentReplacerActivity) requireActivity()).setFragment(new LoginFragment());
        View view = inflater.inflate(R.layout.fragment_verification_code, container, false);
        verifyBtn = view.findViewById(R.id.verifyBtn);
        resendBtn = view.findViewById(R.id.resendOTP);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FragmentReplacerActivity.class);
                // Thêm dữ liệu cho Intent để FragmentReplacerActivity biết cần thay thế fragment nào
                intent.putExtra("fragment_to_load", "change_pass");
                // Bắt đầu activity với Intent đã tạo
                startActivity(intent);
            }
        });
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"CLICKED",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }







}
}
