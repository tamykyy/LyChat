package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatActivityViewModel extends ViewModel {

    @Inject
    public ChatActivityViewModel() {
    }
}
