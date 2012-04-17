package iut.montruil.paris8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilActivity extends Activity{

	Button modifier, avatar, favoris, retour = null;
	Intent monIntent, thisIntent ;
	String id;
	TextView viewN, viewP, viewC, viewl, viewMdp;
	
	public void onCreate(Bundle savedInstanceState) {
			 
		super.onCreate(savedInstanceState);
			 
		setContentView(R.layout.profil);
			 
		thisIntent = getIntent();
		id = thisIntent.getExtras().getString("Identifiant");
		
		HttpClient httpclient = new DefaultHttpClient();    
		HttpPost httppost = new HttpPost("http://webperso.iut.univ-paris8.fr/~odasilva/opentaxi/BDConnect.php?choix=showProfil");
		
		
		
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1); 
			nameValuePairs.add(new BasicNameValuePair("idParticipant",id));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 
			// Execute HTTP Post Request        
			HttpResponse response;
			response = httpclient.execute(httppost);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = ""; 
			String text = "";
			while ((line = reader.readLine()) != null){
				text += line;
			}
			
			
			viewN = (TextView) findViewById(R.id.name);
			viewN.setText("Nom : "+text.split(",")[0].trim());
			
			viewP = (TextView) findViewById(R.id.firstname);
			viewP.setText("Prenom : "+text.split(",")[1].trim());
		
			
			viewC = (TextView) findViewById(R.id.mailP);
			viewC.setText("Mail : "+text.split(",")[2].trim());

			viewl = (TextView) findViewById(R.id.loginP);
			viewl.setText("Login : "+text.split(",")[3].trim());

			viewMdp = (TextView) findViewById(R.id.passP);
			viewMdp.setText(text.split(",")[4].trim());

			
			
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
			
			
	
		modifier = (Button)findViewById(R.id.buttonModifier);
		avatar = (Button)findViewById(R.id.buttonAvatar);
		favoris = (Button)findViewById(R.id.buttonFavoris);
		retour = (Button)findViewById(R.id.buttonRetourPro);
		
		modifier.setOnClickListener(ButtonMODIFIER);
        avatar.setOnClickListener(ButtonAVATAR);
        favoris.setOnClickListener(ButtonFAVORIS);
        retour.setOnClickListener(ButtonRETURN);
        
     
	}
	
	
	OnClickListener ButtonMODIFIER = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == modifier) {
			 
			
		Intent monIntent = new Intent(ProfilActivity.this,UpProfilActivity.class);
		 monIntent.putExtra("Identifiant", id);
		startActivity(monIntent);
		
		}
	}
 	 
 	};
 	
	OnClickListener ButtonAVATAR = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == avatar) {
			 
			
			Intent monIntent = new Intent(ProfilActivity.this,ChangeAvatarActivity.class);
			 monIntent.putExtra("Identifiant", id);
			startActivity(monIntent);
		
		}
	}
 	 
 	};
 	
	OnClickListener ButtonFAVORIS = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == favoris) {
		
		Intent monIntent = new Intent(ProfilActivity.this,FavorisActivity.class);
		monIntent.putExtra("Identifiant", id);
		startActivity(monIntent);
		
		}
	}
 	 
 	};
 	
	OnClickListener ButtonRETURN = new OnClickListener()
 	{
 	public void onClick(View v) {
		
		if(v == retour) {
			 
	
			Intent monIntent = new Intent(ProfilActivity.this,choiceActivity.class);
			monIntent.putExtra("Identifiant", id);
			startActivity(monIntent);
		
		}
	}
 	 
 	};
}
