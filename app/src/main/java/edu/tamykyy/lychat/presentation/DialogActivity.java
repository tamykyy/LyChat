package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivityDialogBinding;
import edu.tamykyy.lychat.domain.models.ChatDomainModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class DialogActivity extends AppCompatActivity {

    private ActivityDialogBinding myBinding;
    private DialogActivityViewModel myViewModel;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ChatDomainModel chatDomainModel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_dialog);
        myViewModel = new ViewModelProvider(this).get(DialogActivityViewModel.class);

        if (myUid == null) return;

        UserDomainModel otherPerson = getIntent().getExtras().getParcelable("user");
        chatDomainModel = getIntent().getExtras().getParcelable("chat");
        updateToolbarUI(otherPerson);

        if (chatDomainModel == null) {
            Disposable dis = myViewModel.getChat(myUid, otherPerson.getUserUID())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            chatModels -> {
                                chatDomainModel = chatModels;
                                getMessagesUpdates(chatDomainModel);
                            },
                            throwable -> Log.d("AAA", throwable.getMessage())
                    );
            compositeDisposable.add(dis);
        } else getMessagesUpdates(chatDomainModel);

        Disposable disposable = myViewModel.getUserUpdates(otherPerson.getUserUID())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::updateToolbarUI,
                        throwable -> Log.d("AAA", throwable.getMessage())
                );
        compositeDisposable.add(disposable);

        myBinding.topAppBar.setNavigationOnClickListener(v -> finish());

        myBinding.base.setOnTouchListener(new SwipeListener(this) {
            @Override
            public void onSwipeRight() {
                finish();
            }
        });

        myBinding.messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) myBinding.sendMessageImageView.setVisibility(View.GONE);
                else myBinding.sendMessageImageView.setVisibility(View.VISIBLE);
            }
        });

        myBinding.sendMessageImageView.setOnClickListener(v -> {
            if (chatDomainModel == null) {
                Disposable disposable1 = myViewModel.createNewChat(myUid, otherPerson.getUserUID())
                        .subscribe(
                                newChatModel -> {
                                    chatDomainModel = newChatModel;
                                    sendMessage(chatDomainModel);
                                },
                                throwable -> Log.d("AAA", throwable.getMessage())
                        );
                compositeDisposable.add(disposable1);
            } else sendMessage(chatDomainModel);
        });
    }

    private void sendMessage(ChatDomainModel chatDomainModel) {
        Log.d("AAA", chatDomainModel.getId());
        Disposable dis = myViewModel.sendMessage(chatDomainModel.getId(), myUid, myBinding.messageEditText.getText().toString())
                .subscribe(
                        ref -> {
                            Log.d("AAA", "SEND");
                            updateLastMessages(ref);
                        },
                        throwable -> Log.d("AAA", throwable.getMessage())
                );
    }

    private void getMessagesUpdates(ChatDomainModel chatDomainModel) {
        Disposable disposable = myViewModel.getMessageUpdates(chatDomainModel.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messages -> Log.d("AAA", messages.toString()),
                        throwable -> Log.d("AAA", throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    private void updateToolbarUI(UserDomainModel otherPerson) {
        if (myUid.equals(otherPerson.getUserUID())) {
            myBinding.avatarImageView.setImageResource(R.drawable.baseline_folder_open_white_24);
            myBinding.avatarImageView.setBackgroundColor(Color.parseColor("#0000ff"));

            myBinding.nameTextView.setText("Saved Messages");
            myBinding.onlineInfoTextView.setVisibility(View.INVISIBLE);
        } else {
            Glide.with(this)
                    .load(otherPerson.getProfilePicture())
                    .into(myBinding.avatarImageView);

            myBinding.nameTextView.setText(otherPerson.getFirstName() + " " + otherPerson.getLastName());
            myBinding.onlineInfoTextView.setText(otherPerson.getOnlineInfo());
        }
    }

    private void updateLastMessages(String ref) {
        myViewModel.setChatLastMessage(chatDomainModel.getId(), ref).subscribe(
                () -> Log.d("AAA", "Ok"),
                throwable -> Log.d("AAA", throwable.getMessage())
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}