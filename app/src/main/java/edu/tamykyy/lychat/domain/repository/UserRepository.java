package edu.tamykyy.lychat.domain.repository;

import edu.tamykyy.lychat.domain.models.UserDomainModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface UserRepository {

    Single<UserDomainModel> get(String uid);

    Completable save(UserDomainModel user);

    Observable<UserDomainModel> getUpdates(String uid);
}
