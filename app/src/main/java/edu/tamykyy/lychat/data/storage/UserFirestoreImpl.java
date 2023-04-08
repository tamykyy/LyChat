package edu.tamykyy.lychat.data.storage;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Locale;

import edu.tamykyy.lychat.data.storage.interfaces.UserFirestore;
import edu.tamykyy.lychat.data.storage.models.UserDataModel;

public class UserFirestoreImpl implements UserFirestore {

    private static final String COLLECTION_NAME = "users";
    private final FirebaseFirestore firestore;

    public UserFirestoreImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Task<Void> save(UserDataModel user) {
        return firestore
                .collection(COLLECTION_NAME)
                .document(user.getUserUID())
                .set(user, SetOptions.merge());
    }

    @Override
    public Task<DocumentSnapshot> get(String uid) {
        return firestore
                .collection(COLLECTION_NAME)
                .document(uid)
                .get();
    }

    @Override
    public DocumentReference getDocumentRef(String uid) {
        return firestore
                .collection(COLLECTION_NAME)
                .document(uid);
    }

    @Override
    public Task<Void> update(String uid, HashMap<String, Object> userMap) {
        return firestore
                .collection(COLLECTION_NAME)
                .document(uid)
                .update(userMap);
    }

    @Override
    public Task<QuerySnapshot> query(String field, String value) {
        return firestore.collection(COLLECTION_NAME).whereEqualTo(field, value).get();
    }

    @Override
    public Task<QuerySnapshot> findUserViaFields(String[] fields, String value) {
        CollectionReference ref = firestore.collection(COLLECTION_NAME);
        Filter filter = Filter.equalTo(fields[0], value);
        for (String field : fields) {
            filter = Filter.or(
                    filter,
                    Filter.or(
                            Filter.equalTo(field, value),
                            Filter.equalTo(field, value.toUpperCase(Locale.ROOT)),
                            Filter.equalTo(field, value.toLowerCase(Locale.ROOT)),
                            Filter.equalTo(field, value.toUpperCase(Locale.ROOT).charAt(0) + value.substring(1))
                            )
            );
        }
        return ref
                .where(filter)
                .get();


//        Query query = firestore
//                .collection(COLLECTION_NAME)
//                .orderBy(field)
//                .startAt(value)
//                .endAt(value + "\uf8ff");
    }


}
