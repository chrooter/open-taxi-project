package iut.montruil.paris8;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpProfilActivity extends Activity{

	Button valider, retour = null;
	EditText newChamps;
	String snewChamps, reponse, id;
	Spinner spinner;
	ArrayAdapter<CharSequence> adapter;
	Intent thisIntent;
	
	
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
			 
		setContentView(R.layout.profil_up);
		
		valider = (Button)findViewById(R.id.buttonValider);
		retour = (Button)findViewById(R.id.buttonRetourUpPro);
		
		valider.setOnClickListener(ButtonVALIDER);
        retour.setOnClickListener(ButtonRETOUR);
        
        spinner = (Spinner) findViewById(R.id.spinner1);
        adapter = ArrayAdapter.createFromResource(this, R.array.newCh_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

        
        thisIntent = getIntent();
		id = thisIntent.getExtras().getString("Identifiant");
	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	          reponse = parent.getItemAtPosition(pos).toString();
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
	
	OnClickListener ButtonVALIDER = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == valider) {
			 
			
			newChamps = (EditText) findViewById(R.id.newModi);
			
			snewChamps = newChamps.getText().toString();
			
			if (snewChamps.equals("")){
				 Toast.makeText(getBaseContext(),"Veuillez remplir le champs",Toast.LENGTH_SHORT).show();
				 return;
			}
			
			
			if(reponse.equals("Mail")){
				Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
				Matcher m = p.matcher(snewChamps);
				if (m.matches() == false){
					Toast.makeText(getBaseContext(),"Le champs email ne correspond pas au format d'une adresse mail",Toast.LENGTH_SHORT).show();
					return;
				}
			}
			
			//TODO Lorsqu'un login deja utilisé
			if (false){
				Toast.makeText(getBaseContext(),"Le login est déjà utilisé",Toast.LENGTH_SHORT).show();
			return;
			}
			
			
			if(reponse.equals("Mot de passe")){
				if(snewChamps.length() < 6){
					Toast.makeText(getBaseContext(),"Le mot de passe est trop court (au moins 6 caractères)",Toast.LENGTH_SHORT).show();
					return;
				}
			}
			
			return;
		/*	Intent changementDePage = new Intent(InscriptionActivity.this,OTActivity.class);
			 
			startActivity(changementDePage);
		*/
		}
	}
 	 
 	};
 	
	OnClickListener ButtonRETOUR = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == retour) {
			 
		
		Intent monIntent = new Intent(UpProfilActivity.this,ProfilActivity.class);
		monIntent.putExtra("Identifiant", id);
		startActivity(monIntent);
		
		}
	}
 	 
 	};
}
