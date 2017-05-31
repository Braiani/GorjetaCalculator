package com.example.braiani.gorjetacalculator;

import android.support.annotation.IdRes;
//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {
    Button btnAddPessoa, btnRemPessoa, btnLimpar, btnCalcular;
    EditText edTxtValorTotal, edTxtNumPessoas, edTxtPorcGorjeta;
    TextView txtValorGorjeta, txtValorFinal, txtValorPessoa;
    RadioGroup radioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        encontrarViews(); //Chama a função responsável por fazer os "findViewById()"
        btnCalcular.setOnClickListener(ouvinte);
        btnRemPessoa.setOnClickListener(ouvinte);
        btnAddPessoa.setOnClickListener(ouvinte);
        btnLimpar.setOnClickListener(ouvinte);
        radioBtn.setOnCheckedChangeListener(radioChangeOuvinte);
    }

    RadioGroup.OnCheckedChangeListener radioChangeOuvinte = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (checkedId == R.id.rdBtnOutro){
                edTxtPorcGorjeta.setEnabled(true);
            }else if (edTxtPorcGorjeta.isEnabled()){
                edTxtPorcGorjeta.setEnabled(false);
            }
        }
    };

    View.OnClickListener ouvinte = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnAddPessoa:
                    addPessoa();
                    break;
                case R.id.btnRemPessoa:
                    removePessoa();
                    break;
                case R.id.btnCalcular:
                    calcular();
                    break;
                case R.id.btnLimpar:
                    limparTela();
                    break;
            }
        }
    };

    //Função para aumentar de 1 em 1 o número de pessoas na mesa
    public void addPessoa(){

        if (edTxtNumPessoas.getError() != null){
            edTxtNumPessoas.setError(null);
        }

        String textoRetorno;
        int numPess = 0;
        if (edTxtNumPessoas.getText().toString().length() > 0){
            numPess = Integer.parseInt(edTxtNumPessoas.getText().toString());
        }
        if (numPess < 100){
            numPess++;
        }else {
            Toast.makeText(getApplicationContext(),"Impossível mesa com mais de 100 cliente", Toast.LENGTH_SHORT).show();
        }
        textoRetorno = String.valueOf(numPess);
        edTxtNumPessoas.setText(textoRetorno);
    }

    //Função para remover de 1 em 1 o número de pessoas na mesa
    public void removePessoa(){

        if (edTxtNumPessoas.getError() != null){
            edTxtNumPessoas.setError(null);
        }
        
        String textoRetorno;
        int numPess = 1;
        if (edTxtNumPessoas.getText().toString().length() > 0){
            numPess = Integer.parseInt(edTxtNumPessoas.getText().toString());
        }
        if (numPess > 1){
            numPess--;
        }else {
            Toast.makeText(getApplicationContext(),"Impossível mesa sem cliente", Toast.LENGTH_SHORT).show();
        }
        textoRetorno = String.valueOf(numPess);
        edTxtNumPessoas.setText(textoRetorno);
    }

    //Função para limpar a tela e setar os valores padrões
    public void limparTela(){
        edTxtNumPessoas.setText("3");
        edTxtValorTotal.setText("");
        radioBtn.check(R.id.rdBtn15);
        txtValorGorjeta.setText("R$ 000,00");
        txtValorFinal.setText("R$ 000,00");
        txtValorPessoa.setText("R$ 000,00");
        edTxtPorcGorjeta.setText("");
        edTxtPorcGorjeta.setEnabled(false);
    }

    public void calcular(){
        //Chama a função de verificação dos campos e caso retorne "true" faz o cálculo
        if (verificarCampos()){
            double valorFinal, total, porcentagem, totalGorjeta, totalPessoa;
            int numPessoas;
            numPessoas = Integer.parseInt(edTxtNumPessoas.getText().toString());
            valorFinal = Double.parseDouble(edTxtValorTotal.getText().toString());
            switch (radioBtn.getCheckedRadioButtonId()) {
                case R.id.rdBtn15:
                    porcentagem = 15;
                    break;
                case R.id.rdBtn20:
                    porcentagem = 20;
                    break;
                case R.id.rdBtnOutro:
                    porcentagem = Double.parseDouble(edTxtPorcGorjeta.getText().toString());
                    break;
                default:
                    porcentagem = 1;
                    break;
            }
            total = valorFinal + (valorFinal * (porcentagem / 100));
            totalGorjeta = valorFinal * porcentagem / 100;
            totalPessoa = total / numPessoas;

            txtValorGorjeta.setText("R$ " + String.valueOf(String.format(Locale.getDefault(), "%.2f", totalGorjeta)));
            txtValorPessoa.setText("R$ " + String.valueOf(String.format(Locale.getDefault(), "%.2f", totalPessoa)));
            txtValorFinal.setText("R$ " + String.valueOf(String.format(Locale.getDefault(), "%.2f", total)));
        }

    }

    //função responsável de verificar o preenchimento adequado dos campos
    public boolean verificarCampos(){
        if (edTxtValorTotal.getText().toString().length() <= 0){
            edTxtValorTotal.setError("Valor total em branco");
            return false;
        }
        if(edTxtNumPessoas.getText().toString().length() <= 0){
            edTxtNumPessoas.setError("Número de pessoas inválido");
            return false;
        }
        if (radioBtn.getCheckedRadioButtonId() == R.id.rdBtnOutro) {
            if (edTxtPorcGorjeta.getText().toString().length() <= 0) {
                edTxtPorcGorjeta.setError("Porcentagem inválida");
                return false;
            }
        }
        return true;
    }
    //Função responsável por fazer os "findViewById()"
    public void encontrarViews(){
        btnAddPessoa = (Button) findViewById(R.id.btnAddPessoa);
        btnRemPessoa = (Button) findViewById(R.id.btnRemPessoa);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        edTxtValorTotal = (EditText) findViewById(R.id.edTxtValorTotal);
        edTxtNumPessoas = (EditText) findViewById(R.id.edTxtNumPessoas);
        edTxtPorcGorjeta = (EditText) findViewById(R.id.edTxtPorcGorjeta);
        radioBtn = (RadioGroup) findViewById(R.id.radioGroup);
        txtValorFinal = (TextView) findViewById(R.id.txtTotalaPagar);
        txtValorGorjeta = (TextView) findViewById(R.id.txtValorGorjeta);
        txtValorPessoa = (TextView) findViewById(R.id.txtTotalporPessoa);
    }
}
