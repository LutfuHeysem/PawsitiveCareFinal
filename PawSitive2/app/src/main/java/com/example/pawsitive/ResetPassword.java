package com.example.pawsitive;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ResetPassword extends AppCompatActivity {

    private Button back, forget, approve;
    private String prevP, newP, newP2;
    private EditText prevEdit, newEdit, newEdit2;
    private FirebaseAuth mAuth;

    private FirebaseUser mUser;
    private HashMap<String, Object> userData;
    private FirebaseFirestore fStore;

    private DocumentReference fDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        back = findViewById(R.id.button3);
        forget = findViewById(R.id.button5);
        approve = findViewById(R.id.button6);

        prevEdit = findViewById(R.id.prev);
        newEdit = findViewById(R.id.newpass);
        newEdit2 = findViewById(R.id.newpass2);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userData = new HashMap<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassword.this, Login.class);
                startActivity(intent);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassword.this, ForgetPassword.class);
                startActivity(intent);
            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ResetPassword.this, mUser.getEmail() + "   " + getPrevPass(mUser.getEmail()) + "   ", Toast.LENGTH_SHORT).show();
                prevP = prevEdit.getText().toString();
                newP = newEdit.getText().toString();
                newP2 = newEdit2.getText().toString();

                if (TextUtils.isEmpty(prevP) || TextUtils.isEmpty(newP) || TextUtils.isEmpty(newP2))
                    Toast.makeText(ResetPassword.this, "You must fill all the blanks!", Toast.LENGTH_SHORT).show();
                else if (!prevP.equals(User.getPassword()))
                    Toast.makeText(ResetPassword.this, "Enter your previous password correctly!", Toast.LENGTH_SHORT).show();
                else if (prevP.equals(newP))
                    Toast.makeText(ResetPassword.this, "New password cannot be same with the previous one!", Toast.LENGTH_SHORT).show();
                else if (!newP.equals(newP2))
                    Toast.makeText(ResetPassword.this, "Check confirmation of your new password!", Toast.LENGTH_SHORT).show();
                else {
                    mUser.updatePassword(newP).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ResetPassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPassword.this, Login.class));
                        }
                    });
                    userData.put("Password", newP);
                    assert mUser != null;
                    updateInfo(userData, mUser.getEmail());
                }
            }
        });
    }

    private void updateInfo(HashMap<String, Object> hashMap, final String email) {
        fStore.collection("Users")
                .document(email) // Use the email as the document ID
                .update(hashMap)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPassword.this, Login.class));
                        } else {
                            Toast.makeText(ResetPassword.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}