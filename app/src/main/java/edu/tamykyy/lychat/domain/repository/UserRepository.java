package edu.tamykyy.lychat.domain.repository;

import androidx.lifecycle.LiveData;

import edu.tamykyy.lychat.data.storage.models.Response;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import io.reactivex.rxjava3.core.Single;

public interface UserRepository {

    Single<UserDomainModel> get(String uid);

    LiveData<Response> save(UserDomainModel user);
}
