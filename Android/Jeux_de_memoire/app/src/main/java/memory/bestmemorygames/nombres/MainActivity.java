package memory.bestmemorygames.nombres;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import memory.bestmemorygames.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button tabButton[] =new Button[24];
    ArrayList buttonArray =new ArrayList<Button>();
    ArrayList caseAcocher=new ArrayList<Button>();
    Integer nombreDecase=1;
    Drawable color;
    private long timer=3000;
    private long timeMillis=timer;
    private boolean timerRunning;
    private int score=0;
    private static int MIN_DEBUT=10;
    private static int MAX_DEBUT=20;
    private static int MIN_INTERVAL=1;
    private static int MAX_INTERVAL=9;
    protected Son error;
    protected Son win;
    protected Handler handlerFin;
    protected ControlTimer ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nombres);
        error=new Son(R.raw.erreur2,this);
        win=new Son(R.raw.correct,this);
        //grandNombre();
        UpdateId();
        setButtonArray();
        assigneCase(nombreDecase);
        changeText();
        startTimer();
        color=tabButton[0].getBackground();
        handlerFin = new Handler();
        ct = new ControlTimer(null, this);
    }


    public void UpdateId(){
         for (int i = 0; i < 24; i++) {
             tabButton[i] = new Button(this);
             int id=getResources().getIdentifier("button"+i, "id", getPackageName());
             tabButton[i].setId(id);
         }
    }
    public int genenAlea(){
        int c = (int) (Math.random() * buttonArray.size());
        return c;
    }
    public void setButtonArray(){
        for (int i = 0; i < tabButton.length; i++) {
             buttonArray.add(tabButton[i]);
        }
    }
     public void assigneCase(int i){
         Button button=new Button(this);
         for (int j = 0; j < i; j++) {
             int h=genenAlea();
             button=(Button)buttonArray.get(h);
             caseAcocher.add(buttonArray.get(h));
             buttonArray.remove(h);
         }
         buttonArray.clear();
         setButtonArray();
     }
     public void changeText() {
         Button button;
         Button assigned;
         int size = caseAcocher.size();
         int prochainNombre=(int) Math.round(Math.random()*(MAX_DEBUT-MIN_DEBUT)+MIN_DEBUT);
         for (int i = 0; i < size; i++) {
             button = (Button) caseAcocher.get(i);
             button.setText("" + prochainNombre);
             prochainNombre+= (int) Math.round(Math.random()*(MAX_INTERVAL-MIN_INTERVAL)+MIN_INTERVAL);
             assigned = findViewById(button.getId());
             assigned.setText(button.getText());
             assigned.setBackgroundColor(Color.WHITE);
             assigned.setTextColor(Color.BLACK);
             assigned.setEnabled(false);
             caseAcocher.set(i, button);

         }
     }
     public void clearBoard(){
         Button button,assigned;
         for (int i = 0; i < buttonArray.size(); i++) {
             button=(Button)buttonArray.get(i);
             assigned=findViewById(button.getId());
             assigned.setText("");
         }
     }
     public void cliquableButton(){
         Button button=new Button(this);
         Button assigned=new Button(this);
         for (int i = 0; i < caseAcocher.size(); i++) {
             button=(Button)caseAcocher.get(i);
             assigned=findViewById(button.getId());
             assigned.setEnabled(true);
             assigned.setText("");

         }
     }
     public void disableAllButton(){
         Button button=new Button(this);
         Button assigned =new Button(this);
         for (int i = 0; i <buttonArray.size() ; i++) {
             button=(Button)buttonArray.get(i);
             assigned=findViewById(button.getId());
             assigned.setEnabled(false);
         }
     }
    public void printAllCase(){
        for (int i = 0; i < caseAcocher.size(); i++) {
            System.out.println("print "+caseAcocher.get(i));

        }
    }

    public void send(View view) {
         Button buttonText,buttonACliquer;
         buttonText=(Button)caseAcocher.get(0);
         buttonACliquer=findViewById(buttonText.getId());
         Button buttonCliqued;
         buttonCliqued=findViewById(view.getId());


         if (view.getId()==buttonText.getId()){
             buttonCliqued.setText(buttonText.getText());
             buttonCliqued.setEnabled(false);
             buttonCliqued.setBackground(color);
             caseAcocher.remove(0);
             score++;

             TextView tv;
             tv=findViewById(R.id.textField);
             tv.setText(getString(R.string.debScore) + ": "+score);

         } else {
             error.jouer();
             buttonCliqued.setBackgroundColor(Color.RED);
             buttonACliquer.setBackgroundColor(Color.GREEN);
             displayCaseRestante();
             disableAllButton();
             ct.start(handlerFin, 5000);
         }

        if (caseAcocher.isEmpty()){
            win.jouer();
            clearBoard();
            nombreDecase++;
            assigneCase(nombreDecase);
            changeText();

            timer=timer-200;
            startTimer();

        }
    }

    private void displayCaseRestante() {
        Button button,assigned;
        for (int i = 0; i < caseAcocher.size(); i++) {
            button=(Button)caseAcocher.get(i);
            assigned=findViewById(button.getId());
            assigned.setText(button.getText());
        }
    }

    private void startTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(timer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeMillis = millisUntilFinished;
            }
            @Override
            public void onFinish() {
                timerRunning = false;
                cliquableButton();
            }
        }.start();

        timerRunning = true;
    }

    public void beforeAlert() {
        nombreDecase=1;
        Button restartButton;
        restartButton=findViewById(R.id.restart);
        restartButton.setText(getString(R.string.Recommencer));
        restartButton.setVisibility(View.VISIBLE);
        restartButton.setEnabled(true);
        alert();
    }

    public void alert() {
        final EditText name = new EditText(this);
        name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        name.setLayoutParams(lp);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.LoseText) + " " + score + "\n" + getString(R.string.demandeNom));
        builder.setView(name);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.Title));
        builder.setPositiveButton(getString(R.string.rejouer), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton(getString(R.string.revenir), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void restart(){
        Intent intent=getIntent();
        finish();
        startActivity(intent);

    }

    public void restart(View view) {
        restart();

    }
    public static void petitNombre(){
        MIN_DEBUT=1;
        MAX_DEBUT=5;
        MIN_INTERVAL=1;
        MAX_INTERVAL=3;
    }
    public static void moyenNombre(){
        MIN_DEBUT=10;
        MAX_DEBUT=20;
        MIN_INTERVAL=1;
        MAX_INTERVAL=9;
    }
    public static void grandNombre(){
        MIN_DEBUT=100;
        MAX_DEBUT=150;
        MIN_INTERVAL=1;
        MAX_INTERVAL=25;
    }
}
