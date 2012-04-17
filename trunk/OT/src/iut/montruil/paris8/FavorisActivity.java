package iut.montruil.paris8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class FavorisActivity extends Activity{

	
	Button retour, supprimer, ajouter = null;
	EditText newFavoris;
	String snewFavoris, reponse, id;
	Spinner spinner;
	ArrayAdapter<CharSequence> adapter;
	Intent thisIntent;
	
	
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
			 
		setContentView(R.layout.favoris);
		
		ajouter = (Button)findViewById(R.id.buttonAdd);
		supprimer = (Button)findViewById(R.id.buttonSupprimer);
		retour = (Button)findViewById(R.id.buttonRetourF);
		
		ajouter.setOnClickListener(ButtonAJOUTER);
		supprimer.setOnClickListener(ButtonSUPPRIMER);
        retour.setOnClickListener(ButtonRETOUR);
        
        spinner = (Spinner) findViewById(R.id.spinner2);
        
        thisIntent = getIntent();
		id = thisIntent.getExtras().getString("Identifiant");
        
        //TODO recupérer les trajet des favoris est ensuit mettre la liste dans la ligne du dessou "R.array.listPa_array"
        adapter = ArrayAdapter.createFromResource(this, R.array.listPa_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	          reponse = parent.getItemAtPosition(pos).toString();
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}
	
	OnClickListener ButtonAJOUTER = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == ajouter) {
			 
			
			newFavoris = (EditText) findViewById(R.id.FavorisPath);
			
			snewFavoris = newFavoris.getText().toString();
			
			if (snewFavoris.equals("")){
				 Toast.makeText(getBaseContext(),"Veuillez remplir le champs",Toast.LENGTH_SHORT).show();
				 return;
			}
		}
		
		//TODO ajouter le trajet dans la base de données 
		
		return;
		
 	}
 	};
 	
	OnClickListener ButtonSUPPRIMER = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == supprimer) {
			 
		return;
		//TODO supprimer le trajet dans la base de données 
		
		/*
		Intent changementDePage = new Intent(FavorisActivity.this,ProfilActivity.class);
			 
			startActivity(changementDePage);
		*/
		}
	}
 	 
 	};
 	
	OnClickListener ButtonRETOUR = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == retour) {
			 
		
		Intent monIntent = new Intent(FavorisActivity.this,ProfilActivity.class);
		monIntent.putExtra("Identifiant", id);
			startActivity(monIntent);
		
		}
	}
 	 
 	};
}
