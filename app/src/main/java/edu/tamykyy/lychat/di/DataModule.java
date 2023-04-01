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
import edu.tamykyy.lychat.data.repository.UserRepositoryImpl;
import edu.tamykyy.lychat.data.storage.UserFirestoreImpl;
import edu.tamykyy.lychat.data.storage.UserProfilePicStorageImpl;
import edu.tamykyy.lychat.domain.repository.AuthenticationRepository;
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
            UserProfilePicStorageImpl userProfilePicStorage) {
        return new UserRepositoryImpl(userFirebase, userProfilePicStorage);
    }

    @Provides
    @Singleton
    public UserFirestoreImpl provideUserFirebaseImpl(FirebaseFirestore firestore) {
        return new UserFirestoreImpl(firestore);
    }

    @Provides
    @Singleton
    public UserProfilePicStorageImpl provideUserProfilePicStorageImpl(FirebaseStorage storage) {
        return new UserProfilePicStorageImpl(storage);
    }
}
