package com.example.calculadoranotasads;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.calculadoranotasads.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {




        private EditText editTextNome, editTextEmail, editTextIdade, editTextDisciplina, editTextNota1, editTextNota2;
        private TextView textViewErro, textViewResumo;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            editTextNome = findViewById(R.id.editTextNome);
            editTextEmail = findViewById(R.id.editTextEmail);
            editTextIdade = findViewById(R.id.editTextIdade);
            editTextDisciplina = findViewById(R.id.editTextDisciplina);
            editTextNota1 = findViewById(R.id.editTextNota1);
            editTextNota2 = findViewById(R.id.editTextNota2);
            textViewErro = findViewById(R.id.textViewErro);
            textViewResumo = findViewById(R.id.textViewResumo);

            Button buttonEnviar = findViewById(R.id.buttonEnviar);
            Button buttonLimpar = findViewById(R.id.buttonLimpar);

            buttonEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validarDados();
                }
            });

            buttonLimpar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    limparFormulario();
                }
            });
        }

        private void validarDados() {
            String nome = editTextNome.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String idadeStr = editTextIdade.getText().toString().trim();
            String disciplina = editTextDisciplina.getText().toString().trim();
            String nota1Str = editTextNota1.getText().toString().trim();
            String nota2Str = editTextNota2.getText().toString().trim();

            StringBuilder erros = new StringBuilder();
            boolean isValid = true;

            if (TextUtils.isEmpty(nome)) {
                erros.append("O campo de nome não pode estar vazio.\n");
                isValid = false;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                erros.append("O email é inválido.\n");
                isValid = false;
            }

            int idade = 0;
            if (TextUtils.isEmpty(idadeStr) || !TextUtils.isDigitsOnly(idadeStr)) {
                erros.append("A idade deve ser um número valido.\n");
                isValid = false;
            } else {
                idade = Integer.parseInt(idadeStr);
                if (idade <= 0) {
                    erros.append("A idade deve ser um número positivo.\n");
                    isValid = false;
                }
            }

            double nota1 = 0, nota2 = 0;
            if (TextUtils.isEmpty(nota1Str) || TextUtils.isEmpty(nota2Str)) {
                erros.append("As notas devem ser preenchidas.\n");
                isValid = false;
            } else {
                try {
                    nota1 = Double.parseDouble(nota1Str);
                    nota2 = Double.parseDouble(nota2Str);
                    if (nota1 < 0 || nota1 > 10 || nota2 < 0 || nota2 > 10) {
                        erros.append("As notas devem estar entre 0 e 10.\n");
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    erros.append("As notas devem ser números.\n");
                    isValid = false;
                }
            }

            if (isValid) {
                exibirResumo(nome, email, idade, disciplina, nota1, nota2);
            } else {
                textViewErro.setText(erros.toString());
                textViewErro.setVisibility(View.VISIBLE);
                textViewResumo.setVisibility(View.GONE);
            }
        }

        private void exibirResumo(String nome, String email, int idade, String disciplina, double nota1, double nota2) {
            double media = (nota1 + nota2) / 2;
            String mensagem = media >= 6 ? "Aprovado" : "Reprovado";

            String resumo = "Nome: " + nome + "\n" +
                    "Email: " + email + "\n" +
                    "Idade: " + idade + "\n" +
                    "Disciplina: " + disciplina + "\n" +
                    "Notas do 1 e 2 Bimestres: " + nota1 + ", " + nota2 + "\n" +
                    "Média do aluno: " + media + "\n" +
                    "Situação: " + mensagem;

            textViewResumo.setText(resumo);
            textViewResumo.setVisibility(View.VISIBLE);
            textViewErro.setVisibility(View.GONE);
        }

        private void limparFormulario() {
            editTextNome.setText("");
            editTextEmail.setText("");
            editTextIdade.setText("");
            editTextDisciplina.setText("");
            editTextNota1.setText("");
            editTextNota2.setText("");
            textViewErro.setText("");
            textViewErro.setVisibility(View.GONE);
            textViewResumo.setVisibility(View.GONE);
        }
    }

