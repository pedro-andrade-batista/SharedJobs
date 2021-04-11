package br.edu.ifsp.scl.ads.pdm.sharedjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nomeEt;
    private EditText emailEt;
    private EditText telefoneEt;
    private EditText telefoneCelularEt;
    private EditText dataNascimentoEt;
    private EditText anoFormaturaEt;
    private EditText anoConclusaoEt;
    private EditText instituicaoEt;
    private EditText tituloMonografiaEt;
    private EditText orientadorEt;
    private EditText vagaInteresseEt;
    private CheckBox emailCb;
    private CheckBox addTelefoneCb;
    private LinearLayout tipoTelefoneLl;
    private LinearLayout telefoneCelularLl;
    private RadioGroup tipoTelefoneRg;
    private RadioGroup sexoRg;
    private RadioButton telefoneResidencialRb;
    private RadioButton telefoneComercialRb;
    private RadioButton masculinoRb;
    private RadioButton femininoRb;
    private Spinner formacaoSp;
    private Button salvarBt;
    private Button limparBt;

    private String phone;
    private String gender;
    private String typeOfPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        telefoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s == null || s.length() == 0)
                    makeVisibleTypeOfPhone(View.GONE);
                else{
                    makeVisibleTypeOfPhone(View.VISIBLE);
                    phone = telefoneCelularEt.getText().toString() + s;
                }
                    
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        formacaoSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) ((TextView) view).getText();
                if(selected.equals("Fundamental") || selected.equals("Médio"))
                    showFieldsForMedioOrFundamental();
                else if(selected.equals("Graduação") || selected.equals("Especialização")) {
                    showFieldsForMedioOrFundamental();
                    showFieldsForGraduacaoOrEspecializacao();
                }
                else
                    showFieldsForMestradoOrDoutorado();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void showFieldsForMestradoOrDoutorado() {
        cleanAllFieldsOfSpinner();
        showFieldsForGraduacaoOrEspecializacao();
        orientadorEt.setVisibility(View.VISIBLE);
        tituloMonografiaEt.setVisibility(View.VISIBLE);
    }

    private void showFieldsForGraduacaoOrEspecializacao() {
        cleanAllFieldsOfSpinner();
        anoFormaturaEt.setVisibility(View.GONE);
        anoConclusaoEt.setVisibility(View.VISIBLE);
        instituicaoEt.setVisibility(View.VISIBLE);


    }

    private void showFieldsForMedioOrFundamental() {
        cleanAllFieldsOfSpinner();
        anoFormaturaEt.setVisibility(View.VISIBLE);
        anoConclusaoEt.setVisibility(View.GONE);
        instituicaoEt.setVisibility(View.GONE);
        tituloMonografiaEt.setVisibility(View.GONE);
        orientadorEt.setVisibility(View.GONE);
    }

    private void makeVisibleTypeOfPhone(int visible) {
        tipoTelefoneLl.setVisibility(visible);
    }

    public void onClickButton(View view){
        switch (view.getId()){
            case R.id.salvarBt:
                saveForm();
                break;
            case R.id.limparBt:
                cleanForm();
                break;
            default:
                break;
        }

    }

    private void cleanForm() {
        nomeEt.setText("");
        emailEt.setText("");
        emailCb.setChecked(false);
        telefoneEt.setText("");
        addTelefoneCb.setChecked(false);
        telefoneCelularLl.setVisibility(View.GONE);
        telefoneCelularEt.setText("");
        masculinoRb.setChecked(true);
        dataNascimentoEt.setText("");
        cleanAllFieldsOfSpinner();
        vagaInteresseEt.setText("");

    }

    public void isCheckedAddCellPhone(View view){
        if(addTelefoneCb.isChecked())
            telefoneCelularLl.setVisibility(View.VISIBLE);
        else{
            telefoneCelularLl.setVisibility(View.GONE);
            telefoneCelularEt.setText("");
        }
    }

    private void getGender(){
       switch(sexoRg.getCheckedRadioButtonId()){
           case R.id.masculinoRb:
                gender = "Masculino";
                break;
           case R.id.femininoRb:
               gender = "Feminino";
               break;
       }
    }

    private void getTypeOfPhone(){
        switch (tipoTelefoneRg.getCheckedRadioButtonId()){
            case R.id.telefoneResidencialRb:
                typeOfPhone = "Residencial";
                break;
            case R.id.telefoneComercialRb:
                typeOfPhone = "Comercial";
                break;
        }
    }

    private String getMessageOfSpinner(){
        StringBuilder messageSpinner = new StringBuilder();
        if(!(anoFormaturaEt.getText().toString().equals("")))
            messageSpinner.append("Ano da formatura: ").append(anoFormaturaEt.getText()).append("\n");
        if(!(anoConclusaoEt.getText().toString().equals("")))
            messageSpinner.append("Ano da conclusao: ").append(anoConclusaoEt.getText()).append("\n");
        if(!(instituicaoEt.getText().toString().equals("")))
            messageSpinner.append("Instituição: ").append(instituicaoEt.getText()).append("\n");
        if(!(tituloMonografiaEt.getText().toString().equals("")))
            messageSpinner.append("Titulo da monografia: ").append(tituloMonografiaEt.getText()).append("\n");
        if(!(orientadorEt.getText().toString().equals("")))
            messageSpinner.append("Orientador: ").append(orientadorEt.getText()).append("\n");

        return String.valueOf(messageSpinner);
    }

    private void cleanAllFieldsOfSpinner(){
        anoFormaturaEt.setText("");
        anoConclusaoEt.setText("");
        instituicaoEt.setText("");
        tituloMonografiaEt.setText("");
        orientadorEt.setText("");
    }

    private void saveForm(){
        boolean checkboxEmail = emailCb.isChecked();
        String moreEmails = checkboxEmail ? "Sim" : "Não";
        getTypeOfPhone();
        StringBuilder message = new StringBuilder();
        message.append("Nome: ").append(nomeEt.getText()).append("\n");
        message.append("E-mail: ").append(emailEt.getText()).append("\n");
        message.append("Deseja receber mais E-mails: ").append(moreEmails).append("\n");
        message.append("Telefone ").append(typeOfPhone).append(": ").append(phone).append("\n");
        if(addTelefoneCb.isChecked())
            message.append("Celular: ").append(telefoneCelularEt.getText()).append("\n");
        getGender();
        message.append("Sexo: ").append(gender).append("\n");
        message.append("Data de nascimento: ").append(dataNascimentoEt.getText()).append("\n");
        String messageSpinner = getMessageOfSpinner();
        message.append(messageSpinner).append("\n");
        message.append("Vaga de interesse: ").append(vagaInteresseEt.getText()).append("\n");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void bindViews(){
        nomeEt = findViewById(R.id.nomeEt);
        emailEt = findViewById(R.id.emailEt);
        telefoneEt = findViewById(R.id.telefoneEt);
        telefoneCelularEt = findViewById(R.id.telefoneCelularEt);
        dataNascimentoEt = findViewById(R.id.dataNascimentoEt);
        anoFormaturaEt = findViewById(R.id.anoFormaturaEt);
        anoConclusaoEt = findViewById(R.id.anoConclusãoEt);
        instituicaoEt = findViewById(R.id.instituicaoEt);
        tituloMonografiaEt = findViewById(R.id.tituloMonografiaEt);
        orientadorEt = findViewById(R.id.orientadorEt);
        vagaInteresseEt = findViewById(R.id.vagaInteresseEt);
        emailCb = findViewById(R.id.emailCb);
        addTelefoneCb = findViewById(R.id.addTelefoneCb);
        tipoTelefoneLl = findViewById(R.id.tipoTelefoneLl);
        telefoneCelularLl = findViewById(R.id.telefoneCelularLl);
        tipoTelefoneRg = findViewById(R.id.tipoTelefoneRg);
        sexoRg = findViewById(R.id.sexoRg);
        telefoneResidencialRb = findViewById(R.id.telefoneResidencialRb);
        telefoneComercialRb = findViewById(R.id.telefoneComercialRb);
        masculinoRb = findViewById(R.id.masculinoRb);
        femininoRb = findViewById(R.id.femininoRb);
        formacaoSp = findViewById(R.id.formacaoSp);
        salvarBt = findViewById(R.id.salvarBt);
        limparBt = findViewById(R.id.limparBt);
    }
}