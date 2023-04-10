package edu.tamykyy.lychat.domain.repository;

import edu.tamykyy.lychat.domain.models.MessageDomainModel;
import io.reactivex.rxjava3.core.Single;

public interface MessageRepository {

    Single<MessageDomainModel> getMessage(String ref);
}
