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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class OTActivity extends Activity{
	
	 Button inscription, mdpOublie, connexion;
	 LinearLayout layout;
	 EditText login, pswd;
	 String slogin, spswd;
	 Intent changementDePage;
	 
	 	@Override
		public void onCreate(Bundle savedInstanceState) {
	 		
	        super.onCreate(savedInstanceState);      
	        setContentView(R.layout.main);
	        changementDePage = new Intent(OTActivity.this, choiceActivity.class);  
	        inscription = (Button)findViewById(R.id.buttonInsc);
	        inscription.setOnClickListener(ButtonInscri); 	
	        mdpOublie = (Button)findViewById(R.id.buttonMdpOublie);
	        mdpOublie.setOnClickListener(ButtonMdpForg);  
	        connexion = (Button)findViewById(R.id.buttonConnexion);
	        connexion.setOnClickListener(ButtonCo); 
		}

	 OnClickListener ButtonInscri = new OnClickListener(){
	 		
	 	public void onClick(View v) {
	 
				Intent monIntent = new Intent(OTActivity.this,InscriptionActivity.class); 
				startActivity(monIntent);
		}	 
	};
	
	OnClickListener ButtonMdpForg = new OnClickListener(){
		
		public void onClick(View v) {	
			 
			Intent monIntent = new Intent(OTActivity.this, MdpForgetActivity.class);			 
			startActivity(monIntent);		
		}	 
	};

	OnClickListener ButtonCo = new OnClickListener(){
	
		public void onClick(View v) {	

			login = (EditText) findViewById(R.id.login);
			pswd = (EditText) findViewById(R.id.mdp);	
			slogin = login.getText().toString();
			spswd = pswd.getText().toString();
			
			if (slogin.trim().equals("") || spswd.trim().equals("")){
				 Toast.makeText(getBaseContext(),"Veuillez remplir les champs Login et Mot de passe",Toast.LENGTH_SHORT).show();
			}
			else{
				// Create a new HttpClient and Post Header    
				HttpClient httpclient = new DefaultHttpClient();    
				HttpPost httppost = new HttpPost("http://webperso.iut.univ-paris8.fr/~cfarot/opentaxi/BDConnect.php?choix=connexion");
				try {        
					// Add your data        
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();        
					nameValuePairs.add(new BasicNameValuePair("login", slogin));        
					nameValuePairs.add(new BasicNameValuePair("mdp", spswd));        
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));        
					// Execute HTTP Post Request        
					HttpResponse response = httpclient.execute(httppost);  
					BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String line = ""; 
					String text = "";
					while ((line = reader.readLine()) != null){
						text += line;
					}
					Toast.makeText(getBaseContext(), text.split(",")[0], Toast.LENGTH_SHORT).show();	 
					 if(text.split(",")[0].trim().equals("Authentification Correcte !")){ 
						 String id = text.split(",")[1].trim();
						 changementDePage.putExtra("Identifiant", id);
						 startActivity(changementDePage);				
					 }
				} catch (ClientProtocolException e) {        
					Toast.makeText(getBaseContext(), "client protocol exception", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {   
					Toast.makeText(getBaseContext(), "IOException", Toast.LENGTH_SHORT).show();  
				}
			}
		}
	};
}