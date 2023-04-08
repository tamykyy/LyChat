package edu.tamykyy.lychat.presentation;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.FindUserUseCase;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class SearchActivityViewModel extends ViewModel {

    private final FindUserUseCase findUserUseCase;

    @Inject
    public SearchActivityViewModel(FindUserUseCase findUserUseCase) {
        this.findUserUseCase = findUserUseCase;
    }

    public Observable<UserDomainModel> findUser(String value) {
        value = value.trim();
        Log.d("AAA", "Start");
        return findUserUseCase.execute(value);
    }
}
