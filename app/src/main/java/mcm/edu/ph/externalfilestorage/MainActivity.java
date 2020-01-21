package mcm.edu.ph.externalfilestorage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.os.Environment;

public class MainActivity extends AppCompatActivity {

    final EditText textbox = findViewById(R.id.textbox);
    final Button buttonRead = findViewById(R.id.buttonread);
    final Button buttonWrite = findViewById(R.id.buttonwrite);
    final Button buttonClear = findViewById(R.id.buttonclear);

    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRead.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            FileInputStream fileIn = openFileInput("mytextfile.txt");
                            InputStreamReader InputRead = new InputStreamReader(fileIn);

                            char[] inputBuffer = new char[READ_BLOCK_SIZE];
                            String s = "";
                            int charRead;

                            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                                // char to string conversion
                                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                                s += readstring;
                            }
                            InputRead.close();
                            textbox.setText(s);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        buttonWrite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            FileOutputStream fileout = openFileOutput("mytextfile.txt", MODE_PRIVATE);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                            outputWriter.write(textbox.getText().toString());
                            outputWriter.close();

                            //display file saved message
                            Toast.makeText(getBaseContext(), "File saved successfully!",
                                    Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        buttonClear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textbox.setText("");

                    }
                }
        );

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            buttonWrite.setEnabled(false);
        }

    }

    public static void isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


}

