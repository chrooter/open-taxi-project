package iut.montruil.paris8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ChangeAvatarActivity extends Activity{
	Button valider, retour = null;
	String reponse =null,id;
	Intent thisIntent;
	ImageView img;

	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
			 
		setContentView(R.layout.change_avatar);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner3);
		
		
		valider = (Button)findViewById(R.id.buttonValiderCA);
		retour = (Button)findViewById(R.id.buttonRetourCA);
		
		valider.setOnClickListener(ButtonVALIDER);
        retour.setOnClickListener(ButtonRETOUR);
        
        img = (ImageView) findViewById(R.id.imageprofil);
		img.setImageResource(R.drawable.imageprofil);

        thisIntent = getIntent();
		id = thisIntent.getExtras().getString("Identifiant");
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.listPro, android.R.layout.simple_spinner_item);
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
	
	
	OnClickListener ButtonVALIDER = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == valider) {
			

			if (reponse == null){
				 Toast.makeText(getBaseContext(),"Vous n'avez pas sélectionner l'image",Toast.LENGTH_SHORT).show();
				 return;
			}
			
			// Create a new HttpClient and Post Header    
						HttpClient httpclient = new DefaultHttpClient();    
						HttpPost httppost = new HttpPost("http://webperso.iut.univ-paris8.fr/~odasilva/opentaxi/BDConnect.php?choix=upAvatar");
						
						try {        
							// Add your data        
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
							nameValuePairs.add(new BasicNameValuePair("idParticipantAv", "1"));				
							if(reponse.equals("Blanc"))
								nameValuePairs.add(new BasicNameValuePair("Avatar", "1"));
							else
							if(reponse.equals("Bleu"))
								nameValuePairs.add(new BasicNameValuePair("Avatar", "2"));
							else
							if(reponse.equals("Rouge"))
								nameValuePairs.add(new BasicNameValuePair("Avatar", "3"));
							else
							if(reponse.equals("Vert"))
								nameValuePairs.add(new BasicNameValuePair("Avatar", "4"));
							else
								nameValuePairs.add(new BasicNameValuePair("Avatar", "5"));
							
							
							httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));        
							// Execute HTTP Post Request        
							HttpResponse response = httpclient.execute(httppost);  
							BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
							String line = ""; 
							String text = "";
							while ((line = reader.readLine()) != null){
								text += line;
							}
							 Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
							 //System.out.println(text);
							 
							 if(text.trim().equals("Changement d'avatar effectuee")){
								 
									if(reponse.equals("Blanc"))
										img.setImageResource(R.drawable.profilwhite);
									else
									if(reponse.equals("Bleu"))
										img.setImageResource(R.drawable.profilblue);
									else
									if(reponse.equals("Rouge"))
										img.setImageResource(R.drawable.profilred);
									else
									if(reponse.equals("Vert"))
										img.setImageResource(R.drawable.profilgreen);
									else
										img.setImageResource(R.drawable.imageprofil);
								
							 }				 
						} catch (ClientProtocolException e) {        
							Toast.makeText(getBaseContext(), "client protocol exception", Toast.LENGTH_SHORT).show();
						} catch (IOException e) {   
							Toast.makeText(getBaseContext(), "IOException", Toast.LENGTH_SHORT).show();  
						}		
					}
			
			
		/*	image = Uri.fromFile(fichier);
			Intent uploadIntent = new Intent();
			uploadIntent.setClassName("iut.montruil.paris8", "iut.montruil.paris8.HttpUploader");
			uploadIntent.setData(image);
			startService(uploadIntent);
			/*
			Intent changementDePage = new Intent(InscriptionActivity.this,OTActivity.class);
			 
			startActivity(changementDePage);
		*/
 	}
 	};
 	
	OnClickListener ButtonRETOUR = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == retour) {
			
			
			Intent monIntent = new Intent(ChangeAvatarActivity.this,ProfilActivity.class);
			 monIntent.putExtra("Identifiant", id);
			startActivity(monIntent);
		
		}
	}
 	 
 	};
 	
}
