package com.example.socialmediaapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;
import static com.example.socialmediaapp.MainActivity.CurrentUser;

public class Account_Fragment extends Fragment {
    //important fragment vars
    View v;
    Context Frag_Context;

    //Database vars
    PlayerDataBase db;
    SQLiteDatabase sql;
    Cursor c;

    //User tries to Sign-Up
    EditText SignUp_Email;
    EditText SignUp_Name;
    EditText SignUp_Password;
    EditText SignUp_Phone;

    //User tries to Sign-In
    EditText SignIn_Name;
    EditText SignIn_Password;

    //always on
    TextView Login_TV;
    ImageView Logout_IV;
    GridLayout Grid_Cards;
    EditText ETPrivateNotes;
    EditText PIusername;
    EditText PIEmail;
    EditText PIphone;
    EditText OldPassword;
    EditText NewPassword;
    EditText NewPasswordRepeat;
    Resources res;
    RelativeLayout SignIn;
    RelativeLayout SignUp;
    RelativeLayout AppInfo;
    RelativeLayout PrivateNotes;
    TextView Dash_Name;
    ScrollView Main_Scroll;
    RelativeLayout Dialog_Type_Cancel;
    RelativeLayout FreqQuestions;
    RelativeLayout AccountInfo;
    RelativeLayout PrivacySettings;
    Button InitiateSignUp;
    Button InitiateSignIn;
    Button Dialog_Button;
    Button Save_btn;
    Button InitiateChange;
    TextView BackToDashboardFromSignIn;
    TextView NavigateToSignUpAnimation;
    TextView BackToDashboardFromSignUp;
    TextView BackToDashboardFromAppInfo;
    TextView BackToDashboardFromFreq;
    TextView SwitchToSignIn;
    TextView BackToDashboardFromNotes;
    TextView BackToDashboardFromAccInfo;
    TextView BackToDashboardFromPrivacySettings;

    //animation objects
    Animation animation_left;
    Animation animation_right;
    Animation animation_top;

    //right side anim
    CardView App_information;
    CardView Sign_Out;
    CardView Privacy_Settings;

    //left side anim
    CardView account_info;
    CardView Private_Notes;
    CardView Freq_Questions;

    //top anim
    FrameLayout MainFrame;

    public Account_Fragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_account_, container, false);
        Frag_Context = this.getContext();
        db = new PlayerDataBase(this.getContext());
        sql = db.getWritableDatabase();
        sql.close();
        Dash_Name = v.findViewById(R.id.Dashboard_TV);
        Login_TV = v.findViewById(R.id.Login_TV);
        Logout_IV = v.findViewById(R.id.Cardview_Logout);
        Main_Scroll = v.findViewById(R.id.Main_ScrollView_Cards);
        Grid_Cards = v.findViewById(R.id.Grid_Cards);
        Save_btn = v.findViewById(R.id.Save_btn);
        OldPassword = v.findViewById(R.id.OldPassword);
        NewPassword = v.findViewById(R.id.NewPassword);
        NewPasswordRepeat = v.findViewById(R.id.NewPasswordRepeat);
        SignUp = v.findViewById(R.id.Sign_Up_Layout);
        BackToDashboardFromAppInfo = v.findViewById(R.id.BackToDashboardFromAppInfo);
        SignIn = v.findViewById(R.id.Sign_In_Layout);
        PIEmail = v.findViewById(R.id.ProfileInfoEmail_ET);
        PIphone = v.findViewById(R.id.ProfileInfoPhone_ET);
        PIusername = v.findViewById(R.id.ProfileInfoUsername_ET);
        BackToDashboardFromPrivacySettings = v.findViewById(R.id.BackToDashboardFromPrivacySettings);
        AppInfo = v.findViewById(R.id.AppInformation_RL);
        AccountInfo = v.findViewById(R.id.AccountInfo_RL);
        PrivacySettings = v.findViewById(R.id.Privacy_Layout);
        ETPrivateNotes = v.findViewById(R.id.PrivateNotes_ET);
        BackToDashboardFromSignIn = v.findViewById(R.id.BackToDashboardFromSignIn);
        NavigateToSignUpAnimation = v.findViewById(R.id.NavigateToSignUpAnimation);
        BackToDashboardFromSignUp = v.findViewById(R.id.BackToDashboardFromSignUp);
        BackToDashboardFromFreq = v.findViewById(R.id.BackToDashboardFromFreqQuestions);
        BackToDashboardFromNotes = v.findViewById(R.id.BackToDashboardFromPrivateNotes);
        BackToDashboardFromAccInfo = v.findViewById(R.id.BackToDashboardFromAccountInfo);
        FreqQuestions = v.findViewById(R.id.FreqQuestion_RL);
        Dialog_Button = v.findViewById(R.id.Dialog_Cancel_Button);
        InitiateSignUp = v.findViewById(R.id.InitiateSignUp);
        SwitchToSignIn = v.findViewById(R.id.SwitchToSignIn);
        InitiateSignIn = v.findViewById(R.id.InitiateSignIn);
        MainFrame = v.findViewById(R.id.MainProfileFrame);
        App_information  = v.findViewById(R.id.App_Information_Card);
        Sign_Out = v.findViewById(R.id.Sign_Out_Card);
        Privacy_Settings = v.findViewById(R.id.Privacy_Settings_Card);
        Freq_Questions = v.findViewById(R.id.Freq_Questions_Card);
        Private_Notes = v.findViewById(R.id.Private_Notes_Card);
        account_info = v.findViewById(R.id.Account_Info_Card);
        PrivateNotes = v.findViewById(R.id.PrivateNotes_RL);
        InitiateChange = v.findViewById(R.id.InitiateChange);

        //On click listeners for buttons and TextViews
        InitiateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OldPass = OldPassword.getText().toString();
                String NewPass = NewPassword.getText().toString();
                String NewPassRepeat = NewPasswordRepeat.getText().toString();
                if(OldPass.matches("") || NewPass.matches("") || NewPassRepeat.matches(""))
                    ShowDialog(false, "Fill ALL fields!");
                else if(!(OldPass.equals(CurrentUser.getPassword())))
                    ShowDialog(false, "Old Password is incorrect");
                else if(!(NewPass.equals(NewPassRepeat)))
                    ShowDialog(false, "New Passwords do no match");
                else {
                    sql = db.getWritableDatabase();
                    db.deleteMemberByRow(CurrentUser.getID());
                    CurrentUser.setPassword(NewPass);
                    db.createUserRecord(CurrentUser);
                    ShowDialog(true, "Password has been changed");
                    sql.close();
                }
            }
        });
        BackToDashboardFromPrivacySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackToDashboardFromView(PrivacySettings);
            }
        });
        Privacy_Settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(CurrentUser != null){
                    StartLayoutAnimation(PrivacySettings);
                    EndElementAnimation();
                }
                else
                    ShowDialog(false, "Sign-in to access Settings");
            }
        });
        account_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentUser != null){
                    PIEmail.setText(CurrentUser.getEmail());
                    PIphone.setText(CurrentUser.getPhone());
                    PIusername.setText(CurrentUser.getName());
                    StartLayoutAnimation(AccountInfo);
                    EndElementAnimation();
                }
                else
                    ShowDialog(false, "Sign-in to access Information");
            }
        });
        BackToDashboardFromAccInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackToDashboardFromView(AccountInfo);
            }
        });
        BackToDashboardFromNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {BackToDashboardFromView(PrivateNotes);}
        });
        Private_Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentUser == null)
                    ShowDialog(false, "Sign-in to access Notes");
                else{
                    String notes_et = MainActivity.buildPrivateNotes(Frag_Context);
                    ETPrivateNotes.setText(notes_et);
                    EndElementAnimation();
                    StartLayoutAnimation(PrivateNotes);
                }
            }
        });
        BackToDashboardFromFreq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){BackToDashboardFromView(FreqQuestions);}
        });
        Freq_Questions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){EndElementAnimation(); StartLayoutAnimation(FreqQuestions);}
        });
        BackToDashboardFromAppInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ BackToDashboardFromView(AppInfo);}
        });
        App_information.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EndElementAnimation();
                StartLayoutAnimation(AppInfo);
            }
        });
        Sign_Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInOrOut();
            }
        });
        BackToDashboardFromSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackToDashboardFromView(SignIn);
            }
        });
        NavigateToSignUpAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateToSignUpAnimation();
            }
        });
        BackToDashboardFromSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackToDashboardFromView(SignUp);
            }
        });
        InitiateSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitiateSignUp();
            }
        });
        SwitchToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { StartLayoutAnimation(SignUp); }
        });
        Dialog_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveDialog();
            }
        });
        InitiateSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitiateSignIn();
            }
        });


        Dialog_Type_Cancel = v.findViewById(R.id.Dialog_Type_Cancel);
        res = getResources();
        BuildMainView();
        return v;
    }


    //buttons event listeners:
    public void SignInOrOut() {
        if(CurrentUser == null){
            EndElementAnimation();
            StartLayoutAnimation(SignIn);
        }
        else{
            CurrentUser = null;
            BuildMainView();
        }
    }

    //Uses the other methods to build a first view for the user (Grid Cards)
    public void BuildMainView(){
        if(CurrentUser == null){
            Login_TV.setText("Sign in");
            Dash_Name.setText("Guest's Dashboard");
            Logout_IV.setImageDrawable(res.getDrawable(R.drawable.cardview_logout_180));
        }
        else{
            Dash_Name.setText(CurrentUser.getName() + "'s Dashboard");
            Login_TV.setText("Sign out");
        }
    }

    //--------------------------------------DataBase Methods--------------------------------------//

    //checks if name already in db
    private boolean checkIfExist(String Name) {
        sql = db.getReadableDatabase();
        c = sql.query(PlayerDataBase.TABLE_RECORD,null,null,null,null,null,null);
        int col1 = c.getColumnIndex(PlayerDataBase.COLUMN_NAME);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            String s1 = c.getString(col1);
            if(s1.equals(Name)){
                return true;
            }
            c.moveToNext();
        }
        c.close();
        sql.close();
        return false;
    }

    //User tries to Sign-Up after filling data
    public void InitiateSignUp() {
        SignUp_Email = v.findViewById(R.id.Email_ET_SignUp);
        SignUp_Name = v.findViewById(R.id.Name_ET_SignUp);
        SignUp_Password = v.findViewById(R.id.Password_ET_SignUp);
        SignUp_Phone = v.findViewById(R.id.Phone_ET_SignUp);
        String Email = SignUp_Email.getText().toString();
        String Name = SignUp_Name.getText().toString();
        String Pass = SignUp_Password.getText().toString();
        String Phone = SignUp_Phone.getText().toString();
        if(checkIfExist(Name)){
            ShowDialog(false, "This username is already taken");
        }
        else if(Email.matches("") || Name.matches("") || Phone.matches("") || Pass.matches("")){
            ShowDialog(false, "Missing Information!");
        }
        else{
            ShowDialog(true, "You have successfully signed up!");
            CurrentUser = new PlayerDataBase.User(Name, Pass, Phone, Email);
            sql = db.getWritableDatabase();
            db.createUserRecord(CurrentUser);
            Save_btn.callOnClick();
            sql.close();
        }
    }

    //User tries to Sign-In after filling data
    public void InitiateSignIn() {
        Boolean isValid = false;
        SignIn_Name = v.findViewById(R.id.Sign_In_User_ET);
        SignIn_Password = v.findViewById(R.id.Sign_In_Pass_ET);
        sql = db.getReadableDatabase();
        Cursor c = sql.query(PlayerDataBase.TABLE_RECORD,null,null,null,null,null,null);
        int col1 = c.getColumnIndex(PlayerDataBase.COLUMN_NAME);
        int col2 = c.getColumnIndex(PlayerDataBase.COLUMN_PASSWORD);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            String s1 = c.getString(col1);
            String s2 = c.getString(col2);
            if(s1.equals(SignIn_Name.getText().toString()) && s2.equals(SignIn_Password.getText().toString()) ) {
                String s3 = c.getString(c.getColumnIndex(PlayerDataBase.COLUMN_PHONE));
                String s4 = c.getString(c.getColumnIndex(PlayerDataBase.COLUMN_EMAIL));
                CurrentUser = new PlayerDataBase.User(s1,s2,s3,s4);
                c.close();
                sql.close();
                isValid = true;
                break;
            }
            c.moveToNext();
        }
        c.close();
        sql.close();
        if(isValid){
            ShowDialog(true, "You have successfully signed in!");
        }
        else
            ShowDialog(false, "Information is incorrect");
    }

    //--------------------------------------END - DataBase Methods--------------------------------------//

    //--------------------------------------Animation methods--------------------------------------//

    //Grid Cards:

    //Starts the Grid Cards 'ease-out-from-sides' animation sequence
    //called when user needs to close the grid cards view (sign-in / menu maneuver)
    public void EndElementAnimation(){
        //fetch xml animation files
        animation_right = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easeout_right);
        animation_left = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easeout_left);
        //animate right col
        account_info.startAnimation(animation_left);
        Private_Notes.startAnimation(animation_left);
        App_information.startAnimation(animation_left);
        //animate left col
        Privacy_Settings.startAnimation(animation_right);
        Freq_Questions.startAnimation(animation_right);
        Sign_Out.startAnimation(animation_right);
        animation_right.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                Grid_Cards.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    //Starts the Login/Logout form 'ease-out-from-bottom' animation sequence
    //Called when user clicks 'back to dashboard' (param = current view)
    public void BackToDashboardFromView(RelativeLayout form) {
        animation_top = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easeout_bottom);
        form.startAnimation(animation_top);
        animation_top.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                form.setVisibility(View.INVISIBLE);
                Grid_Cards.setVisibility(View.VISIBLE);
                //fetch xml animation files
                animation_top = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easein_top);
                animation_right = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easein_right);
                animation_left = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easein_left);
                //animate left col
                account_info.startAnimation(animation_left);
                Private_Notes.startAnimation(animation_left);
                App_information.startAnimation(animation_left);
                //animate right col
                Privacy_Settings.startAnimation(animation_right);
                Freq_Questions.startAnimation(animation_right);
                Sign_Out.startAnimation(animation_right);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    //sign-in:

    //Starts the sign-in form 'ease-in-from-bottom' animation sequence
    //Called when user needs to sign in from the main dashboard (param = false)
    //Called when user navigates to sign-in from sign-up view (param = true) (while closing sign-up view)
    public void StartLayoutAnimation(RelativeLayout layout) {
        if(!(layout.equals(SignUp))){
            layout.setVisibility(View.VISIBLE);
            animation_top = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easein_bottom);
            layout.startAnimation(animation_top);
        }
        else{
            animation_right = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easeout_right);
            animation_left = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easein_left);
            SignUp.startAnimation(animation_right);
            animation_right.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    SignUp.setVisibility(View.INVISIBLE);
                    SignIn.setVisibility(View.VISIBLE);
                    SignIn.startAnimation(animation_left);
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        }
    }

    //sign-up:

    //Starts the sign-up form 'ease-in-from-right' animation sequence (while closing sign-in view)
    //Called when user navigates to the sign-up from the sign-in view
    public void NavigateToSignUpAnimation(){
        animation_right = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easein_right);
        animation_left = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_easeout_left);
        SignIn.startAnimation(animation_left);
        animation_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                SignIn.setVisibility(View.INVISIBLE);
                SignUp.setVisibility(View.VISIBLE);
                SignUp.startAnimation(animation_right);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
    }

    //Starts the sign-up form 'ease-out-from-right' animation sequence (while closing sign-up view)
    //Called when user needs to go back to the dashboard from sign-up
    public void BackToDashboardFromSignUp() {
        BackToDashboardFromView(SignUp);
    }


    //Dialog:

    //Cancel Dialog
    public void RemoveDialog() {
        TextView Dialog_Main = v.findViewById(R.id.Dialog_MainMessage);
        if(Dialog_Main.getText().toString() == "You have successfully signed up!"){
            StartLayoutAnimation(SignUp);
            EditText SignIn_Username = v.findViewById(R.id.Sign_In_User_ET);
            EditText SignIn_Password = v.findViewById(R.id.Sign_In_Pass_ET);
            SignIn_Username.setText(CurrentUser.getName());
            SignIn_Password.setText(CurrentUser.getPassword());
        }
        else if(Dialog_Main.getText().toString() == "You have successfully signed in!"){
            BackToDashboardFromView(SignIn);
            BuildMainView();
        }
        animation_top = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_fadeout);
        Dialog_Type_Cancel.startAnimation(animation_top);
        animation_top.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                Dialog_Type_Cancel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
    //Show up Success / Error Dialog
    public void ShowDialog(boolean IsSuccess, String txt) {
        animation_top = AnimationUtils.loadAnimation(Frag_Context, R.anim.animation_fadein);
        TextView Dialog_Main = v.findViewById(R.id.Dialog_MainMessage);
        TextView Dialog_Title = v.findViewById(R.id.Dialog_Title);
        ImageView Dialog_Icon = v.findViewById(R.id.Error_Cry_Icon);
        if(IsSuccess){
            Dialog_Button.setText("Continue");
            Dialog_Main.setText(txt);
            Dialog_Title.setText("Success!");
            Dialog_Icon.setImageDrawable(res.getDrawable(R.drawable.success_happy_icon));
        }
        else{
            Dialog_Button.setText("Cancel");
            Dialog_Main.setText(txt);
            Dialog_Title.setText("Uh oh");
            Dialog_Icon.setImageDrawable(res.getDrawable(R.drawable.dialog_error_icon));
        }
        Dialog_Type_Cancel.setVisibility(View.VISIBLE);
        Dialog_Type_Cancel.startAnimation(animation_top);

    }
    //--------------------------------------END - Animation methods--------------------------------------//
}