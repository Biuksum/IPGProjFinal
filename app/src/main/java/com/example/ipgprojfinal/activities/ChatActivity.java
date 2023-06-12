package com.example.ipgprojfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ipgprojfinal.R;
import com.example.ipgprojfinal.databinding.ActivityChatBinding;
import com.example.ipgprojfinal.models.User;
import com.example.ipgprojfinal.utilities.Constants;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private User receiverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();
    }

    private void loadReceiverDetails(){
        receiverUser =(User) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.textName.setText(receiverUser.name);
    }

    private void  setListeners(){
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }
}