package edu.tamykyy.lychat.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.concurrent.Future;

import edu.tamykyy.lychat.data.storage.models.Response;
import edu.tamykyy.lychat.domain.models.UserDomainModel;

public interface UserRepository {

//    Future<Boolean> contains(String uid);

    LiveData<Response> save(UserDomainModel user);

}
