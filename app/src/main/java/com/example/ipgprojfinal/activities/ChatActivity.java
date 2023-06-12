package com.example.ipgprojfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;

import com.example.ipgprojfinal.R;
import com.example.ipgprojfinal.adapters.ChatAdapter;
import com.example.ipgprojfinal.databinding.ActivityChatBinding;
import com.example.ipgprojfinal.models.ChatMessage;
import com.example.ipgprojfinal.models.User;
import com.example.ipgprojfinal.utilities.Constants;
import com.example.ipgprojfinal.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private User receiverUser;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private PreferenceManager preferenceManager;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();
        init();
    }

    private void init(){
        preferenceManager = new PreferenceManager(getApplicationContext());
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(
                chatMessages,
                getBitmapFromEncodedString(receiverUser.image),
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        binding.chatRecyclerView.setAdapter(chatAdapter);
        database = FirebaseFirestore.getInstance();
    }

    private void sendMessage(){
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
        message.put(Constants.KEY_MESSAGE, binding.inputMessage.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, new Date());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message);
        binding.inputMessage.setText(null);
    }

    private Bitmap getBitmapFromEncodedString(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void loadReceiverDetails(){
        receiverUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.textName.setText(receiverUser.name);
    }

    private void  setListeners(){

        binding.imageBack.setOnClickListener(v -> onBackPressed());
        binding.layoutSend.setOnClickListener(v -> sendMessage());
    }
}