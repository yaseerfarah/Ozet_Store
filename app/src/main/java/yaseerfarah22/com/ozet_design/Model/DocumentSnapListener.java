package yaseerfarah22.com.ozet_design.Model;

import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Created by DELL on 5/5/2019.
 */

public class DocumentSnapListener {

    DocumentSnapshot documentSnapshot;
    int actionType;

    public DocumentSnapListener(DocumentSnapshot documentSnapshot, int actionType) {
        this.documentSnapshot = documentSnapshot;
        this.actionType = actionType;
    }

    public DocumentSnapshot getDocumentSnapshot() {
        return documentSnapshot;
    }

    public void setDocumentSnapshot(DocumentSnapshot documentSnapshot) {
        this.documentSnapshot = documentSnapshot;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }
}
