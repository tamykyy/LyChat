package edu.tamykyy.lychat.domain.repository;

import androidx.lifecycle.LiveData;

import edu.tamykyy.lychat.data.storage.models.Response;
import edu.tamykyy.lychat.domain.models.UserDomainModel;

public interface UserRepository {

    LiveData<Response> save(UserDomainModel user);
}
