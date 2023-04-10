package edu.tamykyy.lychat.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import edu.tamykyy.lychat.data.repository.AuthenticationRepositoryImpl;
import edu.tamykyy.lychat.data.repository.ChatRepositoryImpl;
import edu.tamykyy.lychat.data.repository.MessageRepositoryImpl;
import edu.tamykyy.lychat.data.repository.UserLocalRepositoryImpl;
import edu.tamykyy.lychat.data.repository.UserRepositoryImpl;
import edu.tamykyy.lychat.data.storage.ChatFirestoreImpl;
import edu.tamykyy.lychat.data.storage.MessageFirestoreImpl;
import edu.tamykyy.lychat.data.storage.PhoneStorageImpl;
import edu.tamykyy.lychat.data.storage.UserFirestoreImpl;
import edu.tamykyy.lychat.data.storage.UserStorageImpl;
import edu.tamykyy.lychat.data.storage.interfaces.PhoneStorage;
import edu.tamykyy.lychat.domain.repository.AuthenticationRepository;
import edu.tamykyy.lychat.domain.repository.ChatRepository;
import edu.tamykyy.lychat.domain.repository.UserLocalRepository;
import edu.tamykyy.lychat.domain.repository.UserRepository;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    @Singleton
    public AuthenticationRepository provideAuthenticationRepositoryImpl(FirebaseAuth auth, UserRepository userRepository) {
        return new AuthenticationRepositoryImpl(auth, userRepository);
    }

    @Provides
    @Singleton
    public UserRepository provideUserRepositoryImpl(
            UserFirestoreImpl userFirebase,
            UserStorageImpl userProfilePicStorage) {
        return new UserRepositoryImpl(userFirebase, userProfilePicStorage);
    }

    @Provides
    @Singleton
    public UserFirestoreImpl provideUserFirebaseImpl(FirebaseFirestore firestore) {
        return new UserFirestoreImpl(firestore);
    }

    @Provides
    @Singleton
    public UserStorageImpl provideUserStorageImpl(FirebaseStorage storage) {
        return new UserStorageImpl(storage);
    }
    @Provides
    @Singleton
    public PhoneStorage providePhoneStorageImpl() {
        return new PhoneStorageImpl();
    }

    @Provides
    @Singleton
    public UserLocalRepository provideUserLocalRepositoryImpl(PhoneStorage phoneStorage) {
        return new UserLocalRepositoryImpl(phoneStorage);
    }

    @Provides
    @Singleton
    public ChatFirestoreImpl provideChatFirestoreImpl(FirebaseFirestore firestore) {
        return new ChatFirestoreImpl(firestore);
    }

    @Provides
    @Singleton
    public ChatRepository provideChatRepositoryImpl(ChatFirestoreImpl chatFirestore,
                                                    MessageRepositoryImpl messageRepository) {
        return new ChatRepositoryImpl(chatFirestore, messageRepository);
    }

    @Provides
    @Singleton
    public MessageFirestoreImpl provideMessageFirestoreImpl(FirebaseFirestore firestore) {
        return new MessageFirestoreImpl(firestore);
    }

    @Provides
    @Singleton
    public MessageRepositoryImpl provideMessageRepositoryImpl(MessageFirestoreImpl messageFirestore) {
        return new MessageRepositoryImpl(messageFirestore);
    }
}
