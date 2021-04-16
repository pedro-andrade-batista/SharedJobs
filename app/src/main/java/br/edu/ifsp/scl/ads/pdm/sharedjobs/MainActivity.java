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

import br.edu.ifsp.scl.ads.pdm.sharedjobs.databinding.ActivityMainBinding;

import static br.edu.ifsp.scl.ads.pdm.sharedjobs.R.id.femininoRb;
import static br.edu.ifsp.scl.ads.pdm.sharedjobs.R.id.masculinoRb;

public class MainActivity extends AppCompatActivity {


    private String phone;
    private String gender;
    private String typeOfPhone;


    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        //bindViews();

        activityMainBinding.telefoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s == null || s.length() == 0)
                    makeVisibleTypeOfPhone(View.GONE);
                else{
                    makeVisibleTypeOfPhone(View.VISIBLE);
                    phone = activityMainBinding.telefoneCelularEt.getText().toString() + s;
                }
                    
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        activityMainBinding.formacaoSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        activityMainBinding.orientadorEt.setVisibility(View.VISIBLE);
        activityMainBinding.tituloMonografiaEt.setVisibility(View.VISIBLE);
    }

    private void showFieldsForGraduacaoOrEspecializacao() {
        cleanAllFieldsOfSpinner();
        activityMainBinding.anoFormaturaEt.setVisibility(View.GONE);
        activityMainBinding.anoConclusaoEt.setVisibility(View.VISIBLE);
        activityMainBinding.instituicaoEt.setVisibility(View.VISIBLE);
    }
    private void showFieldsForMedioOrFundamental() {
        cleanAllFieldsOfSpinner();
        activityMainBinding.anoFormaturaEt.setVisibility(View.VISIBLE);
        activityMainBinding.anoConclusaoEt.setVisibility(View.GONE);
        activityMainBinding.instituicaoEt.setVisibility(View.GONE);
        activityMainBinding.tituloMonografiaEt.setVisibility(View.GONE);
        activityMainBinding.orientadorEt.setVisibility(View.GONE);
    }

    private void makeVisibleTypeOfPhone(int visible) {
        activityMainBinding.tipoTelefoneLl.setVisibility(visible);
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
        activityMainBinding.nomeEt.setText("");
        activityMainBinding.emailEt.setText("");
        activityMainBinding.emailCb.setChecked(false);
        activityMainBinding.telefoneEt.setText("");
        activityMainBinding.addTelefoneCb.setChecked(false);
        activityMainBinding.telefoneCelularLl.setVisibility(View.GONE);
        activityMainBinding.telefoneCelularEt.setText("");
        activityMainBinding.masculinoRb.setChecked(true);
        activityMainBinding.dataNascimentoEt.setText("");
        cleanAllFieldsOfSpinner();
        activityMainBinding.vagaInteresseEt.setText("");

    }

    public void isCheckedAddCellPhone(View view){
        if(activityMainBinding.addTelefoneCb.isChecked())
            activityMainBinding.telefoneCelularLl.setVisibility(View.VISIBLE);
        else{
            activityMainBinding.telefoneCelularLl.setVisibility(View.GONE);
            activityMainBinding.telefoneCelularEt.setText("");
        }
    }

    private void getGender(){
       switch(activityMainBinding.sexoRg.getCheckedRadioButtonId()){
           case masculinoRb:
                gender = "Masculino";
                break;
           case femininoRb:
               gender = "Feminino";
               break;
       }
    }

    private void getTypeOfPhone(){
        switch (activityMainBinding.tipoTelefoneRg.getCheckedRadioButtonId()){
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
        if(!(activityMainBinding.anoFormaturaEt.getText().toString().equals("")))
            messageSpinner.append("Ano da formatura: ").append(activityMainBinding.anoFormaturaEt.getText()).append("\n");
        if(!(activityMainBinding.anoConclusaoEt.getText().toString().equals("")))
            messageSpinner.append("Ano da conclusao: ").append(activityMainBinding.anoConclusaoEt.getText()).append("\n");
        if(!(activityMainBinding.instituicaoEt.getText().toString().equals("")))
            messageSpinner.append("Instituição: ").append(activityMainBinding.instituicaoEt.getText()).append("\n");
        if(!(activityMainBinding.tituloMonografiaEt.getText().toString().equals("")))
            messageSpinner.append("Titulo da monografia: ").append(activityMainBinding.tituloMonografiaEt.getText()).append("\n");
        if(!(activityMainBinding.orientadorEt.getText().toString().equals("")))
            messageSpinner.append("Orientador: ").append(activityMainBinding.orientadorEt.getText()).append("\n");

        return String.valueOf(messageSpinner);
    }

    private void cleanAllFieldsOfSpinner(){
        activityMainBinding.anoFormaturaEt.setText("");
        activityMainBinding.anoConclusaoEt.setText("");
        activityMainBinding.instituicaoEt.setText("");
        activityMainBinding.tituloMonografiaEt.setText("");
        activityMainBinding.orientadorEt.setText("");
    }

    private void saveForm(){
        boolean checkboxEmail = activityMainBinding.emailCb.isChecked();
        String moreEmails = checkboxEmail ? "Sim" : "Não";
        getTypeOfPhone();
        StringBuilder message = new StringBuilder();
        message.append("Nome: ").append(activityMainBinding.nomeEt.getText()).append("\n");
        message.append("E-mail: ").append(activityMainBinding.emailEt.getText()).append("\n");
        message.append("Deseja receber mais E-mails: ").append(moreEmails).append("\n");
        message.append("Telefone ").append(typeOfPhone).append(": ").append(phone).append("\n");
        if(activityMainBinding.addTelefoneCb.isChecked())
            message.append("Celular: ").append(activityMainBinding.telefoneCelularEt.getText()).append("\n");
        getGender();
        message.append("Sexo: ").append(gender).append("\n");
        message.append("Data de nascimento: ").append(activityMainBinding.dataNascimentoEt.getText()).append("\n");
        String messageSpinner = getMessageOfSpinner();
        message.append(messageSpinner).append("\n");
        message.append("Vaga de interesse: ").append(activityMainBinding.vagaInteresseEt.getText()).append("\n");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}