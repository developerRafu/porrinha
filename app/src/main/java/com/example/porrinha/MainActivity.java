package com.example.porrinha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnItemSelected;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    private int totalPalitos = (this.palitosApp + this.palitosUsuario);
    private List<ImageView> palitos = new ArrayList<>();
    private Button button;
    private ImageView maoFechada;
    private ImageView maoAberta;
    private ImageView palitoUm;
    private ImageView palitoDois;
    private ImageView palitoTres;
    private TextView txtResultado;
    private Spinner spinnerChute;
    private Spinner spinnerPalitos;

    private int chuteUsuario = 0;
    private int palitosUsuario = 3;
    private int palitosQueOUsuarioVaiJogar = 0;

    private int chuteApp;
    private int palitosApp = 3;
    private int palitosQueOAppVaiJogar;

    private int sumUserAndApp;
    private boolean existsWinner = false;
    private String winner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.instiateVars();
        this.addActionsToButtons();
        this.hideImgs();
        this.loadSpinnerChute();
        this.loadSpinnerPalitos();
    }

    private void instiateVars() {
        this.button = this.findViewById(R.id.button);
        this.maoFechada = this.findViewById(R.id.maoFechada);
        this.maoAberta = this.findViewById(R.id.maoAberta);
        this.palitoUm = this.findViewById(R.id.palitoUm);
        this.palitoDois = this.findViewById(R.id.palitoDois);
        this.palitoTres = this.findViewById(R.id.palitoTres);
        this.txtResultado = this.findViewById(R.id.txtResultado);
        this.spinnerChute = this.findViewById(R.id.spinnerChute);
        this.spinnerPalitos = this.findViewById(R.id.spinnerPalito);
        this.palitos.addAll(Arrays.asList(this.palitoUm, this.palitoDois, this.palitoTres));
    }

    private void addActionsToButtons() {
        this.button.setOnClickListener(view -> this.actionEvent());
    }

    private void actionEvent() {
        if (existsWinner) {
            Toast.makeText(this, winner + " ganhou, fim de jogo", Toast.LENGTH_SHORT).show();
            return;
        }
        this.hideImgs();
        this.setValueOfApp();
        this.sumUserAndApp = (this.palitosQueOAppVaiJogar + this.palitosQueOUsuarioVaiJogar);
        this.campareAll();
        this.loadSpinnerChute();
        this.loadSpinnerPalitos();
        this.ifThereIsWinner();
    }

    private void ifThereIsWinner() {
        if (this.palitosUsuario == 0) {
            this.existsWinner = true;
            this.winner = "usuário";
            this.txtResultado.setText("Usuário ganhou!!!\nResultado: " + this.sumUserAndApp + "\nChute usuário: " + this.chuteUsuario + "\nChute App: " + this.chuteApp);
            return;
        }
        if (this.palitosApp == 0) {
            this.existsWinner = true;
            this.winner = "App";
            this.txtResultado.setText("App ganhou!!!\nResultado: " + this.sumUserAndApp + "\nChute usuário: " + this.chuteUsuario + "\nChute App: " + this.chuteApp);
            return;
        }
    }

    private void setValueOfApp() {
        int limite = this.palitosApp + 1;
        Random random = new Random();
        this.palitosQueOAppVaiJogar = random.nextInt(limite);
        int limiteChute = this.palitosUsuario;
        this.chuteApp = random.nextInt(limiteChute) + this.palitosQueOAppVaiJogar;
    }

    private void campareAll() {
        for (int i = 0; i < this.palitosQueOAppVaiJogar; i++) {
            this.maoFechada.setVisibility(View.INVISIBLE);
            this.maoAberta.setVisibility(View.VISIBLE);
            this.palitos.get(i).setVisibility(View.VISIBLE);
        }
        if (this.palitosQueOAppVaiJogar == 0) {
            this.maoFechada.setVisibility(View.INVISIBLE);
            this.maoAberta.setVisibility(View.VISIBLE);
            this.palitos.forEach(p -> p.setVisibility(View.INVISIBLE));
        }
        if (this.chuteApp == this.chuteUsuario) {
            this.txtResultado.setText("Empate!!!\nResultado: " + this.sumUserAndApp + "\nChute usuário: " + this.chuteUsuario + "\nChute App: " + this.chuteApp);
            this.palitosUsuario--;
            return;
        }
        if (this.sumUserAndApp == this.chuteUsuario) {
            this.txtResultado.setText("Usuário acertou!!!\nResultado: " + this.sumUserAndApp + "\nChute usuário: " + this.chuteUsuario + "\nChute App: " + this.chuteApp);
            this.palitosUsuario--;
            return;
        }
        if (this.sumUserAndApp == this.chuteApp) {
            this.palitosApp--;
            this.txtResultado.setText("O App acertou!!!\nResultado: " + this.sumUserAndApp + "\nChute usuário: " + this.chuteUsuario + "\nChute App: " + this.chuteApp);
            return;
        }
        this.txtResultado.setText("Ninguém acertou!!!\nResultado: " + this.sumUserAndApp + "\nChute usuário: " + this.chuteUsuario + "\nChute App: " + this.chuteApp);
    }

    private void hideImgs() {
        this.maoFechada.setVisibility(View.VISIBLE);
        this.maoAberta.setVisibility(View.INVISIBLE);
        this.palitos.forEach(p -> p.setVisibility(View.INVISIBLE));
    }

    private void loadSpinnerChute() {
        List<String> view = new ArrayList<>();

        for (int i = 0; i <= totalPalitos; i++) {
            view.add("" + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, view);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinnerChute.setAdapter(adapter);
    }

    @OnItemSelected(R.id.spinnerChute)
    public void onSpinnerItemSelected(int index) {
        this.chuteUsuario = index;
    }

    @OnItemSelected(R.id.spinnerPalito)
    public void onPalitoSelected(int index) {
        this.palitosQueOUsuarioVaiJogar = index;
    }

    private void loadSpinnerPalitos() {
        List<String> view = new ArrayList<>();

        for (int i = 0; i <= this.palitosUsuario; i++) {
            view.add("" + i);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, view);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinnerPalitos.setAdapter(dataAdapter);

    }
}